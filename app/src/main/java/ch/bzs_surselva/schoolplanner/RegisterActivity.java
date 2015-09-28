package ch.bzs_surselva.schoolplanner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.dto.RegisterDto;
import ch.bzs_surselva.schoolplanner.dto.ResultDto;
import ch.bzs_surselva.schoolplanner.helpers.StringHelper;

public class RegisterActivity extends AppCompatActivity
{
    private EditText editTextAccount;
    private EditText editTextPassword;
    private EditText editTextPasswordVerify;
    private EditText editTextEmail;
    private RegisterTask registerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);

        this.editTextAccount = (EditText)this.findViewById(R.id.editTextAccount);
        this.editTextPassword = (EditText)this.findViewById(R.id.editTextPassword);
        this.editTextPasswordVerify = (EditText)this.findViewById(R.id.editTextPasswordVerify);
        this.editTextEmail = (EditText)this.findViewById(R.id.editTextEmail);
    }

    public void buttonRegisterOnClick(View v)
    {
        boolean register = true;
        // Checks if the passwords do match.
        if (!this.editTextPassword.getText().toString().equals(this.editTextPasswordVerify.getText().toString()))
        {
            register = false;
            this.editTextPassword.setError(this.getString(R.string.passwords_do_not_match));
            this.editTextPasswordVerify.setError(this.getString(R.string.passwords_do_not_match));
        }
        else
        {
            this.editTextPassword.setError(null);
            this.editTextPasswordVerify.setError(null);
        }

        // Checks if the email address is a real email address.
        String email = this.editTextEmail.getText().toString();
        if (!StringHelper.isEmailValid(email))
        {
            register = false;
            this.editTextEmail.setError(this.getString(R.string.email_not_valid));
        }
        else
        {
            this.editTextEmail.setError(null);
        }

        if (register)
        {
            RegisterDto registerItem = new RegisterDto(this.editTextAccount.getText().toString(), this.editTextPassword.getText().toString(), this.editTextEmail.getText().toString());
            this.registerTask = new RegisterTask(registerItem);
            this.registerTask.execute((Void) null);
        }
    }

    private void displaySaveAlert(String alertMessage)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getString(R.string.error_while_register));
        sb.append("\n");
        sb.append(alertMessage);
        new AlertDialog.Builder(RegisterActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(this.getString(R.string.error))
                .setMessage(sb)
                .setPositiveButton(this.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .show();
    }

    public class RegisterTask extends AsyncTask<Void, Void, Boolean>
    {
        private final RegisterDto registerItem;
        private ProgressDialog dialog;
        private String content;

        public RegisterTask(RegisterDto registerItem)
        {
            this.registerItem = registerItem;
            this.dialog = new ProgressDialog(RegisterActivity.this);
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
                URL url = new URL("https://bzssurselva.azurewebsites.net/appservice.svc/Register");
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
                wr.writeBytes(this.registerItem.toJson().toString());
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
            registerTask = null;
            this.dialog.dismiss();

            if (success)
            {
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
            }
            else
            {
                displaySaveAlert(getString(R.string.unknown_error_occurred));
            }
        }

        @Override
        protected void onCancelled()
        {
            registerTask = null;
            this.dialog.dismiss();
        }
    }
}
