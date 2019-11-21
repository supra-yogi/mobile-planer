package com.skripsi.yogi.planner.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Yogi on 08/11/2017.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setEmail(String email) {
        prefs.edit().putString("email", email).apply();
    }

    public void setId(int id) {
        prefs.edit().putInt("id", id).apply();
    }

    public String getEmail() {
        return prefs.getString("email", "");
    }

    public int getId() {
        return prefs.getInt("id", 0);
    }

    public void logOut() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
