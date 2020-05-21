package com.example.watch.Student;

import android.graphics.Bitmap;

import java.util.List;
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
    public String StudentQR_str;
    public String Content;
    public List<Bitmap> StudentQR_List;
    public String Username;
    public String Password;
    public String Address;

    public StudentInfo(){

    }

    public StudentInfo(String id ,String fullName ,String username, String password, String phone , String busNumber , String age ,
                       String classNo , String bloodType ,String address ,Bitmap studentQR ){
        this.ID = id;
        this.FullName = fullName;
        this.Username = username;
        this.Password = password;
        this.Phone = phone;
        this.BusNumber = busNumber;
        this.Age = age ;
        this.ClassNo = classNo;
        this.BloodType = bloodType;
        this.Address = address;
        this.StudentQR_str = studentQR.toString();
    }



    public String UsernameGenerator(String name){
        String username = name.replace(" ","_").toLowerCase();
        String Signature = "@watch.com";
        this.Username = username;
        return  username + Signature;
    }

    public String PasswordGenerator(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        this.Password = buffer.toString();
        return buffer.toString();
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
