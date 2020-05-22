package com.example.watch.Bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.watch.R;
import com.example.watch.models.BusSessionManager;
import com.example.watch.models.SchoolSessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusLoginActivity extends AppCompatActivity implements View.OnClickListener  {

    BusSessionManager session ;

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth mAuth;

    private Button login;
    private EditText Email , Pass ;

    BusInfo busInfo = new BusInfo();
    private String Email_Str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_login);

        session = new BusSessionManager(getApplicationContext());


        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("BusInfo");
        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.txtEmail);
        Pass = findViewById(R.id.txtPass);
        findViewById(R.id.twits_img).setOnClickListener(this);

        login = findViewById(R.id.buttonLogin);

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
                        if(Email_Str.equals(dataSnapshot2.child("Username").getValue(String.class))){
                           busInfo.FullName  = dataSnapshot2.child("FullName").getValue(String.class);
                        }
                        Log.d("Firebase State","Read Name Successful" +" >> " + busInfo.FullName);
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
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(BusLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Welcome " + busInfo.FullName,Toast.LENGTH_LONG).show();
                    session.createLoginSession(busInfo.FullName, email);
                    Intent i = new Intent(getApplicationContext(), BusProfileActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login Error , Login Again .. ",Toast.LENGTH_LONG).show();
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
                Intent i = new Intent(BusLoginActivity.this, BusProfileActivity.class);
                startActivity(i);
            }break;


        }
    }
}
