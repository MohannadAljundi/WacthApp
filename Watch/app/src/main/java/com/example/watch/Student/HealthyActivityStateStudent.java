package com.example.watch.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.R;

public class HealthyActivityStateStudent extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_state_schoole);
        findViewById(R.id.go_back_student_profile).setOnClickListener(this);

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
