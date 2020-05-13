package com.example.watch.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.watch.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private Button btnsave;
    private Button btnproceed;
    private EditText editTextName;
    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_info);
        btnproceed = findViewById(R.id.btnproceed);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("UserLocations");
        UserID = mDatabase.push().getKey();
        editTextName = findViewById(R.id.editTextName);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        btnsave = findViewById(R.id.btnsave);
        btnsave.setOnClickListener(this);
        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddingInfoActivity.this,Maps2Activity.class);
                startActivity(i);
            }
        });
    }
    private void saveUserInformation(){
        String name =editTextName.getText().toString().trim();
        double latitude= Double.parseDouble(editTextLatitude.getText().toString().trim());
        double longitude= Double.parseDouble(editTextLongitude.getText().toString().trim());
        UserInformation userInformation=new UserInformation(name,latitude,longitude);
        mDatabase.child("UserLocations").setValue(userInformation);
        Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        if(view==btnproceed){
            finish();
        }
        if (view==btnsave){
            saveUserInformation();
            editTextName.getText().clear();
            editTextLatitude.getText().clear();
            editTextLongitude.getText().clear();
        }
    }
}
