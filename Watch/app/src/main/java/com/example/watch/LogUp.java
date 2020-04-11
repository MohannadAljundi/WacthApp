package com.example.watch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LogUp extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText Email , Pass , Name ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up);
        findViewById(R.id.go_back_nav_to).setOnClickListener(this);


        Email = findViewById(R.id.txtEmail);
        Pass = findViewById(R.id.txtPass);
        findViewById(R.id.move_on_create_account).setOnClickListener(this);
        Name = findViewById(R.id.txtNicName);
        mAuth = FirebaseAuth.getInstance();

    }

    private void RegisterUser(){
        String email = Email.getText().toString().trim();
        String pass = Pass.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is Required !!");
            Email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Enter an Valid Email !!");
            Email.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            Pass.setError("Password is Required !!");
            Pass.requestFocus();
            return;
        }

        if(pass.length() < 5){
            Pass.setError("Minimum length at least 5 character !!");
            Pass.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(LogUp.this, SchoolLoginActivity.class);
                    startActivity(i);
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You Are already registered",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back_nav_to:{
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }break;

            case R.id.move_on_create_account:{
                RegisterUser();
            }break;


        }
    }
}
