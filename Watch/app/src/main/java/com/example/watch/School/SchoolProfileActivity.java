package com.example.watch.School;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch.MainActivity;
import com.example.watch.R;
import com.example.watch.SettingsActivity;
import com.example.watch.models.LoadingDialoge;
import com.example.watch.models.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SchoolProfileActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    SessionManager session ;

    private TextView Name_View , Email_View;

    public String Email_Get , Name_Get ;
    String name , email ;

    SchoolInfo schoolInfo = new SchoolInfo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_profile);

        session = new SessionManager(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        findViewById(R.id.add_new_btn_school).setOnClickListener(this);

        findViewById(R.id.attendance_btn_school).setOnClickListener(this);

        findViewById(R.id.health_btn_school).setOnClickListener(this);

        findViewById(R.id.go_back_AllUser_school).setOnClickListener(this);

        findViewById(R.id.btn_setting_profile_school).setOnClickListener(this);

        findViewById(R.id.set_location_btn_school).setOnClickListener(this);

        findViewById(R.id.student_location_school).setOnClickListener(this);

        findViewById(R.id.btn_Signout_school).setOnClickListener(this);

        findViewById(R.id.btn_edit_profile_school).setOnClickListener(this);

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("SchoolInfo");

        Name_View = findViewById(R.id.name_view_profile_school);
        Email_View = findViewById(R.id.email_view_profile_school);

        Toast.makeText(getApplicationContext(),"User Login State : " + session.isLoggedIn(),Toast.LENGTH_LONG).show();

        session.checkLogin();

        HashMap<String,String > schoolUser = session.getUserDetails();

        name  = schoolUser.get(SessionManager.KEY_NAME);
        email = schoolUser.get(SessionManager.KEY_EMAIL);

        Email_View.setText(email);
        Name_View.setText(name);

        ReadNiceNameFromFirebase();

    }


    void ReadNiceNameFromFirebase(){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                        if(email.equals(dataSnapshot2.child("Email").getValue(String.class))){
                            schoolInfo.NiceName = dataSnapshot2.child("NiceName").getValue(String.class);
                        }
                        Log.d("Firebase State","Read Name Successful" +" >> " + schoolInfo.NiceName );
                    }
                }
                Name_View.setText(schoolInfo.NiceName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

         Email_Get = getIntent().getStringExtra("Email_Value");
         Name_Get = getIntent().getStringExtra("Name_Str_Value");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_btn_school :{
                Intent go = new Intent(SchoolProfileActivity.this, AddNewMemberSchoolActivity.class);  //link two screens.
                startActivity(go);
            }break;

            case R.id.health_btn_school :{
                Intent go = new Intent(SchoolProfileActivity.this, HealthStateSchoolActivity.class);
                startActivity(go);
            }break;

            case R.id.attendance_btn_school:{
                Intent go = new Intent(SchoolProfileActivity.this, AttendanceSchoolActivity.class);
                startActivity(go);
            }break;

            case R.id.go_back_AllUser_school:{
                Intent go = new Intent(SchoolProfileActivity.this, MainActivity.class);
                startActivity(go);
            }break;

            case R.id.btn_setting_profile_school:{
                Intent go = new Intent(SchoolProfileActivity.this, SettingsActivity.class);
                startActivity(go);
            }break;

            case R.id.set_location_btn_school:{
                Intent go = new Intent(SchoolProfileActivity.this, BusTrafficActivity.class);
                startActivity(go);
            }break;

            case R.id.student_location_school:{
                Intent go = new Intent(SchoolProfileActivity.this, StudentLocationActivity.class);
                startActivity(go);
            }break;

            case R.id.btn_Signout_school:{
                Intent go = new Intent(SchoolProfileActivity.this, MainActivity.class);
                startActivity(go);
                FirebaseAuth.getInstance().signOut();
                session.logoutUser();
            }break;


            case R.id.btn_edit_profile_school:{
                final LoadingDialoge loadingDialoge = new LoadingDialoge(this);
                Intent i = new Intent(getBaseContext(), SchoolEditProfileActivity.class);
                i.putExtra("email_2", Email_Get);
                i.putExtra("name_2", Name_Get);
                startActivity(i);
                loadingDialoge.StartLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialoge.DismissDialog();
                    }
                },7000);

            }break;
        }


    }
}
