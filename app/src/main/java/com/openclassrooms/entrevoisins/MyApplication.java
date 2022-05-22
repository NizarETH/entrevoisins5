package com.openclassrooms.entrevoisins;

import android.app.Application;


public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PreferencesManager.initializeInstance(this);


    }
}
