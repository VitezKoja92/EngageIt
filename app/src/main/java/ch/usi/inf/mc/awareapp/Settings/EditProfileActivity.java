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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;
import ch.usi.inf.mc.awareapp.TermsActivity;
import ch.usi.inf.mc.awareapp.WelcomeActivity;

import static ch.usi.inf.mc.awareapp.R.string.registration;

public class EditProfileActivity extends AppCompatActivity {

    private Button doneBtn;
    Spinner ageSpinner;
    Spinner facultySpinner;
    Button chooseCourse;
    Spinner userPhoneSpinner;
    RadioGroup genderGroup;
    RadioGroup studyLevelGroup;
    private ProgressDialog progressDialog;
    String gender;
    String studyLevel;
    Boolean registrationDone = false;
    String SERIALNumber;
    DatabaseHandler dbHandler;
    String androidID;
    EditText usernameField;
    ArrayList<Integer> selectedCourses;
    String selectedCoursesString = "";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        RegistrationClass registration = new RegistrationClass();
        for(RegistrationClass reg: dbHandler.getAllRegistrations()){
            if(reg._username.equals(UserData.Username)){
                registration = reg;
                break;
            }
        }

        /* DEFINING INPUTS - BEGIN*/

            /* Defining Username textfield - it should be disabled as user should not change it*/
        usernameField = (EditText)findViewById(R.id.username_field);
        usernameField.setEnabled(false);
        //set initial value of usernameField
        usernameField.setText(registration._username);

            /* Defining Age spinner */
        ArrayList<String> ages = new ArrayList<>();
        ages.add("17 - 25");
        ages.add("26 - 35");
        ages.add("36 - 45");
        ages.add("46 - 100");


        ageSpinner = (Spinner) findViewById(R.id.age_spinner);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ages);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter1);

        //set initial value of ageSpinner
        ageSpinner.setSelection(ages.indexOf(registration._age));

            /* Defining Faculty spinner */
        ArrayList<String> faculties = new ArrayList<>();
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
        //set initial value of facultySpinner
        facultySpinner.setSelection(faculties.indexOf(registration._faculty));



        /* Defining Course list */
        selectedCourses = new ArrayList<Integer>();
        final boolean[] checkedCourses = new boolean[4];
        if(registration._courses.contains("Mobile Computing")){
            checkedCourses[0] = true;
            selectedCourses.add(0);
        }else{
            checkedCourses[0] = false;
        }
        if(registration._courses.contains("Software Quality")){
            selectedCourses.add(1);
            checkedCourses[1] = true;
        }else{
            checkedCourses[1] = false;
        }
        if(registration._courses.contains("Data Analytics")){
            selectedCourses.add(2);
            checkedCourses[2] = true;
        }else{
            checkedCourses[2] = false;
        }
        if(registration._courses.contains("Cyber Communication")){
            selectedCourses.add(3);
            checkedCourses[3] = true;
        }else{
            checkedCourses[3] = false;
        }
        System.out.println("Selected courses: "+selectedCourses);

        chooseCourse = (Button)findViewById(R.id.courseDialogBtn);
        chooseCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog


                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
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

        //Set initial gender value
        if(registration._gender.equals("Male")){
            genderGroup.check(R.id.m_radio_button);
        }else{
            genderGroup.check(R.id.f_radio_button);
        }


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

        //Set initial level of studies value
        if(registration._levelOfStudies.equals("Bachelor")){
            studyLevelGroup.check(R.id.bach_radio_button);
        }else if(registration._levelOfStudies.equals("Master")){
            studyLevelGroup.check(R.id.mast_radio_button);
        }else if(registration._levelOfStudies.equals("Other")){
            studyLevelGroup.check(R.id.other_radio_button);
        }else{
            studyLevelGroup.check(R.id.phd_radio_button);
        }

        /* DEFINING INPUTS - END*/


        /* Defining "Done" button - By clicking it the form data should be saved, and "TermsActivity" should be presented */
            /* Collecting data from the form */
        doneBtn = (Button) findViewById(R.id.reg_done_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Gender
                if (genderGroup.getCheckedRadioButtonId() == R.id.m_radio_button) {
                    gender = "Male";
                } else {
                    gender = "Female";
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

//                registrationDone = true;

                //CurrentDate - Timestamp
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String currentDateAndTime = sdf.format(new Date());

                //Other data
                String age = ageSpinner.getSelectedItem().toString();
                String faculty = facultySpinner.getSelectedItem().toString();

                for(int course: selectedCourses){
                    switch (course){
                        case 0:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Information Security";
                            }else{
                                selectedCoursesString += ", Information Security";
                            }
                            break;
                        case 1:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Cyber Communication";
                            }else{
                                selectedCoursesString += ", Cyber Communication";
                            }
                            break;
                        case 2:
                            if(selectedCoursesString.equals("")){
                                selectedCoursesString += "Software Architecture";
                            }else{
                                selectedCoursesString += ", Software Architecture";
                            }
                            break;
                    }
                }
                System.out.println("Selected courses: " +selectedCoursesString);


                //Get registration with current username
                dbHandler.updateRegistration(age,gender, faculty, studyLevel, selectedCoursesString,true, true, UserData.Username,currentDateAndTime);

                UserData.SelectedCourses = selectedCoursesString;
                //test-remove
                RegistrationClass registration = new RegistrationClass();
                for(RegistrationClass reg: dbHandler.getAllRegistrations()){
                    if(reg._username.equals(UserData.Username)){
                        registration = reg;
                        break;
                    }
                }

                System.out.println("EditProfileActivity, data saved: ");
                System.out.println("username: "+registration._username+", android_id: "+registration._android_id+", age: "+registration._age+
                        ", gender: "+registration._gender+", faculty: "+registration._faculty+", level: "+registration._levelOfStudies+
                        ", courses: "+registration._courses+", currentDateAndTIme: "+registration._currentDateAndTime+", registrationDone: "+registration._registrationDone+", terms:"+registration._termsCompleted);

                System.out.println("EditProfileActivity, going to WelcomeActivity.");
                Toast.makeText(getApplicationContext(), "Data is successfully stored!", Toast.LENGTH_SHORT).show();

                //uncomment
                Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
//                //finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
