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
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.Spinner;

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
        import java.util.Date;
        import java.util.UUID;

        import javax.net.ssl.HttpsURLConnection;

        import ch.bzs_surselva.schoolplanner.dto.DayLookupDto;
        import ch.bzs_surselva.schoolplanner.dto.LessonDisplayDto;
        import ch.bzs_surselva.schoolplanner.dto.LessonDto;
        import ch.bzs_surselva.schoolplanner.dto.ResultDto;
        import ch.bzs_surselva.schoolplanner.dto.RoomLookupDto;
        import ch.bzs_surselva.schoolplanner.dto.SubjectLookupDto;
        import ch.bzs_surselva.schoolplanner.dto.TeacherLookupDto;
        import ch.bzs_surselva.schoolplanner.helpers.RequestHelper;

public class LessonActivity extends AppCompatActivity {
    private LoadDayLookupTask loadDayLookupTask;
    private LoadSubjectLookupTask loadSubjectLookupTask;
    private LoadTeacherLookupTask loadTeacherLookupTask;
    private LoadRoomLookupTask loadRoomLookupTask;
    private Spinner spinnerDay;
    private Spinner spinnerSubject;
    private Spinner spinnerTeacher;
    private Spinner spinnerRoom;
    private EditText editTextRemark;
    private SaveTask saveTask;
    private LessonDto model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lesson);

        this.spinnerDay = (Spinner) this.findViewById(R.id.spinnerDay);
        this.spinnerSubject = (Spinner) this.findViewById(R.id.spinnerSubject);
        this.spinnerTeacher = (Spinner) this.findViewById(R.id.spinnerTeacher);
        this.spinnerRoom = (Spinner) this.findViewById(R.id.spinnerRoom);

        this.loadDayLookupTask = new LoadDayLookupTask();
        this.loadDayLookupTask.execute((Void) null);

        this.loadSubjectLookupTask = new LoadSubjectLookupTask();
        this.loadSubjectLookupTask.execute((Void) null);

        this.loadTeacherLookupTask = new LoadTeacherLookupTask();
        this.loadTeacherLookupTask.execute((Void) null);

        this.loadRoomLookupTask = new LoadRoomLookupTask();
        this.loadRoomLookupTask.execute((Void) null);

        Intent intent = this.getIntent();
        if (intent.hasExtra("Id") /*&& intent.hasExtra("DayOfWeek") && intent.hasExtra("From")&& intent.hasExtra("To")*/&& intent.hasExtra("Subject")&& intent.hasExtra("Teacher")&& intent.hasExtra("Room")&& intent.hasExtra("Remark")) //Caption & Code noch übergeben
        {
            String id = intent.getStringExtra("Id");
           // String dayOfWeek = intent.getStringExtra("DayOfWeek");
            //String from = intent.getStringExtra("From");
           // String to = intent.getStringExtra("To");
            String subject = intent.getStringExtra("Subject");
            String teacher = intent.getStringExtra("Teacher");
            String room = intent.getStringExtra("Room");
            String remark = intent.getStringExtra("Remark");
            UUID uid = UUID.fromString(id);
            LessonDto m = new LessonDto(uid, subject, teacher, room, remark);
            didLoadModel(m);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_lesson, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            return true;
        }

        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void didLoadDayLookup(ArrayList<DayLookupDto> dayLookupData) {
       /* Collections.sort(dayLookupData, new Comparator<DayLookupDto>() {
            @Override
            public int compare(DayLookupDto lhs, DayLookupDto rhs) {
                return lhs.getOrder() - rhs.getOrder();
            }
        });*/

        ArrayAdapter<DayLookupDto> spinnerDayArrayAdapter = new ArrayAdapter<DayLookupDto>(this, android.R.layout.simple_spinner_item, dayLookupData);
        spinnerDayArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerDay.setAdapter(spinnerDayArrayAdapter);
    }

    private void didLoadSubjectLookup(ArrayList<SubjectLookupDto> subjectLookupData) {
        Collections.sort(subjectLookupData, new Comparator<SubjectLookupDto>() {
            @Override
            public int compare(SubjectLookupDto lhs, SubjectLookupDto rhs) {
                return lhs.getCode().compareTo(rhs.getCode());
            }
        });

        ArrayAdapter<SubjectLookupDto> spinnerSubjectArrayAdapter = new ArrayAdapter<SubjectLookupDto>(this, android.R.layout.simple_spinner_item, subjectLookupData);
        spinnerSubjectArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerSubject.setAdapter(spinnerSubjectArrayAdapter);
    }

    private void didLoadTeacherLookup(ArrayList<TeacherLookupDto> teacherItems) {

        Collections.sort(teacherItems, new Comparator<TeacherLookupDto>() {
            @Override
            public int compare(TeacherLookupDto lhs, TeacherLookupDto rhs) {
                return lhs.getCode().compareTo(rhs.getCode());
            }
        });

        ArrayAdapter<TeacherLookupDto> spinnerTeacherArrayAdapter = new ArrayAdapter<TeacherLookupDto>(this, android.R.layout.simple_spinner_item, teacherItems);
        spinnerTeacherArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerTeacher.setAdapter(spinnerTeacherArrayAdapter);
    }

    private void didLoadRoomLookup(ArrayList<RoomLookupDto> RoomItems) {

        Collections.sort(RoomItems, new Comparator<RoomLookupDto>() {
            @Override
            public int compare(RoomLookupDto lhs, RoomLookupDto rhs) {
                return lhs.getCode().compareTo(rhs.getCode());
            }
        });

        ArrayAdapter<RoomLookupDto> spinnerRoomArrayAdapter = new ArrayAdapter<RoomLookupDto>(this, android.R.layout.simple_spinner_item, RoomItems);
        spinnerRoomArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerRoom.setAdapter(spinnerRoomArrayAdapter);
    }

    public class LoadDayLookupTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog dialog;
        private String content;

        public LoadDayLookupTask() {
            this.dialog = new ProgressDialog(LessonActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpsURLConnection connection = RequestHelper.createRequest("GetDayLookup", "GET");
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
        protected void onPostExecute(final Boolean success) {
            loadDayLookupTask = null;
            this.dialog.dismiss();

            if (success) {
                ArrayList<DayLookupDto> dayItems = new ArrayList<DayLookupDto>();
                try {
                    JSONArray jsonArray = new JSONArray(this.content);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        dayItems.add(new DayLookupDto(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                }

                didLoadDayLookup(dayItems);
            }
        }

        @Override
        protected void onCancelled() {
            loadDayLookupTask = null;
            this.dialog.dismiss();
        }
    }

    public class LoadSubjectLookupTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog dialog;
        private String content;

        public LoadSubjectLookupTask() {
            this.dialog = new ProgressDialog(LessonActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpsURLConnection connection = RequestHelper.createRequest("GetSubjectLookup", "GET");
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
        protected void onPostExecute(final Boolean success) {
            loadSubjectLookupTask = null;
            this.dialog.dismiss();

            if (success) {
                ArrayList<SubjectLookupDto> subjectItems = new ArrayList<SubjectLookupDto>();
                try {
                    JSONArray jsonArray = new JSONArray(this.content);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        subjectItems.add(new SubjectLookupDto(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                }

                didLoadSubjectLookup(subjectItems);
            }
        }

        @Override
        protected void onCancelled() {
            Object loadTeacherLookupTask = null;
            this.dialog.dismiss();
        }
    }

    public class LoadTeacherLookupTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog dialog;
        private String content;

        public LoadTeacherLookupTask() {
            this.dialog = new ProgressDialog(LessonActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpsURLConnection connection = RequestHelper.createRequest("GetTeacherLookup", "GET");
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
        protected void onPostExecute(final Boolean success) {
            Object loadTeacherLookupTask = null;
            this.dialog.dismiss();

            if (success) {
                ArrayList<TeacherLookupDto> teacherItems = new ArrayList<TeacherLookupDto>();
                try {
                    JSONArray jsonArray = new JSONArray(this.content);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        teacherItems.add(new TeacherLookupDto(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                }

                didLoadTeacherLookup(teacherItems);
            }
        }

        @Override
        protected void onCancelled() {
            Object loadRoomLookupTask = null;
            this.dialog.dismiss();
        }

    }

    public class LoadRoomLookupTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog dialog;
        private String content;

        public LoadRoomLookupTask() {
            this.dialog = new ProgressDialog(LessonActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
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
        protected void onPostExecute(final Boolean success) {
            loadRoomLookupTask = null;
            this.dialog.dismiss();

            if (success) {
                ArrayList<RoomLookupDto> roomItems = new ArrayList<RoomLookupDto>();
                try {
                    JSONArray jsonArray = new JSONArray(this.content);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        roomItems.add(new RoomLookupDto(jsonArray.getJSONObject(i)));
                    }
                } catch (JSONException e) {
                }

                didLoadRoomLookup(roomItems);
            }
        }


        @Override
        protected void onCancelled() {
            loadRoomLookupTask = null;
            this.dialog.dismiss();
        }
    }
    private LessonDto applyChanges(LessonDto modelToApply)
    {
        modelToApply.setRemark(this.editTextRemark.getText().toString());
        modelToApply.setRoom(this.spinnerRoom.getTag().toString());
        modelToApply.setSubject(this.spinnerSubject.getTag().toString());
        modelToApply.setTeacher(this.spinnerTeacher.getTag().toString());
        return modelToApply;
    }
    private void saveChanges()
    {
        LessonDto itemToSave;
        if (this.model == null)
        {
            itemToSave = new LessonDto();
        }
        else
        {
            itemToSave = this.model;
        }


        itemToSave = this.applyChanges(itemToSave);
        if (this.model == null)
        {
            this.saveTask = new SaveTask(itemToSave, "I");
        }
        else
        {
            this.saveTask = new SaveTask(itemToSave, "U");
        }

        this.saveTask.execute((Void) null);
    }

    private void didLoadModel(LessonDto loadedItem)
    {
        this.model = loadedItem;
        if (this.model != null)
        {
            this.editTextRemark.setText(this.model.getRemark());
            this.spinnerRoom.setTag(this.model.getRoom());
            this.spinnerSubject.setTag(this.model.getSubject());
            this.spinnerTeacher.setTag(this.model.getTeacher());
        }
    }

    private void displaySaveAlert(String alertMessage)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getString(R.string.error_while_register));
        sb.append("\n");
        sb.append(alertMessage);
        new AlertDialog.Builder(LessonActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(this.getString(R.string.error))
                .setMessage(sb)
                .setPositiveButton(this.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) { dialog.cancel(); }
                        })
                .show();
    }



    public class SaveTask extends AsyncTask<Void, Void, Boolean>
    {
        private final LessonDto itemToSave;
        private final String insertUpdate;
        private ProgressDialog dialog;
        private String content;

        public SaveTask(LessonDto itemToSave, String insertUpdate)
        {
            this.itemToSave = itemToSave;
            if (!insertUpdate.equals("I") && !insertUpdate.equals("U"))
            {
                throw new IllegalArgumentException("insertUpdate is not 'I' or 'U'.");
            }

            this.insertUpdate = insertUpdate;
            this.dialog = new ProgressDialog(LessonActivity.this);
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
            String service = "InsertLesson";
            String method = "PUT";
            if (insertUpdate == "U")
            {
                service = "UpdateLesson";
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
            saveTask = null;
            this.dialog.dismiss();

            if (success)

                try
                {
                    JSONObject json = new JSONObject(this.content);
                    ResultDto result = new ResultDto(json);
                    if (result.getSuccess())
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
