package com.example.watch.School;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch.R;
import com.example.watch.modes.EditAddressDialog;
import com.example.watch.modes.EditEmailDialog;
import com.example.watch.modes.EditNameDialog;
import com.example.watch.modes.EditPasswordDialog;
import com.example.watch.modes.EditPhoneDialog;
import com.example.watch.modes.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class SchoolEditProfileActivity extends AppCompatActivity implements View.OnClickListener
        , EditNameDialog.EditNameDialogListener , EditAddressDialog.EditAddressDialogListener ,
        EditEmailDialog.EditEmailDialogListener , EditPhoneDialog.EditPhoneDialogListener ,
        EditPasswordDialog.EditPasswordDialogListener {

    SessionManager session ;

    private TextView name_view , email_view , address_view , phone_view , email_headLine , name_headLine ;
    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private String UserID;
    String old_pass = "";

    String Email_Get_2 , Name_Get_2 ;

    SchoolInfo schoolInfo = new SchoolInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_edit_profile);

        session = new SessionManager(getApplicationContext());

        name_view = findViewById(R.id.nameTextView);
        email_view = findViewById(R.id.emailTextView);
        address_view = findViewById(R.id.addressTextView);
        phone_view = findViewById(R.id.phoneTextView);
        email_headLine = findViewById(R.id.email_headline_edit);
        name_headLine = findViewById(R.id.name_headline_edit);

        findViewById(R.id.edit_name).setOnClickListener(this);
        findViewById(R.id.edit_email).setOnClickListener(this);
        findViewById(R.id.edit_address).setOnClickListener(this);
        findViewById(R.id.edit_password).setOnClickListener(this);
        findViewById(R.id.edit_phone).setOnClickListener(this);

        session.checkLogin();

        HashMap<String,String > schoolUser = session.getUserDetails();

        String name  = schoolUser.get(SessionManager.KEY_NAME);
        String email = schoolUser.get(SessionManager.KEY_EMAIL);

        name_headLine.setText(email);
        email_headLine.setText(name);

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("SchoolInfo");
        UserID = firebaseDatabase.push().getKey();

        Email_Get_2  = getIntent().getStringExtra("email_2");
        Name_Get_2 = getIntent().getStringExtra("name_2");

        ReadNiceNameFromFirebase();



    }


    void ReadNiceNameFromFirebase(){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                        if(Email_Get_2.equals(dataSnapshot2.child("Email").getValue(String.class))){
                            schoolInfo.NiceName = dataSnapshot2.child("NiceName").getValue(String.class);
                            schoolInfo.Email = dataSnapshot2.child("Email").getValue(String.class);
                            schoolInfo.Phone = dataSnapshot2.child("Phone").getValue(String.class);
                            schoolInfo.Address = dataSnapshot2.child("Address").getValue(String.class);
                        }
                        Log.d("Firebase State","Read Name Successful" +" >> " + schoolInfo.NiceName );
                        Log.d("Firebase State","Read Email Successful" +" >> " + schoolInfo.Email);
                        Log.d("Firebase State","Read Phone Successful" +" >> " + schoolInfo.Phone);
                        Log.d("Firebase State","Read Address Successful" +" >> " + schoolInfo.Address);
                    }
                }
                name_view.setText( schoolInfo.NiceName);
                email_view.setText(schoolInfo.Email);
                address_view.setText(schoolInfo.Address);
                phone_view.setText(schoolInfo.Phone);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }



    public void updateInfo(String filed , String child){
        firebaseDatabase.child("School").child(UserID).child(child).setValue(filed);
    }

    public void OpenEditNameDialog(){
        EditNameDialog editNameDialog = new EditNameDialog();
        editNameDialog.show(getSupportFragmentManager(),"Edit Name Dialog");

    }

    public void OpenEditEmailDialog(){
        EditEmailDialog editEmailDialog = new EditEmailDialog();
        editEmailDialog.show(getSupportFragmentManager(),"Edit Email Dialog");

    }

    public void OpenEditPhoneDialog(){
        EditPhoneDialog editPhoneDialog = new EditPhoneDialog();
        editPhoneDialog.show(getSupportFragmentManager(),"Edit Phone Dialog");

    }

    public void OpenEditAddressDialog(){
        EditAddressDialog editAddressDialog = new EditAddressDialog();
        editAddressDialog.show(getSupportFragmentManager(),"Edit Address Dialog");

    }

    public void OpenEditPasswordDialog(){
        EditPasswordDialog editPasswordDialog = new EditPasswordDialog();
        editPasswordDialog.show(getSupportFragmentManager(),"Edit Password Dialog");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_to_home_btn:{
                Intent intent = new Intent(getApplicationContext(), SchoolProfileActivity.class);
                startActivity(intent);
            }break;

            case R.id.edit_name:{
                OpenEditNameDialog();
            }break;

            case R.id.edit_email:{
                OpenEditEmailDialog();
            }break;

            case R.id.edit_address:{
                OpenEditAddressDialog();
            }break;

            case R.id.edit_phone:{
                OpenEditPhoneDialog();
            }break;

            case R.id.edit_password:{
                OpenEditPasswordDialog();
            }break;


        }
    }

    @Override
    public void TransferNameText(String username) {
        name_view.setText(username);
        updateInfo(username,"NiceName");

    }

    @Override
    public void TransferAddressText(String address) {
        address_view.setText(address);
        updateInfo(address,"Address");
    }

    @Override
    public void TransferEmailText(String Email) {
        email_view.setText(Email);
        updateInfo(Email,"Email");
    }

    @Override
    public void TransferPhoneText(String phone) {
        phone_view.setText(phone);
        updateInfo(phone,"Phone");

    }

    @Override
    public void TransferPasswordText(String Old_password, String New_Password) {

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                        old_pass = dataSnapshot2.child("Password").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        if(old_pass.equals(Old_password)){
            updateInfo(New_Password,"Password");
        }else{
            Toast.makeText(getApplicationContext(),"The Old Password is Wrong",Toast.LENGTH_LONG).show();
        }
    }
}
