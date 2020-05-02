package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class NavigateToStudentActivity  extends  AppCompatActivity implements View.OnClickListener {


    private Button AddPlace;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to_student);

        findViewById(R.id.txt_add_place_nav_to).setOnClickListener(this);
        findViewById(R.id.home_txt_nav_to).setOnClickListener(this);
        findViewById(R.id.txt_nav_to_school).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.add_place_img_nav_to | R.id.txt_add_place_nav_to:{
                Intent i = new Intent(getApplicationContext(), LocationSetAutoActivity.class);
                startActivity(i);
            }break;

            case  R.id.home_txt_nav_to:{
//                Intent i = new Intent(getApplicationContext(),LocationSetAutoActivity.class );
//                startActivity(i);
            }break;

            case  R.id.txt_nav_to_school:{
//                Intent i = new Intent(getApplicationContext(),LocationSetAutoActivity.class );
//                startActivity(i);
            }break;
            case  R.id.go_back_nav_to:{
                Intent i = new Intent(getApplicationContext(),StudentProfileActivity.class );
                startActivity(i);
            }break;
        }
    }
}

