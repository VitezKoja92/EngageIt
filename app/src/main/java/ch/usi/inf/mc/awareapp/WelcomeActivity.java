package ch.usi.inf.mc.awareapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aware.Aware;
import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;
import com.aware.utils.Scheduler;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ch.usi.inf.mc.awareapp.Courses.MyScheduler;
import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.LocalDbUtility;
import ch.usi.inf.mc.awareapp.Database.LocalStorageController;
import ch.usi.inf.mc.awareapp.Database.LocalTables;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.SQLiteController;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.RemoteStorage.RemoteStorageController;
import ch.usi.inf.mc.awareapp.RemoteStorage.SwitchDriveController;
import ch.usi.inf.mc.awareapp.RemoteStorage.Uploader;
import ch.usi.inf.mc.awareapp.Settings.SettingsActivity;


public class WelcomeActivity extends Activity {

    Button surveysBtn;
    Button yourDataBtn;
    ImageButton settingsBtn;
    DatabaseHandler dbHandler;
    TextView usernameLabel;
    MyScheduler scheduler;

    String course;
    int minutes;
    int hours;
    String username;
    String weekday;
    Calendar calendar;
    SimpleDateFormat dayFormat;
    String androidID;
    SwitchDriveController switchDriveController;
    SQLiteController localController;
    int month;
    int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        username = UserData.Username;

        //Join AWARE and Aware study
//        Intent startAware = new Intent(getApplicationContext(), Aware.class);
//        startService(startAware);
        Aware.startESM(this);
        Aware.startScheduler(this);

//        Aware.joinStudy(getApplicationContext(), "https://api.awareframework.com/index.php/webservice/index/1096/zZfIitzO9Wb5");
//        Intent sync = new Intent(Aware.ACTION_AWARE_SYNC_DATA);
//        sendBroadcast(sync);


        /************** REMOTE STORAGE - BEGIN **************/

        //Remote storing of the data
        switchDriveController = new SwitchDriveController(getString(R.string.server_address), getString(R.string.token), getString(R.string.password));
        localController = new SQLiteController(getApplicationContext());


        //number of tables
        int nbTableToClean = LocalTables.values().length;
        int i = 0;
        Cursor c;
        //current table to clean
        LocalTables currTable;
        String fileName;

        while(i < nbTableToClean) {
            currTable = LocalTables.values()[i];
            //build name of file to upload
            fileName = buildFileName(currTable);

            //get all data currently in the table
            c = getRecords(currTable);

            if (c.getCount() > 0) {
                c.moveToFirst();

                //upload the data to the server
                int response = switchDriveController.upload(fileName, toCSV(c, currTable));

                //if the file was put, delete records and update the arrays
                if (response >= 200 && response <= 207) {
                    //delete from the db the records where id > startId and id <= endId !!!!!!!!!!!
                } else {
                    Log.d("DATA UPLOAD SERVICE", "Owncould's response: " + Integer.toString(response));
                }
            }
            i++;
        }
        /************** REMOTE STORAGE - END **************/



        /************** TRIGGER SCHEDULERS - BEGIN **************/

        dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        calendar = Calendar.getInstance();
        weekday = dayFormat.format(calendar.getTime());
        scheduler = new MyScheduler();
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        //Trigger schedulers only in period between February 20th and March 12th
        if((month == 2 && dayOfMonth >=20 && dayOfMonth <= 29) || (month == 3 && dayOfMonth >=1 && dayOfMonth <= 12)){
            //Triggering schedulers
            if(!UserData.Username.equals("/")){
//                scheduler.createFirstPAM(UserData.SelectedCourses, this);
//                scheduler.createSecondPAM(UserData.SelectedCourses, this);
//                scheduler.createThirdPAM(UserData.SelectedCourses, this);
//                scheduler.createFirstPostLectureESM(this,UserData.SelectedCourses);
//                scheduler.createSecondPostLectureESM(this,UserData.SelectedCourses);
            }
        }

        /************** TRIGGER SCHEDULERS - END **************/




        usernameLabel = (TextView)findViewById(R.id.username_label);
        usernameLabel.setText("User: "+ username);

        System.out.println("I am in WelcomeActivity!");
        for(RegistrationClass reg: dbHandler.getAllRegistrations()){
            System.out.println(" android_id: "+reg._android_id+", username: "+reg._username+", age: "+reg._age+
                    ", gender: "+reg._gender+", faculty: "+reg._faculty+", level: "+reg._levelOfStudies+
                    ", course: "+reg._courses+", registrationDone: "+reg._registrationDone+", terms:"+reg._termsCompleted+", currentTimeAndDate:"+reg._currentDateAndTime);
        }
        System.out.println("Username from UserData: "+ UserData.Username);
        System.out.println("Courses from UserData: "+ UserData.SelectedCourses);




        /************** DEFINING BUTTONS - BEGIN **************/

        /* Defining "Surveys" button - By clicking it it should lead us to "SurveysActivity" */
        surveysBtn = (Button)findViewById(R.id.surveys_btn);
        surveysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.equals("/")){
                    Toast.makeText(getApplicationContext(), "No profile is selected. Please choose your profile to access surveys.", Toast.LENGTH_LONG).show();
                }else{
                    Intent i =new Intent (getApplicationContext(), SurveysActivity.class);
                    startActivity(i);
//                    finish();
                }

            }
        });

        /* Defining "YourData" button */
        yourDataBtn = (Button)findViewById(R.id.your_data_btn);
        yourDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.equals("/")){
                    Toast.makeText(getApplicationContext(), "No profile is selected. Please choose your profile to access your data.", Toast.LENGTH_LONG).show();
                }else{
//                    Intent i =new Intent (getApplicationContext(), YourDataActivity.class);
//                    startActivity(i);
//                    finish();
                }
            }
        });

        /*Defining "Settings" button*/
        settingsBtn = (ImageButton)findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                finish();
            }
        });
        /************** DEFINING BUTTONS - END **************/
    }


    @Override
    public void onBackPressed() {

//        System.exit(0);
        finish();
//        super.onBackPressed();
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
        return androidID + "_" + today + "_" + LocalDbUtility.getTableName(table) + "_" + UserData.Username + ".csv";
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

    /************** OBSERVE CHANGES IN AWARE DATABASE - BEGIN **************/

//    @Override
//    protected void onResume(){
//        getContentResolver().registerContentObserver();
//    }
//
//    protected void onDestroy(){
//        getContentResolver().unregisterContentObserver();
//    }


    /************** OBSERVE CHANGES IN AWARE DATABASE - END **************/

}
