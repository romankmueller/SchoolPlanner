package ch.bzs_surselva.schoolplanner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.adapters.RoomOverviewAdapter;
import ch.bzs_surselva.schoolplanner.dto.IdDto;
import ch.bzs_surselva.schoolplanner.dto.RoomLookupDto;
import ch.bzs_surselva.schoolplanner.dto.SubjectLookupDto;
import ch.bzs_surselva.schoolplanner.helpers.RequestHelper;

public class RoomOverviewActivity extends AppCompatActivity
{
    private LoadTask loadTask;
    private DeleteTask deleteTask;
    private RoomOverviewAdapter adapter;
    private AlertDialog.Builder alertBuilder;
    private UUID deleteId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_overview);

        ArrayList<RoomLookupDto> data = new ArrayList<>();
        this.adapter = new RoomOverviewAdapter(this, data);
        ListView listViewRoom = (ListView) findViewById(R.id.listViewRoom);
        listViewRoom.setAdapter(this.adapter);
        listViewRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                RoomLookupDto value = (RoomLookupDto) adapter.getItemAtPosition(position);
                editRoom(value.getId(), value.getCode(), value.getCaption());
            }
        });
        this.alertBuilder = new AlertDialog.Builder(this);
        this.alertBuilder.setMessage(this.getString(R.string.cc_would_you_like_to_delete_the_room));
        this.alertBuilder.setPositiveButton(this.getString(R.string.cc_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteRoom(deleteId);
                deleteId = null;
            }
        });


        this.alertBuilder.setNegativeButton(this.getString(R.string.cc_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteId = null;
            }
        });

        listViewRoom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int arg2, long arg3) {
                RoomLookupDto item = (RoomLookupDto) parent.getItemAtPosition(arg2);
                deleteId = item.getId();
                AlertDialog dlg = alertBuilder.create();
                dlg.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_room_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {
            this.createRoom();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.refreshData();
    }

    private void refreshData() {
        this.loadTask = new LoadTask();
        this.loadTask.execute((Void) null);
    }

    private void didLoadModel(ArrayList<RoomLookupDto> loadedData) {
        this.adapter.clear();
        this.adapter.addAll(loadedData);
        this.adapter.notifyDataSetChanged();
    }

    private void createRoom() {
        Intent intent = new Intent(this, RoomEditActivity.class);
        this.startActivity(intent);
    }

    private void editRoom(UUID id, String code, String caption) {
        Intent intent = new Intent(this, RoomEditActivity.class);
        intent.putExtra("Id", id.toString());
        intent.putExtra("Code", code);
        intent.putExtra("Caption", caption);
        this.startActivity(intent);
    }

    private void deleteRoom(UUID id) {
        this.deleteTask = new DeleteTask(id);
        this.deleteTask.execute((Void) null);
    }

    private void didDeleteModel()
    {
        this.refreshData();
    }

    public class LoadTask extends AsyncTask<Void, Void, Boolean>
    {
        private ProgressDialog dialog;
        private String content;

        public LoadTask()
        {
            this.dialog = new ProgressDialog(RoomOverviewActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try {
                HttpsURLConnection connection = RequestHelper.createRequest("GetRoomLookup", "GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();
                int status = connection.getResponseCode();
                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }

                    br.close();
                    this.content = sb.toString();
                    return true;
                }
            } catch (MalformedURLException e) {
                return false;
            } catch (NullPointerException e) {
                return false;
            } catch (IOException e) {
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            loadTask = null;
            this.dialog.dismiss();

            ArrayList<RoomLookupDto> loadedData = new ArrayList<>();
            if (success) {

                try {
                    JSONArray json = new JSONArray(this.content);
                    for (int i = 0; i < json.length(); i++) {
                        loadedData.add(new RoomLookupDto(json.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                }
            }
            Collections.sort(loadedData, new Comparator<RoomLookupDto>() {
                @Override
                public int compare(RoomLookupDto p1, RoomLookupDto p2) {
                    return p1.getCaption().compareToIgnoreCase(p2.getCaption());
                }
            });

            didLoadModel(loadedData);
        }

        @Override
        protected void onCancelled() {
            loadTask = null;
            this.dialog.dismiss();
        }
    }

    public class DeleteTask extends AsyncTask<Void, Void, Boolean> {
        private UUID idToDelete;
        private ProgressDialog dialog;
        private String content;

        public DeleteTask(UUID idToDelete) {
            this.idToDelete = idToDelete;
            this.dialog = new ProgressDialog(RoomOverviewActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpsURLConnection connection = RequestHelper.createRequest("DeleteRoom", "DELETE");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                OutputStream stream = connection.getOutputStream();
                DataOutputStream wr = new DataOutputStream(stream);
                String json = new IdDto(this.idToDelete).toJson().toString();
                wr.writeBytes(json);
                wr.flush();
                wr.close();

                int status = connection.getResponseCode();
                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }

                    br.close();
                    this.content = sb.toString();
                    return true;
                }
            } catch (MalformedURLException e) {
                return false;
            } catch (NullPointerException e) {
                return false;
            } catch (IOException e) {
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            deleteTask = null;
            this.dialog.dismiss();

            if (success) {
                try {
                    JSONObject json = new JSONObject(this.content);
                    if (json.getBoolean("Success") == false)
                    {
                        refreshData();
                        //json.getString("Error");
                    }
                }
                catch (JSONException e)
                {
                }
            }

            didDeleteModel();
        }

        @Override
        protected void onCancelled() {
            deleteTask = null;
            this.dialog.dismiss();
        }


    }
}


