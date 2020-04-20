package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseTest extends AppCompatActivity {

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private TextView dbtext;
    private String UserID;
    private EditText Username , Email;
    private TextView Userdb , Emaildb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        Username = findViewById(R.id.username_txt);
        Email = findViewById(R.id.email_txt);
        Userdb = findViewById(R.id.textView_dbusername);
        Emaildb = findViewById(R.id.textView_dbemail);

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("DataUser");
        UserID = firebaseDatabase.push().getKey();


    }

    public void addUser (String username , String email){
        User users = new User(username,email);
        firebaseDatabase.child("Users").child(UserID).setValue(users);
    }

    public void updateUser(String username , String email){
        firebaseDatabase.child("Users").child(UserID).child("username").setValue(username);
        firebaseDatabase.child("Users").child(UserID).child("email").setValue(email);
    }

    public void insertData(View view){
        addUser(Username.getText().toString().trim(),Email.getText().toString().trim());
    }

    public void updateData(View view){
        updateUser(Username.getText().toString().trim(),Email.getText().toString().trim());
    }

    public void readData(View view){
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                   for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                       String dbuser = dataSnapshot2.child("username").getValue(String.class);
                       String dbemail = dataSnapshot2.child("email").getValue(String.class);
                       Log.d("TAGdb",dbuser +" | " + dbemail);
                       Userdb.setText(dbuser);
                       Emaildb.setText(dbemail);

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


}
