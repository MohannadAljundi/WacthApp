package com.example.watch;

import android.graphics.Bitmap;

import java.util.Random;

public class StudentInfo {
    private String ID;
    private String FullName;
    private String Phone;
    private String BusNumber;
    private String Age ;
    private String ClassNo;
    private String BloodType;
    private Bitmap StudentQR;

    public StudentInfo(){

    }

    public StudentInfo(String fullName , String phone , String busNumber , String age ,
                       String classNo , String bloodType , Bitmap studentQR){
        ID = IDGenerator();
        this.FullName = fullName;
        this.Phone = phone;
        this.BusNumber = busNumber;
        this.Age = age ;
        this.ClassNo = classNo;
        this.BloodType = bloodType;
        this.StudentQR = studentQR;
    }


    private String IDGenerator(){

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }





}
