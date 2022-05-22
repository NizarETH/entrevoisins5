package com.openclassrooms.entrevoisins;

import android.app.Application;

import com.openclassrooms.entrevoisins.PreferencesManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PreferencesManager.initializeInstance(this);


    }
}
