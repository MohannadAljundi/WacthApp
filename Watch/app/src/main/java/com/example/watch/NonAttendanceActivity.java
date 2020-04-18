package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NonAttendanceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_attendance);
        findViewById(R.id.go_back_student_profile_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.go_back_student_profile_3:{
                Intent go = new Intent(NonAttendanceActivity.this, StudentProfileActivity.class);
                startActivity(go);
            }break;
        }

    }
}
