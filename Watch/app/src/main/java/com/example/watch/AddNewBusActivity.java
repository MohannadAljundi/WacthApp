package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

public class AddNewBusActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private String  UserID , Full_name , Bus_number , Plate_number , phone_str , age_str;
    private EditText FullName , BusNumber , PlateNumber ,Phone , Age;
    private Button btnConform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_bus);

        findViewById(R.id.bktomain).setOnClickListener(this);

        FullName = findViewById(R.id.name_add_bus);
        BusNumber = findViewById(R.id.bus_number_add_bus);
        PlateNumber = findViewById(R.id.plate_number_add_bus);
        Phone = findViewById(R.id.user_phone_add_bus);
        Age = findViewById(R.id.age_add_student);
        btnConform = findViewById(R.id.conf_butt);

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("BusInfo");
        UserID = firebaseDatabase.push().getKey();

        btnConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStudentToFirebase();
                Toast.makeText(getApplicationContext(),"Saving Data ..",Toast.LENGTH_LONG).show();
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

        Log.d("Tag : ", Full_name + Bus_number + Plate_number + phone_str + age_str);
        BusInfo busInfo = new BusInfo(Full_name,Bus_number,Plate_number,phone_str,age_str);

        firebaseDatabase.child("Bus").child(UserID).setValue(busInfo);
        Log.d("Tag2 : ", Full_name + Bus_number + Plate_number + phone_str + age_str);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bktomain:{
                Intent go = new Intent(AddNewBusActivity.this, AddNewMemberSchoolActivity.class);
                startActivity(go);
            }
        }
    }
}
