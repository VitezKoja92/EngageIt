package ch.usi.inf.mc.awareapp.RemoteStorage;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;

import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.ESMClass;
import ch.usi.inf.mc.awareapp.Database.UserData;

/**
 * Created by denzil on 19/02/2017.
 */

public class ESMUploader extends IntentService {

    public static String EXTRA_ESM_DATA = "esm_JSON";
    DatabaseHandler dbHandler;
    final Context context = this;
    String extendedJSON;
    final String IP = "127.0.0.1";
    final int PORT = 27017;
    JSONObject jsonReceived;
    SimpleDateFormat dateFormat;
    String time;
    String info = "";
    String question = "";
    String answer= "";
    JSONObject jsonToSend;



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
        extendedJSON = "{\"username\":\""+username+"\",\"androidID\":\""+androidID+"\","+fpESM_JSON+spESM_JSON+"}";
        try{
            jsonReceived = new JSONObject(extendedJSON);
            info = jsonReceived.getJSONObject("esm_json").getString("esm_title");
            question = jsonReceived.getJSONObject("esm_json").getString("esm_instructions");
            answer = jsonReceived.getString("esm_user_answer");
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = dateFormat.format(new Date());
            String stringToSend  ="{\"username\":\""+username+"\",\"androidID\":\"" + androidID + "\",\"time\":\"" + time + "\",\"info\":\"" + info + "\",\"question\":\"" + question + "\",\"answer\":\"" + answer + "\"}";

            jsonToSend = new JSONObject(stringToSend);

//            MongoClient mongo = new MongoClient(IP, PORT);
//            DB db = mongo.getDB("androidDatabase");
//            DBCollection collection = db.getCollection("androidDatabase");
//
//            DBObject dataToSend = new BasicDBObject().append("username", username)
//                                                    .append("androidId", androidID)
//                                                    .append("time", time)
//                                                    .append("info", info)
//                                                    .append("question", question)
//                                                    .append("answer", answer);
//            collection.insert(dataToSend);

            //test
//            System.out.println("jsonToSend: "+jsonToSend.toString());
//            System.out.println("username:"+jsonToSend.getString("username"));
//            System.out.println("androidID:"+jsonToSend.getString("androidID"));
//            System.out.println("time:"+jsonToSend.getString("time"));
//            System.out.println("info:"+jsonToSend.getString("info"));
//            System.out.println("question:"+jsonToSend.getString("question"));
//            System.out.println("answer:"+jsonToSend.getString("answer"));

        }catch (Exception e){
            System.out.println("ERROR WHILE SENDING THE DATA!");
        }





        ESMClass esm = new ESMClass();
        esm._username = username;
        esm._android_id = androidID;
        esm._esm_json = esm_JSON;

        dbHandler.addESM(esm);


        //Post data to server
//        try{
//            postData("http://192.168.0.12:3000/questions");
//
//        }catch(IOException ex){
//             System.out.println("Network error!");
//
//        }catch (JSONException ex){
//            System.out.println("Data invalid!");
//        }


    }



    public String postData(String urlPath) throws IOException, JSONException{

        StringBuilder result = new StringBuilder();
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;

        try{
            //Data to send
            JSONObject  dataToSend = new JSONObject(extendedJSON); //check if extendedJSON is right

            //Initialize and config request, and connect to the server
            URL url = new URL(urlPath);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000); //10sec
            urlConnection.setConnectTimeout(10000); //10sec
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); //enables output
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.connect();

            //Write data into server
            OutputStream outputStream = urlConnection.getOutputStream();
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(dataToSend.toString());
            bufferedWriter.flush();

            //Read data response from server
            InputStream inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null){
                result.append(line).append("\n");
            }
        }finally {
            System.out.println("Response from server: " +result);
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
        }

        return result.toString();
    }
}
