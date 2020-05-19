package com.example.watch.School;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.watch.QRCodeHelper;
import com.example.watch.R;
import com.example.watch.Student.StudentInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewStudentActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseInstance;
    private DatabaseReference firebaseDatabase;
    private EditText User_FullName , User_BusNo ,
            User_Age, User_ClassNo , User_BloodType , User_Phone ;
    private Button btnRQ , banConfirm ;
    public String  UserID , Full_name , bus_no , class_no , blood_type , phone , age;
    private QRCodeHelper qrCodeHelper;
    StudentInfo studentInfo;

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
        banConfirm = findViewById(R.id.conf_butt);




        firebaseInstance = FirebaseDatabase.getInstance();
        firebaseDatabase = firebaseInstance.getReference("StudentInfo");
        UserID = firebaseDatabase.push().getKey();

        banConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddStudentToFirebase();
                Toast.makeText(getApplicationContext(), "Saving Data ..", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void AddStudentToFirebase(){
        Full_name = User_FullName.getText().toString();
        bus_no = User_BusNo.getText().toString();
        class_no = User_ClassNo.getText().toString();
        blood_type = User_BloodType.getText().toString();
        phone = User_Phone.getText().toString();
        age = User_Age.getText().toString();

         studentInfo = new StudentInfo(Full_name,phone,bus_no,age
                ,class_no,blood_type,studentInfo.StudentQR);
        firebaseDatabase.child("Student").child(UserID).setValue(studentInfo);


    }

    public void GenerateQR(View view){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        Full_name = User_FullName.getText().toString();
        bus_no = User_BusNo.getText().toString();
        class_no = User_ClassNo.getText().toString();
        blood_type = User_BloodType.getText().toString();
        phone = User_Phone.getText().toString();
        age = User_Age.getText().toString();

        studentInfo.Content = Full_name + " , " + bus_no + " , "
                + phone + " , " + age + " , " +
                class_no + " , " + blood_type ;

        Log.d("Content",studentInfo.Content);
        qrCodeHelper.setContent(studentInfo.Content);
        qrCodeHelper.setWidthAndHeight(160,160);
        qrCodeHelper.setMargin(2);
        studentInfo.StudentQR = qrCodeHelper.getQRCOde();
        Toast.makeText(getApplicationContext(),"QR is Generated ",Toast.LENGTH_LONG).show();
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
