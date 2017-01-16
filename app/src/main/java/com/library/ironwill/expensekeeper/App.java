package com.library.ironwill.expensekeeper;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

public class App extends Application {

    public static Boolean isCalendarIcon = true;

    @Override
    public void onCreate() {
        super.onCreate();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
