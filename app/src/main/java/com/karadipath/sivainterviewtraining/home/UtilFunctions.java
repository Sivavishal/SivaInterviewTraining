package com.karadipath.sivainterviewtraining.home;

import android.content.Context;
import android.content.SharedPreferences;



public class UtilFunctions {

    public static final String NETWORK_UNAVAILABLE = "Network Not Available";
    public static final String SUCCESS = "SUCCESS";
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    public static final String DELETED = "Deleted";
    public static final String PREFS_NAME = "BYDOC";
    public static final String PREFS_USEREMAIL = "USEREMAIL";
    public static final String PREFS_USER_PIN = "USERPIN";





    public static boolean setPreference(Context c, String key, String value) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        // settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        // settings = c.getSharedPreferences(PREFS_NAME, 0);
        String value = settings.getString(key, "");
        return value;
    }

    public static void clearPreference(Context mContext) {
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }





}
