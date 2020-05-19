package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.Bus.BusLoginActivity;
import com.example.watch.School.SchoolLoginActivity;
import com.example.watch.School.SchoolProfileActivity;
import com.example.watch.Student.StudenLoginActivity;
import com.example.watch.modes.SessionManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager sessionManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.all_user_student).setOnClickListener(this);
        findViewById(R.id.all_user_school).setOnClickListener(this);
        findViewById(R.id.all_user_bus).setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_user_student:{
                Intent i = new Intent(getApplicationContext(), StudenLoginActivity.class);
                startActivity(i);
            }break;

            case R.id.all_user_school:{
                Intent i2 = new Intent(getApplicationContext(), SchoolLoginActivity.class);
                startActivity(i2);
            }break;

            case R.id.all_user_bus:{
                Intent i2 = new Intent(getApplicationContext(), BusLoginActivity.class);
                startActivity(i2);
            }break;
        }
    }
}
