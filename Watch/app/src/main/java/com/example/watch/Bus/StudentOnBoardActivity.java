package com.example.watch.Bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.Bus.BusProfileActivity;
import com.example.watch.R;

public class StudentOnBoardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_on_board);
        findViewById(R.id.back_to_bus_profile_bord2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_to_bus_profile_bord2:{
                Intent i=new Intent(getApplicationContext(), BusProfileActivity.class);
                startActivity(i);

            }break;
        }

    }
}

