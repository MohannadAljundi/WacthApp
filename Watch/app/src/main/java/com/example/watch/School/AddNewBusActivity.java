package com.example.watch.School;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.watch.Bus.BusInfo;
import com.example.watch.R;
import com.example.watch.models.SchoolSessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewBusActivity extends AppCompatActivity implements View.OnClickListener {

    SchoolSessionManager session ;

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private FirebaseAuth mAuth;
    private String  UserID , Full_name , Bus_number , Plate_number , phone_str , age_str;
    private EditText FullName , BusNumber , PlateNumber ,Phone , Age;
    private Button btnConform;
    AlertDialog.Builder builder;

    BusInfo busInfo = new BusInfo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_bus);

        session = new SchoolSessionManager(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("BusInfo");
        UserID = firebaseDatabase.push().getKey();

        findViewById(R.id.bktomain).setOnClickListener(this);


        FullName = findViewById(R.id.name_add_bus);
        BusNumber = findViewById(R.id.bus_number_add_bus);
        PlateNumber = findViewById(R.id.plate_number_add_bus);
        Phone = findViewById(R.id.user_phone_add_bus);
        Age = findViewById(R.id.age_add_student);
        btnConform = findViewById(R.id.conf_butt);

        session.checkLogin();


        btnConform.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AddStudentToFirebase();

                Toast.makeText(getApplicationContext(), "Saving Data ..", Toast.LENGTH_LONG).show();
                FullName.setText("");
                BusNumber.setText("");
                PlateNumber.setText("");
                Phone.setText("");
                Age.setText("");


            }

        });

    }

    public void AddStudentToFirebase(){

        Full_name = FullName.getText().toString();
        Bus_number = BusNumber.getText().toString();
        Plate_number = PlateNumber.getText().toString();
        phone_str = Phone.getText().toString();
        age_str = Age.getText().toString();

        String Username = busInfo.UsernameGenerator(Full_name);
        Log.d("Username Value >> ",Username);
        String Password = busInfo.PasswordGenerator();
        Log.d("Password Value >> ",Password);

        busInfo = new BusInfo(UserID,Username,Password,Full_name,Bus_number,Plate_number,phone_str,age_str,"None");
        firebaseDatabase.child("Bus").child(UserID).setValue(busInfo);

        mAuth.createUserWithEmailAndPassword(Username, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"The User Authenticated Successfully",Toast.LENGTH_LONG).show();
                    Log.d("Register State >> ",task.getResult().toString());
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                        Log.d("Register State >> ","You are already registered");

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Register State >> ",task.getException().getMessage());
                    }

                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bktomain:{

                    builder = new AlertDialog.Builder(this);
                    builder.setMessage("Welcome") .setTitle("Exit");
                    //Setting message manually and performing action on button click
                    builder.setMessage("Do you want to close this page ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                    Toast.makeText(getApplicationContext(),"you choose ''Yes'' action for alertbox",
                                            Toast.LENGTH_SHORT).show();

                                    Intent go = new Intent(AddNewBusActivity.this, AddNewMemberSchoolActivity.class);
                                    startActivity(go);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(),"you choose ''No'' action for alertbox",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Exit Alert");
                    alert.show();

            }break;


        }


    }
}
