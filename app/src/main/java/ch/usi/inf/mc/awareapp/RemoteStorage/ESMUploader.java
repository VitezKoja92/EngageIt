package ch.usi.inf.mc.awareapp.RemoteStorage;

import android.app.IntentService;
import android.content.Intent;
import android.provider.Settings;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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



        /* EXTENDED JSON - USE IT AS THE JSON YOU NEED FOR THE VISUALIZATION LATER */
        String fpESM_JSON = esm_JSON.substring(2).replace("\\","").split("\"esm_json\":\"")[0];
        String spESM_JSON = "\"esm_json\": "+esm_JSON.substring(2).replace("\\","").split("\"esm_json\":\"")[1];
        spESM_JSON = spESM_JSON.substring(0,spESM_JSON.length()-3);

//        System.out.println("fpESM_JSON: "+fpESM_JSON);
//        System.out.println("spESM_JSON: "+spESM_JSON);


            /* JSON String that has to be used - extendedJSON */
        String extendedJSON = "[{\"username\":\""+username+"\",\"androidID\":\""+androidID+"\","+fpESM_JSON+spESM_JSON+"}]";



//        System.out.println("ExtendedJSON: "+extendedJSON);

        /* TEST extendedJSON - WORKS */
//        JSONArray json = new JSONArray();
//        try {
//            json = new JSONArray(extendedJSON);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        try {
//            System.out.println("JSON esm_title: "+json.getJSONObject(0).getJSONObject("esm_json").getString("esm_title"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        /* EXTENDED JSON - END*/


        ESMClass esm = new ESMClass();
        esm._username = username;
        esm._android_id = androidID;
        esm._esm_json = esm_JSON;

        dbHandler.addESM(esm);
    }
}
