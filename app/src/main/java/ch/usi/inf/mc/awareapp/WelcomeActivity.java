package ch.usi.inf.mc.awareapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aware.Aware;
import com.aware.Aware_Preferences;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

//import ch.usi.inf.mc.awareapp.Courses.MyScheduler;
import ch.usi.inf.mc.awareapp.Courses.NewScheduler;
import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.ESMClass;
import ch.usi.inf.mc.awareapp.Database.LocalDbUtility;
import ch.usi.inf.mc.awareapp.Database.LocalStorageController;
import ch.usi.inf.mc.awareapp.Database.LocalTables;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.SQLiteController;
import ch.usi.inf.mc.awareapp.Database.SaveSharedPreference;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.RemoteStorage.AlarmReceiver;
import ch.usi.inf.mc.awareapp.RemoteStorage.RemoteStorageController;
import ch.usi.inf.mc.awareapp.RemoteStorage.SwitchDriveController;
import ch.usi.inf.mc.awareapp.RemoteStorage.Uploader;
import ch.usi.inf.mc.awareapp.Settings.ChooseOtherProfilesActivity;
import ch.usi.inf.mc.awareapp.Settings.EditProfileActivity;
import ch.usi.inf.mc.awareapp.Settings.SettingsActivity;

import static android.R.attr.id;
import static ch.usi.inf.mc.awareapp.Database.DatabaseHandler.TABLE_PAM;


public class WelcomeActivity extends ActionBarActivity {

    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 3;
    private static final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 4;

    Button surveysBtn;
    Button yourDataBtn;
    TextView hint;
    ImageButton settingsBtn;
    DatabaseHandler dbHandler;
    TextView usernameLabel;
    NewScheduler scheduler;

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
    final Context context = this;
    String courses;
    NewScheduler newScheduler;
    SaveSharedPreference saveSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        saveSharedPreference = new SaveSharedPreference(WelcomeActivity.this);
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        username = saveSharedPreference.getUsername();

        /************** CHECKING PERMISSIONS **************/
        checkForPermissions();

        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

            /************** JOIN AWARE AND AWARE'S STUDY **************/
            Intent startAware = new Intent(getApplicationContext(), Aware.class);
            startService(startAware);

            Aware.joinStudy(getApplicationContext(), "https://api.awareframework.com/index.php/webservice/index/1096/zZfIitzO9Wb5");
            Intent sync = new Intent(Aware.ACTION_AWARE_SYNC_DATA);
            sendBroadcast(sync);

            //Aware.setSetting(this, Aware_Preferences.STATUS_ESM, true);


            if (!Aware.isStudy(context)) {
                //this will reset and apply the server side settings. It will also trigger the first sync and set the sync schedule etc.
                Aware.joinStudy(getApplicationContext(), "https://api.awareframework.com/index.php/webservice/index/1096/zZfIitzO9Wb5");
            }

            //Trigger schedulers
            triggerSchedulers();

            //Trigger alarm for remote data storing
            triggerAlarm();
        }


        /************** TEST **************/

        System.out.println("Registration data:");
        for (RegistrationClass reg : dbHandler.getAllRegistrations()) {
            System.out.println("android_id: " + reg._android_id + ", username: " + reg._username + ", age: " + reg._age +
                    ", gender: " + reg._gender + ", faculty: " + reg._faculty + ", level: " + reg._levelOfStudies +
                    ", course: " + reg._courses + ", registrationDone: " + reg._registrationDone + ", terms:" + reg._termsCompleted + ", currentTimeAndDate:" + reg._currentDateAndTime);
        }
        System.out.println("ESM data:");
        for (ESMClass esm : dbHandler.getAllESMs()) {
            System.out.println("android_id: " + esm._android_id + ", username: " + esm._username + ", json: " + esm._esm_json);
        }


        /************** DEFINING BUTTONS **************/

        /* Defining "Surveys" button - By clicking it it should lead us to "SurveysActivity" */
        surveysBtn = (Button) findViewById(R.id.surveys_btn);
        surveysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.equals("/")) {
                    Toast.makeText(getApplicationContext(), "No profile is selected. Please choose your profile to access surveys.", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), SurveysActivity.class);
                    startActivity(i);
