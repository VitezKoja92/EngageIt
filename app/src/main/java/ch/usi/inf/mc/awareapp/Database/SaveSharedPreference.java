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

}
