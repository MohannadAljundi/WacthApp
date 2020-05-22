package com.example.watch.Bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.watch.MainActivity;
import com.example.watch.R;
import com.example.watch.SettingsActivity;
import com.example.watch.models.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BusProfileActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager session ;

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;

    String email , name ;
    private TextView Name_View , Email_View ;

    BusInfo busInfo = new BusInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_profile);

        session = new SessionManager(getApplicationContext());

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("BusInfo");

        findViewById(R.id.qr_cod_profile_bus).setOnClickListener(this);
        findViewById(R.id.student_home_profile_bus).setOnClickListener(this);
        findViewById(R.id.student_on_board_profile_bus).setOnClickListener(this);
        findViewById(R.id.go_back_bus_profile).setOnClickListener(this);
        findViewById(R.id.btn_setting_profile_bus).setOnClickListener(this);
        findViewById(R.id.student_on_board_profile_bus).setOnClickListener(this);
        findViewById(R.id.btn_Signout_bus).setOnClickListener(this);
        findViewById(R.id.btn_edit_profile_bus).setOnClickListener(this);
        Name_View = findViewById(R.id.name_view_profile_bus);
        Email_View = findViewById(R.id.email_view_profile_bus);

        session.checkLogin();


        HashMap<String,String > schoolUser = session.getUserDetails();

        name  = schoolUser.get(SessionManager.KEY_NAME);
        email = schoolUser.get(SessionManager.KEY_EMAIL);

        Name_View.setText(name);
        Email_View.setText(email);

        ReadNiceNameFromFirebase();



    }

    void ReadNiceNameFromFirebase(){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                        if(email.equals(dataSnapshot2.child("Username").getValue(String.class))){
                            busInfo.FullName = dataSnapshot2.child("FullName").getValue(String.class);
                        }
                        Log.d("Firebase State","Read Name Successful" +" >> " + busInfo.FullName );
                    }
                }
                Name_View.setText(busInfo.FullName);
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
        switch (v.getId()) {
            case R.id.qr_cod_profile_bus: {
                Intent i = new Intent(getApplicationContext(), ScanQRActivity.class);
                startActivity(i);
            }
            break;
            case R.id.go_back_bus_profile: {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
            break;
            case R.id.btn_setting_profile_bus:{
                Intent i=new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);

            }break;
            case R.id.student_home_profile_bus:{
                Intent i=new Intent(getApplicationContext(), StudentHomeActivity.class);
                startActivity(i);

            }break;
            case R.id.student_on_board_profile_bus:{
                Intent i=new Intent(getApplicationContext(), StudentOnBoardActivity.class);
                startActivity(i);
            }break;

            case R.id.btn_edit_profile_bus:{
                Intent i=new Intent(getApplicationContext(), BusEditProfileActivity.class);
                startActivity(i);
            }break;

            case  R.id.btn_Signout_bus:{
                Intent go = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(go);
                FirebaseAuth.getInstance().signOut();
                session.logoutUser();
            }break;

        }
    }

}