//                    finish();
                }

            }
        });

        /* Defining "YourData" button */
        yourDataBtn = (Button) findViewById(R.id.your_data_btn);
        yourDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.equals("/")) {
                    Toast.makeText(getApplicationContext(), "No profile is selected. Please choose your profile to access your data.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "This feature will be implemented in next version.", Toast.LENGTH_LONG).show();
//                    Intent i =new Intent (getApplicationContext(), YourDataActivity.class);
//                    startActivity(i);
//                    finish();
                }
            }
        });

//        /*Defining "Settings" button*/
//        settingsBtn = (ImageButton) findViewById(R.id.settings_btn);
//        settingsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });


        System.out.println("Username: "+saveSharedPreference.getUsername());

        //Hide Log in hint if user is logged in
        if(!username.equals("/")){
           hint = (TextView)findViewById(R.id.loginHint);
            hint.setVisibility(View.INVISIBLE);
        }
        //set username label (invisible when user is logged out)
        usernameLabel = (TextView) findViewById(R.id.username_label);
        usernameLabel.setText("User: " + username);
        if(username.equals("/")){
            usernameLabel.setVisibility(View.INVISIBLE);
        }

        if(UserData.general){
            createGeneralSurvey();
            UserData.general = false;
        }

    }

    public void triggerSchedulers() {

        dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        calendar = Calendar.getInstance();
        weekday = dayFormat.format(calendar.getTime());
        newScheduler = new NewScheduler();
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        //Take courses that user choose
        for (RegistrationClass reg : dbHandler.getAllRegistrations()) {
            if (reg._username.equals(username)) { //UserData.Username
                courses = reg._courses;
            }
        }

        //Trigger schedulers only in period between April 10th and May 31th
        if ((month == 4 && dayOfMonth >= 10 && dayOfMonth <= 30) || (month == 5 && dayOfMonth >= 1 && dayOfMonth <= 31)) {

            if (!username.equals("/")) { //Check if user is logged in
                newScheduler.createFirstPAM(getApplicationContext(), courses);
                newScheduler.createFirstPostlecture(getApplicationContext(), courses);
                newScheduler.createSecondPostlecture(getApplicationContext(), courses);
            }
        }
    }

    public void triggerAlarm() {
        //Method for triggering alarms at specified time, so that uploading of the data
        //to remote storage can start

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();
        int hour = 19; //19
        int minute = getRandomNumberInInterval(1, 59);
        System.out.println("Time of data uploading begin: Hour "+hour+", Minute "+minute );

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute); //minute
        cal.set(Calendar.SECOND, 0);

        if (cal.getTimeInMillis() > System.currentTimeMillis()) { //if it is more than 19:00 o'clock, trigger it tomorrow
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date());
            System.out.println(time + ": Alarm should fire in the future");

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String time = sdf.format(new Date());
            System.out.println(time + ": Alarm in the past");
            cal.add(Calendar.DAY_OF_MONTH, 1); //trigger alarm tomorrow

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_ONE_SHOT));
        }
    }

    public void createGeneralSurvey(){

        try {

            ESMFactory factory1 = new ESMFactory();


            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (1/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("When I'm studying, I feel mentally strong.")
                    .setSubmitButton("Next");


            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*30)
                    .setTitle("General Survey (2/29)")
                    .setInstructions("I can continue for a very long time when I am studying.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*30)
                    .setTitle("General Survey (3/29)")
                    .setInstructions("When I study, I feel like I am bursting with energy.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*30)
                    .setTitle("General Survey (4/29)")
                    .setInstructions("When studying I feel strong and vigorous.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (5/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("When I get up in the morning, I feel like going to class.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (6/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I find my studies to be full of meaning and purpose.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio7 = new ESM_Radio();
            esmRadio7.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (7/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("My studies inspire me.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio8 = new ESM_Radio();
            esmRadio8.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (8/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I am enthusiastic about my studies.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio9 = new ESM_Radio();
            esmRadio9.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (9/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I am proud of my studies.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio10 = new ESM_Radio();
            esmRadio10.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (10/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I find my studies challenging.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio11 = new ESM_Radio();
            esmRadio11.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (11/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("Time flies when I'm studying.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio12 = new ESM_Radio();
            esmRadio12.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (12/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("When I am studying, I forget everything else around me.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio13 = new ESM_Radio();
            esmRadio13.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (13/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I feel happy when I am studying intensively.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio14 = new ESM_Radio();
            esmRadio14.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (14/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I can get carried away by my studies.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio15 = new ESM_Radio();
            esmRadio15.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (15/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I pay attention in class.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio16 = new ESM_Radio();
            esmRadio16.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (16/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("IWhen I am in class I behave as if it was my job.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio17 = new ESM_Radio();
            esmRadio17.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (17/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I follow the school's rules.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio18 = new ESM_Radio();
            esmRadio18.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (18/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I have problems with some teachers in school.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio19 = new ESM_Radio();
            esmRadio19.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (19/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I feel happy at this school.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio20 = new ESM_Radio();
            esmRadio20.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (20/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I don't feel very accomplished at this school.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio21 = new ESM_Radio();
            esmRadio21.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (21/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I feel excited by the school work.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio22 = new ESM_Radio();
            esmRadio22.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (22/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I like being at school.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio23 = new ESM_Radio();
            esmRadio23.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (23/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I am interested in the school work.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio24 = new ESM_Radio();
            esmRadio24.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (24/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("My classroom is an interesting place to be.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio25 = new ESM_Radio();
            esmRadio25.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (25/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("When I read a book, I question myself to make sure I understand the subject I’m reading about.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio26 = new ESM_Radio();
            esmRadio26.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (26/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I study at home even when I do not have assessment tests.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio27 = new ESM_Radio();
            esmRadio27.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (27/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I try to watch TV programs on subjects that we are talking about in class.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio28 = new ESM_Radio();
            esmRadio28.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (28/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I check my homework to correct for errors.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio29 = new ESM_Radio();
            esmRadio29.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("General Survey (29/29)")
                    .setExpirationThreshold(60*30)
                    .setInstructions("I read other books or materials to learn more about the subjects we discuss in class.")
                    .setSubmitButton("Done");


            factory1.addESM(esmRadio1);
            factory1.addESM(esmRadio2);
            factory1.addESM(esmRadio3);
            factory1.addESM(esmRadio4);
            factory1.addESM(esmRadio5);
            factory1.addESM(esmRadio6);
            factory1.addESM(esmRadio7);
            factory1.addESM(esmRadio8);
            factory1.addESM(esmRadio9);
            factory1.addESM(esmRadio10);
            factory1.addESM(esmRadio11);
            factory1.addESM(esmRadio12);
            factory1.addESM(esmRadio13);
            factory1.addESM(esmRadio14);
            factory1.addESM(esmRadio15);
            factory1.addESM(esmRadio16);
            factory1.addESM(esmRadio17);
            factory1.addESM(esmRadio18);
            factory1.addESM(esmRadio19);
            factory1.addESM(esmRadio20);
            factory1.addESM(esmRadio21);
            factory1.addESM(esmRadio22);
            factory1.addESM(esmRadio23);
            factory1.addESM(esmRadio24);
            factory1.addESM(esmRadio25);
            factory1.addESM(esmRadio26);
            factory1.addESM(esmRadio27);
            factory1.addESM(esmRadio28);
            factory1.addESM(esmRadio29);


            ESM.queueESM(getApplicationContext(), factory1.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);

        MenuItem add_profile = menu.findItem(R.id.addProfileMenu);

        MenuItem edit_profile = menu.findItem(R.id.editProfileMenu);
        MenuItem choose_profile = menu.findItem(R.id.chooseProfileMenu);
        MenuItem terms = menu.findItem(R.id.termsMenu);
        MenuItem logout = menu.findItem(R.id.logoutMenu);

        add_profile.setVisible(true);
        edit_profile.setVisible(true);
        choose_profile.setVisible(true);
        terms.setVisible(true);
        logout.setVisible(true);

        if (username.equals("/")) {
            terms.setEnabled(false);
            edit_profile.setEnabled(false);
            logout.setEnabled(false);
        } else {
            terms.setEnabled(true);
            edit_profile.setEnabled(true);
            logout.setEnabled(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {

            //Add button
            case R.id.addProfileMenu:

                if (username.equals("/")) {
                    if (dbHandler.getAllRegistrations().size() > 0) { // check the database
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View passwordView = inflater.inflate(R.layout.dialog_password, null);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setView(passwordView);

                        final EditText passwordField = (EditText) passwordView.findViewById(R.id.password_field);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String enteredPassword = passwordField.getText().toString();
                                String adminPassword = "engageit2017";
                                if (enteredPassword.equals(adminPassword)) {

                                    Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error - wrong admin password! Try again!", Toast.LENGTH_SHORT).show();
                                }
                                ;
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog passwordDialog = builder.create();
                        passwordDialog.setTitle("Password check");
                        passwordDialog.show();
                    } else {
                        Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please, first logout in order to add new profile!", Toast.LENGTH_SHORT).show();
                }
                return true;


            //Edit button
            case R.id.editProfileMenu:
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(i);
                finish();

                return true;

            //Choose button
            case R.id.chooseProfileMenu:
                LayoutInflater inflater = LayoutInflater.from(context);
                View passwordView = inflater.inflate(R.layout.dialog_password, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(passwordView);

                final EditText passwordField = (EditText) passwordView.findViewById(R.id.password_field);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String enteredPassword = passwordField.getText().toString();
                        String adminPassword = "engageit2017";
                        if (enteredPassword.equals(adminPassword)) {
                            Intent i = new Intent(getApplicationContext(), ChooseOtherProfilesActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error - wrong admin password! Try again!", Toast.LENGTH_SHORT).show();
                        }
                        ;
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog passwordDialog = builder.create();
                passwordDialog.setTitle("Password check");
                passwordDialog.show();

                return true;

            case R.id.termsMenu:
                Intent intent1 = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(intent1);
                finish();

                return true;

            case R.id.logoutMenu:
                saveSharedPreference.setUsername("/");
                Intent intent3 = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent3);
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent aware = new Intent(this, Aware.class);
        startService(aware);
        Aware.startAWARE(this); //keep everything running on the background
    }

    private void checkForPermissions() {
        // Android 6 (API level 23) now require ACCESS_COARSE_LOCATION permission to use BLE
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        } else if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
        } else if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkForPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Fine Location permission required")
                            .setMessage("Without this permission you cannot continue, allow it in order to be able to use the app.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkForPermissions();
                                    } else {
                                        // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("Exit application", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // without permission exit is the only way
                                    finish();
                                }
                            })
                            .show();
                }
                break;

            case MY_PERMISSIONS_READ_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkForPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Fine Location permission required")
                            .setMessage("Without this permission you cannot continue, allow it in order to be able to use the app.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkForPermissions();
                                    } else {
                                        // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("Exit application", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // without permission exit is the only way
                                    finish();
                                }
                            })
                            .show();
                }
                break;

            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkForPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Fine Location permission required")
                            .setMessage("Without this permission you cannot continue, allow it in order to be able to use the app.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkForPermissions();
                                    } else {
                                        // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("Exit application", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // without permission exit is the only way
                                    finish();
                                }
                            })
                            .show();
                }
                break;

            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted, yay!
                    checkForPermissions();
                } else {
                    // Permission denied, boo!
                    final boolean needRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);
                    new AlertDialog.Builder(this)
                            .setTitle("Access Coarse Location permission required")
                            .setMessage("Without this permission bluetooth low energy devices cannot be found, allow it in order to connect to Empatica E4 device.")
                            .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // try again
                                    if (needRationale) {
                                        // the "never ask again" flash is not set, try again with permission request
                                        checkForPermissions();
                                    } else {
                                        // the "never ask again" flag is set so the permission requests is disabled, try open app settings to enable the permission
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .setNegativeButton("Exit application", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // without permission exit is the only way
                                    finish();
                                }
                            })
                            .show();
                }
                break;
        }
    }

    private int getRandomNumberInInterval(int min, int max) {
        Random r = new Random();
        int ran = r.nextInt(max - min + 1) + min;
        return ran;
    }

}
