package com.example.watch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.mail.MessagingException;

public class SignUPSchoolActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText Email , Pass , Name , Phone;
    private EmailSendingClass EmialS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_school);
        findViewById(R.id.go_back_nav_to).setOnClickListener(this);


        Email = findViewById(R.id.txtEmail);
        Pass = findViewById(R.id.txtPass);
        findViewById(R.id.move_on_create_account).setOnClickListener(this);
        Name = findViewById(R.id.txtNicName);
        Phone = findViewById(R.id.txtphone);
        mAuth = FirebaseAuth.getInstance();

    }

    private void RegisterUser() throws MessagingException {
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

        EmialS = new EmailSendingClass();
        EmialS.sendMail(Email.getText().toString(),Name.getText().toString());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back_nav_to:{
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }break;

            case R.id.move_on_create_account:{
                try {
                    RegisterUser();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }break;


        }
    }
}
