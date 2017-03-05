package ch.usi.inf.mc.awareapp.Settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;
import ch.usi.inf.mc.awareapp.TermsActivity;
import ch.usi.inf.mc.awareapp.WelcomeActivity;

public class ChooseOtherProfilesActivity extends AppCompatActivity {

    ListView profilesList;
    ArrayAdapter<String> adapter;
    DatabaseHandler dbHandler;
    String androidID;
    ArrayList<String> usernameList;
    TextView usernameLabel;
    ImageButton goToWelcome;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_other_profiles);

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        usernameLabel = (TextView)findViewById(R.id.username_label);



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



        /********** DEFINING LIST OF PROFILES **********/
        profilesList = (ListView)findViewById(R.id.profiles_list);
        profilesList.setClickable(true);

        usernameList = new ArrayList<String>();
        for(RegistrationClass reg: dbHandler.getAllRegistrations()){
            usernameList.add(reg._username);
        }

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.black_item_list,usernameList);
        profilesList.setAdapter(adapter);
        profilesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData.Username = profilesList.getItemAtPosition(position).toString();

                RegistrationClass registration = new RegistrationClass();
                for(RegistrationClass reg: dbHandler.getAllRegistrations()){
                    if(reg._username.equals(UserData.Username)){
                        registration = reg;
                    }
                }
                UserData.SelectedCourses = registration._courses;
                Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        });


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
