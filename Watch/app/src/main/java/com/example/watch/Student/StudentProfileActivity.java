package com.example.watch.Student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch.MainActivity;
import com.example.watch.R;
import com.example.watch.SettingsActivity;
import com.example.watch.models.SchoolSessionManager;
import com.example.watch.models.StudentSessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StudentProfileActivity extends AppCompatActivity implements View.OnClickListener  {

    StudentSessionManager session ;

    private static final int PICK_IMAGE_REQUEST = 71 ;
    private ImageView profile_image ;
    private TextView Name_View , Email_View ;

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;

    String email , name ;

    StudentInfo studentInfo = new StudentInfo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studen_profile);

        session = new StudentSessionManager(getApplicationContext());

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("StudentInfo");

        // set On Click Listener nav
        findViewById(R.id.navigate_profile_student).setOnClickListener(this);
        findViewById(R.id.bus_map_profile_student).setOnClickListener(this);
        findViewById(R.id.health_profile_student).setOnClickListener(this);
        findViewById(R.id.view_loc_profile_student).setOnClickListener(this);
        findViewById(R.id.non_atn_profile_student).setOnClickListener(this);
        findViewById(R.id.go_back_profile_student).setOnClickListener(this);
        findViewById(R.id.btn_setting_profile_student).setOnClickListener(this);
        findViewById(R.id.btn_edit_profile_student).setOnClickListener(this);
        findViewById(R.id.btn_Signout_student).setOnClickListener(this);
        Name_View = findViewById(R.id.name_view_profile_student);
        Email_View = findViewById(R.id.email_view_profile_student);

        Toast.makeText(getApplicationContext(),"User Login State : " + session.isLoggedIn(),Toast.LENGTH_LONG).show();

        session.checkLogin();

        HashMap<String,String > schoolUser = session.getUserDetails();

        name  = schoolUser.get(StudentSessionManager.KEY_NAME);
        email = schoolUser.get(StudentSessionManager.KEY_EMAIL);

        ReadNiceNameFromFirebase();

        Name_View.setText(name);
        Email_View.setText(email);





    }

    void ReadNiceNameFromFirebase(){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                        if(email.equals(dataSnapshot2.child("Username").getValue(String.class))){
                            studentInfo.FullName = dataSnapshot2.child("FullName").getValue(String.class);
                        }
                        Log.d("Firebase State","Read Name Successful" +" >> " + studentInfo.FullName );
                    }
                }
                Name_View.setText(studentInfo.FullName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigate_profile_student :{

                Intent i = new Intent(getApplicationContext(), NavigateToStudentActivity.class);
                startActivity(i);


            }break;
            case R.id.bus_map_profile_student :{
                Intent i = new Intent(getApplicationContext(), BusMapActivity.class);
                startActivity(i);


            }break;
            case R.id.health_profile_student :{

                Intent i = new Intent(getApplicationContext(), HealthyActivityStateStudent.class);
                startActivity(i);

            }break;
            case R.id.view_loc_profile_student :{
                Intent i = new Intent(getApplicationContext(), ViewMapStudentActivity.class);
                startActivity(i);


            }break;
            case R.id.non_atn_profile_student :{
                Intent i = new Intent(getApplicationContext(), NonAttendanceStudentActivity.class);
                startActivity(i);

            }break;

            case R.id.go_back_profile_student:{
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }break;

            case R.id.btn_setting_profile_student:{
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }break;

            case R.id.btn_edit_profile_student:{
                Intent i = new Intent(getApplicationContext(), StudentEditProfileActivity.class);
                startActivity(i);
            }break;

            case  R.id.btn_Signout_student:{
                Intent go = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(go);
                FirebaseAuth.getInstance().signOut();
                session.logoutUser();
            }break;
        }

    }
}
