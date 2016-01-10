package ch.bzs_surselva.schoolplanner;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.dto.DayLookupDto;
import ch.bzs_surselva.schoolplanner.dto.SubjectLookupDto;
import ch.bzs_surselva.schoolplanner.helpers.RequestHelper;

public class LessonActivity extends AppCompatActivity
{
    private LoadDayLookupTask loadDayLookupTask;
    private LoadSubjectLookupTask loadSubjectLookupTask;
    private Spinner spinnerDay;
    private Spinner spinnerSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_lesson);

        this.spinnerDay = (Spinner)this.findViewById(R.id.spinnerDay);
        this.spinnerSubject = (Spinner)this.findViewById(R.id.spinnerSubject);

        this.loadDayLookupTask = new LoadDayLookupTask();
        this.loadDayLookupTask.execute((Void) null);

        this.loadSubjectLookupTask = new LoadSubjectLookupTask();
        this.loadSubjectLookupTask.execute((Void) null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.menu_lesson, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_save)
        {
            return true;
        }

        if (id == R.id.action_delete)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void didLoadDayLookup(ArrayList<DayLookupDto> dayLookupData)
    {
        Collections.sort(dayLookupData, new Comparator<DayLookupDto>()
        {
            @Override
            public int compare(DayLookupDto lhs, DayLookupDto rhs)
            {
                return lhs.getOrder() - rhs.getOrder();
            }
        });

        ArrayAdapter<DayLookupDto> spinnerDayArrayAdapter = new ArrayAdapter<DayLookupDto>(this, android.R.layout.simple_spinner_item, dayLookupData);
        spinnerDayArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerDay.setAdapter(spinnerDayArrayAdapter);
    }

    private void didLoadSubjectLookup(ArrayList<SubjectLookupDto> subjectLookupData)
    {
        Collections.sort(subjectLookupData, new Comparator<SubjectLookupDto>()
        {
            @Override
            public int compare(SubjectLookupDto lhs, SubjectLookupDto rhs)
            {
                return lhs.getCode().compareTo(rhs.getCode());
            }
        });

        ArrayAdapter<SubjectLookupDto> spinnerSubjectArrayAdapter = new ArrayAdapter<SubjectLookupDto>(this, android.R.layout.simple_spinner_item, subjectLookupData);
        spinnerSubjectArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerSubject.setAdapter(spinnerSubjectArrayAdapter);
    }

    public class LoadDayLookupTask extends AsyncTask<Void, Void, Boolean>
    {
        private ProgressDialog dialog;
        private String content;

        public LoadDayLookupTask()
        {
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
            try
            {
                HttpsURLConnection connection = RequestHelper.createRequest("GetDayLookup", "GET");
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
            loadDayLookupTask = null;
            this.dialog.dismiss();

            if (success)
            {
                ArrayList<DayLookupDto> dayItems = new ArrayList<DayLookupDto>();
                try
                {
                    JSONArray jsonArray = new JSONArray(this.content);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        dayItems.add(new DayLookupDto(jsonArray.getJSONObject(i)));
                    }
                }
                catch (JSONException e)
                {
                }

                didLoadDayLookup(dayItems);
            }
        }

        @Override
        protected void onCancelled()
        {
            loadDayLookupTask = null;
            this.dialog.dismiss();
        }
    }

    public class LoadSubjectLookupTask extends AsyncTask<Void, Void, Boolean>
    {
        private ProgressDialog dialog;
        private String content;

        public LoadSubjectLookupTask()
        {
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
            try
            {
                HttpsURLConnection connection = RequestHelper.createRequest("GetSubjectLookup", "GET");
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
            loadSubjectLookupTask = null;
            this.dialog.dismiss();

            if (success)
            {
                ArrayList<SubjectLookupDto> subjectItems = new ArrayList<SubjectLookupDto>();
                try
                {
                    JSONArray jsonArray = new JSONArray(this.content);
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        subjectItems.add(new SubjectLookupDto(jsonArray.getJSONObject(i)));
                    }
                }
                catch (JSONException e)
                {
                }

                didLoadSubjectLookup(subjectItems);
            }
        }

        @Override
        protected void onCancelled()
        {
            loadSubjectLookupTask = null;
            this.dialog.dismiss();
        }
    }
}
