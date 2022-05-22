package com.openclassrooms.entrevoisins;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        PreferencesManager.initializeInstance(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(config);

    }
}
