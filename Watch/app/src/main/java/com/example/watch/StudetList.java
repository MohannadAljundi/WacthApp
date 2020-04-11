package com.example.watch;

import android.content.res.Resources;

import java.util.ArrayList;

public class StudetList {


    private String name;
    private Class activityClass;

    private StudetList(String name, Class activityClass) {
        this.name = name;
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    Class getActivityClass() {
        return activityClass;
    }

    static ArrayList<StudetList> createChartList(Resources resources) {
        ArrayList<StudetList> chartList = new ArrayList<>();



        return chartList;
    }

}

