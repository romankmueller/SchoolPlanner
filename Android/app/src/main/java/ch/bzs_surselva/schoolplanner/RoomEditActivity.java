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
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.dto.ResultDto;
import ch.bzs_surselva.schoolplanner.dto.RoomEditDto;
import ch.bzs_surselva.schoolplanner.helpers.RequestHelper;

/**
 * Created by conrad on 02.03.2016.
 */
public class RoomEditActivity extends AppCompatActivity
{
    private EditText editTextCode;
    private EditText editTextCaption;
    private LoadTask loadTask;
    private SaveTask saveTask;
    private RoomEditDto model;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_room_edit);

        this.editTextCode = (EditText)this.findViewById(R.id.editTextCode);
        this.editTextCaption = (EditText) this.findViewById(R.id.editTextCaption);

        Intent intent = this.getIntent();
        if (intent.hasExtra("Id"))
        {
            String id = intent.getStringExtra("Id");
            if(id != null)
            {
                this.loadTask = new LoadTask(UUID.fromString(id));
                this.loadTask.execute((Void) null);

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.menu_room_edit, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       int id = item.getItemId();
        if(id == R.id.action_save)
        {
           this.saveChanges();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private RoomEditDto applyChanges(RoomEditDto modelToApply)
    {
        modelToApply.setCode(this.editTextCode.getText().toString());
        modelToApply.setCaption(this.editTextCaption.getText().toString());
        return modelToApply;
    }

    private void saveChanges()
    {
        RoomEditDto itemToSave;
        if (this.model == null)
        {
            itemToSave = new RoomEditDto();
        }
        else
        {
            itemToSave = this.model;
        }

        itemToSave = this.applyChanges(itemToSave);
        if(this.model == null)
        {
            this.saveTask = new SaveTask(itemToSave, "I");
        }
        else
        {
            this.saveTask = new SaveTask(itemToSave, "U");
        }
        this.saveTask.execute((Void) null);
    }

    public void didLoadModel(RoomEditDto loadedItem)
    {
        this.model = loadedItem;
        if(this.model != null)
        {
            this.editTextCode.setText(this.model.getCode());
            this.editTextCaption.setText(this.model.getCaption());
        }
    }

    private void displaySaveAlert(String alertMessage)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getString(R.string.error_while_register));
        sb.append("\n");
        sb.append(alertMessage);
        new AlertDialog.Builder(RoomEditActivity.this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(this.getString(R.string.error))
                .setMessage(sb)
                .setPositiveButton(this.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .show();

    }

    public class LoadTask extends AsyncTask< Void, Void, Boolean>
    {
        private final UUID idToLoad;
        private ProgressDialog dialog;
        private String content;

        public LoadTask(UUID idToLoad)
        {
           this.idToLoad = idToLoad;
            this.dialog = new ProgressDialog(RoomEditActivity.this);
        }

        @Override
        protected void onPreExecute()
        {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void...params)
        {
            try
            {
                HttpsURLConnection connection = RequestHelper.createRequest("GetRoom/"+ this.idToLoad.toString(), "GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.connect();
                int status = connection.getResponseCode();
                        if(status == 200 || status == 201)
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
            catch (MalformedURLException e )
            {
                return false;
            }
            catch (NullPointerException e )
            {
                return false;
            }
            catch (IOException e )
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

            if (success)
            {
                JSONObject json = null;
                try
                {
                    json = new JSONObject(this.content);
                }
                catch (JSONException e)
                {
                }
                if (json != null)
                {
                    didLoadModel(new RoomEditDto(json));
                }
            }
        }
        @Override
        protected void onCancelled()
        {
            loadTask = null;
            this.dialog.dismiss();
        }
    }

    public class SaveTask extends AsyncTask<Void, Void, Boolean>
    {
        private final RoomEditDto itemToSave;
        private final String insertUpdate;
        private ProgressDialog dialog;
        private String content;

        public SaveTask(RoomEditDto itemToSave, String insertUpdate)
        {
            this.itemToSave = itemToSave;
            if(!insertUpdate.equals("I") && !insertUpdate.equals("U"))
            {
                throw new IllegalArgumentException("insertUpdate is not 'I' or 'U'. ");
            }

            this.insertUpdate = insertUpdate;
            this.dialog = new ProgressDialog(RoomEditActivity.this);
        }

        @Override
        protected void onPreExecute()
        {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }
        @Override
        protected Boolean doInBackground(Void...param)
        {
            String service = "InsertRoom";
            String method =  "PUT";
            if(insertUpdate == "U")
            {
                service = "UpdateRoom";
                method = "POST";
            }
            try
            {
                HttpsURLConnection connection = RequestHelper.createRequest(service, method);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                OutputStream stream = connection.getOutputStream();
                DataOutputStream wr = new DataOutputStream(stream);
                wr.writeBytes(this.itemToSave.toJson().toString());
                wr.flush();
                wr.close();

                int status = connection.getResponseCode();

                    if(status == 200 || status == 201)
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
            saveTask = null;
            this.dialog.dismiss();

            if (success)
            try
            {
                JSONObject json = new JSONObject(content);
                ResultDto result = new ResultDto(json);
                if(result.getSuccess())
                {
                    finish();
                }
                else
                {
                    displaySaveAlert(result.getError());
                }
            }

                catch (JSONException e)
                {
                   displaySaveAlert(getString(R.string.unknown_error_occurred));
                }


            else
            {
                displaySaveAlert(getString(R.string.unknown_error_occurred));
            }
        }
        @Override
        protected void onCancelled()
        {
            saveTask = null;
            this.dialog.dismiss();
        }

    }

}