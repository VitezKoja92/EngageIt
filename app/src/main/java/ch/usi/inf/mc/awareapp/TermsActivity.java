package ch.usi.inf.mc.awareapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;

public class TermsActivity extends AppCompatActivity{




    private Button acceptBtn;
    private Button refuseBtn;
    Boolean termsAccepted =false;
    DatabaseHandler dbHandler;
    List<RegistrationClass> allRegs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());

        //Username from extras
        final String username = UserData.Username;

        /* Defining "Accept" button - By clicking it we need to save the agreement, and go to the RegistrationActivity*/
        acceptBtn = (Button) findViewById(R.id.accept_btn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //update registration
                RegistrationClass registration = new RegistrationClass();
                for(RegistrationClass reg: dbHandler.getAllRegistrations()){
                    if(reg._username.equals(username)){
                        registration = reg;
                        break;
                    }
                }
                dbHandler.updateRegistration(registration._age,registration._gender, registration._faculty, registration._levelOfStudies
                ,registration._courses, true, true, registration._username, registration._currentDateAndTime);



                RegistrationClass regi = new RegistrationClass();
                for(RegistrationClass reg: dbHandler.getAllRegistrations()){
                    if(reg._username.equals(username)){
                        regi = reg;
                        break;
                    }
                }
                System.out.println("TermsActivity, data saved after the update: ");
                System.out.println(" android_id: "+regi._android_id+", username: "+regi._username+", age: "+regi._age+
                        ", gender: "+regi._gender+", faculty: "+regi._faculty+", level: "+regi._levelOfStudies+
                        ", courses: "+regi._courses+", currentDateAndTime: "+regi._currentDateAndTime+", registrationDone: "+regi._registrationDone+", terms:"+regi._termsCompleted);
                System.out.println("TermsActivity, going to WelcomeActivity!");

                Intent i =new Intent (getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        /* Defining "Refuse" button - by clicking it we need to open the refuse_dialog */
        refuseBtn = (Button)findViewById(R.id.refuse_btn);
        refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TermsActivity.this);
                builder.setMessage(R.string.refuse_text)
                        .setCancelable(true)
                        .setPositiveButton(R.string.refuse_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //update registration
                                RegistrationClass registration = new RegistrationClass();
                                for(RegistrationClass reg: dbHandler.getAllRegistrations()){
                                    if(reg._username.equals(username)){
                                        registration = reg;
                                        break;
                                    }
                                }
                                dbHandler.updateRegistration(registration._age,registration._gender, registration._faculty, registration._levelOfStudies
                                        ,registration._courses, true, true, registration._username, registration._currentDateAndTime);


                                System.out.println("TermsActivity, data saved after the update: ");
                                System.out.println(" android_id: "+registration._android_id+", username: "+registration._username+", age: "+registration._age+
                                        ", gender: "+registration._gender+", faculty: "+registration._faculty+", level: "+registration._levelOfStudies+
                                        ", courses: "+registration._courses+", currentDateAndTime: "+registration._currentDateAndTime+", registrationDone: "+registration._registrationDone+", terms:"+registration._termsCompleted);
                                System.out.println("TermsActivity, going to WelcomeActivity!");

                                Intent i =new Intent(getApplicationContext(), WelcomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.refuse_nok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("TermsActivity, leaving the app as user don't want to accept terms. Data is not updated! termsComplated = false ");
                                dialog.cancel();
                                System.exit(0);
                            }
                        });
                AlertDialog refuseDialog = builder.create();
                refuseDialog.setTitle(R.string.refuse_title);
                refuseDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i =new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume(){ super.onResume();}
    @Override
    protected void onPause(){
        super.onPause();
    }

}
