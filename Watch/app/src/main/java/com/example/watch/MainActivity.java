package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.all_user_student).setOnClickListener(this);
        findViewById(R.id.all_user_school).setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_user_student:{
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }break;

            case R.id.all_user_school:{
                Intent i = new Intent(getApplicationContext(), SchoolProfileActivity.class);
                startActivity(i);
            }break;
        }
    }
}
