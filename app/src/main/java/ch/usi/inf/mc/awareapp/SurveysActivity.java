package ch.usi.inf.mc.awareapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_Likert;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONException;

import java.util.ArrayList;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.Settings.ChooseOtherProfilesActivity;
import ch.usi.inf.mc.awareapp.Settings.EditProfileActivity;


public class SurveysActivity extends AppCompatActivity {

    private Button generalQuestionnaireBtn;
    private Button postLectureBtn;
    DatabaseHandler dbHandler;
    String androidID;
    Double timestampFirst;
    private Button pamBtn;
    ImageButton goToWelcome;
    final Context context = this;


    private Intent awareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);


        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);




        /********** DEFINING HOME BUTTON **********/
        goToWelcome = (ImageButton)findViewById(R.id.welcome);
        goToWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        });



        /********** DEFINING GENERAL QUESTIONNAIRE BUTTON **********/
        generalQuestionnaireBtn = (Button) findViewById(R.id.general_survey_btn);
        generalQuestionnaireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });



        /********** DEFINING PAM BUTTON **********/
        pamBtn = (Button) findViewById(R.id.pam_survey_btn);
        pamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ESMFactory factory2 = new ESMFactory();

                    ESM_PAM q1 = new ESM_PAM();
                    q1.setTitle("PAM")
                            .setInstructions("Pick the closest to how you feel now before the lecture starts!")
                            .setSubmitButton("Done")
                            .setExpirationThreshold(60*30); //setNotificationRetry(3) - number of times to retry notification if it expires

                    factory2.addESM(q1);
                    ESM.queueESM(getApplicationContext(), factory2.build());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        /********** DEFINING POST-LECTURE BUTTON **********/
        postLectureBtn = (Button) findViewById(R.id.post_lecture_btn);
        postLectureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ESMFactory factory3 = new ESMFactory();

                    ESM_Radio esmRadio1 = new ESM_Radio();
                    esmRadio1.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("Post Lecture Survey (1/6)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I was happy in this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("Post Lecture Survey (2/6)")
                            .setInstructions("I didn't feel very accomplished in this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("Post Lecture Survey (3/6)")
                            .setInstructions("I felt excited by the work in this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("Post Lecture Survey (4/6)")
                            .setInstructions("I liked being at this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("Post Lecture Survey (5/6)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I am interested in the work done in this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("Post Lecture Survey (6/6)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("My classroom is an interesting place to be.")
                            .setSubmitButton("Done");

                    factory3.addESM(esmRadio1);
                    factory3.addESM(esmRadio2);
                    factory3.addESM(esmRadio3);
                    factory3.addESM(esmRadio4);
                    factory3.addESM(esmRadio5);
                    factory3.addESM(esmRadio6);

                    ESM.queueESM(getApplicationContext(), factory3.build());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

        if(UserData.Username.equals("/")){
            terms.setEnabled(false);
            edit_profile.setEnabled(false);
            logout.setEnabled(false);
        }else{
            terms.setEnabled(true);
            edit_profile.setEnabled(true);
            logout.setEnabled(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch(item.getItemId()){

            //Add button
            case R.id.addProfileMenu:

                if(UserData.Username =="/"){
                    if(dbHandler.getAllRegistrations().size() > 0){ // check the database
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View passwordView = inflater.inflate(R.layout.dialog_password, null);

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setView(passwordView);

                        final EditText passwordField = (EditText) passwordView.findViewById(R.id.password_field);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String enteredPassword = passwordField.getText().toString();
                                String adminPassword = "123";
                                if(enteredPassword.equals(adminPassword)){

                                    Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                                    startActivity(i);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Error - wrong admin password! Try again!", Toast.LENGTH_SHORT).show();
                                };
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
                    }else{
                        Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please, first logout in order to add new profile!", Toast.LENGTH_SHORT).show();
                }
                return true;


            //Edit button
            case R.id.editProfileMenu:
                Intent i  = new Intent(getApplicationContext(), EditProfileActivity.class);
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
                        String adminPassword = "123";
                        if(enteredPassword.equals(adminPassword)){
                            Intent i = new Intent(getApplicationContext(), ChooseOtherProfilesActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Error - wrong admin password! Try again!", Toast.LENGTH_SHORT).show();
                        };
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
                UserData.Username = "/";
                Intent intent3 = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent3);
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

