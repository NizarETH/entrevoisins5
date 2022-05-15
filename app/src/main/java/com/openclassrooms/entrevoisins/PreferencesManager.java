package com.openclassrooms.entrevoisins;


import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesManager {


    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);

    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setStringValue(String key, String value) {
        mPref.edit()
                .putString(key, value)
                .apply();
    }



    public void setIntValue(String key, int value) {
        mPref.edit()
                .putInt(key, value)
                .apply();
    }

    public String getStringValue(String key) {
        return mPref.getString(key, "");
    }


    public String getStringValueDefault(String key, String defValue) {
        return mPref.getString(key, defValue);
    }

    public int getIntValue(String key) {
        return mPref.getInt(key, 0);
    }

    public int getIntValueDefault(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }

    public boolean getBooleanValue(String key) {
        return mPref.getBoolean(key, false);
    }

    public boolean getBooleanValueDefault(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public void setBooleanValue(String key, boolean value) {
        mPref.edit()
                .putBoolean(key, value)
                .apply();
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .apply();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }


}