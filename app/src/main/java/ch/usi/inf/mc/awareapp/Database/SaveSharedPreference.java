package ch.usi.inf.mc.awareapp.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Danilo on 4/25/17.
 */

public class SaveSharedPreference {

    private SharedPreferences sharedPreferences;
    private final String USERNAME = "USERNAME";
    private final String GENERAL = "GENERAL";

    public SaveSharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("Preferences", 0);
    }

    public void setUsername(String username){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(USERNAME, username);
        edit.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString(USERNAME, "/");
    }

    public void setGeneral(Boolean general){
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(GENERAL, general);
        edit.commit();
    }
    public Boolean getGeneral(){
        return sharedPreferences.getBoolean(GENERAL, false);
    }

}
