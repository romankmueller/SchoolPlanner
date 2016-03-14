package ch.bzs_surselva.schoolplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MasterDataActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_data);
    }

    public void buttonSubjectsOnClick(View view)
    {
        Intent intent = new Intent(this, SubjectOverviewActivity.class);
        this.startActivity(intent);
    }

    public void buttonTeachersOnClick(View view)
    {
        Intent intent = new Intent (this, TeacherOverviewActivity.class);
        this.startActivity(intent);
    }
    public void buttonRoomsOnClick(View view)
    {
        Intent intent = new Intent(this,RoomOverviewActivity.class);
        this.startActivity(intent);
    }
}
