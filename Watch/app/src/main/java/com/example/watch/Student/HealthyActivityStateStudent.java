package com.example.watch.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.R;
import com.example.watch.models.SchoolSessionManager;
import com.example.watch.models.StudentSessionManager;

public class HealthyActivityStateStudent extends AppCompatActivity implements View.OnClickListener {

    StudentSessionManager session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_state_schoole);
        findViewById(R.id.go_back_student_profile).setOnClickListener(this);

        session = new StudentSessionManager(getApplicationContext());
        session.checkLogin();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back_student_profile: {
                Intent go = new Intent(HealthyActivityStateStudent.this, StudentProfileActivity.class);  //link two screens.
                startActivity(go);
            }break;
        }
    }
}
