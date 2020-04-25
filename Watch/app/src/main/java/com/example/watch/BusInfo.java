package com.example.watch;

import java.util.Random;

public class BusInfo {

    public String ID;
    public String FullName;
    public String Phone;
    public String BusNumber;
    public String PlateNumber;
    public String Age ;


    public BusInfo(){}

    public BusInfo(String fullName , String phone , String busNumber , String plateNumber , String age){
        ID = IDGenerator();
        this.FullName = fullName;
        this.Phone = phone;
        this.BusNumber = busNumber;
        this.PlateNumber = plateNumber;
        this.Age = age;
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
