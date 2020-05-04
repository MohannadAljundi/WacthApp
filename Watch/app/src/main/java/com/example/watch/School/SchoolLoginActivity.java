package com.example.watch.School;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SchoolLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login;
    private TextView Signup;
    private FirebaseAuth mAuth;
    private EditText Email , Pass ;
    private String  Email_Str , Password_Str , Name_Str;

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



    private void LoginSoGood(final String email, String pass){

        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(getApplicationContext(),"Welcome " + email,Toast.LENGTH_LONG).show();
                                Intent i = new Intent(SchoolLoginActivity.this, SchoolProfileActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Please Verify Your Email.",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
}
