package ch.bzs_surselva.schoolplanner;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.helpers.StringHelper;

public class PasswordActivity extends AppCompatActivity
{
    private PasswordRetrieveTask passwordRetrieveTask;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_password);

        this.editTextEmail = (EditText)this.findViewById(R.id.editTextEmail);
        Button buttonRetrieve = (Button)this.findViewById(R.id.buttonRetrieve);
        buttonRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrievePassword();
            }
        });
    }

    private void retrievePassword()
    {
        String email = this.editTextEmail.getText().toString();
        email = StringHelper.nullToEmpty(email);
        if (StringHelper.isEmailValid(email))
        {
            this.passwordRetrieveTask = new PasswordRetrieveTask(email);
            this.passwordRetrieveTask.execute((Void) null);
        }
        else
        {
            this.editTextEmail.setError(this.getString(R.string.email_not_valid));
        }
    }

    public class PasswordRetrieveTask extends AsyncTask<Void, Void, Boolean>
    {
        private final String email;

        public PasswordRetrieveTask(String email)
        {
            this.email = email;
        }

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                URL url = new URL("https://bzssurselva.azurewebsites.net/appservice.svc/RetrieveCredentials");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                OutputStream stream = connection.getOutputStream();
                DataOutputStream wr = new DataOutputStream(stream);
                JSONObject json = new JSONObject();
                json.put("Email", this.email);
                wr.writeBytes(json.toString());
                wr.flush();
                wr.close();
                int status = connection.getResponseCode();
                if (status == 200 || status == 201)
                {
                    return true;
                }
            }
            catch (JSONException e)
            {
                return false;
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
            passwordRetrieveTask = null;
            finish();
        }

        @Override
        protected void onCancelled()
        {
            passwordRetrieveTask = null;
            finish();
        }
    }
}
