package com.example.watch.Bus;

import java.util.Random;

public class BusInfo {

    public String ID;
    public String FullName;
    public String Username;
    public String Password;
    public String Phone;
    public String BusNumber;
    public String PlateNumber;
    public String Age ;
    public String Address ;


    public BusInfo(){}

    public BusInfo(String id ,String username,String password,String fullName , String phone , String busNumber , String plateNumber , String age ,String address){
        this.ID = id ;
        this.FullName = fullName;
        this.Username = username;
        this.Password = password;
        this.Phone = phone;
        this.BusNumber = busNumber;
        this.PlateNumber = plateNumber;
        this.Age = age;
        this.Address = address;
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
