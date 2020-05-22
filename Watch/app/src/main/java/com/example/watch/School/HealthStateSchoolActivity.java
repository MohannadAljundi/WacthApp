package com.example.watch.School;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.R;
import com.example.watch.models.SessionManager;

public class HealthStateSchoolActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_state_school);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        findViewById(R.id.go_back_school_profile_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back_school_profile_2 : {
                Intent go = new Intent(HealthStateSchoolActivity.this, SchoolProfileActivity.class);  //link two screens.
                startActivity(go);
            }break;
        }
    }
}
