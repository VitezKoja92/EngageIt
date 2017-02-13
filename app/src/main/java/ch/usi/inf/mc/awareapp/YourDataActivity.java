package ch.usi.inf.mc.awareapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class YourDataActivity extends AppCompatActivity {

    Button wareableDataBtn;
    Button surveyDataBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_data);

        /*Defining "WearableData" button*/
        wareableDataBtn = (Button)findViewById(R.id.wareable_data_btn);
        wareableDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), WearableDataActivity.class);
                startActivity(i);
            }
        });

        /*Defining "SurveyData" button*/
        surveyDataBtn = (Button)findViewById(R.id.survey_data_btn);
        surveyDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=  new Intent(getApplicationContext(), SurveyDataActivity.class);
                startActivity(i);
            }
        });
    }
}
