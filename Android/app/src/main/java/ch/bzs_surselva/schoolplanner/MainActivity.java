package ch.bzs_surselva.schoolplanner;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import ch.bzs_surselva.schoolplanner.adapters.SubjectOverviewAdapter;
import ch.bzs_surselva.schoolplanner.dto.ResultDto;
import ch.bzs_surselva.schoolplanner.dto.SubjectLookupDto;
import ch.bzs_surselva.schoolplanner.helpers.CredentialHelper;
import ch.bzs_surselva.schoolplanner.helpers.RequestHelper;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayoutLogin;
    private EditText editTextAccount;
    private EditText editTextPassword;
    private LoginTask loginTask;
    private LinearLayout linearLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        this.linearLayoutLogin = (LinearLayout) this.findViewById(R.id.linearLayoutLogin);
        this.editTextAccount = (EditText) this.findViewById(R.id.editTextAccount);
        this.editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);

        this.linearLayoutMain = (LinearLayout) this.findViewById(R.id.linearLayoutMain);

        this.displayLoginOrMainView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //menu.getItem(0).setVisible(CredentialHelper.getAccount() != null);
        //menu.getItem(1).setVisible(CredentialHelper.getAccount() != null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_masterdata) {
            Intent intent = new Intent(this, MasterDataActivity.class);
            this.startActivity(intent);
            return true;
            //Test
        }

        // Wenn auf den Abmelden-Eintrag geklickt wurde, dann löschen wir die Anmeldeinfos.
        if (id == R.id.action_logoff) {
            this.editTextAccount.setText(null);
            this.editTextPassword.setText(null);
            CredentialHelper.setAccount(null);
            CredentialHelper.setPassword(null);
            this.displayLoginOrMainView();
            return true;
        }

        // Wenn auf den About-Menü-Eintrag geklickt wurde, dann zeigen wir den entsprechenden Screen.
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.displayLoginOrMainView();
    }

    public void buttonLoginOnClick(View v) {
        this.loginTask = new LoginTask(this.editTextAccount.getText().toString(), this.editTextPassword.getText().toString());
        this.loginTask.execute((Void) null);
    }

    public void buttonRegisterOnClick(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        this.startActivity(intent);
    }

    public void buttonCredentialsForgotten(View v) {
        Intent intent = new Intent(this, PasswordActivity.class);
        this.startActivity(intent);
    }

    public void buttonTimetableOnClick(View v) {
        Intent intent = new Intent(this, TimetableActivity.class);
        this.startActivity(intent);
    }

    private void displayLoginOrMainView() {
        if (CredentialHelper.getAccount() == null) {
            this.linearLayoutLogin.setVisibility(View.VISIBLE);
            this.linearLayoutMain.setVisibility(View.GONE);
        } else {
            this.linearLayoutLogin.setVisibility(View.GONE);
            this.linearLayoutMain.setVisibility(View.VISIBLE);
        }
    }

    private void displayAlert(String alertMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(alertMessage);
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(this.getString(R.string.login))
                .setMessage(sb)
                .setPositiveButton(this.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .show();
    }

    public class LoginTask extends AsyncTask<Void, Void, Boolean> {
        private final String account;
        private final String password;
        private ProgressDialog dialog;
        private String content;

        public LoginTask(String account, String password) {
            this.account = account;
            this.password = password;
            this.dialog = new ProgressDialog(MainActivity.this);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.please_wait));
            this.dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                URL url = new URL("https://bzssurselva.azurewebsites.net/appservice.svc/Login");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                String userPassword = this.account + ":" + this.password;
                connection.setRequestProperty("Authorization", "Basic " + new String(Base64.encode(userPassword.getBytes(), Base64.NO_WRAP)));
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(30000);
                connection.setInstanceFollowRedirects(true);
                connection.setUseCaches(false);
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
            loginTask = null;
            this.dialog.dismiss();

            if (success) {
                try {
                    JSONObject json = new JSONObject(this.content);
                    ResultDto result = new ResultDto(json);
                    if (result.getSuccess()) {
                        CredentialHelper.setAccount(this.account);
                        CredentialHelper.setPassword(this.password);
                        displayLoginOrMainView();
                    } else {
                        displayAlert(getString(R.string.invalid_credentials));
                    }
                } catch (JSONException e) {
                    displayAlert(getString(R.string.unknown_error_occurred));
                }
            } else {
                displayAlert(getString(R.string.unknown_error_occurred));
            }
        }

        @Override
        protected void onCancelled() {
            loginTask = null;
            this.dialog.dismiss();
        }
    }
}
    /**
     * Created by diggi on 22.02.2016.
     */



