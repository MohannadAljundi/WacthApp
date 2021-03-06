package com.example.watch.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.R;
import com.example.watch.models.SchoolSessionManager;
import com.example.watch.models.StudentSessionManager;

public class NonAttendanceStudentActivity extends AppCompatActivity implements View.OnClickListener {

    StudentSessionManager session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_attendance_student);
        findViewById(R.id.go_back_student_profile_3).setOnClickListener(this);

        session = new StudentSessionManager(getApplicationContext());
        session.checkLogin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.go_back_student_profile_3:{
                Intent go = new Intent(NonAttendanceStudentActivity.this, StudentProfileActivity.class);
                startActivity(go);
            }break;
        }

    }
}
