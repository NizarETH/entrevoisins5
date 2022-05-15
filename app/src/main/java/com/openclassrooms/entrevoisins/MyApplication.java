package com.openclassrooms.entrevoisins;

import android.app.Application;

import com.openclassrooms.entrevoisins.PreferencesManager;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PreferencesManager.initializeInstance(this);

    }
}
