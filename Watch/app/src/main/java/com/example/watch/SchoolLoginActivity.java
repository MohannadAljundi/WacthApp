package com.example.watch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SchoolLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login;
    private TextView Signup;
    private FirebaseAuth mAuth;
    private EditText Email , Pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_login);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

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

    private void LoginSoGood(final String email, String pass){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(SchoolLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Login Error , Login Again .. ",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Welcome " + email,Toast.LENGTH_LONG).show();
                    if(email.contains("adm")){
                        Intent i = new Intent(SchoolLoginActivity.this,SchoolProfileActivity.class);
                        startActivity(i);
                    }

                    if(email.contains("stu")){
                        Intent i = new Intent(SchoolLoginActivity.this, StudentProfileActivity.class);
                        startActivity(i);
                    }

                    if(email.contains("bus")){
                        Intent i = new Intent(SchoolLoginActivity.this,BusProfileActivity.class);
                        startActivity(i);
                    }

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

        }
    }
}
