package ch.bzs_surselva.schoolplanner;

import android.app.ProgressDialog;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.adapters.LessonOverviewAdapter;
import ch.bzs_surselva.schoolplanner.dto.LessonDisplayDto;
import ch.bzs_surselva.schoolplanner.dto.SubjectEditDto;
import ch.bzs_surselva.schoolplanner.helpers.RequestHelper;

public class TimetableActivity extends AppCompatActivity
{
    private LoadTask loadTask;
    private LessonOverviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_timetable);

        ArrayList<LessonDisplayDto> data = new ArrayList<>();
        this.adapter = new LessonOverviewAdapter(this, data);
        ListView listViewLesson = (ListView) findViewById(R.id.listViewLesson);
        listViewLesson.setAdapter(this.adapter);
        listViewLesson.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                LessonDisplayDto value = (LessonDisplayDto) adapter.getItemAtPosition(position);
                showLesson(value.getId());
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
            Intent intent = new Intent(this, LessonActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.refreshData();
    }

    private void refreshData()
    {
        this.loadTask = new LoadTask();
        this.loadTask.execute((Void) null);
    }

    private void didLoadModel(ArrayList<LessonDisplayDto> loadedData)
    {
        this.adapter.addAll(loadedData);
        this.adapter.notifyDataSetChanged();
    }

    private void showLesson(UUID lessonId)
    {
        Intent intent = new Intent(this, LessonActivity.class);
        intent.putExtra("Id", lessonId.toString());
        startActivity(intent);
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
                HttpsURLConnection connection = RequestHelper.createRequest("GetLessonOfWeek", "GET");
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

            didLoadModel(loadedData);
        }

        @Override
        protected void onCancelled()
        {
            loadTask = null;
            this.dialog.dismiss();
        }
    }
}
