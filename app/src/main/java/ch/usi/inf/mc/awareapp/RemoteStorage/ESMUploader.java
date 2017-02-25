package ch.usi.inf.mc.awareapp.RemoteStorage;

import android.app.IntentService;
import android.content.Intent;
import android.provider.Settings;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.ESMClass;
import ch.usi.inf.mc.awareapp.Database.UserData;

/**
 * Created by denzil on 19/02/2017.
 */

public class ESMUploader extends IntentService {

    public static String EXTRA_ESM_DATA = "esm_JSON";
    DatabaseHandler dbHandler;


    public ESMUploader() {
        super("ESMUploader");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());

        String esm_JSON = intent.getStringExtra(EXTRA_ESM_DATA);
        String username = UserData.Username;
        String androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

//        System.out.println("USERNAME: "+ username);
//        System.out.println("ANDROID_ID: "+ androidID);
//        System.out.println("JSON: "+ esm_JSON);

        ESMClass esm = new ESMClass();
        esm._username = username;
        esm._android_id = androidID;
        esm._esm_json = esm_JSON;

        dbHandler.addESM(esm);
    }
}
