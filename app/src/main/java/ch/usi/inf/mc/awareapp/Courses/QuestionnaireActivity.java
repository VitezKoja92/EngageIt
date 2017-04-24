package ch.usi.inf.mc.awareapp.Courses;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONException;

import ch.usi.inf.mc.awareapp.R;
import ch.usi.inf.mc.awareapp.WelcomeActivity;

public class QuestionnaireActivity extends AppCompatActivity {
    Intent intent;
    String course;
    String questionnaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);


        //Define home button
        Button goHome = (Button)findViewById(R.id.goHome);
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Take extras from notification's pending intent
        intent = getIntent();
        course = intent.getExtras().getString("course");
        questionnaire = intent.getExtras().getString("questionnaire");


        System.out.println("I am in Questionnaire Activity");
        System.out.println("Course in QuestionnaireActivity: "+course);
        System.out.println("Questionnaire in QuestionnaireActivity: "+ questionnaire);



        if(course.equals("MondayLA") || course.equals("WednesdayLA")){
            if(questionnaire.equals("PAM")){
                createPAM("before", "Linear Algebra","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part","after", "Linear Algebra","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part","after", "Linear Algebra","Post-lecture PAM", 40);
            }
        }

        if(course.equals("MondayPF") || course.equals("WednesdayPF") || course.equals("FridayPF")){
            if(questionnaire.equals("PAM")){
                createPAM("before", "Programming Fundamentals 2","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "after","Programming Fundamentals 2","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part","after" , "Programming Fundamentals 2","Post-lecture PAM", 40);
            }
        }

        if(course.equals("TuesdayCC") || course.equals("WednesdayCC") || course.equals("ThursdayCC")){
            if(questionnaire.equals("PAM")){
                createPAM("before", "Cyber Communication","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part","after", "Cyber Communication","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part","after", "Cyber Communication","Post-lecture PAM", 40);
            }
        }

        if(course.equals("MondayInf1") || course.equals("MondayInf2")){
            if(questionnaire.equals("PAM")){
                createPAM("before", "Information Security","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part","after", "Information Security","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part","after", "Information Security","Post-lecture PAM", 40);
            }
        }

        if(course.equals("TuesdaySAD") || course.equals("ThursdaySAD")){
            if(questionnaire.equals("PAM")){
                createPAM("before", "Software Architecture and Design","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part","after", "Software Architecture and Design","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part","after", "Software Architecture and Design","Post-lecture PAM", 40);
            }
        }
    }



    public void createPAM(String moment, String course, String title, int expirationThreshold){
        try {
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle(title)
                    .setInstructions("Pick the closest to how you feel now, "+moment+" " + course + "!")
                    .setSubmitButton("Done")
                    .setExpirationThreshold(60*expirationThreshold); //setNotificationRetry(3) - number of times to retry notification if it expires

            factory.addESM(q1);
            ESM.queueESM(getApplicationContext(), factory.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void createPostLecture(String moment, String momentPAM, String course, String title, int expirationThreshold){
        try {
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle(title)
                    .setInstructions("Pick the closest to how you feel now, "+momentPAM+" the "+moment+" of "+ course + "! (1/7)")
                    .setSubmitButton("Next")
                    .setExpirationThreshold(60*expirationThreshold); //setNotificationRetry(3) - number of times to retry notification if it expires


            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("Survey about the "+moment+" of "+course+" (2/7)") // moment - "first part"; course - "Linear Algebra"
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("I was happy in this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (3/7)")
                    .setInstructions("I didn't feel very accomplished in this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (4/7)")
                    .setInstructions("I felt excited by the work in this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (5/7)")
                    .setInstructions("I liked being at this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("Survey about the "+moment+" of "+course+" (6/7)")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("I am interested in the work done in this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("Survey about the "+moment+" of "+course+" (7/7)")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("My classroom is an interesting place to be.")
                    .setSubmitButton("Done");

            factory.addESM(q1);
            factory.addESM(esmRadio1);
            factory.addESM(esmRadio2);
            factory.addESM(esmRadio3);
            factory.addESM(esmRadio4);
            factory.addESM(esmRadio5);
            factory.addESM(esmRadio6);

            ESM.queueESM(getApplicationContext(), factory.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        Intent i =new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(i);
        finish();
    }
}
