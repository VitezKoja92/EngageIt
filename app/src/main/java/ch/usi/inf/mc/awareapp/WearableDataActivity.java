package ch.usi.inf.mc.awareapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.Settings.ChooseOtherProfilesActivity;
import ch.usi.inf.mc.awareapp.Settings.EditProfileActivity;

public class WearableDataActivity extends AppCompatActivity {

    final Context context = this;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearable_data);

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
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
                                String adminPassword = "engageit2017";
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
                        String adminPassword = "engageit2017";
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
