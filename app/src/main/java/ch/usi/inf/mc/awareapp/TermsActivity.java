package ch.usi.inf.mc.awareapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import java.util.ArrayList;
import java.util.List;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.Settings.AddProfileActivity;

public class TermsActivity extends AppCompatActivity{




    private Button acceptBtn;
    private Button refuseBtn;
    Boolean termsAccepted =false;
    DatabaseHandler dbHandler;
    List<RegistrationClass> allRegs;
    ImageButton goToWelcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        final String username = UserData.Username;

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



        /********** DEFINING ACCEPT BUTTON **********/
        acceptBtn = (Button) findViewById(R.id.accept_btn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent (getApplicationContext(), AddProfileActivity.class);
                startActivity(i);
                finish();
            }
        });



        /********** DEFINING REFUSE BUTTON **********/
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
                                Intent i =new Intent(getApplicationContext(), AddProfileActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).setNegativeButton(R.string.refuse_nok, new DialogInterface.OnClickListener() {
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
