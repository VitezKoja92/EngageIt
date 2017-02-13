package ch.usi.inf.mc.awareapp.PhotoGrid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.PAMClass;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;
import ch.usi.inf.mc.awareapp.WelcomeActivity;

public class PhotoGridActivity extends AppCompatActivity {



    public String imageDescription;

    public DatabaseHandler dbHandler;
    public String android;
    public Boolean onButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);


        android = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
//        try{
//            onButton = getIntent().getExtras().getBoolean("onButton");
//        }catch (Exception e){
//            System.out.println("We cannot get boolean value 'onButton' from intent");
//        }



        //Perform the vibration - if it is triggered onButton click, don't perform it
       // if(!onButton){
            Vibrator mVibrator  = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
            int vsLength = 400;
            int bsLength = 100;
            int bbLength = 1000;
            long[] vPattern = {
                    0,  vsLength, bsLength, vsLength, bsLength, vsLength,
                    bbLength,
                    vsLength, bsLength, vsLength, bsLength, vsLength
            };
            mVibrator.vibrate(vPattern, -1);
      //  }


        //Attendance question before the gridview
        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoGridActivity.this);
        builder.setMessage(R.string.attend_text)
                .setCancelable(true)
                .setPositiveButton(R.string.attend_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.attend_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "See you next time!", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivity(i);
                    }
                });
        AlertDialog refuseDialog = builder.create();
        refuseDialog.setTitle(R.string.attend_title);
        refuseDialog.show();



        //PAM gridview
        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        imageDescription = "Afraid";
                        break;
                    case 1:
                        imageDescription = "Tense";
                        break;
                    case 2:
                        imageDescription = "Excited";
                        break;
                    case 3:
                        imageDescription = "Delighted";
                        break;
                    case 4:
                        imageDescription = "Frustrated";
                        break;
                    case 5:
                        imageDescription = "Angry";
                        break;
                    case 6:
                        imageDescription = "Happy";
                        break;
                    case 7:
                        imageDescription = "Glad";
                        break;
                    case 8:
                        imageDescription = "Miserable";
                        break;
                    case 9:
                        imageDescription = "Sad";
                        break;
                    case 10:
                        imageDescription = "Calm";
                        break;
                    case 11:
                        imageDescription = "Satisfied";
                        break;
                    case 12:
                        imageDescription = "Gloomy";
                        break;
                    case 13:
                        imageDescription = "Tired";
                        break;
                    case 14:
                        imageDescription = "Sleepy";
                        break;
                    case 15:
                        imageDescription = "Serene";
                        break;
                    default:
                        imageDescription = "Error!";
                        break;
                }


                //save the PAM in database

                //CurrentDate - Timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());


                PAMClass pam = new PAMClass();

                pam._username = ""; //add username here
                pam._android_id = android;
                pam._attendance = true;
                pam._pam_answer = imageDescription;
                pam._currentDateAndTime = currentDateAndTime;

                dbHandler.addPAM(pam);

                Toast.makeText(getApplicationContext(), "PAM saved successfully!", Toast.LENGTH_SHORT).show();

                //leave the grid after toast, maybe with System.exit(0);
                Intent i =new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
                finish();

            }
        });



    }
}
