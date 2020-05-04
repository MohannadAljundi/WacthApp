package com.example.watch.Student;

import android.graphics.Bitmap;

import java.util.Random;

public class StudentInfo {
    public String ID;
    public String FullName;
    public String Phone;
    public String BusNumber;
    public String Age ;
    public String ClassNo;
    public String BloodType;
    public Bitmap StudentQR;

    public StudentInfo(){

    }

    public StudentInfo(String fullName , String phone , String busNumber , String age ,
                       String classNo , String bloodType ){
        //ID = IDGenerator();
        this.FullName = fullName;
        this.Phone = phone;
        this.BusNumber = busNumber;
        this.Age = age ;
        this.ClassNo = classNo;
        this.BloodType = bloodType;
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
