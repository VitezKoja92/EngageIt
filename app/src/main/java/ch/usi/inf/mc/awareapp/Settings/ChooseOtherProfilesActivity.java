package ch.usi.inf.mc.awareapp.Settings;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;
import ch.usi.inf.mc.awareapp.WelcomeActivity;

public class ChooseOtherProfilesActivity extends AppCompatActivity {

    ListView profilesList;
    ArrayAdapter<String> adapter;
    DatabaseHandler dbHandler;
    String androidID;
    ArrayList<String> usernameList;
    TextView usernameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_other_profiles);

        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        usernameLabel = (TextView)findViewById(R.id.username_label);





        //Populate clickable list on click of the "Search" button
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
}
