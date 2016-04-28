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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.adapters.LessonOverviewAdapter;
import ch.bzs_surselva.schoolplanner.dto.DayLookupDto;
import ch.bzs_surselva.schoolplanner.dto.IdDto;
import ch.bzs_surselva.schoolplanner.dto.LessonDisplayDto;
import ch.bzs_surselva.schoolplanner.dto.LessonDto;
import ch.bzs_surselva.schoolplanner.dto.SubjectEditDto;
import ch.bzs_surselva.schoolplanner.dto.TeacherLookupDto;
import ch.bzs_surselva.schoolplanner.helpers.RequestHelper;

public class TimetableActivity extends AppCompatActivity
{
    private LoadTask loadTask;
    private LessonOverviewAdapter adapter;
    private AlertDialog.Builder alertBuilder;
    private UUID deleteId;
    private DeleteTask deleteTask;
    private Calendar datum;
    private TextView textViewDay;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_timetable);
        this.datum = Calendar.getInstance();

        ArrayList<LessonDisplayDto> data = new ArrayList<>();
        this.adapter = new LessonOverviewAdapter(this, data);
        ListView listViewLesson = (ListView) findViewById(R.id.listViewLesson);
        this.textViewDay = (TextView)findViewById(R.id.textViewDay);
        String text = datum.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        this.textViewDay.setText(text);
        listViewLesson.setAdapter(this.adapter);
       /* listViewLesson.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                LessonDisplayDto value = (LessonDisplayDto) adapter.getItemAtPosition(position);
                editLesson(value.getId());
            }
        });*/

        this.alertBuilder = new AlertDialog.Builder(this);
        this.alertBuilder.setMessage(this.getString(R.string.cc_would_you_like_to_delete_the_lesson));
        this.alertBuilder.setPositiveButton(this.getString(R.string.cc_yes), new DialogInterface.OnClickListener()
        {
            @Override
           public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteLesson(deleteId);
                deleteId = null;
            }

            });
            this.alertBuilder.setNegativeButton(this.getString(R.string.cc_no),new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    deleteId = null;
                }
            });

                listViewLesson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
                {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int arg2, long arg3)
                    {
                        LessonDisplayDto item = (LessonDisplayDto) parent.getItemAtPosition(arg2);
                        deleteId = item.getId();
                        AlertDialog dlg = alertBuilder.create();
                        dlg.show();
                        return true;
                    }
                });

            }


            @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.menu_timetable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_new)
        {
           this.createLesson();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnClickLeftButton (View v)
    {
        this.datum.add(Calendar.DAY_OF_WEEK, -1);
        String text = datum.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        this.textViewDay.setText(text);
    }
    public void OnClickRightButton (View v)
    {
        this.datum.add(Calendar.DAY_OF_WEEK, +1);
        String text = datum.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        this.textViewDay.setText(text);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        this.refreshData();
    }

    private void refreshData()
    {
        this.loadTask = new LoadTask();
        this.loadTask.execute((Void) null);
    }

    private void didLoadModel(ArrayList<LessonDisplayDto> loadedData){

        this.adapter.clear();
        this.adapter.addAll(loadedData);
        this.adapter.notifyDataSetChanged();

    }
            private void createLesson()
            {
                Intent intent = new Intent(this, LessonActivity.class);
                this.startActivity(intent);
            }

    private void editLesson(UUID lessonId, String subject)
    {
        Intent intent = new Intent(this, LessonActivity.class);
        intent.putExtra("Id", lessonId.toString());
        intent.putExtra("Subject", subject);
        this.startActivity(intent);
    }
            private void deleteLesson(UUID id)
            {
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
            this.dialog = new ProgressDialog(TimetableActivity.this);
        }

        @Override
        protected void onPreExecute()
        {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                HttpsURLConnection connection = RequestHelper.createRequest("GetLessonDto", "GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();
                int status = connection.getResponseCode();
                if (status == 200 || status == 201)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        sb.append(line).append("\n");
                    }

                    br.close();
                    this.content = sb.toString();
                    return true;
                }
            }
            catch (MalformedURLException e)
            {
                return false;
            }
            catch (NullPointerException e)
            {
                return false;
            }
            catch (IOException e)
            {
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            loadTask = null;
            this.dialog.dismiss();

            ArrayList<LessonDisplayDto> loadedData = new ArrayList<LessonDisplayDto>();
            if (success)
            {

                try
                {
                    JSONArray json = new JSONArray(this.content);
                    for (int i = 0; i < json.length(); i++)
                    {
                        loadedData.add(new LessonDisplayDto(json.getJSONObject(i)));
                    }
                }
                catch (JSONException e)
                {
                }
            }
           /* Collections.sort(loadedData, new Comparator<LessonDisplayDto>() {
                @Override
                public int compare(LessonDisplayDto lhs, LessonDisplayDto rhs) {
                    return lhs.getCaption().compareTo(rhs.getCaption());
                }
            });*/
            didLoadModel(loadedData);
        }

        @Override
        protected void onCancelled()
        {
            loadTask = null;
            this.dialog.dismiss();
        }
    }

            public class DeleteTask extends AsyncTask<Void, Void, Boolean>
            {
                private UUID idToDelete;
                private ProgressDialog dialog;
                private String content;

                public DeleteTask(UUID idToDelete)
                {
                    this.idToDelete = idToDelete;
                    this.dialog = new ProgressDialog(TimetableActivity.this);
                }

                @Override
                protected void onPreExecute()
                {
                    this.dialog.setMessage(getString(R.string.please_wait));
                    this.dialog.show();
                }

                @Override
                protected Boolean doInBackground(Void... params)
                {
                    try
                    {
                        HttpsURLConnection connection = RequestHelper.createRequest("DeleteLesson","DELETE");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setDoOutput(true);
                        OutputStream stream = connection.getOutputStream();
                        DataOutputStream wr = new DataOutputStream(stream);
                        String json = new IdDto( this.idToDelete).toJson().toString();
                        wr.writeBytes(json);
                        wr.flush();
                        wr.close();

                        int status = connection.getResponseCode();
                        if (status == 200 || status == 201)
                        {
                            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = br.readLine()) != null)
                            {
                                sb.append(line).append("\n");
                            }

                            br.close();
                            this.content = sb.toString();
                            return true;
                        }
                    }
                    catch (MalformedURLException e)
                    {
                        return false;
                    }
                    catch (NullPointerException e)
                    {
                        return false;
                    }
                    catch (IOException e)
                    {
                        return false;
                    }

                    return false;
                }

                @Override
                protected void onPostExecute(final Boolean success)
                {
                    deleteTask = null;
                    this.dialog.dismiss();

                    if (success)
                    {
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
                protected void onCancelled()
                {
                    deleteTask = null;
                    this.dialog.dismiss();
                }
            }
        }
