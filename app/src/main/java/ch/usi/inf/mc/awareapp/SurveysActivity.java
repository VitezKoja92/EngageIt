package ch.usi.inf.mc.awareapp;

import android.content.Intent;
import android.database.Cursor;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


public class SurveysActivity extends AppCompatActivity {

    private Button generalQuestionnaireBtn;
    private Button postLectureBtn;
    DatabaseHandler dbHandler;
    String androidID;
    Double timestampFirst;
    private Button pamBtn;


    private Intent awareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);


        dbHandler = DatabaseHandler.getInstance(getApplicationContext());
        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        generalQuestionnaireBtn = (Button) findViewById(R.id.general_survey_btn);
        postLectureBtn = (Button) findViewById(R.id.post_lecture_btn);
        pamBtn = (Button) findViewById(R.id.pam_survey_btn);



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
                            .setTitle("General Survey (1/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("This course is interesting to me.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("General Survey (2/11)")
                            .setInstructions("This course is useful for my future life and career.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("General Survey (3/11)")
                            .setInstructions("I am a careful listener.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("General Survey (4/11)")
                            .setInstructions("I feel happy in this course.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("General Survey (5/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I feel excited by the work done in this course.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("General Survey (6/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I like being at the lectures of this course.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio7 = new ESM_Radio();
                    esmRadio7.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("General Survey (7/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I am interested in the work done in this course.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio8 = new ESM_Radio();
                    esmRadio8.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("General Survey (8/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I study at home for this course even when I don't have a test.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio9 = new ESM_Radio();
                    esmRadio9.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("General Survey (9/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I try to watch videos about things we are doing in this course.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio10 = new ESM_Radio();
                    esmRadio10.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("General Survey (10/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I read extra books to learn more about things we do in this course.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio11 = new ESM_Radio();
                    esmRadio11.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("General Survey (11/11)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I don't feel bored in this course.")
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

                    ESM.queueESM(getApplicationContext(), factory1.build());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                awareIntent = new Intent(getApplicationContext(), Aware.class);
//                startService(awareIntent);
//
//                Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
//
//
//                String generalEsmString = "[{'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'1. This course is interesting to me.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':1, 'esm_scale_max':5, 'esm_scale_start':1, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'1 (strongly disagree)', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'2. This course is useful for my future life and career.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'3. I am a careful listener.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'4. I feel happy in this course.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'5. I feel excited by the work done in this course.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'6. I like being at the lectures of this course.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'7. I am interested in the work done in this course.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'8. I study at home even when I do not have a test.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'9. I watch videos about things we are doing in this course.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'10. I read extra books for this course.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Next', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}," +
//                        " {'esm': {'esm_type':" + ESM.TYPE_ESM_SCALE + ", 'esm_title':'11. I do not feel bored in this course.', 'esm_instructions':'Slide to the appropriate value on the scale:', 'esm_scale_min':0, 'esm_scale_max':5, 'esm_scale_start':0, 'esm_scale_max_label':'5 (strongly agree)', 'esm_scale_min_label':'0', 'esm_scale_step':1, 'esm_submit':'Done', 'esm_expiration_threshold':120, 'esm_trigger':'AWARE Tester'}}]";
//
//                //Queue the ESM to be displayed when possible
//                Intent esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
//                esm.putExtra(ESM.EXTRA_ESM, generalEsmString);
//                sendBroadcast(esm);
//
//                String[] tableColumns = {"timestamp", "esm_status", "esm_user_answer"};
//                String[] tableArguments = {};
//                Cursor data = getContentResolver().query(ESM_Provider.ESM_Data.CONTENT_URI, tableColumns, "",tableArguments,"");
//
//                ArrayList<String> questions = new ArrayList<String>();
//                while(data.moveToNext()){
//                    timestampFirst = data.getDouble(data.getColumnIndex("timestamp"));
//                    System.out.println(timestampFirst);
//                }
//
//                data.moveToLast();
//
//                if(data.getCount() != 0){
//                    for(int i = 1; i<=11; i++){
//                        questions.add(data.getString(data.getColumnIndex("esm_user_answer")));
//                        data.moveToPrevious();
//                    }
//
//                    GeneralSurveyClass generalSurvey = new GeneralSurveyClass();
//                    generalSurvey._timestamp = timestampFirst;
//                    generalSurvey._android_id = androidID;
//                    generalSurvey._username = "";
//                    generalSurvey._question11 = questions.get(0);
//                    generalSurvey._question10 = questions.get(1);
//                    generalSurvey._question9 = questions.get(2);
//                    generalSurvey._question8 = questions.get(3);
//                    generalSurvey._question7 = questions.get(4);
//                    generalSurvey._question6 = questions.get(5);
//                    generalSurvey._question5 = questions.get(6);
//                    generalSurvey._question4 = questions.get(7);
//                    generalSurvey._question3 = questions.get(8);
//                    generalSurvey._question2 = questions.get(9);
//                    generalSurvey._question1 = questions.get(10);
//
//                    dbHandler.addGeneralSurvey(generalSurvey);
//
//                }
//                System.out.println("database");
//                for(GeneralSurveyClass gen: dbHandler.getAllGeneralSurveys()){
//                    System.out.println(gen._question1+", "+ gen._question2+", "+gen._question3+", "+gen._question4+", "+
//                            gen._question5+", "+gen._question6+", "+gen._question7+", "+gen._question8+", "+
//                            gen._question9+", "+gen._question10+", "+gen._question11);
//                }

            }
        });

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
                            .setTitle("Post Lecture Survey (1/7)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I am interested in the topic explained in this lecture.")
                            .setSubmitButton("Next");


                    ESM_Radio esmRadio2 = new ESM_Radio();
                    esmRadio2.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("Post Lecture Survey (2/7)")
                            .setInstructions("I felt happy in this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio3 = new ESM_Radio();
                    esmRadio3.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("Post Lecture Survey (3/7)")
                            .setInstructions("I liked being at this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio4 = new ESM_Radio();
                    esmRadio4.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setExpirationThreshold(60*30)
                            .setTitle("Post Lecture Survey (4/7)")
                            .setInstructions("I didn't feel bored during this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio5 = new ESM_Radio();
                    esmRadio5.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("Post Lecture Survey (5/7)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I think the teacher was engaged during this lecture.")
                            .setSubmitButton("Next");

                    ESM_Radio esmRadio6 = new ESM_Radio();
                    esmRadio6.addRadio("Strongly Agree")
                            .addRadio("Agree")
                            .addRadio("Neutral")
                            .addRadio("Disagree")
                            .addRadio("Strongly Disagree")
                            .setTitle("Post Lecture Survey (6/7)")
                            .setExpirationThreshold(60*30)
                            .setInstructions("I think the teacher felt confident with the topic he/she explained.")
                            .setSubmitButton("Next");

                    ESM_QuickAnswer esmQuickAnswerDone = new ESM_QuickAnswer();
                    esmQuickAnswerDone.addQuickAnswer("Exit")
                            .setNotificationTimeout(60 * 4)
                            .setInstructions("Thank you very much for your participation! You earned 7 chocolates");


                    ESM_Freetext esmFreeText = new ESM_Freetext();
                    esmFreeText.setTitle("Post Lecture Survey (7/7)")
                            .setSubmitButton("Done")
                            .setExpirationThreshold(60*30)
                            .setInstructions("Please describe the moment(s) during which you felt particularly engaged")
                            .addFlow("Cancle", esmQuickAnswerDone.build());


                    ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
                    esmQuickAnswer.addQuickAnswer("Yes")
                            .addQuickAnswer("No")
                            .setExpirationThreshold(60*30)
                            .setTitle("Post Lecture Survey (7/7)")
                            .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                            .addFlow("Yes", esmFreeText.build())
                            .addFlow("No", esmQuickAnswerDone.build());

                    factory3.addESM(esmRadio1);
                    factory3.addESM(esmRadio2);
                    factory3.addESM(esmRadio3);
                    factory3.addESM(esmRadio4);
                    factory3.addESM(esmRadio5);
                    factory3.addESM(esmRadio6);
                    factory3.addESM(esmQuickAnswer);


                    ESM.queueESM(getApplicationContext(), factory3.build());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

