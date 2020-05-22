package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.Bus.BusLoginActivity;
import com.example.watch.Bus.BusProfileActivity;
import com.example.watch.School.SchoolLoginActivity;
import com.example.watch.School.SchoolProfileActivity;
import com.example.watch.Student.StudenLoginActivity;
import com.example.watch.Student.StudentProfileActivity;
import com.example.watch.models.BusSessionManager;
import com.example.watch.models.SchoolSessionManager;
import com.example.watch.models.StudentSessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SchoolSessionManager session_school ;
    StudentSessionManager session_student;
    BusSessionManager session_bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session_school = new SchoolSessionManager(getApplicationContext());
        session_student = new StudentSessionManager(getApplicationContext());
        session_bus = new BusSessionManager(getApplicationContext());


        findViewById(R.id.all_user_student).setOnClickListener(this);
        findViewById(R.id.all_user_school).setOnClickListener(this);
        findViewById(R.id.all_user_bus).setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_user_student:{
                if(session_student.isLoggedIn()){
                    Intent i = new Intent(getApplicationContext(), StudentProfileActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(getApplicationContext(), StudenLoginActivity.class);
                    startActivity(i);
                }

            }break;

            case R.id.all_user_school:{
                if(session_school.isLoggedIn()){
                    Intent i2 = new Intent(getApplicationContext(), SchoolProfileActivity.class);
                    startActivity(i2);
                }else {
                    Intent i2 = new Intent(getApplicationContext(), SchoolLoginActivity.class);
                    startActivity(i2);
                }

            }break;

            case R.id.all_user_bus:{
                if(session_bus.isLoggedIn()){
                    Intent i2 = new Intent(getApplicationContext(), BusProfileActivity.class);
                    startActivity(i2);
                }else {
                    Intent i2 = new Intent(getApplicationContext(), BusLoginActivity.class);
                    startActivity(i2);
                }

            }break;
        }
    }
}
