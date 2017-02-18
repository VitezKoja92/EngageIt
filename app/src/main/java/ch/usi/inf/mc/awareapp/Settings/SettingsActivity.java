package ch.usi.inf.mc.awareapp.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;
import ch.usi.inf.mc.awareapp.TermsActivity;
import ch.usi.inf.mc.awareapp.WelcomeActivity;

public class SettingsActivity extends AppCompatActivity {

    Button registrationDetailsBtn;
    Button addYourProfile;
    Button editYourProfile;
    Button chooseProfile;
    Button termsBtn;
    Button logoutBtn;
    final Context context = this;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        System.out.println("Number of registrations: "+dbHandler.getAllRegistrations().size());


        //Defining editProfile button
        editYourProfile = (Button)findViewById(R.id.edit_your_profile_btn);
        editYourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });
        //EditYourProfile button is initially disabled
        if(UserData.Username.equals("/")){
            editYourProfile.setEnabled(false);
        }else{
            editYourProfile.setEnabled(true);
        }


        //Defining chooseProfile button
        chooseProfile = (Button)findViewById(R.id.choose_other_profiles_btn);
        chooseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });


        //Defining addProfile button
        addYourProfile = (Button)findViewById(R.id.add_your_profile_btn);
        addYourProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                                Intent i = new Intent(getApplicationContext(), AddProfileActivity.class);
                                startActivity(i);
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
                    Intent i = new Intent(getApplicationContext(), AddProfileActivity.class);
                    startActivity(i);
                }

            }
        });


        //Defining chooseProfile button
        termsBtn = (Button)findViewById(R.id.terms_btn);
        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(i);
            }
        });

        //TermsAndConditions button is initially disabled
        if(UserData.Username.equals("/")){
            termsBtn.setEnabled(false);
        }else{
            termsBtn.setEnabled(true);
        }


        //Defining logout button
        logoutBtn = (Button)findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData.Username = "/";
                Intent i =new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
            }
        });




    }
}
