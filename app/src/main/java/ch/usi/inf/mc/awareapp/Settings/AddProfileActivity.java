package ch.usi.inf.mc.awareapp.Settings;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;
import ch.usi.inf.mc.awareapp.TermsActivity;

public class AddProfileActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    private static final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 3;

    private Button doneBtn;
    Spinner ageSpinner;
    Spinner facultySpinner;
    Button chooseCourse;
    Spinner userPhoneSpinner;
    RadioGroup genderGroup;
    RadioGroup studyLevelGroup;
    private ProgressDialog progressDialog;
    String gender = "";
    String studyLevel = "";
    Boolean registrationDone = false;
    String SERIALNumber;
    DatabaseHandler dbHandler;
    String androidID;
    EditText usernameField;
    ArrayList<Integer> selectedCourses;
    String selectedCoursesString = "";
    String username;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);


        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        /* DEFINING INPUTS - BEGIN*/

            /* Defining Username textfield */
        usernameField = (EditText)findViewById(R.id.username_field);

            /* Defining Age spinner */
        ArrayList<String> ages = new ArrayList<>();
        ages.add("");
        ages.add("17 - 25");
        ages.add("26 - 35");
        ages.add("36 - 45");
        ages.add("46 - 100");

        ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ages);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter1);

            /* Defining Faculty spinner */
        ArrayList<String> faculties = new ArrayList<>();
        faculties.add("");
        faculties.add("The Academy of Architecture");
        faculties.add("Communication Sciences");
        faculties.add("Economics");
        faculties.add("Informatics");
        faculties.add("Institute of Italian Studies");

        facultySpinner = (Spinner) findViewById(R.id.faculty_spinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, faculties);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        facultySpinner.setAdapter(adapter2);


            /* Defining Course list */

        selectedCourses = new ArrayList<Integer>();
        chooseCourse = (Button)findViewById(R.id.courseDialogBtn);
        chooseCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog
                final boolean[] checkedCourses = new boolean[5];
                for(int course: selectedCourses){
                    checkedCourses[course] = true;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AddProfileActivity.this);
                builder.setTitle("Choose your courses")
                        .setMultiChoiceItems(R.array.courses, checkedCourses, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if(isChecked){
                                    selectedCourses.add(which);
                                    System.out.println("Selected course: " + which);
                                }else if(selectedCourses.contains(which)){
                                    selectedCourses.remove(Integer.valueOf(which));
                                }
                            }
                        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("Selected courses are: "+ selectedCourses);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });



            /* Gender RadioGroup */
        genderGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.m_radio_button) {
                    Toast.makeText(getApplicationContext(), "Your choice: Male", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.f_radio_button) {
                    Toast.makeText(getApplicationContext(), "Your choice: Female", Toast.LENGTH_SHORT).show();
                }

            }
        });

            /* StudyLevel RadioGroup */
        studyLevelGroup = (RadioGroup) findViewById(R.id.studies_radio_group);
        studyLevelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.bach_radio_button) {
                    Toast.makeText(getApplicationContext(), "Your choice: Bachelor studies", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.mast_radio_button) {
                    Toast.makeText(getApplicationContext(), "Your choice: Master studies", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.other_radio_button) {
                    Toast.makeText(getApplicationContext(), "Your choice: Other", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.phd_radio_button) {
                    Toast.makeText(getApplicationContext(), "Your choice: PhD studies", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* DEFINING INPUTS - END*/


        /* Defining "Done" button - By clicking it the form data should be saved, and "TermsActivity" should be presented */
            /* Collecting data from the form */
        doneBtn = (Button) findViewById(R.id.reg_done_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validation
                username= usernameField.getText().toString();
                if(username.equals("")){
                    Toast.makeText(getApplicationContext(), "Enter the username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Gender
                if (genderGroup.getCheckedRadioButtonId() == R.id.m_radio_button) {
                    gender = "Male";
                } else if(genderGroup.getCheckedRadioButtonId() == R.id.f_radio_button) {
                    gender = "Female";
                }

                //Gender validation
                if(gender.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select your gender!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //StudyLevel
                if (studyLevelGroup.getCheckedRadioButtonId() == R.id.bach_radio_button) {
                    studyLevel = "Bachelor";
                } else if (studyLevelGroup.getCheckedRadioButtonId() == R.id.mast_radio_button) {
                    studyLevel = "Master";
                } else if (studyLevelGroup.getCheckedRadioButtonId() == R.id.phd_radio_button) {
                    studyLevel = "PhD";
                } else if (studyLevelGroup.getCheckedRadioButtonId() == R.id.other_radio_button) {
                    studyLevel = "Other";
                }

                //Study level validation
                if(studyLevel.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select level of your studies!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                registrationDone = true;

                //CurrentDate - Timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                //Other data
                String age = ageSpinner.getSelectedItem().toString();
                String faculty = facultySpinner.getSelectedItem().toString();

                //Age validation
                if(age.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select your age!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Faculties validation
                if(faculty.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select your faculty!", Toast.LENGTH_SHORT).show();
                    return;
                }



                for(int course: selectedCourses){

                    switch (course){
                        case 0:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Linear Algebra";
                            }else{
                                selectedCoursesString += ", Linear Algebra";
                            }
                            break;
                        case 1:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Programming Fundamentals";
                            }else{
                                selectedCoursesString += ", Programming Fundamentals";
                            }
                            break;
                        case 2:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Cyber Communication";
                            }else{
                                selectedCoursesString += ", Cyber Communication";
                            }
                            break;
                        case 3:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Information Security";
                            }else{
                                selectedCoursesString += ", Information Security";
                            }
                            break;
                        case 4:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Software Architecture and Design";
                            }else{
                                selectedCoursesString += ", Software Architecture and Design";
                            }
                            break;
                    }
                }

                //Courses validation
                if(selectedCoursesString.equals("")){
                    Toast.makeText(getApplicationContext(), "Please select the courses you are attending!", Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println("Selected courses: " +selectedCoursesString);

                //Get registration with current username
                RegistrationClass registration = new RegistrationClass();

                registration._android_id = androidID;
                registration._age = age;
                registration._termsCompleted = false;
                registration._registrationDone = true;
                registration._courses = selectedCoursesString;
                registration._faculty=faculty;
                registration._username = username;
                registration._levelOfStudies = studyLevel;
                registration._gender = gender;
                registration._currentDateAndTime = currentDateAndTime;

                dbHandler.addRegistration(registration);


                //test-remove
                System.out.println("RegistrationActivity, data saved: ");
                System.out.println("username: "+registration._username+", android_id: "+registration._android_id+", age: "+registration._age+
                        ", gender: "+registration._gender+", faculty: "+registration._faculty+", level: "+registration._levelOfStudies+
                        ", courses: "+registration._courses+", currentDateAndTIme: "+registration._currentDateAndTime+", registrationDone: "+registration._registrationDone+", terms:"+registration._termsCompleted);
                System.out.println("RegistrationActivity, going to TermsActivity.");



                Toast.makeText(getApplicationContext(), "Data is successfully stored!", Toast.LENGTH_SHORT).show();

                UserData.SelectedCourses = selectedCoursesString;
                System.out.println("Selected courses are: "+ UserData.SelectedCourses);
                UserData.Username = username;

                //General survey after the registration
                createGeneralSurvey();

                Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkForPemissions();
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

    private void checkForPemissions() {

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
            } else {
                Toast.makeText(getApplicationContext(), "Please grant Location Permission from Settings!", Toast.LENGTH_LONG).show();
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }
            else {
                Toast.makeText(getApplicationContext(), "Please grant Storage Permission from Settings!", Toast.LENGTH_LONG).show();
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            } else {
                Toast.makeText(getApplicationContext(), "Please grant Storage Permission from Settings!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
