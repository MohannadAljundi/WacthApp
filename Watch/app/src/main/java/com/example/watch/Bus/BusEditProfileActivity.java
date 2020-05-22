package com.example.watch.Bus;

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
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.watch.R;
        import com.example.watch.models.BusSessionManager;
        import com.example.watch.models.EditAddressDialog;
        import com.example.watch.models.EditEmailDialog;
        import com.example.watch.models.EditNameDialog;
        import com.example.watch.models.EditPasswordDialog;
        import com.example.watch.models.EditPhoneDialog;
        import com.example.watch.models.SchoolSessionManager;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;

        import java.io.IOException;
        import java.util.HashMap;
        import java.util.UUID;


public class BusEditProfileActivity extends AppCompatActivity implements View.OnClickListener
        , EditNameDialog.EditNameDialogListener , EditAddressDialog.EditAddressDialogListener ,
        EditEmailDialog.EditEmailDialogListener , EditPhoneDialog.EditPhoneDialogListener ,
        EditPasswordDialog.EditPasswordDialogListener {

    BusSessionManager session ;

    private TextView name_view , email_view , address_view , phone_view , email_headLine , name_headLine ;
    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private String UserID;
    String old_pass = "";

    String Email_Get_2 , Name_Get_2 ;

    String email , name ;

    BusInfo busInfo = new BusInfo();

    private StorageReference mStorageRef;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 71 ;
    private ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_edit_profile);

        session = new BusSessionManager(getApplicationContext());

        name_view = findViewById(R.id.nameTextView_bus);
        email_view = findViewById(R.id.emailTextView_bus);
        address_view = findViewById(R.id.addressTextView_bus);
        phone_view = findViewById(R.id.phoneTextView_bus);
        email_headLine = findViewById(R.id.email_headline_edit_bus);
        name_headLine = findViewById(R.id.name_headline_edit_bus);

        findViewById(R.id.edit_name_bus).setOnClickListener(this);
        findViewById(R.id.edit_email_bus).setOnClickListener(this);
        findViewById(R.id.edit_address_bus).setOnClickListener(this);
        findViewById(R.id.edit_password_bus).setOnClickListener(this);
        findViewById(R.id.edit_phone_bus).setOnClickListener(this);
        findViewById(R.id.back_to_home_btn_bus).setOnClickListener(this);

        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("BusInfo");
        UserID = firebaseDatabase.push().getKey();

        session.checkLogin();

        HashMap<String,String > schoolUser = session.getUserDetails();

        name  = schoolUser.get(BusSessionManager.KEY_NAME);
        email = schoolUser.get(BusSessionManager.KEY_EMAIL);

        name_headLine.setText(name);
        email_headLine.setText(email);



        Email_Get_2  = getIntent().getStringExtra("email_2");
        Name_Get_2 = getIntent().getStringExtra("name_2");

        ReadNiceNameFromFirebase();

        name_view.setText(busInfo.FullName);


        profile_image = findViewById(R.id.profile_img_edit_bus);
        profile_image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                boolean wait = chooseImage();

                if(wait){
                    uploadImage();
                }
            }
        });


        mStorageRef = FirebaseStorage.getInstance().getReference();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private boolean chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);

        return true;
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

                    final String ImageURL = taskSnapshot.getUploadSessionUri().toString();
                    Log.d("Image URL : ",ImageURL);
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


    void ReadNiceNameFromFirebase(){

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){  // row read
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){ // column read
                        if(email.equals(dataSnapshot2.child("Username").getValue(String.class))){
                            busInfo.ID = dataSnapshot2.child("ID").getValue(String.class);
                            busInfo.FullName = dataSnapshot2.child("FullName").getValue(String.class);
                            busInfo.Username = dataSnapshot2.child("Username").getValue(String.class);
                            busInfo.Phone = dataSnapshot2.child("Phone").getValue(String.class);
                            busInfo.Address = dataSnapshot2.child("Address").getValue(String.class);
                        }
                        Log.d("Firebase State","Read Name Successful" +" >> " + busInfo.FullName );
                        Log.d("Firebase State","Read Email Successful" +" >> " + busInfo.Username);
                        Log.d("Firebase State","Read Phone Successful" +" >> " + busInfo.Phone);
                        Log.d("Firebase State","Read Address Successful" +" >> " + busInfo.Address);
                        Log.d("Firebase State","Read ID Successful" +" >> " + busInfo.ID);

                    }
                }
                name_view.setText( busInfo.FullName);
                email_view.setText(busInfo.Username);
                address_view.setText(busInfo.Address);
                phone_view.setText(busInfo.Phone);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }



    public void updateInfo(String filed , String child){
        firebaseDatabase.child("Bus").child(busInfo.ID).child(child).setValue(filed);
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
            case R.id.back_to_home_btn_bus:{
                Intent intent = new Intent(getApplicationContext(), BusProfileActivity.class);
                startActivity(intent);
            }break;

            case R.id.edit_name_bus:{
                OpenEditNameDialog();
            }break;

            case R.id.edit_email_bus:{
                OpenEditEmailDialog();
            }break;

            case R.id.edit_address_bus:{
                OpenEditAddressDialog();
            }break;

            case R.id.edit_phone_bus:{
                OpenEditPhoneDialog();
            }break;

            case R.id.edit_password_bus:{
                OpenEditPasswordDialog();
            }break;


        }
    }

    @Override
    public void TransferNameText(String username) {
      name_view.setText(username);
      name_headLine.setText(username);
      updateInfo(username,"FullName");
    }

    @Override
    public void TransferAddressText(String address) {
        address_view.setText(address);
        updateInfo(address,"Address");
    }

    @Override
    public void TransferEmailText(String Email) {
        email_view.setText(Email);
        email_headLine.setText(Email);
        updateInfo(Email,"Username");
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

