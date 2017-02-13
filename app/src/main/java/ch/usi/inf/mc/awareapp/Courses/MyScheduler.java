package ch.usi.inf.mc.awareapp.Courses;

import android.content.Context;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_Likert;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONException;

import java.util.ArrayList;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;

/**
 * Created by Danilo on 2/11/17.
 */

public class MyScheduler {
    DatabaseHandler dbHandler;





    /* Creation of the courses - BEGIN */


    //Information Security on Monday from 13:30 until 17:15
    public Weekday mondayInf1 = new Weekday(17, 2, "Monday");
    public Weekday mondayInf2 = new Weekday(13, 46, "Monday");
    public Course InformationSecurity = new Course(mondayInf1, mondayInf2, "Information Security");

    //Cyber Communication on Tuesday, Wednesday and Thursday from 10:30 until 12:15
    public Weekday tuesdayCC = new Weekday(17, 5, "Monday");
    public Weekday wednesdayCC = new Weekday(10, 30, "Wednesday");
    public Weekday thursdayCC = new Weekday(10, 30, "Thursday");
    public Course CyberCommunication = new Course(tuesdayCC, wednesdayCC,thursdayCC, "Cyber Communication");

    //Software Architecture on Tuesday and Thursday from 13:30 until 17:15
    public Weekday tuesdaySAD = new Weekday(17, 8, "Monday");
    public Weekday thursdaySAD = new Weekday(13, 30, "Thursday");
    public Course SoftwareArchitecture = new Course(tuesdaySAD, thursdaySAD, "Software Architecture");

    /* Creation of the courses - END */


    ////Create first PAM 15 minutes before the lecture starts and remove notification 15 after it starts
    public void createFirstPAM(String userCourses, Context context){

        dbHandler = DatabaseHandler.getInstance(context);

        try {

            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM")
                    .setSubmitButton("Done")
                    .setNotificationTimeout(60*4);

            factory.addESM(q1);


            if (userCourses.contains(InformationSecurity.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + InformationSecurity.getName() + "!");


                if(!UserData.Username.equals("/")){
                    com.aware.utils.Scheduler.Schedule first_pam1 = new com.aware.utils.Scheduler.Schedule("first_pam1"+UserData.Username);
                    first_pam1.addHour(InformationSecurity.getDay1().Hour);
                    first_pam1.addMinute(InformationSecurity.getDay1().Minute);
                    first_pam1.addWeekday(InformationSecurity.getDay1().Day);

                    first_pam1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, first_pam1);


                    com.aware.utils.Scheduler.Schedule first_pam2 = new com.aware.utils.Scheduler.Schedule("first_pam2"+UserData.Username);
                    first_pam2.addHour(InformationSecurity.getDay2().Hour);
                    first_pam2.addMinute(InformationSecurity.getDay2().Minute);
                    first_pam2.addWeekday(InformationSecurity.getDay2().Day);

                    first_pam2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, first_pam2);
                }

            }



            if (userCourses.contains((CyberCommunication.getName()))) {
                q1.setInstructions("Pick the closest to how you feel now before " + CyberCommunication.getName() + "!");

                if(!UserData.Username.equals("/")){
                    com.aware.utils.Scheduler.Schedule first_pam3 = new com.aware.utils.Scheduler.Schedule("first_pam3"+UserData.Username);
                    first_pam3.addHour(CyberCommunication.getDay1().Hour);
                    first_pam3.addMinute(CyberCommunication.getDay1().Minute);
                    first_pam3.addWeekday(CyberCommunication.getDay1().Day);

                    first_pam3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, first_pam3);


                    com.aware.utils.Scheduler.Schedule first_pam4 = new com.aware.utils.Scheduler.Schedule("first_pam4"+UserData.Username);
                    first_pam4.addHour(CyberCommunication.getDay2().Hour);
                    first_pam4.addMinute(CyberCommunication.getDay2().Minute);
                    first_pam4.addWeekday(CyberCommunication.getDay2().Day);

                    first_pam4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, first_pam4);

