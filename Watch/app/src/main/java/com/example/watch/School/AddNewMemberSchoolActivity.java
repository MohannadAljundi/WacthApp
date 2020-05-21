package com.example.watch.School;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.R;
import com.example.watch.modes.SessionManager;

public class AddNewMemberSchoolActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager session ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member_school);

        session = new SessionManager(getApplicationContext());

        //this.setTitle("Add New Member"); //change screen title.
        findViewById(R.id.User_button).setOnClickListener(this);
        findViewById(R.id.Bus_button).setOnClickListener(this);
        findViewById(R.id.go_back_school_profile).setOnClickListener(this);

        session.checkLogin();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.User_button:{
                Intent go = new Intent(AddNewMemberSchoolActivity.this, AddNewStudentActivity.class);  //link two screens.
                startActivity(go);
            }break;

            case R.id.Bus_button:{
                Intent go = new Intent(AddNewMemberSchoolActivity.this, AddNewBusActivity.class);       //link two screens.
                startActivity(go);
            }break;

            case R.id.go_back_school_profile:{
                Intent go = new Intent(AddNewMemberSchoolActivity.this, SchoolProfileActivity.class);  //link two screens.
                startActivity(go);
            }break;
        }
    }
}
