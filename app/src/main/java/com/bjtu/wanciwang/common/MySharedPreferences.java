package com.bjtu.wanciwang.common;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class MySharedPreferences extends AppCompatActivity {
    private static MySharedPreferences singleInstance;
    private static Boolean isFirstIn = true;
    private static Boolean isFirstLogIn = true;
    private static SharedPreferences sharedPreferences;

    private MySharedPreferences(SharedPreferences sharedPreferences) {
        MySharedPreferences.sharedPreferences = sharedPreferences;
    }

    public static MySharedPreferences getSharedPreferences(SharedPreferences sharedPreferences) {
        if (singleInstance == null) {
            singleInstance = new MySharedPreferences(sharedPreferences);
        }
        return singleInstance;
    }

    public Boolean getIsFirstIn() {
        return isFirstIn;
    }

    public void setIsFirstInOne() {
        isFirstIn = sharedPreferences.getBoolean("isFirstIn", true);
    }

    public void setIsFirstInTwo() {
        sharedPreferences.edit().putBoolean("isFirstIn", false).apply();
    }

    public Boolean getIsFirstLogIn() {
        return isFirstLogIn;
    }

    public void setIsFirstLogInOne() {
        isFirstLogIn = sharedPreferences.getBoolean("isFirstLogIn", true);
    }

    public void setIsFirstLogInTwo() {
        sharedPreferences.edit().putBoolean("isFirstLogIn", false).apply();
    }
}