                    com.aware.utils.Scheduler.Schedule first_pam5 = new com.aware.utils.Scheduler.Schedule("first_pam5"+UserData.Username);
                    first_pam5.addHour(CyberCommunication.getDay3().Hour);
                    first_pam5.addMinute(CyberCommunication.getDay3().Minute);
                    first_pam5.addWeekday(CyberCommunication.getDay3().Day);

                    first_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, first_pam5);
                }

            }

            if (userCourses.contains(SoftwareArchitecture.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + SoftwareArchitecture.getName() + "!");

                if(!UserData.Username.equals("/")) {
                    com.aware.utils.Scheduler.Schedule first_pam6 = new com.aware.utils.Scheduler.Schedule("first_pam6" + UserData.Username);
                    first_pam6.addHour(SoftwareArchitecture.getDay1().Hour);
                    first_pam6.addMinute(SoftwareArchitecture.getDay1().Minute);
                    first_pam6.addWeekday(SoftwareArchitecture.getDay1().Day);

                    first_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, first_pam6);


                    com.aware.utils.Scheduler.Schedule first_pam7 = new com.aware.utils.Scheduler.Schedule("first_pam7" + UserData.Username);
                    first_pam7.addHour(SoftwareArchitecture.getDay2().Hour);
                    first_pam7.addMinute(SoftwareArchitecture.getDay2().Minute);
                    first_pam7.addWeekday(SoftwareArchitecture.getDay2().Day);

                    first_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, first_pam7);
                }
            }

            factory.addESM(q1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ////Create first PAM 15 minutes before the beak and remove notification 15 after break
    public void createSecondPAM(String userCourses, Context context){
        try {
            System.out.println("Second PAM triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM")
                    .setSubmitButton("Done")
                    .setNotificationTimeout(60*4); //Right now in seconds - 30minutes * 60seconds
            factory.addESM(q1);


            if (userCourses.contains(InformationSecurity.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + InformationSecurity.getName() + "!");


                if(!UserData.Username.equals("/")){
                    com.aware.utils.Scheduler.Schedule second_pam1 = new com.aware.utils.Scheduler.Schedule("second_pam1"+UserData.Username);
                    second_pam1.addHour(InformationSecurity.getDay1().Hour);
                    second_pam1.addMinute(InformationSecurity.getDay1().Minute + 1);
                    second_pam1.addWeekday(InformationSecurity.getDay1().Day);

                    second_pam1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    second_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    second_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, second_pam1);


                    com.aware.utils.Scheduler.Schedule second_pam2 = new com.aware.utils.Scheduler.Schedule("second_pam2"+UserData.Username);
                    second_pam2.addHour(InformationSecurity.getDay2().Hour);
                    second_pam2.addMinute(InformationSecurity.getDay2().Minute);
                    second_pam2.addWeekday(InformationSecurity.getDay2().Day);

                    second_pam2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    second_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    second_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, second_pam2);
                }

            }



            if (userCourses.contains((CyberCommunication.getName()))) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + CyberCommunication.getName() + "!");

                if(!UserData.Username.equals("/")){
                    com.aware.utils.Scheduler.Schedule second_pam3 = new com.aware.utils.Scheduler.Schedule("second_pam3"+UserData.Username);
                    second_pam3.addHour(CyberCommunication.getDay1().Hour);
                    second_pam3.addMinute(CyberCommunication.getDay1().Minute+1);
                    second_pam3.addWeekday(CyberCommunication.getDay1().Day);

                    second_pam3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    second_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    second_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, second_pam3);


                    com.aware.utils.Scheduler.Schedule second_pam4 = new com.aware.utils.Scheduler.Schedule("second_pam4"+UserData.Username);
                    second_pam4.addHour(CyberCommunication.getDay2().Hour);
                    second_pam4.addMinute(CyberCommunication.getDay2().Minute);
                    second_pam4.addWeekday(CyberCommunication.getDay2().Day);

                    second_pam4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    second_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    second_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, second_pam4);

                    com.aware.utils.Scheduler.Schedule second_pam5 = new com.aware.utils.Scheduler.Schedule("second_pam5"+UserData.Username);
                    second_pam5.addHour(CyberCommunication.getDay3().Hour);
                    second_pam5.addMinute(CyberCommunication.getDay3().Minute);
                    second_pam5.addWeekday(CyberCommunication.getDay3().Day);

                    second_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    second_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    second_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, second_pam5);
                }

            }

            if (userCourses.contains(SoftwareArchitecture.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + SoftwareArchitecture.getName() + "!");

                if(!UserData.Username.equals("/")) {
                    com.aware.utils.Scheduler.Schedule second_pam6 = new com.aware.utils.Scheduler.Schedule("second_pam6" + UserData.Username);
                    second_pam6.addHour(SoftwareArchitecture.getDay1().Hour);
                    second_pam6.addMinute(SoftwareArchitecture.getDay1().Minute + 1);
                    second_pam6.addWeekday(SoftwareArchitecture.getDay1().Day);

                    second_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    second_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    second_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, second_pam6);


                    com.aware.utils.Scheduler.Schedule second_pam7 = new com.aware.utils.Scheduler.Schedule("second_pam7" + UserData.Username);
                    second_pam7.addHour(SoftwareArchitecture.getDay2().Hour);
                    second_pam7.addMinute(SoftwareArchitecture.getDay2().Minute);
                    second_pam7.addWeekday(SoftwareArchitecture.getDay2().Day);

                    second_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    second_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    second_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, second_pam7);
                }


                factory.addESM(q1);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    ////Create first PAM 15 minutes before the beak and remove notification 15 after break
    public void createThirdPAM(String userCourses, Context context){
        try {
            System.out.println("Third PAM triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM")
                    .setSubmitButton("Done")
                    .setNotificationTimeout(60*4); //Right now in seconds - 30minutes * 60seconds
            factory.addESM(q1);


            if (userCourses.contains(InformationSecurity.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after " + InformationSecurity.getName() + "!");


                if(!UserData.Username.equals("/")){
                    com.aware.utils.Scheduler.Schedule third_pam1 = new com.aware.utils.Scheduler.Schedule("third_pam1"+UserData.Username);
                    third_pam1.addHour(InformationSecurity.getDay1().Hour);
                    third_pam1.addMinute(InformationSecurity.getDay1().Minute + 2);
                    third_pam1.addWeekday(InformationSecurity.getDay1().Day);

                    third_pam1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    third_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    third_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, third_pam1);


                    com.aware.utils.Scheduler.Schedule third_pam2 = new com.aware.utils.Scheduler.Schedule("third_pam2"+UserData.Username);
                    third_pam2.addHour(InformationSecurity.getDay2().Hour);
                    third_pam2.addMinute(InformationSecurity.getDay2().Minute + 2);
                    third_pam2.addWeekday(InformationSecurity.getDay2().Day);

                    third_pam2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    third_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    third_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, third_pam2);
                }

            }



            if (userCourses.contains((CyberCommunication.getName()))) {
                q1.setInstructions("Pick the closest to how you feel now before " + CyberCommunication.getName() + "!");

                if(!UserData.Username.equals("/")){
                    com.aware.utils.Scheduler.Schedule third_pam3 = new com.aware.utils.Scheduler.Schedule("third_pam3"+UserData.Username);
                    third_pam3.addHour(CyberCommunication.getDay1().Hour);
                    third_pam3.addMinute(CyberCommunication.getDay1().Minute);
                    third_pam3.addWeekday(CyberCommunication.getDay1().Day);

                    third_pam3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    third_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    third_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, third_pam3);


                    com.aware.utils.Scheduler.Schedule third_pam4 = new com.aware.utils.Scheduler.Schedule("third_pam4"+UserData.Username);
                    third_pam4.addHour(CyberCommunication.getDay2().Hour);
                    third_pam4.addMinute(CyberCommunication.getDay2().Minute);
                    third_pam4.addWeekday(CyberCommunication.getDay2().Day);

                    third_pam4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    third_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    third_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, third_pam4);

                    com.aware.utils.Scheduler.Schedule third_pam5 = new com.aware.utils.Scheduler.Schedule("third_pam5"+UserData.Username);
                    third_pam5.addHour(CyberCommunication.getDay3().Hour);
                    third_pam5.addMinute(CyberCommunication.getDay3().Minute);
                    third_pam5.addWeekday(CyberCommunication.getDay3().Day);

                    third_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    third_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    third_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, third_pam5);
                }

            }

            if (userCourses.contains(SoftwareArchitecture.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + SoftwareArchitecture.getName() + "!");

                if(!UserData.Username.equals("/")) {
                    com.aware.utils.Scheduler.Schedule third_pam6 = new com.aware.utils.Scheduler.Schedule("third_pam6" + UserData.Username);
                    third_pam6.addHour(SoftwareArchitecture.getDay1().Hour);
                    third_pam6.addMinute(SoftwareArchitecture.getDay1().Minute + 2);
                    third_pam6.addWeekday(SoftwareArchitecture.getDay1().Day);

                    third_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    third_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    third_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, third_pam6);


                    com.aware.utils.Scheduler.Schedule third_pam7 = new com.aware.utils.Scheduler.Schedule("third_pam7" + UserData.Username);
                    third_pam7.addHour(SoftwareArchitecture.getDay2().Hour);
                    third_pam7.addMinute(SoftwareArchitecture.getDay2().Minute);
                    third_pam7.addWeekday(SoftwareArchitecture.getDay2().Day);

                    third_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    third_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    third_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());

                    com.aware.utils.Scheduler.saveSchedule(context, third_pam7);
                }


                factory.addESM(q1);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    ////Create first post lecture survey
    public void createFirstPostLectureESM(Context context, String userCourses, String day) {
        try {

            System.out.println("First Postlecture triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.setNotificationTimeout(60 * 4)
                    .setInstructions("I am interested in the topic explained in this lecture.");


            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.setNotificationTimeout(60 * 4)
                    .setInstructions("I felt happy in this lecture.");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.setNotificationTimeout(60 * 4)
                    .setInstructions("I liked being at this lecture.");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.setNotificationTimeout(60 * 4)
                    .setInstructions("I didn't feel bored during this lecture.");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.setNotificationTimeout(60 * 4)
                    .setInstructions("I think the teacher was engaged during this lecture.");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.setNotificationTimeout(60 * 4)
                    .setInstructions("I think the teacher felt confident with the topic he/she explained.");


            ESM_Freetext esmFreeText = new ESM_Freetext();
            esmFreeText.setNotificationTimeout(60 * 4)
                    .setInstructions("Please describe the moment(s) during which you felt particularly engaged")
                    .setSubmitButton("Done");


            ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
            esmQuickAnswer.addQuickAnswer("Yes")
                    .addQuickAnswer("No")
                    .setNotificationTimeout(60 * 4)
                    .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                    .addFlow("Yes", esmFreeText.build());

            ArrayList<ESM_Radio> esms = new ArrayList<>();
            esms.add(esmRadio1);
            esms.add(esmRadio2);
            esms.add(esmRadio3);
            esms.add(esmRadio4);
            esms.add(esmRadio5);
            esms.add(esmRadio6);

            for (ESM_Radio esmRadio : esms) {
                esmRadio.addRadio("Strongly Agree")
                        .addRadio("Agree")
                        .addRadio("Neutral")
                        .addRadio("Disagree")
                        .addRadio("Strongly Disagree")
                        .setSubmitButton("Next");

            }


            com.aware.utils.Scheduler.Schedule post_lecture1 = com.aware.utils.Scheduler.getSchedule(context, "post_lecture1");
            com.aware.utils.Scheduler.Schedule post_lecture2 = com.aware.utils.Scheduler.getSchedule(context, "post_lecture2");

            if (post_lecture1 == null && post_lecture2 == null) { //not set yet
                post_lecture1 = new com.aware.utils.Scheduler.Schedule("post_lecture1");
                post_lecture2 = new com.aware.utils.Scheduler.Schedule("post_lecture2");


                if (userCourses.contains(InformationSecurity.getName())) {
                    esmRadio1.setTitle("Survey about the first lecture of Information Security (1/7)");
                    esmRadio2.setTitle("Survey about the first lecture of Information Security (2/7)");
                    esmRadio3.setTitle("Survey about the first lecture of Information Security (3/7)");
                    esmRadio4.setTitle("Survey about the first lecture of Information Security (4/7)");
                    esmRadio5.setTitle("Survey about the first lecture of Information Security (5/7)");
                    esmRadio6.setTitle("Survey about the first lecture of Information Security (6/7)");
                    esmFreeText.setTitle("Survey about the first lecture of Information Security (7/7)");
                    esmQuickAnswer.setTitle("Survey about the first lecture of Information Security (7/7)");
                    if(day.equals(InformationSecurity.getDay1().Day)){
                        post_lecture1.addWeekday(InformationSecurity.getDay1().Day);
                        post_lecture1.addHour(InformationSecurity.getDay1().Hour);
                        post_lecture1.addMinute(InformationSecurity.getDay1().Minute + 4);

                        post_lecture2.addWeekday(InformationSecurity.getDay2().Day);
                        post_lecture2.addHour(InformationSecurity.getDay2().Hour);
                        post_lecture2.addMinute(InformationSecurity.getDay2().Minute + 4);
                    }
                }


                if (userCourses.contains(CyberCommunication.getName())) {
                    esmRadio1.setTitle("Survey about the first lecture of Cyber Communication (1/7)");
                    esmRadio2.setTitle("Survey about the first lecture of Cyber Communication (2/7)");
                    esmRadio3.setTitle("Survey about the first lecture of Cyber Communication (3/7)");
                    esmRadio4.setTitle("Survey about the first lecture of Cyber Communication (4/7)");
                    esmRadio5.setTitle("Survey about the first lecture of Cyber Communication (5/7)");
                    esmRadio6.setTitle("Survey about the first lecture of Cyber Communication (6/7)");
                    esmFreeText.setTitle("Survey about the first lecture of Cyber Communication (7/7)");
                    esmQuickAnswer.setTitle("Survey about the first lecture of Cyber Communication (7/7)");

                    if (day.equals(CyberCommunication.getDay1().Day)) {
                        post_lecture1.addHour(CyberCommunication.getDay1().Hour);
                        post_lecture1.addMinute(CyberCommunication.getDay1().Minute);
                    } else if (day.equals(CyberCommunication.getDay2().Day)) {
                        post_lecture1.addHour(CyberCommunication.getDay2().Hour);
                        post_lecture1.addMinute(CyberCommunication.getDay2().Minute);
                    } else if (day.equals(CyberCommunication.getDay3().Day)) {
                        post_lecture1.addHour(CyberCommunication.getDay3().Hour);
                        post_lecture1.addMinute(CyberCommunication.getDay3().Minute);
                    }
                }

                if (userCourses.contains(SoftwareArchitecture.getName())) {
                    esmRadio1.setTitle("Survey about the first lecture of Software Architecture (1/7)");
                    esmRadio2.setTitle("Survey about the first lecture of Software Architecture (2/7)");
                    esmRadio3.setTitle("Survey about the first lecture of Software Architecture (3/7)");
                    esmRadio4.setTitle("Survey about the first lecture of Software Architecture (4/7)");
                    esmRadio5.setTitle("Survey about the first lecture of Software Architecture (5/7)");
                    esmRadio6.setTitle("Survey about the first lecture of Software Architecture (6/7)");
                    esmFreeText.setTitle("Survey about the first lecture of Software Architecture (7/7)");
                    esmQuickAnswer.setTitle("Survey about the first lecture of Software Architecture (7/7)");
                    if (day.equals(SoftwareArchitecture.getDay1().Day)) {
                        post_lecture1.addHour(SoftwareArchitecture.getDay1().Hour);
                        post_lecture1.addMinute(SoftwareArchitecture.getDay1().Minute);
                    } else if (day.equals(SoftwareArchitecture.getDay2().Day)) {
                        post_lecture1.addHour(SoftwareArchitecture.getDay2().Hour);
                        post_lecture1.addMinute(SoftwareArchitecture.getDay2().Minute);
                    }
                }

                factory.addESM(esmRadio1);
                factory.addESM(esmRadio2);
                factory.addESM(esmRadio3);
                factory.addESM(esmRadio4);
                factory.addESM(esmRadio5);
                factory.addESM(esmRadio6);
                factory.addESM(esmQuickAnswer);

                post_lecture1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                post_lecture1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                post_lecture1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                post_lecture2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                post_lecture2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                post_lecture2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, post_lecture1);
                com.aware.utils.Scheduler.saveSchedule(context, post_lecture2);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ////Create second post lecture survey
    public void createSecondPostLectureESM(Context context, String userCourses, String day) {
        try {

            System.out.println("Second postlecture triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.setNotificationTimeout(60 * 4)
                    .setInstructions("I am interested in the topic explained in this lecture.");


            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.setNotificationTimeout(60 * 4)
                    .setInstructions("I felt happy in this lecture.");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.setNotificationTimeout(60 * 4)
                    .setInstructions("I liked being at this lecture.");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.setNotificationTimeout(60 * 4)
                    .setInstructions("I didn't feel bored during this lecture.");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.setNotificationTimeout(60 * 4)
                    .setInstructions("I think the teacher was engaged during this lecture.");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.setNotificationTimeout(60 * 4)
                    .setInstructions("I think the teacher felt confident with the topic he/she explained.");


            ESM_Freetext esmFreeText = new ESM_Freetext();
            esmFreeText.setNotificationTimeout(60 * 4)
                    .setInstructions("Please describe the moment(s) during which you felt particularly engaged")
                    .setSubmitButton("Done");


            ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
            esmQuickAnswer.addQuickAnswer("Yes")
                    .addQuickAnswer("No")
                    .setNotificationTimeout(60 * 4)
                    .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
                    .addFlow("Yes", esmFreeText.build());

            ArrayList<ESM_Radio> esms = new ArrayList<>();
            esms.add(esmRadio1);
            esms.add(esmRadio2);
            esms.add(esmRadio3);
            esms.add(esmRadio4);
            esms.add(esmRadio5);
            esms.add(esmRadio6);

            for (ESM_Radio esmRadio : esms) {
                esmRadio.addRadio("Strongly Agree")
                        .addRadio("Agree")
                        .addRadio("Neutral")
                        .addRadio("Disagree")
                        .addRadio("Strongly Disagree")
                        .setSubmitButton("Next");
            }


            com.aware.utils.Scheduler.Schedule post_lecture1 = com.aware.utils.Scheduler.getSchedule(context, "post_lecture3");
            com.aware.utils.Scheduler.Schedule post_lecture2 = com.aware.utils.Scheduler.getSchedule(context, "post_lecture4");

            if (post_lecture1 == null && post_lecture2 == null) { //not set yet
                post_lecture1 = new com.aware.utils.Scheduler.Schedule("post_lecture3");
                post_lecture2 = new com.aware.utils.Scheduler.Schedule("post_lecture4");


                if (userCourses.contains(InformationSecurity.getName())) {
                    esmRadio1.setTitle("Survey about the second lecture of Information Security (1/7)");
                    esmRadio2.setTitle("Survey about the second lecture of Information Security (2/7)");
                    esmRadio3.setTitle("Survey about the second lecture of Information Security (3/7)");
                    esmRadio4.setTitle("Survey about the second lecture of Information Security (4/7)");
                    esmRadio5.setTitle("Survey about the second lecture of Information Security (5/7)");
                    esmRadio6.setTitle("Survey about the second lecture of Information Security (6/7)");
                    esmFreeText.setTitle("Survey about the second lecture of Information Security (7/7)");
                    esmQuickAnswer.setTitle("Survey about the second lecture of Information Security (7/7)");
                    if(day.equals(InformationSecurity.getDay1().Day)){
                        post_lecture1.addWeekday(InformationSecurity.getDay1().Day);
                        post_lecture1.addHour(InformationSecurity.getDay1().Hour);
                        post_lecture1.addMinute(InformationSecurity.getDay1().Minute +11);

                        post_lecture2.addWeekday(InformationSecurity.getDay2().Day);
                        post_lecture2.addHour(InformationSecurity.getDay2().Hour);
                        post_lecture2.addMinute(InformationSecurity.getDay2().Minute + 11);
                    }
                }


                if (userCourses.contains(CyberCommunication.getName())) {
                    esmRadio1.setTitle("Survey about the second lecture of Cyber Communication (1/7)");
                    esmRadio2.setTitle("Survey about the second lecture of Cyber Communication (2/7)");
                    esmRadio3.setTitle("Survey about the second lecture of Cyber Communication (3/7)");
                    esmRadio4.setTitle("Survey about the second lecture of Cyber Communication (4/7)");
                    esmRadio5.setTitle("Survey about the second lecture of Cyber Communication (5/7)");
                    esmRadio6.setTitle("Survey about the second lecture of Cyber Communication (6/7)");
                    esmFreeText.setTitle("Survey about the second lecture of Cyber Communication (7/7)");
                    esmQuickAnswer.setTitle("Survey about the second lecture of Cyber Communication (7/7)");

                    if (day.equals(CyberCommunication.getDay1().Day)) {
                        post_lecture1.addHour(CyberCommunication.getDay1().Hour);
                        post_lecture1.addMinute(CyberCommunication.getDay1().Minute);
                    } else if (day.equals(CyberCommunication.getDay2().Day)) {
                        post_lecture1.addHour(CyberCommunication.getDay2().Hour);
                        post_lecture1.addMinute(CyberCommunication.getDay2().Minute);
                    } else if (day.equals(CyberCommunication.getDay3().Day)) {
                        post_lecture1.addHour(CyberCommunication.getDay3().Hour);
                        post_lecture1.addMinute(CyberCommunication.getDay3().Minute);
                    }
                }

                if (userCourses.contains(SoftwareArchitecture.getName())) {
                    esmRadio1.setTitle("Survey about the second lecture of Software Architecture (1/7)");
                    esmRadio2.setTitle("Survey about the second lecture of Software Architecture (2/7)");
                    esmRadio3.setTitle("Survey about the second lecture of Software Architecture (3/7)");
                    esmRadio4.setTitle("Survey about the second lecture of Software Architecture (4/7)");
                    esmRadio5.setTitle("Survey about the second lecture of Software Architecture (5/7)");
                    esmRadio6.setTitle("Survey about the second lecture of Software Architecture (6/7)");
                    esmFreeText.setTitle("Survey about the second lecture of Software Architecture (7/7)");
                    esmQuickAnswer.setTitle("Survey about the second lecture of Software Architecture (7/7)");
                    if (day.equals(SoftwareArchitecture.getDay1().Day)) {
                        post_lecture1.addHour(SoftwareArchitecture.getDay1().Hour);
                        post_lecture1.addMinute(SoftwareArchitecture.getDay1().Minute);
                    } else if (day.equals(SoftwareArchitecture.getDay2().Day)) {
                        post_lecture1.addHour(SoftwareArchitecture.getDay2().Hour);
                        post_lecture1.addMinute(SoftwareArchitecture.getDay2().Minute);
                    }
                }

                factory.addESM(esmRadio1);
                factory.addESM(esmRadio2);
                factory.addESM(esmRadio3);
                factory.addESM(esmRadio4);
                factory.addESM(esmRadio5);
                factory.addESM(esmRadio6);
                factory.addESM(esmQuickAnswer);

                post_lecture1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                post_lecture1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                post_lecture1.addActionExtra(ESM.EXTRA_ESM, factory.build());

                post_lecture2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                post_lecture2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                post_lecture2.addActionExtra(ESM.EXTRA_ESM, factory.build());

                com.aware.utils.Scheduler.saveSchedule(context, post_lecture1);
                com.aware.utils.Scheduler.saveSchedule(context, post_lecture2);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
