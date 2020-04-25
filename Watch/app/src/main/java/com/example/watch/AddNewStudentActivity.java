package com.example.watch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewStudentActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private EditText User_FullName , User_BusNo ,
            User_Age, User_ClassNo , User_BloodType , User_Phone ;
    private Button btnRQ , banConfirm ;
    private String  UserID , Full_name , bus_no , class_no , blood_type , phone , age;
    private Bitmap student_qr;
    private StudentInfo studentInfo;
    private QRCodeHelper qrCodeHelper;
    private String Content ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        findViewById(R.id.bktomain).setOnClickListener(this);

        User_FullName = findViewById(R.id.name_add_student);
        User_BusNo = findViewById(R.id.bus_number_add_student);
        User_Age = findViewById(R.id.age_add_student);
        User_ClassNo = findViewById(R.id.Class_add_student);
        User_BloodType = findViewById(R.id.blood_type_add_student);
        User_Phone = findViewById(R.id.user_phone_add_student);




        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("StudentInfo");
        UserID = firebaseDatabase.push().getKey();

    }

    public void GenerateQR(View view){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        Full_name = User_FullName.getText().toString();
        bus_no = User_BusNo.getText().toString();
        class_no = User_ClassNo.getText().toString();
        blood_type = User_BloodType.getText().toString();
        phone = User_Phone.getText().toString();
        age = User_Age.getText().toString();

        Content = Full_name + " , " + bus_no + " , "
                + phone + " , " + age + " , " +
                class_no + " , " + blood_type ;

//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
//                Toast.makeText(getApplicationContext(),"Generating ...",Toast.LENGTH_LONG).show();
//                Log.d("Tag","Generating ....");
//
//            }
//            }, 1000);


        Log.d("Content",Content);
        qrCodeHelper.setContent(Content);
        qrCodeHelper.setWidthAndHeight(160,160);
        qrCodeHelper.setMargin(2);
        student_qr = qrCodeHelper.getQRCOde();
        Toast.makeText(getApplicationContext(),"QR is Generated ",Toast.LENGTH_LONG).show();
    }

    public void AddStudentToFirebase(View view){

        if(student_qr != null){
            studentInfo = new StudentInfo(Full_name,phone,bus_no,age
                    ,class_no,blood_type,student_qr);
        }else {
            Toast.makeText(getApplicationContext(),"Please Click On Generate QR ",Toast.LENGTH_LONG).show();
        }
//        Full_name = User_FullName.getText().toString();
//        bus_no = User_BusNo.getText().toString();
//        class_no = User_ClassNo.getText().toString();
//        blood_type = User_BloodType.getText().toString();
//        phone = User_Phone.getText().toString();
//        age = User_Age.getText().toString();
        student_qr = null;
        studentInfo = new StudentInfo(Full_name,phone,bus_no,age
                    ,class_no,blood_type,student_qr);
        firebaseDatabase.child("Student").child(UserID).setValue(studentInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bktomain:{
                Intent go = new Intent(AddNewStudentActivity.this, AddNewMemberSchoolActivity.class);
                startActivity(go);
            }break;
        }
    }
}
