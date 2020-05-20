package com.example.watch.School;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaCas;
import android.os.Bundle;
import android.os.Handler;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch.R;
import com.example.watch.modes.LoadingDialoge;
import com.example.watch.modes.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SchoolLoginActivity extends AppCompatActivity implements View.OnClickListener , DialogInterface {

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private Button login;
    private TextView Signup;
    private FirebaseAuth mAuth;
    private EditText Email , Pass ;
    private String  Email_Str , Password_Str ;
    public String Name_Str;
    SchoolInfo schoolInfo  = new SchoolInfo();

    // Alert Dialog Manager
    //AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_login);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.txtEmail);
        Pass = findViewById(R.id.txtPass);
        findViewById(R.id.twits_img).setOnClickListener(this);
        findViewById(R.id.textViewSignup).setOnClickListener(this);

        final LoadingDialoge loadingDialoge = new LoadingDialoge(this);

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("SchoolInfo");

        login = findViewById(R.id.buttonLogin);

        session = new SessionManager(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String pass = Pass.getText().toString();
                if(email.isEmpty()){
                    Email.setError("Enter Your Email !!");
                    Email.requestFocus();
                    return;
                }
                else if(pass.isEmpty()){
                    Pass.setError("Enter Your Password !!");
                    Pass.requestFocus();
                }

                LoginSoGood(email,pass);
                loadingDialoge.StartLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialoge.DismissDialog();
                    }
                },5000);
            }
        });
    }


            void ReadNiceNameFromFirebase(){

                Email_Str = Email.getText().toString();
                firebaseDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                            for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                                if(Email_Str.equals(dataSnapshot2.child("Email").getValue(String.class))){
                                    Name_Str = dataSnapshot2.child("NiceName").getValue(String.class);
                                    schoolInfo.NiceName = Name_Str;
                                }
                                Log.d("Firebase State","Read Name Successful" +" >> " + Name_Str);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });
            }
            private void LoginSoGood(final String email, String pass){

                ReadNiceNameFromFirebase();
                mAuth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Welcome " + schoolInfo.NiceName,Toast.LENGTH_LONG).show();
                                    session.createLoginSession(schoolInfo.NiceName, email);
                                    Intent i = new Intent(getBaseContext(), SchoolProfileActivity.class);
                                    i.putExtra("Email_Value", email);
                                    i.putExtra("Name_Str_Value", schoolInfo.NiceName);


                                    startActivity(i);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.twits_img :{
                Intent i = new Intent(SchoolLoginActivity.this, SchoolProfileActivity.class);
                startActivity(i);
            }break;

            case R.id.textViewSignup:{
                Intent i = new Intent(SchoolLoginActivity.this, SignUPSchoolActivity.class);
                startActivity(i);
            }break;

        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void dismiss() {

    }
}
