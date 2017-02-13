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
        setContentView(R.layout.activity_add_profile);


        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        /* DEFINING INPUTS - BEGIN*/

            /* Defining Username textfield */
        usernameField = (EditText)findViewById(R.id.username_field);

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


            /* Defining Course list */


        chooseCourse = (Button)findViewById(R.id.courseDialogBtn);
        chooseCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog
                selectedCourses = new ArrayList<Integer>();

                AlertDialog.Builder builder = new AlertDialog.Builder(AddProfileActivity.this);
                builder.setTitle("Choose your courses")
                        .setMultiChoiceItems(R.array.courses, null, new DialogInterface.OnMultiChoiceClickListener() {
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
                String username =  usernameField.getText().toString();

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

                Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                UserData.SelectedCourses = selectedCoursesString;
                System.out.println("Selected courses are: "+ UserData.SelectedCourses);
                UserData.Username = username;
                UserData.AddProfileCounter ++;
                startActivity(i);
//                //finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForPemissions();
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
