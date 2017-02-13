package ch.usi.inf.mc.awareapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
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
import ch.usi.inf.mc.awareapp.Database.LocalStorageController;
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
    SQLiteController sqLiteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        //Remote storing of the data
        switchDriveController = new SwitchDriveController(getString(R.string.server_address), getString(R.string.token), getString(R.string.password));
        sqLiteController = new SQLiteController(getApplicationContext());

        Uploader uploader = new Uploader(androidID, switchDriveController, sqLiteController);
        uploader.upload();

        Aware.startESM(this);
        Aware.startScheduler(this);

        dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        calendar = Calendar.getInstance();
        weekday = dayFormat.format(calendar.getTime());
        scheduler = new MyScheduler();


        //Triggering schedulers
        if(!UserData.Username.equals("/")){
            scheduler.createFirstPAM(UserData.SelectedCourses, this);
            scheduler.createSecondPAM(UserData.SelectedCourses, this);
            scheduler.createThirdPAM(UserData.SelectedCourses, this);
//            scheduler.createFirstPostLectureESM(this,UserData.SelectedCourses, weekday);
//            scheduler.createSecondPostLectureESM(this,UserData.SelectedCourses, weekday);
        }

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        username = UserData.Username;

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
            }
        });
    }

}
