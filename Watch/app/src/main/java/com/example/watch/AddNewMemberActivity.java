package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddNewMemberActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        this.setTitle("Add New Member"); //change screen title.
        findViewById(R.id.User_button).setOnClickListener(this);
        findViewById(R.id.Bus_button).setOnClickListener(this);
        findViewById(R.id.go_back_school_profile).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.User_button:{
                Intent go = new Intent(AddNewMemberActivity.this, AddNewStudentActivity.class);  //link two screens.
                startActivity(go);
            }break;

            case R.id.Bus_button:{
                Intent go = new Intent(AddNewMemberActivity.this, AddNewBusActivity.class);       //link two screens.
                startActivity(go);
            }break;

            case R.id.go_back_school_profile:{
                Intent go = new Intent(AddNewMemberActivity.this, SchoolProfileActivity.class);  //link two screens.
                startActivity(go);
            }break;
        }
    }
}
