package com.example.watch.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.watch.MainActivity;
import com.example.watch.Student.StudenLoginActivity;

import java.util.HashMap;

public class StudentSessionManager {

    // Shared Preferences
    SharedPreferences pref_student;

    // Editor for Shared preferences
    SharedPreferences.Editor editor_student;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "Pref_student";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn_student";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name_student";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email_student";


    // Constructor
    public StudentSessionManager(Context context){
        this._context = context;
        pref_student = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor_student = pref_student.edit();
    }



    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor_student.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor_student.putString(KEY_NAME, name);

        // Storing email in pref
        editor_student.putString(KEY_EMAIL, email);

        // commit changes
        editor_student.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, StudenLoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref_student.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref_student.getString(KEY_EMAIL, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor_student.clear();
        editor_student.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref_student.getBoolean(IS_LOGIN, false);
    }
}