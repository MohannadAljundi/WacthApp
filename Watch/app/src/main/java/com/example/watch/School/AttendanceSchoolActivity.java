package com.example.watch.School;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.watch.R;
import com.example.watch.models.SessionManager;

public class AttendanceSchoolActivity extends AppCompatActivity {

    SessionManager session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_school);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
    }
}
