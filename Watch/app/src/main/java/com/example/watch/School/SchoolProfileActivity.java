package com.example.watch.School;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch.MainActivity;
import com.example.watch.R;
import com.example.watch.SettingsActivity;
import com.example.watch.modes.LoadingDialoge;
import com.example.watch.modes.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class SchoolProfileActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    SessionManager session ;

    private TextView Name_View , Email_View;

    public String Email_Get , Name_Get ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_profile);

        session = new SessionManager(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        findViewById(R.id.add_new_butt).setOnClickListener(this);

        findViewById(R.id.Attendence_butt).setOnClickListener(this);

        findViewById(R.id.health_butt).setOnClickListener(this);

        findViewById(R.id.go_back_AllUser).setOnClickListener(this);

        findViewById(R.id.btn_setting_profile).setOnClickListener(this);

        findViewById(R.id.st_location_butt).setOnClickListener(this);

        findViewById(R.id.student_location).setOnClickListener(this);

        findViewById(R.id.btnSignout).setOnClickListener(this);

        findViewById(R.id.btn_edit_profile).setOnClickListener(this);

        Name_View = findViewById(R.id.txtname_profil);
        Email_View = findViewById(R.id.txtemail_profil);

        Toast.makeText(getApplicationContext(),"User Login State : " + session.isLoggedIn(),Toast.LENGTH_LONG).show();

        session.checkLogin();

        HashMap<String,String > schoolUser = session.getUserDetails();

        String name  = schoolUser.get(SessionManager.KEY_NAME);
        String email = schoolUser.get(SessionManager.KEY_EMAIL);

        Email_View.setText(email);
        Name_View.setText(name);


    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

         Email_Get = getIntent().getStringExtra("Email_Value");
         Name_Get = getIntent().getStringExtra("Name_Str_Value");


//        if(Email_Get != null){
//            Email_View.setText(firebaseUser.getEmail());
//            Name_View.setText(Name_Get);
//        }
//        else {
//            Email_View.setText("Gust");
//            Name_View.setText("Gust");
//        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_butt :{
                Intent go = new Intent(SchoolProfileActivity.this, AddNewMemberSchoolActivity.class);  //link two screens.
                startActivity(go);
            }break;

            case R.id.health_butt :{
                Intent go = new Intent(SchoolProfileActivity.this, HealthStateSchoolActivity.class);
                startActivity(go);
            }break;

            case R.id.Attendence_butt:{
                Intent go = new Intent(SchoolProfileActivity.this, AttendanceSchoolActivity.class);
                startActivity(go);
            }break;

            case R.id.go_back_AllUser:{
                Intent go = new Intent(SchoolProfileActivity.this, MainActivity.class);
                startActivity(go);
            }break;

            case R.id.btn_setting_profile:{
                Intent go = new Intent(SchoolProfileActivity.this, SettingsActivity.class);
                startActivity(go);
            }break;

            case R.id.st_location_butt:{
                Intent go = new Intent(SchoolProfileActivity.this, BusTrafficActivity.class);
                startActivity(go);
            }break;

            case R.id.student_location:{
                Intent go = new Intent(SchoolProfileActivity.this, StudentLocationActivity.class);
                startActivity(go);
            }break;

            case R.id.btnSignout:{
                FirebaseAuth.getInstance().signOut();
                Intent go = new Intent(SchoolProfileActivity.this, MainActivity.class);
                go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(go);
                session.logoutUser();
            }break;


            case R.id.btn_edit_profile:{
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
