package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SchoolProfileActivity extends AppCompatActivity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_profile);

        findViewById(R.id.add_new_butt).setOnClickListener(this);

        findViewById(R.id.Attendence_butt).setOnClickListener(this);

        findViewById(R.id.health_butt).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_butt :{
                Intent go = new Intent(SchoolProfileActivity.this, AddNewMemberActivity.class);  //link two screens.
                startActivity(go);          // start excution.
            }break;

            case R.id.health_butt :{
                Intent go = new Intent(SchoolProfileActivity.this, HealthStateActivity.class);  //link two screens.
                startActivity(go);
            }break;

            case R.id.Attendence_butt:{
                Intent go = new Intent(SchoolProfileActivity.this, AttendanceActivity.class);  //link two screens.
                startActivity(go);          // start excution.
            }break;
        }


    }
}
