package com.example.watch.School;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.watch.MainActivity;
import com.example.watch.R;
import com.example.watch.SettingsActivity;
import com.example.watch.models.LoadingDialoge;
import com.example.watch.models.SchoolSessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;

public class SchoolProfileActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    SchoolSessionManager session ;

    private TextView Name_View , Email_View;
    private ImageView profile_img;

    public String Email_Get , Name_Get ;
    String name , email , filePath_str;
    private StorageReference mStorageRef;

    private FirebaseStorage storage_d;
    private StorageReference storageRef;
    private  StorageReference islandRef;

    SchoolInfo schoolInfo = new SchoolInfo();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_profile);

        session = new SchoolSessionManager(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // upload
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // download
        storage_d = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        storageRef = storage_d.getReference();


        findViewById(R.id.add_new_btn_school).setOnClickListener(this);

        findViewById(R.id.attendance_btn_school).setOnClickListener(this);

        findViewById(R.id.health_btn_school).setOnClickListener(this);

        findViewById(R.id.go_back_AllUser_school).setOnClickListener(this);

        findViewById(R.id.btn_setting_profile_school).setOnClickListener(this);

        findViewById(R.id.set_location_btn_school).setOnClickListener(this);

        findViewById(R.id.student_location_school).setOnClickListener(this);

        findViewById(R.id.btn_Signout_school).setOnClickListener(this);

        findViewById(R.id.btn_edit_profile_school).setOnClickListener(this);

        profile_img = findViewById(R.id.profile_img_profile_school);


        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("SchoolInfo");

        Name_View = findViewById(R.id.name_view_profile_school);
        Email_View = findViewById(R.id.email_view_profile_school);

        Toast.makeText(getApplicationContext(),"User Login State : " + session.isLoggedIn(),Toast.LENGTH_LONG).show();

        session.checkLogin();

        HashMap<String,String > schoolUser = session.getUserDetails();

        name  = schoolUser.get(SchoolSessionManager.KEY_NAME);
        email = schoolUser.get(SchoolSessionManager.KEY_EMAIL);
        schoolInfo.ImageBitmapStringValue = schoolUser.get(SchoolSessionManager.KEY_IMAGE);
        filePath_str = schoolUser.get(SchoolSessionManager.KEY_FILE_PATH);

        Email_View.setText(email);
        Name_View.setText(name);

        schoolInfo.ImageProfileID = "image/"+ name.toLowerCase()
                .replaceAll("'","").replaceAll(" ","_")+"_profile_img";


        if(schoolInfo.ImageBitmapStringValue == null) {
            boolean ImageState_l = getImageFromFirebase();
            if(!ImageState_l){
                Drawable myDrawable = getResources().getDrawable(R.drawable.school_icon);
                profile_img.setImageDrawable(myDrawable);
            }

        }else {
            Bitmap image_view_rec = SchoolSessionManager.decodeBase64(schoolInfo.ImageBitmapStringValue);
            profile_img.setImageBitmap(image_view_rec);
            Log.d("Image_str State : ",schoolInfo.ImageBitmapStringValue);
//            if(filePath_str != null && !schoolInfo.ImageState){
//                uploadImage(Uri.parse(filePath_str));
//            }
        }
    }

    private boolean getImageFromFirebase(){
        // [START download_to_memory]
        islandRef = storageRef.child(schoolInfo.ImageProfileID);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                schoolInfo.ImageBitmapStringValue = image.toString();
                profile_img.setImageBitmap(image);
                schoolInfo.ImageState = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                schoolInfo.ImageState = false;
                Log.e("image_str Value State", String.valueOf(schoolInfo.ImageState));
            }
        });
        return schoolInfo.ImageState;
        // [END download_to_memory]
    }


    private void uploadImage(Uri image_path){
        if(image_path != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Upload Image ...");
            progressDialog.show();

            StorageReference ref = mStorageRef.child("image/"+ name.toLowerCase()
                    .replaceAll("'","").replaceAll(" ","_")+"_profile_img");
            ref.putFile(image_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_LONG).show();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Image not Uploaded",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount() );
                            progressDialog.setMessage("Uploaded " + (int)progress + "%");
                        }
                    });
        }
    }



    void ReadNiceNameFromFirebase(){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                        if(email.equals(dataSnapshot2.child("Email").getValue(String.class))){
                            schoolInfo.NiceName = dataSnapshot2.child("NiceName").getValue(String.class);
                        }
                        Log.d("Firebase State","Read Name Successful" +" >> " + schoolInfo.NiceName );
                    }
                }
                Name_View.setText(schoolInfo.NiceName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("bitmap State", "Error getting bitmap", e);
        }
        return bm;
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

         Email_Get = getIntent().getStringExtra("Email_Value");
         Name_Get = getIntent().getStringExtra("Name_Str_Value");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_new_btn_school :{
                Intent go = new Intent(SchoolProfileActivity.this, AddNewMemberSchoolActivity.class);  //link two screens.
                startActivity(go);
            }break;

            case R.id.health_btn_school :{
                Intent go = new Intent(SchoolProfileActivity.this, HealthStateSchoolActivity.class);
                startActivity(go);
            }break;

            case R.id.attendance_btn_school:{
                Intent go = new Intent(SchoolProfileActivity.this, AttendanceSchoolActivity.class);
                startActivity(go);
            }break;

            case R.id.go_back_AllUser_school:{
                Intent go = new Intent(SchoolProfileActivity.this, MainActivity.class);
                startActivity(go);
            }break;

            case R.id.btn_setting_profile_school:{
                Intent go = new Intent(SchoolProfileActivity.this, SettingsActivity.class);
                startActivity(go);
            }break;

            case R.id.set_location_btn_school:{
                Intent go = new Intent(SchoolProfileActivity.this, BusTrafficActivity.class);
                startActivity(go);
            }break;

            case R.id.student_location_school:{
                Intent go = new Intent(SchoolProfileActivity.this, StudentLocationActivity.class);
                startActivity(go);
            }break;

            case R.id.btn_Signout_school:{
                Intent go = new Intent(SchoolProfileActivity.this, MainActivity.class);
                startActivity(go);
                FirebaseAuth.getInstance().signOut();
                session.logoutUser();
            }break;


            case R.id.btn_edit_profile_school:{
                final LoadingDialoge loadingDialoge = new LoadingDialoge(this);
                Intent i = new Intent(getBaseContext(), SchoolEditProfileActivity.class);
                i.putExtra("email_2", Email_Get);
                i.putExtra("name_2", Name_Get);
                startActivity(i);
                loadingDialoge.StartLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialoge.DismissDialog();
                    }
                },7000);

            }break;
        }


    }
}
