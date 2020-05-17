package com.example.watch.School;

import android.graphics.Bitmap;

import java.util.Random;

public class SchoolInfo {

    public String ID;
    public String NiceName;
    public String Phone;
    public String Email;
    public String Password ;
    public boolean Locked ;


    public SchoolInfo(){

    }

    public SchoolInfo(String niceName , String email , String password , String phone , boolean locked){
        //this.ID = id;
        this.NiceName = niceName;
        this.Phone = phone;
        this.Email = email;
        this.Password = password ;
        this.Locked = locked;

    }

//    public void setLocked(boolean locked){
//        this.Locked = locked;
//    }
//
//    public boolean isLocked(){
//        return Locked ;
//    }



}
