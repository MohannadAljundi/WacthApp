package com.example.watch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class StudentProfileActivity extends AppCompatActivity implements View.OnClickListener  {

    private static final int PICK_IMAGE_REQUEST = 71 ;
    private ImageView navigate_to , heath , bus_map , non_atnd , view_loc , go_back_nav_to ;
    private Button btn_setting , select_pic;
    private StorageReference mStorageRef;
    private Uri filePath;
    private ImageView profile_image ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studen_profile);

        // set On Click Listener nav
        findViewById(R.id.navigate_profile).setOnClickListener(this);
        findViewById(R.id.bus_map_profile).setOnClickListener(this);
        findViewById(R.id.health_profile).setOnClickListener(this);
        findViewById(R.id.view_loc_profile).setOnClickListener(this);
        findViewById(R.id.non_atn_profile).setOnClickListener(this);
        findViewById(R.id.go_back_profile).setOnClickListener(this);
        findViewById(R.id.btn_setting_profile).setOnClickListener(this);
        select_pic = findViewById(R.id.select_pic);
        select_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        profile_image = findViewById(R.id.profile_img);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();


    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage(){
        if(filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Upload Image ...");
            progressDialog.show();

            StorageReference ref = mStorageRef.child("image/"+ UUID.randomUUID().toString());
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                            progressDialog.setMessage("Uploaded" + (int)progress + "%");
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                profile_image.setImageBitmap(bitmap);
            }
            catch (IOException ex){
                ex.printStackTrace();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.navigate_profile :{

                Intent i = new Intent(getApplicationContext(), NavigateTo.class);
                startActivity(i);


            }break;
            case R.id.bus_map_profile :{
                Intent i = new Intent(getApplicationContext(), BusMapActivity.class);
                startActivity(i);


            }break;
            case R.id.health_profile :{


            }break;
            case R.id.view_loc_profile :{
                Intent i = new Intent(getApplicationContext(), ViewMapActivity.class);
                startActivity(i);


            }break;
            case R.id.non_atn_profile :{
                Intent i = new Intent(getApplicationContext(), NonAttendanceActivity.class);
                startActivity(i);

            }break;

            case R.id.go_back_profile:{
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }break;

            case R.id.btn_setting_profile:{
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
            }break;
        }

    }
}
