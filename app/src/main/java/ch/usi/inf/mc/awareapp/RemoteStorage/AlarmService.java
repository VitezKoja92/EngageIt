package ch.usi.inf.mc.awareapp.RemoteStorage;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ch.usi.inf.mc.awareapp.Courses.MyScheduler;
import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.ESMClass;
import ch.usi.inf.mc.awareapp.Database.LocalDbUtility;
import ch.usi.inf.mc.awareapp.Database.LocalTables;
import ch.usi.inf.mc.awareapp.Database.SQLiteController;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;

/**
 * Created by Danilo on 2/25/17.
 */

public class AlarmService extends IntentService {

    SwitchDriveController switchDriveController;
    SQLiteController localController;
    DatabaseHandler dbHandler;
    String androidID;
    SimpleDateFormat dayFormat;
    Calendar calendar;
    int month;
    int dayOfMonth;
    String weekday;
    NetworkInfo mWifi;
    Timer timer;

    public AlarmService() {
        super("UploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //Remote storing of the data
        switchDriveController = new SwitchDriveController(getString(R.string.server_address), getString(R.string.token), getString(R.string.password));
        localController = new SQLiteController(getApplicationContext());
        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        /************** REMOTE STORAGE - BEGIN **************/

        dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        calendar = Calendar.getInstance();
        weekday = dayFormat.format(calendar.getTime());
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);



        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());

        //System.out.println(time + ": I am in AlarmService");

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                ConnectivityManager connManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = sdf.format(new Date());
                //System.out.println(time +":In timer");

                //cancel if it is after 21:00
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, 21);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                if(System.currentTimeMillis() > cal.getTimeInMillis()){
                    //System.out.println("Timer in canceled");
                    //timer.cancel();
                    //timer.purge();
                }

                if ((month == 3 && dayOfMonth >= 1 && dayOfMonth <= 31) || (month == 3 && dayOfMonth >= 1 && dayOfMonth <= 31)) { //check time interval

                    if (mWifi.isConnected()) { //check wi-fi connection
                        //System.out.println("I am in AlarmService, WiFi is connected");

                        //number of tables
                        int nbTableToClean = LocalTables.values().length; //2
                        int i = 0;
                        Cursor c;
                        //current table to clean
                        LocalTables currTable;
                        String fileName;

                        while(i < nbTableToClean) {
                            currTable = LocalTables.values()[i]; //ESMTable and RegistrationTable
                            //build name of file to upload
                            fileName = buildFileName(currTable);

                            //get all data currently in the table
                            c = getRecords(currTable);

                            if (c.getCount() >= 1) {
                                c.moveToFirst();
                                //upload the data to the server
                                int response = switchDriveController.upload(fileName, toCSV(c, currTable));

                                //if the file was put, delete records and update the arrays
                                if (response < 200 || response > 207) {
                                    Log.d("DATA UPLOAD SERVICE", "Owncould's response: " + Integer.toString(response));
                                }else{
                                    System.out.println("Data uploaded. Timer is canceled");
                                    timer.cancel();
                                    timer.purge();
                                }
                            }
                            i++;
                        }
//                        for(ESMClass esm: dbHandler.getAllESMs()){
//                            System.out.println("ESM database before deletion: android_id: "+esm._android_id+", username: "+esm._username+", json: "+esm._esm_json);
//                        }
//
//                        //dbHandler.deleteESM();
//
//                        //test
//                        for(ESMClass esm: dbHandler.getAllESMs()){
//                            System.out.println("ESM database after deletion: android_id: "+esm._android_id+", username: "+esm._username+", json: "+esm._esm_json);
//                        }
                    }else{
                        System.out.println("No Wi-Fi");
                    }
                }
            }
        }, 0, 1000*60*10); //check everything every 10 minutes. Purge and cancel in case it is uploaded successfully

    }





    //Methods for making remote storage works
    private String toCSV(Cursor records, LocalTables table) {
        String csv = "";
        String[] columns = LocalDbUtility.getTableColumns(table);

        for(int i = 0; i < columns.length; i++) {
            csv += columns[i] + ",";
        }
        csv = csv.substring(0, csv.length()-1);
        csv += "\n";
        do {
            for(int i = 0; i < columns.length; i++) {
                csv += records.getString(i) + ",";
            }
            csv = csv.substring(0, csv.length()-1);
            csv += "\n";
        } while(records.moveToNext());
        csv = csv.substring(0, csv.length()-1);
        return csv;
    }


    private String buildFileName(LocalTables table) {
        //get current date
        String today = buildDate();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        return androidID + "_" +time+"_"+ today + "_" + LocalDbUtility.getTableName(table) + "_" + UserData.Username + ".csv";
    }


    private String buildDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM-dd-yyyy");
        return mdformat.format(calendar.getTime());
    }


    private Cursor getRecords(LocalTables table) {
        String query = "SELECT * FROM " + LocalDbUtility.getTableName(table);
//                " WHERE " + LocalDbUtility.getTableColumns(table)[0] + " > " + Integer.toString(getRecordId(table));
        return localController.rawQuery(query, null);
    }
}
