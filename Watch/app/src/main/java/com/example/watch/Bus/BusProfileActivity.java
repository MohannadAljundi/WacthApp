package com.example.watch.Bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.watch.MainActivity;
import com.example.watch.R;
import com.example.watch.SettingsActivity;

public class BusProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_profile);
        findViewById(R.id.qr_cod_profile_bus).setOnClickListener(this);
        findViewById(R.id.student_home_profile_bus).setOnClickListener(this);
        findViewById(R.id.student_on_board_profile_bus).setOnClickListener(this);
        findViewById(R.id.go_back_bus_profile).setOnClickListener(this);
        findViewById(R.id.btn_setting_profile_bus).setOnClickListener(this);
        findViewById(R.id.student_on_board_profile_bus).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qr_cod_profile_bus: {

                Intent i = new Intent(getApplicationContext(), ScanQRActivity.class);
                startActivity(i);


            }
            break;
            case R.id.go_back_bus_profile: {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);


            }
            break;
            case R.id.btn_setting_profile_bus:{
                Intent i=new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);

            }break;
            case R.id.student_home_profile_bus:{
                Intent i=new Intent(getApplicationContext(), StudentHomeActivity.class);
                startActivity(i);

            }break;
            case R.id.student_on_board_profile_bus:{
                Intent i=new Intent(getApplicationContext(), StudentOnBoardActivity.class);
                startActivity(i);

            }break;

        }
    }

}
