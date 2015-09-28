package ch.bzs_surselva.schoolplanner;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_about);

        TextView textViewVersion = (TextView)this.findViewById(R.id.textViewVersion);
        textViewVersion.setText(this.getVersion());
    }

    public void buttonSupportOnClick(View v)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, "roman.mueller@bzs-surselva.ch");
        intent.putExtra(Intent.EXTRA_SUBJECT, "BZS Surselva for Android");
        intent.putExtra(Intent.EXTRA_TEXT, "");

        this.startActivity(Intent.createChooser(intent, "Send Email"));
    }

    public void buttonReviewOnClick(View v)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=ch.bzs_surselva.schoolplanner"));
        this.startActivity(intent);
    }

    private String getVersion()
    {
        try
        {
            PackageInfo packageInfo = this.getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return this.getResources().getString(R.string.not_available);
        }
    }
}
