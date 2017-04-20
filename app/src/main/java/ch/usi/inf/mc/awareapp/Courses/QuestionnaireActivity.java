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
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Linear Algebra","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPAM")){
                createPAM("after the first part of", "Linear Algebra","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("ThirdPAM")){
                createPAM("after the second part of", "Linear Algebra","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Linear Algebra", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Linear Algebra", 40);
            }
        }

        if(course.equals("MondayPF") || course.equals("WednesdayPF") || course.equals("FridayPF")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Programming Fundamentals 2","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPAM")){
                createPAM("after the first part of", "Programming Fundamentals 2","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("ThirdPAM")){
                createPAM("after the second part of", "Programming Fundamentals 2","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Programming Fundamentals 2", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Programming Fundamentals 2", 40);
            }
        }

        if(course.equals("TuesdayCC") || course.equals("WednesdayCC") || course.equals("ThursdayCC")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Cyber Communication","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPAM")){
                createPAM("after the first part of", "Cyber Communication","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("ThirdPAM")){
                createPAM("after the second part of", "Cyber Communication","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Cyber Communication", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Cyber Communication", 40);
            }
        }

        if(course.equals("MondayInf1") || course.equals("MondayInf2")){
            if(questionnaire.equals("FirstPAM")){
                createPAM("before", "Information Security","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPAM")){
                createPAM("after the first part of", "Information Security","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("ThirdPAM")){
                createPAM("after the second part of", "Information Security","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Information Security", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Information Security", 40);
            }
        }

        if(course.equals("TuesdaySAD") || course.equals("ThursdaySAD")){
            if(questionnaire.equals("FirstPAM")){
                System.out.println("in SAD");
                createPAM("before", "Software Architecture and Design","Pre-lecture PAM", 40);
            }
            if(questionnaire.equals("SecondPAM")){
                createPAM("after the first part of", "Software Architecture and Design","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("ThirdPAM")){
                createPAM("after the second part of", "Software Architecture and Design","Post-lecture PAM", 40);
            }
            if(questionnaire.equals("FirstPostlecture")){
                createPostLecture("first part", "Software Architecture and Design", 40);
            }
            if(questionnaire.equals("SecondPostlecture")){
                createPostLecture("second part", "Software Architecture and Design", 40);
            }
        }
    }



    public void createPAM(String moment, String course,String title, int expirationThreshold){
        try {
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle(title)
                    .setInstructions("Pick the closest to how you feel now "+moment+" " + course + "!")
                    .setSubmitButton("Done")
                    .setExpirationThreshold(60*expirationThreshold); //setNotificationRetry(3) - number of times to retry notification if it expires

            factory.addESM(q1);
            ESM.queueESM(getApplicationContext(), factory.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void createPostLecture(String moment, String course, int expirationThreshold){
        try {
            ESMFactory factory = new ESMFactory();

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("Survey about the "+moment+" of "+course+" (1/6)") // moment - "first part"; course - "Linear Algebra"
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
                    .setTitle("Survey about the "+moment+" of "+course+" (2/6)")
                    .setInstructions("I didn't feel very accomplished in this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (3/6)")
                    .setInstructions("I felt excited by the work in this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setTitle("Survey about the "+moment+" of "+course+" (4/6)")
                    .setInstructions("I liked being at this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("Survey about the "+moment+" of "+course+" (5/6)")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("I am interested in the work done in this lecture.")
                    .setSubmitButton("Next");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.addRadio("Strongly Agree")
                    .addRadio("Agree")
                    .addRadio("Neutral")
                    .addRadio("Disagree")
                    .addRadio("Strongly Disagree")
                    .setTitle("Survey about the "+moment+" of "+course+" (6/6)")
                    .setExpirationThreshold(60*expirationThreshold)
                    .setInstructions("My classroom is an interesting place to be.")
                    .setSubmitButton("Done");

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
