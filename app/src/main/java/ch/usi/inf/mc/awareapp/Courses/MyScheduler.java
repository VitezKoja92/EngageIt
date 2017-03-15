package ch.usi.inf.mc.awareapp.Courses;

import android.app.Activity;
import android.content.Context;

import com.aware.ESM;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_Freetext;
import com.aware.ui.esms.ESM_Likert;
import com.aware.ui.esms.ESM_PAM;
import com.aware.ui.esms.ESM_QuickAnswer;
import com.aware.ui.esms.ESM_Radio;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ch.usi.inf.mc.awareapp.Database.DatabaseHandler;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;
import ch.usi.inf.mc.awareapp.Database.UserData;

/**
 * Created by Danilo on 2/11/17.
 */

public class MyScheduler{

    DatabaseHandler dbHandler;


    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE",Locale.US);
    Calendar calendar = Calendar.getInstance();
    String weekday = dayFormat.format(calendar.getTime());



    /* Creation of the courses - BEGIN */


    //Linear Algebra on Monday and  Wednesday from 8:30 until 10:15
    public Weekday mondayLA = new Weekday(9, 35, "Wednesday");             //Exact schedule should be Monday 8:30
    public Weekday wednesdayLA = new Weekday(9, 36, "Wednesday");       //Exact schedule should be Wednesday 8:30
    public Course LinearAlgebra = new Course(mondayLA, wednesdayLA, "Linear Algebra");

    //Programming Fundamentals on Monday, Wednesday and Friday from 10:30 until 12:15
    public Weekday mondayPF = new Weekday(14, 30, "Tuesday");            //Exact schedule should be Monday 10:30
    public Weekday wednesdayPF = new Weekday(14, 48, "Tuesday");      //Exact schedule should be Wednesday 10:30
    public Weekday fridayPF = new Weekday(14, 49, "Tuesday");            //Exact schedule should be Friday 10:30
    public Course ProgrammingFundamentals = new Course(mondayPF, wednesdayPF,fridayPF, "Programming Fundamentals");

    //Cyber Communication on Tuesday, Wednesday and Thursday from 10:30 until 12:15
    public Weekday tuesdayCC = new Weekday(14, 50, "Tuesday");          //Exact schedule should be Tuesday 10:30
    public Weekday wednesdayCC = new Weekday(13, 51, "Tuesday");      //Exact schedule should be Wednesday 10:30
    public Weekday thursdayCC = new Weekday(13, 52, "Tuesday");        //Exact schedule should be Thursday 10:30
    public Course CyberCommunication = new Course(tuesdayCC, wednesdayCC,thursdayCC, "Cyber Communication");

    //Information Security on Monday from 13:30 until 17:15
    public Weekday mondayInf1 = new Weekday(13, 53, "Tuesday");          //Exact schedule should be Monday 13:30
    public Weekday mondayInf2 = new Weekday(13, 54, "Tuesday");          //Exact schedule should be Monday 15:30
    public Course InformationSecurity = new Course(mondayInf1, mondayInf2, "Information Security");

    //Software Architecture on Tuesday and Thursday from 13:30 until 17:15
    public Weekday tuesdaySAD = new Weekday(13, 55, "Tuesday");         //Exact schedule should be Tuesday 13:30
    public Weekday thursdaySAD = new Weekday(13, 56, "Tuesday");       //Exact schedule should be Thursday 13:30
    public Course SoftwareArchitecture = new Course(tuesdaySAD, thursdaySAD, "Software Architecture and Design");

    /* Creation of the courses - END */


    //Create first PAM 15 minutes before the lecture starts
    public void createFirstPAM(String userCourses, Context context){

        dbHandler = DatabaseHandler.getInstance(context);

        try {

            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM")
                    .setSubmitButton("Done")
                    .setNotificationTimeout(60*630);        //First PAM stays active until 25 min after the beginning of the lecture

            //First PAM for Linear Algebra
            if (userCourses.contains(LinearAlgebra.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + LinearAlgebra.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                    com.aware.utils.Scheduler.Schedule first_pam1 = new com.aware.utils.Scheduler.Schedule("first_pam1"+UserData.Username);
                    first_pam1.addHour(LinearAlgebra.getDay1().Hour);
                    first_pam1.addMinute(LinearAlgebra.getDay1().Minute - 5); //8:25 Monday
                    first_pam1.addWeekday(LinearAlgebra.getDay1().Day);

                    first_pam1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam1);

                    com.aware.utils.Scheduler.Schedule first_pam2 = new com.aware.utils.Scheduler.Schedule("first_pam2"+UserData.Username);
                    first_pam2.addHour(LinearAlgebra.getDay2().Hour);
                    first_pam2.addMinute(LinearAlgebra.getDay2().Minute - 5); //8:25 Wednesday
                    first_pam2.addWeekday(LinearAlgebra.getDay2().Day);

                    first_pam2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam2);

                }
            }

            //First PAM for Programming Fundamentals
            if (userCourses.contains(ProgrammingFundamentals.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + ProgrammingFundamentals.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                    com.aware.utils.Scheduler.Schedule first_pam3 = new com.aware.utils.Scheduler.Schedule("first_pam3"+UserData.Username);
                    first_pam3.addHour(ProgrammingFundamentals.getDay1().Hour);
                    first_pam3.addMinute(ProgrammingFundamentals.getDay1().Minute - 5); //10:25 Monday -good
                    first_pam3.addWeekday(ProgrammingFundamentals.getDay1().Day);

                    first_pam3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam3);

                    com.aware.utils.Scheduler.Schedule first_pam4 = new com.aware.utils.Scheduler.Schedule("first_pam4"+UserData.Username);
                    first_pam4.addHour(ProgrammingFundamentals.getDay2().Hour);
                    first_pam4.addMinute(ProgrammingFundamentals.getDay2().Minute - 5); //10:25 Wednesday
                    first_pam4.addWeekday(ProgrammingFundamentals.getDay2().Day);

                    first_pam4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam4);


                    com.aware.utils.Scheduler.Schedule first_pam5 = new com.aware.utils.Scheduler.Schedule("first_pam5"+UserData.Username);
                    first_pam5.addHour(ProgrammingFundamentals.getDay3().Hour);
                    first_pam5.addMinute(ProgrammingFundamentals.getDay3().Minute - 5); //10:25 Friday
                    first_pam5.addWeekday(ProgrammingFundamentals.getDay3().Day);

                    first_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam5);

                }
            }

            //First PAM for Cyber Communication
            if (userCourses.contains(CyberCommunication.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + CyberCommunication.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                    com.aware.utils.Scheduler.Schedule first_pam6 = new com.aware.utils.Scheduler.Schedule("first_pam6"+UserData.Username);
                    first_pam6.addHour(CyberCommunication.getDay1().Hour);
                    first_pam6.addMinute(CyberCommunication.getDay1().Minute - 5); //10:25 Tuesday
                    first_pam6.addWeekday(CyberCommunication.getDay1().Day);

                    first_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam6);


                    com.aware.utils.Scheduler.Schedule first_pam7 = new com.aware.utils.Scheduler.Schedule("first_pam7"+UserData.Username);
                    first_pam7.addHour(CyberCommunication.getDay2().Hour);
                    first_pam7.addMinute(CyberCommunication.getDay2().Minute - 5); //10:25 Wednesday
                    first_pam7.addWeekday(CyberCommunication.getDay2().Day);

                    first_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam7);


                    com.aware.utils.Scheduler.Schedule first_pam8 = new com.aware.utils.Scheduler.Schedule("first_pam8"+UserData.Username);
                    first_pam8.addHour(CyberCommunication.getDay3().Hour);
                    first_pam8.addMinute(CyberCommunication.getDay3().Minute - 5); //10:25 Thursday
                    first_pam8.addWeekday(CyberCommunication.getDay3().Day);

                    first_pam8.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam8.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam8);

                }
            }

            //First PAM for Information Security
            if (userCourses.contains(InformationSecurity.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + InformationSecurity.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                    com.aware.utils.Scheduler.Schedule first_pam9 = new com.aware.utils.Scheduler.Schedule("first_pam9"+UserData.Username);
                    first_pam9.addHour(InformationSecurity.getDay1().Hour);
                    first_pam9.addMinute(InformationSecurity.getDay1().Minute - 5); //13:25 Monday - good
                    first_pam9.addWeekday(InformationSecurity.getDay1().Day);

                    first_pam9.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam9.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam9);

                    com.aware.utils.Scheduler.Schedule first_pam10 = new com.aware.utils.Scheduler.Schedule("first_pam10"+UserData.Username);
                    first_pam10.addHour(InformationSecurity.getDay2().Hour);
                    first_pam10.addMinute(InformationSecurity.getDay2().Minute - 5); //15:25 Monday - BAD!!!!!!!!
                    first_pam10.addWeekday(InformationSecurity.getDay2().Day);

                    first_pam10.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam10.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam10);

                }
            }

            //First PAM for Software Architecture and Design
            if (userCourses.contains(SoftwareArchitecture.getName())) {
                q1.setInstructions("Pick the closest to how you feel now before " + SoftwareArchitecture.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")) {

                    com.aware.utils.Scheduler.Schedule first_pam11 = new com.aware.utils.Scheduler.Schedule("first_pam11" + UserData.Username);
                    first_pam11.addHour(SoftwareArchitecture.getDay1().Hour);
                    first_pam11.addMinute(SoftwareArchitecture.getDay1().Minute - 5); //13:25 Tuesday
                    first_pam11.addWeekday(SoftwareArchitecture.getDay1().Day);

                    first_pam11.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam11.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam11);


                    com.aware.utils.Scheduler.Schedule first_pam12 = new com.aware.utils.Scheduler.Schedule("first_pam12" + UserData.Username);
                    first_pam12.addHour(SoftwareArchitecture.getDay2().Hour);
                    first_pam12.addMinute(SoftwareArchitecture.getDay2().Minute - 5); //13:25 Thursday
                    first_pam12.addWeekday(SoftwareArchitecture.getDay2().Day);

                    first_pam12.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                    first_pam12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                    first_pam12.addActionExtra(ESM.EXTRA_ESM, factory.build());
                    com.aware.utils.Scheduler.saveSchedule(context, first_pam12);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ////Create second PAM 5 minutes before the break
    public void createSecondPAM(String userCourses, Context context){
        try {
            System.out.println("Second PAM triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM")
                    .setSubmitButton("Done")
                    .setNotificationTimeout(60*630);              //Second PAM reamins active until 19:00

            //Second PAM for Linear Algebra
            if (userCourses.contains(LinearAlgebra.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + LinearAlgebra.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule second_pam1 = new com.aware.utils.Scheduler.Schedule("second_pam1"+UserData.Username);
                        second_pam1.addHour(LinearAlgebra.getDay1().Hour + 1);
                        second_pam1.addMinute(LinearAlgebra.getDay1().Minute -25); //9:05 Monday
                        second_pam1.addWeekday(LinearAlgebra.getDay1().Day);

                        second_pam1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam1);


                        com.aware.utils.Scheduler.Schedule second_pam2 = new com.aware.utils.Scheduler.Schedule("second_pam2"+UserData.Username);
                        second_pam2.addHour(LinearAlgebra.getDay2().Hour + 1);
                        second_pam2.addMinute(LinearAlgebra.getDay2().Minute -25); //9:05 Wednesday
                        second_pam2.addWeekday(LinearAlgebra.getDay2().Day);

                        second_pam2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam2);

                }
            }

            //Second PAM for Programming Fundamentals
            if (userCourses.contains(ProgrammingFundamentals.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + ProgrammingFundamentals.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule second_pam3 = new com.aware.utils.Scheduler.Schedule("second_pam3"+UserData.Username);
                        second_pam3.addHour(ProgrammingFundamentals.getDay1().Hour + 1);
                        second_pam3.addMinute(ProgrammingFundamentals.getDay1().Minute -25); //11:05 Monday - good
                        second_pam3.addWeekday(ProgrammingFundamentals.getDay1().Day);

                        second_pam3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam3);


                        com.aware.utils.Scheduler.Schedule second_pam4 = new com.aware.utils.Scheduler.Schedule("second_pam4"+UserData.Username);
                        second_pam4.addHour(ProgrammingFundamentals.getDay2().Hour +1);
                        second_pam4.addMinute(ProgrammingFundamentals.getDay2().Minute -25); //11:05 Wednesday
                        second_pam4.addWeekday(ProgrammingFundamentals.getDay2().Day);

                        second_pam4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam4);


                        com.aware.utils.Scheduler.Schedule second_pam5 = new com.aware.utils.Scheduler.Schedule("second_pam5"+UserData.Username);
                        second_pam5.addHour(ProgrammingFundamentals.getDay3().Hour + 1);
                        second_pam5.addMinute(ProgrammingFundamentals.getDay3().Minute -25); //11:05 Friday
                        second_pam5.addWeekday(ProgrammingFundamentals.getDay3().Day);

                        second_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam5);

                }
            }

            //Second PAM for Cyber Communication
            if (userCourses.contains(CyberCommunication.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + CyberCommunication.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule second_pam6 = new com.aware.utils.Scheduler.Schedule("second_pam6"+UserData.Username);
                        second_pam6.addHour(CyberCommunication.getDay1().Hour + 1);
                        second_pam6.addMinute(CyberCommunication.getDay1().Minute -25); //11:05 Tuesday
                        second_pam6.addWeekday(CyberCommunication.getDay1().Day);

                        second_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam6);


                        com.aware.utils.Scheduler.Schedule second_pam7 = new com.aware.utils.Scheduler.Schedule("second_pam7"+UserData.Username);
                        second_pam7.addHour(CyberCommunication.getDay2().Hour + 1);
                        second_pam7.addMinute(CyberCommunication.getDay2().Minute -25); //11:05 Wednesday
                        second_pam7.addWeekday(CyberCommunication.getDay2().Day);

                        second_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam7);


                        com.aware.utils.Scheduler.Schedule second_pam8 = new com.aware.utils.Scheduler.Schedule("second_pam8"+UserData.Username);
                        second_pam8.addHour(CyberCommunication.getDay3().Hour +1);
                        second_pam8.addMinute(CyberCommunication.getDay3().Minute -25); //11:05 Thursday
                        second_pam8.addWeekday(CyberCommunication.getDay3().Day);

                        second_pam8.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam8.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam8);

                }
            }

            //Second PAM for Information Security
            if (userCourses.contains(InformationSecurity.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + InformationSecurity.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule second_pam9 = new com.aware.utils.Scheduler.Schedule("second_pam9"+UserData.Username);
                        second_pam9.addHour(InformationSecurity.getDay1().Hour +1);
                        second_pam9.addMinute(InformationSecurity.getDay1().Minute -25); //14:05 Monday -good
                        second_pam9.addWeekday(InformationSecurity.getDay1().Day);

                        second_pam9.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam9.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam9);


                        com.aware.utils.Scheduler.Schedule second_pam10 = new com.aware.utils.Scheduler.Schedule("second_pam10"+UserData.Username);
                        second_pam10.addHour(InformationSecurity.getDay2().Hour + 1);
                        second_pam10.addMinute(InformationSecurity.getDay2().Minute -25); //16:05 Monday -BAD!!!!!!!!!!!!
                        second_pam10.addWeekday(InformationSecurity.getDay2().Day);

                        second_pam10.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam10.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam10);

                }
            }

            //Second PAM for Software Architecture and Design
            if (userCourses.contains(SoftwareArchitecture.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the first part of " + SoftwareArchitecture.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")) {

                        com.aware.utils.Scheduler.Schedule second_pam11 = new com.aware.utils.Scheduler.Schedule("second_pam11" + UserData.Username);
                        second_pam11.addHour(SoftwareArchitecture.getDay1().Hour + 1);
                        second_pam11.addMinute(SoftwareArchitecture.getDay1().Minute -25); //14:05 Tuesday
                        second_pam11.addWeekday(SoftwareArchitecture.getDay1().Day);

                        second_pam11.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam11.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam11);


                        com.aware.utils.Scheduler.Schedule second_pam12 = new com.aware.utils.Scheduler.Schedule("second_pam12" + UserData.Username);
                        second_pam12.addHour(SoftwareArchitecture.getDay2().Hour + 1);
                        second_pam12.addMinute(SoftwareArchitecture.getDay2().Minute -25); //14:05 Thursday
                        second_pam12.addWeekday(SoftwareArchitecture.getDay2().Day);

                        second_pam12.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_pam12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_pam12.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_pam12);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Create third PAM 5 minutes before the end of the class
    public void createThirdPAM(String userCourses, Context context){
        try {
            System.out.println("Third PAM triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_PAM q1 = new ESM_PAM();
            q1.setTitle("PAM")
                    .setSubmitButton("Done")
                    .setNotificationTimeout(60*630);              //Third PAM reamins active until 19:00

            //Third PAM for Linear Algebra
            if (userCourses.contains(LinearAlgebra.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the second part of " + LinearAlgebra.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule third_pam1 = new com.aware.utils.Scheduler.Schedule("third_pam1"+UserData.Username);
                        third_pam1.addHour(LinearAlgebra.getDay1().Hour + 2);
                        third_pam1.addMinute(LinearAlgebra.getDay1().Minute -25); //10:05 Monday -good
                        third_pam1.addWeekday(LinearAlgebra.getDay1().Day);

                        third_pam1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam1.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam1);

                        com.aware.utils.Scheduler.Schedule third_pam2 = new com.aware.utils.Scheduler.Schedule("third_pam2"+UserData.Username);
                        third_pam2.addHour(LinearAlgebra.getDay2().Hour + 2);
                        third_pam2.addMinute(LinearAlgebra.getDay2().Minute -25); //10:05 Wednesday
                        third_pam2.addWeekday(LinearAlgebra.getDay2().Day);

                        third_pam2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam2.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam2);

                }
            }

            //Third PAM for Programming Fundamentals
            if (userCourses.contains(ProgrammingFundamentals.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the second part of " + ProgrammingFundamentals.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule third_pam3 = new com.aware.utils.Scheduler.Schedule("third_pam3"+UserData.Username);
                        third_pam3.addHour(ProgrammingFundamentals.getDay1().Hour + 2);
                        third_pam3.addMinute(ProgrammingFundamentals.getDay1().Minute -25); //12:05 Monday - BAD!!!!!
                        third_pam3.addWeekday(ProgrammingFundamentals.getDay1().Day);

                        third_pam3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam3.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam3);


                        com.aware.utils.Scheduler.Schedule third_pam4 = new com.aware.utils.Scheduler.Schedule("third_pam4"+UserData.Username);
                        third_pam4.addHour(ProgrammingFundamentals.getDay2().Hour +2);
                        third_pam4.addMinute(ProgrammingFundamentals.getDay2().Minute -25); //12:05 Wednesday
                        third_pam4.addWeekday(ProgrammingFundamentals.getDay2().Day);

                        third_pam4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam4.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam4);


                        com.aware.utils.Scheduler.Schedule third_pam5 = new com.aware.utils.Scheduler.Schedule("third_pam5"+UserData.Username);
                        third_pam5.addHour(ProgrammingFundamentals.getDay3().Hour + 2);
                        third_pam5.addMinute(ProgrammingFundamentals.getDay3().Minute -25); //12:05 Friday
                        third_pam5.addWeekday(ProgrammingFundamentals.getDay3().Day);

                        third_pam5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam5.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam5);

                }
            }

            //Third PAM for Cyber Communication
            if (userCourses.contains(CyberCommunication.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the second part of " + CyberCommunication.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule third_pam6 = new com.aware.utils.Scheduler.Schedule("third_pam6"+UserData.Username);
                        third_pam6.addHour(CyberCommunication.getDay1().Hour + 2);
                        third_pam6.addMinute(CyberCommunication.getDay1().Minute -25); //12:05 Tuesday
                        third_pam6.addWeekday(CyberCommunication.getDay1().Day);

                        third_pam6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam6.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam6);


                        com.aware.utils.Scheduler.Schedule third_pam7 = new com.aware.utils.Scheduler.Schedule("third_pam7"+UserData.Username);
                        third_pam7.addHour(CyberCommunication.getDay2().Hour +2);
                        third_pam7.addMinute(CyberCommunication.getDay2().Minute -25); //12:05 Wednesday
                        third_pam7.addWeekday(CyberCommunication.getDay2().Day);

                        third_pam7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam7.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam7);


                        com.aware.utils.Scheduler.Schedule third_pam8 = new com.aware.utils.Scheduler.Schedule("third_pam8"+UserData.Username);
                        third_pam8.addHour(CyberCommunication.getDay3().Hour + 2);
                        third_pam8.addMinute(CyberCommunication.getDay3().Minute -25); //12:05 Thursday
                        third_pam8.addWeekday(CyberCommunication.getDay3().Day);

                        third_pam8.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam8.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam8);

                }
            }

            //Third PAM for Information Security
            if (userCourses.contains(InformationSecurity.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the second part of " + InformationSecurity.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")){

                        com.aware.utils.Scheduler.Schedule third_pam9 = new com.aware.utils.Scheduler.Schedule("third_pam9"+UserData.Username);
                        third_pam9.addHour(InformationSecurity.getDay1().Hour + 2);
                        third_pam9.addMinute(InformationSecurity.getDay1().Minute -25); //15:05 Monday - good!
                        third_pam9.addWeekday(InformationSecurity.getDay1().Day);

                        third_pam9.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam9.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam9);


                        com.aware.utils.Scheduler.Schedule third_pam10 = new com.aware.utils.Scheduler.Schedule("third_pam10"+UserData.Username);
                        third_pam10.addHour(InformationSecurity.getDay2().Hour + 2);
                        third_pam10.addMinute(InformationSecurity.getDay2().Minute -25); //17:05 Monday - BAD!
                        third_pam10.addWeekday(InformationSecurity.getDay2().Day);

                        third_pam10.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam10.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam10);

                }
            }

            //Third PAM for Software Architecture and Design
            if (userCourses.contains(SoftwareArchitecture.getName())) {
                q1.setInstructions("Pick the closest to how you feel now after the second part of " + SoftwareArchitecture.getName() + "!");

                factory.addESM(q1);

                if(!UserData.Username.equals("/")) {

                        com.aware.utils.Scheduler.Schedule third_pam11 = new com.aware.utils.Scheduler.Schedule("third_pam11" + UserData.Username);
                        third_pam11.addHour(SoftwareArchitecture.getDay1().Hour + 2);
                        third_pam11.addMinute(SoftwareArchitecture.getDay1().Minute -25); //15:05 Tuesday
                        third_pam11.addWeekday(SoftwareArchitecture.getDay1().Day);

                        third_pam11.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam11.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam11);


                        com.aware.utils.Scheduler.Schedule third_pam12 = new com.aware.utils.Scheduler.Schedule("third_pam12" + UserData.Username);
                        third_pam12.addHour(SoftwareArchitecture.getDay2().Hour + 2);
                        third_pam12.addMinute(SoftwareArchitecture.getDay2().Minute -25); //15:05 Thursday
                        third_pam12.addWeekday(SoftwareArchitecture.getDay2().Day);

                        third_pam12.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        third_pam12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        third_pam12.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, third_pam12);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    ////Create first post lecture survey
    public void createFirstPostLectureESM(Context context, String userCourses) {
        try {

            System.out.println("First Postlecture triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.setNotificationTimeout(60 * 630)              //Timeout should be set so that FirstLectureESM expires at 19:00
                    .setInstructions("I was happy in this lecture.");


            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.setNotificationTimeout(60 * 630)              //Timeout should be set so that FirstLectureESM expires at 19:00
                    .setInstructions("I didn't feel very accomplished in this lecture.");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.setNotificationTimeout(60 * 630)              //Timeout should be set so that FirstLectureESM expires at 19:00
                    .setInstructions("I felt excited by the work in this lecture.");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.setNotificationTimeout(60 * 630)              //Timeout should be set so that FirstLectureESM expires at 19:00
                    .setInstructions("I liked being at this lecture.");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.setNotificationTimeout(60 * 630)              //Timeout should be set so that FirstLectureESM expires at 19:00
                    .setInstructions("I am interested in the work done in this lecture.");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.setNotificationTimeout(60 * 630)              //Timeout should be set so that FirstLectureESM expires at 19:00
                    .setInstructions("My classroom is an interesting place to be.");


//            ESM_Freetext esmFreeText = new ESM_Freetext();
//            esmFreeText.setNotificationTimeout(60 * 100)            //Timeout should be set so that FirstLectureESM expires at 19:00
//                    .setInstructions("Please describe the moment(s) during which you felt particularly engaged")
//                    .setSubmitButton("Done");
//
//
//            ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
//            esmQuickAnswer.addQuickAnswer("Yes")
//                    .addQuickAnswer("No")
//                    .setNotificationTimeout(60 * 100)              //Timeout should be set so that FirstLectureESM expires at 19:00
//                    .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
//                    .addFlow("Yes", esmFreeText.build());

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

            //First post-lecture questionnaire for Linear Algebra
            if(userCourses.contains(LinearAlgebra.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the first part of Linear Algebra (1/6)");
                    esmRadio2.setTitle("Survey about the first part of Linear Algebra (2/6)");
                    esmRadio3.setTitle("Survey about the first part of Linear Algebra (3/6)");
                    esmRadio4.setTitle("Survey about the first part of Linear Algebra (4/6)");
                    esmRadio5.setTitle("Survey about the first part of Linear Algebra (5/6)");
                    esmRadio6.setTitle("Survey about the first part of Linear Algebra (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);

//                    esmFreeText.setTitle("Survey about the first lecture of Linear Algebra (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Linear Algebra (7/7)");

                        com.aware.utils.Scheduler.Schedule first_post_lecture1 = new com.aware.utils.Scheduler.Schedule("first_post_lecture1" + UserData.Username);
                        first_post_lecture1.addWeekday(LinearAlgebra.getDay1().Day);
                        first_post_lecture1.addHour(LinearAlgebra.getDay1().Hour + 1);
                        first_post_lecture1.addMinute(LinearAlgebra.getDay1().Minute -25); //9:05 Monday - ???

                        first_post_lecture1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture1.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture1);


                        com.aware.utils.Scheduler.Schedule first_post_lecture2 = new com.aware.utils.Scheduler.Schedule("first_post_lecture2" + UserData.Username);
                        first_post_lecture2.addWeekday(LinearAlgebra.getDay2().Day);
                        first_post_lecture2.addHour(LinearAlgebra.getDay2().Hour +1);
                        first_post_lecture2.addMinute(LinearAlgebra.getDay2().Minute -25); //9:05 Wednesday

                        first_post_lecture2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture2.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture2);

                }
            }

            //First post-lecture questionnaire for Programming Fundamentals
            if(userCourses.contains(ProgrammingFundamentals.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the first part of Programming Fundamentals (1/6)");
                    esmRadio2.setTitle("Survey about the first part of Programming Fundamentals (2/6)");
                    esmRadio3.setTitle("Survey about the first part of Programming Fundamentals (3/6)");
                    esmRadio4.setTitle("Survey about the first part of Programming Fundamentals (4/6)");
                    esmRadio5.setTitle("Survey about the first part of Programming Fundamentals (5/6)");
                    esmRadio6.setTitle("Survey about the first part of Programming Fundamentals (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);

//                    esmFreeText.setTitle("Survey about the first lecture of Programming Fundamentals (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Programming Fundamentals (7/7)");


                        com.aware.utils.Scheduler.Schedule first_post_lecture3 = new com.aware.utils.Scheduler.Schedule("first_post_lecture3" + UserData.Username);
                        first_post_lecture3.addWeekday(ProgrammingFundamentals.getDay1().Day);
                        first_post_lecture3.addHour(ProgrammingFundamentals.getDay1().Hour + 1);
                        first_post_lecture3.addMinute(ProgrammingFundamentals.getDay1().Minute -25); //11:05 Monday -good

                        first_post_lecture3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture3.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture3);


                        com.aware.utils.Scheduler.Schedule first_post_lecture4 = new com.aware.utils.Scheduler.Schedule("first_post_lecture4" + UserData.Username);
                        first_post_lecture4.addWeekday(ProgrammingFundamentals.getDay2().Day);
                        first_post_lecture4.addHour(ProgrammingFundamentals.getDay2().Hour +1);
                        first_post_lecture4.addMinute(ProgrammingFundamentals.getDay2().Minute -25); //11:05 Wednesday

                        first_post_lecture4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture4.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture4);


                        com.aware.utils.Scheduler.Schedule first_post_lecture5 = new com.aware.utils.Scheduler.Schedule("first_post_lecture5" + UserData.Username);
                        first_post_lecture5.addWeekday(ProgrammingFundamentals.getDay3().Day);
                        first_post_lecture5.addHour(ProgrammingFundamentals.getDay3().Hour +1);
                        first_post_lecture5.addMinute(ProgrammingFundamentals.getDay3().Minute -25); //11:05 Friday

                        first_post_lecture5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture5.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture5);

                }
            }
            //First post-lecture questionnaire for Cyber Communication
            if(userCourses.contains(CyberCommunication.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the first part of Cyber Communication (1/6)");
                    esmRadio2.setTitle("Survey about the first part of Cyber Communication (2/6)");
                    esmRadio3.setTitle("Survey about the first part of Cyber Communication (3/6)");
                    esmRadio4.setTitle("Survey about the first part of Cyber Communication (4/6)");
                    esmRadio5.setTitle("Survey about the first part of Cyber Communication (5/6)");
                    esmRadio6.setTitle("Survey about the first part of Cyber Communication (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);

//                    esmFreeText.setTitle("Survey about the first lecture of Cyber Communication (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Cyber Communication (7/7)");


                        com.aware.utils.Scheduler.Schedule first_post_lecture6 = new com.aware.utils.Scheduler.Schedule("first_post_lecture6" + UserData.Username);
                        first_post_lecture6.addWeekday(CyberCommunication.getDay1().Day);
                        first_post_lecture6.addHour(CyberCommunication.getDay1().Hour +1);
                        first_post_lecture6.addMinute(CyberCommunication.getDay1().Minute -25); //11:05 Tuesday

                        first_post_lecture6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture6.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture6);


                        com.aware.utils.Scheduler.Schedule first_post_lecture7 = new com.aware.utils.Scheduler.Schedule("first_post_lecture7" + UserData.Username);
                        first_post_lecture7.addWeekday(CyberCommunication.getDay2().Day);
                        first_post_lecture7.addHour(CyberCommunication.getDay2().Hour +1);
                        first_post_lecture7.addMinute(CyberCommunication.getDay2().Minute -25); //11:05 Wedneday

                        first_post_lecture7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture7.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture7);


                        com.aware.utils.Scheduler.Schedule first_post_lecture8 = new com.aware.utils.Scheduler.Schedule("first_post_lecture8" + UserData.Username);
                        first_post_lecture8.addWeekday(CyberCommunication.getDay3().Day);
                        first_post_lecture8.addHour(CyberCommunication.getDay3().Hour +1);
                        first_post_lecture8.addMinute(CyberCommunication.getDay3().Minute -25); //11:05 Thursday

                        first_post_lecture8.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture8.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture8);

                }
            }

            //First post-lecture questionnaire for Information Security
            if(userCourses.contains(InformationSecurity.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the first part of Information Security (1/6)");
                    esmRadio2.setTitle("Survey about the first part of Information Security (2/6)");
                    esmRadio3.setTitle("Survey about the first part of Information Security (3/6)");
                    esmRadio4.setTitle("Survey about the first part of Information Security (4/6)");
                    esmRadio5.setTitle("Survey about the first part of Information Security (5/6)");
                    esmRadio6.setTitle("Survey about the first part of Information Security (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);

//                    esmFreeText.setTitle("Survey about the first lecture of Information Security (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Information Security (7/7)");


                        com.aware.utils.Scheduler.Schedule first_post_lecture9 = new com.aware.utils.Scheduler.Schedule("first_post_lecture9" + UserData.Username);
                        first_post_lecture9.addWeekday(InformationSecurity.getDay1().Day);
                        first_post_lecture9.addHour(InformationSecurity.getDay1().Hour +1);
                        first_post_lecture9.addMinute(InformationSecurity.getDay1().Minute -25); //14:05 Monday -good

                        first_post_lecture9.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture9.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture9);

                        com.aware.utils.Scheduler.Schedule first_post_lecture10 = new com.aware.utils.Scheduler.Schedule("first_post_lecture10" + UserData.Username);
                        first_post_lecture10.addWeekday(InformationSecurity.getDay2().Day);
                        first_post_lecture10.addHour(InformationSecurity.getDay2().Hour +1);
                        first_post_lecture10.addMinute(InformationSecurity.getDay2().Minute -25); //16:05 Monday -BAD!!!!!

                        first_post_lecture10.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture10.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture10);

                }
            }

            //First post-lecture questionnaire for Software Architecture and Design
            if(userCourses.contains(SoftwareArchitecture.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the first part of Software Architecture and Design (1/6)");
                    esmRadio2.setTitle("Survey about the first part of Software Architecture and Design (2/6)");
                    esmRadio3.setTitle("Survey about the first part of Software Architecture and Design (3/6)");
                    esmRadio4.setTitle("Survey about the first part of Software Architecture and Design (4/6)");
                    esmRadio5.setTitle("Survey about the first part of Software Architecture and Design (5/6)");
                    esmRadio6.setTitle("Survey about the first part of Software Architecture and Design (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);

//                    esmFreeText.setTitle("Survey about the first lecture of Software Architecture and Design (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Software Architecture and Design (7/7)");


                        com.aware.utils.Scheduler.Schedule first_post_lecture11 = new com.aware.utils.Scheduler.Schedule("first_post_lecture11" + UserData.Username);
                        first_post_lecture11.addWeekday(SoftwareArchitecture.getDay1().Day);
                        first_post_lecture11.addHour(SoftwareArchitecture.getDay1().Hour +1);
                        first_post_lecture11.addMinute(SoftwareArchitecture.getDay1().Minute -25); //14:05 Tuesday

                        first_post_lecture11.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture11.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture11);


                        com.aware.utils.Scheduler.Schedule first_post_lecture12 = new com.aware.utils.Scheduler.Schedule("first_post_lecture12" + UserData.Username);
                        first_post_lecture12.addWeekday(SoftwareArchitecture.getDay2().Day);
                        first_post_lecture12.addHour(SoftwareArchitecture.getDay2().Hour +1);
                        first_post_lecture12.addMinute(SoftwareArchitecture.getDay2().Minute -25); //14:05 Thursday

                        first_post_lecture12.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        first_post_lecture12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        first_post_lecture12.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, first_post_lecture12);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    ////Create second post lecture survey
    public void createSecondPostLectureESM(Context context, String userCourses) {
        try {

            System.out.println("Second Postlecture triggered.");
            ESMFactory factory = new ESMFactory();

            ESM_Radio esmRadio1 = new ESM_Radio();
            esmRadio1.setNotificationTimeout(60 * 630)              //Timeout should be set so that SecondLectureESM expires at 19:00
                    .setInstructions("I was happy in this lecture.");


            ESM_Radio esmRadio2 = new ESM_Radio();
            esmRadio2.setNotificationTimeout(60 * 630)              //Timeout should be set so that SecondLectureESM expires at 19:00
                    .setInstructions("I didn't feel very accomplished in this lecture.");

            ESM_Radio esmRadio3 = new ESM_Radio();
            esmRadio3.setNotificationTimeout(60 * 630)              //Timeout should be set so that SecondLectureESM expires at 19:00
                    .setInstructions("I felt excited by the work in this lecture.");

            ESM_Radio esmRadio4 = new ESM_Radio();
            esmRadio4.setNotificationTimeout(60 * 630)              //Timeout should be set so that SecondLectureESM expires at 19:00
                    .setInstructions("I liked being at this lecture.");

            ESM_Radio esmRadio5 = new ESM_Radio();
            esmRadio5.setNotificationTimeout(60 * 630)              //Timeout should be set so that SecondLectureESM expires at 19:00
                    .setInstructions("I am interested in the work done in this lecture.");

            ESM_Radio esmRadio6 = new ESM_Radio();
            esmRadio6.setNotificationTimeout(60 * 630)              //Timeout should be set so that SecondLectureESM expires at 19:00
                    .setInstructions("My classroom is an interesting place to be.");


//            ESM_Freetext esmFreeText = new ESM_Freetext();
//            esmFreeText.setNotificationTimeout(60 * 100)            //Timeout should be set so that SecondLectureESM expires at 19:00
//                    .setInstructions("Please describe the moment(s) during which you felt particularly engaged")
//                    .setSubmitButton("Done");
//
//
//            ESM_QuickAnswer esmQuickAnswer = new ESM_QuickAnswer();
//            esmQuickAnswer.addQuickAnswer("Yes")
//                    .addQuickAnswer("No")
//                    .setNotificationTimeout(60 * 100)              //Timeout should be set so that SecondLectureESM expires at 19:00
//                    .setInstructions("Did you feel particularly engaged in one or more moments during the lecture?")
//                    .addFlow("Yes", esmFreeText.build());

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

            //Second post-lecture questionnaire for Linear Algebra
            if(userCourses.contains(LinearAlgebra.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the second part of Linear Algebra (1/6)");
                    esmRadio2.setTitle("Survey about the second part of Linear Algebra (2/6)");
                    esmRadio3.setTitle("Survey about the second part of Linear Algebra (3/6)");
                    esmRadio4.setTitle("Survey about the second part of Linear Algebra (4/6)");
                    esmRadio5.setTitle("Survey about the second part of Linear Algebra (5/6)");
                    esmRadio6.setTitle("Survey about the second part of Linear Algebra (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);

//                    esmFreeText.setTitle("Survey about the first lecture of Linear Algebra (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Linear Algebra (7/7)");


                        com.aware.utils.Scheduler.Schedule second_post_lecture1 = new com.aware.utils.Scheduler.Schedule("second_post_lecture1" + UserData.Username);
                        second_post_lecture1.addWeekday(LinearAlgebra.getDay1().Day);
                        second_post_lecture1.addHour(LinearAlgebra.getDay1().Hour +2);
                        second_post_lecture1.addMinute(LinearAlgebra.getDay1().Minute -25); //10:05 Monday -good

                        second_post_lecture1.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture1.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture1.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture1);


                        com.aware.utils.Scheduler.Schedule second_post_lecture2 = new com.aware.utils.Scheduler.Schedule("second_post_lecture2" + UserData.Username);
                        second_post_lecture2.addWeekday(LinearAlgebra.getDay2().Day);
                        second_post_lecture2.addHour(LinearAlgebra.getDay2().Hour +2 );
                        second_post_lecture2.addMinute(LinearAlgebra.getDay2().Minute -25); //10:05 Wednesday

                        second_post_lecture2.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture2.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture2.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture2);

                }
            }

            //Second post-lecture questionnaire for Programming Fundamentals
            if(userCourses.contains(ProgrammingFundamentals.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the second part of Programming Fundamentals (1/6)");
                    esmRadio2.setTitle("Survey about the second part of Programming Fundamentals (2/6)");
                    esmRadio3.setTitle("Survey about the second part of Programming Fundamentals (3/6)");
                    esmRadio4.setTitle("Survey about the second part of Programming Fundamentals (4/6)");
                    esmRadio5.setTitle("Survey about the second part of Programming Fundamentals (5/6)");
                    esmRadio6.setTitle("Survey about the second part of Programming Fundamentals (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);
//                    esmFreeText.setTitle("Survey about the first lecture of Programming Fundamentals (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Programming Fundamentals (7/7)");


                        com.aware.utils.Scheduler.Schedule second_post_lecture3 = new com.aware.utils.Scheduler.Schedule("second_post_lecture3" + UserData.Username);
                        second_post_lecture3.addWeekday(ProgrammingFundamentals.getDay1().Day);
                        second_post_lecture3.addHour(ProgrammingFundamentals.getDay1().Hour +2);
                        second_post_lecture3.addMinute(ProgrammingFundamentals.getDay1().Minute -25); //12:05 Monday -good

                        second_post_lecture3.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture3.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture3.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture3);


                        com.aware.utils.Scheduler.Schedule second_post_lecture4 = new com.aware.utils.Scheduler.Schedule("second_post_lecture4" + UserData.Username);
                        second_post_lecture4.addWeekday(ProgrammingFundamentals.getDay2().Day);
                        second_post_lecture4.addHour(ProgrammingFundamentals.getDay2().Hour +2);
                        second_post_lecture4.addMinute(ProgrammingFundamentals.getDay2().Minute -25); //12:05 Wednesday

                        second_post_lecture4.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture4.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture4.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture4);


                        com.aware.utils.Scheduler.Schedule second_post_lecture5 = new com.aware.utils.Scheduler.Schedule("second_post_lecture5" + UserData.Username);
                        second_post_lecture5.addWeekday(ProgrammingFundamentals.getDay3().Day);
                        second_post_lecture5.addHour(ProgrammingFundamentals.getDay3().Hour +2);
                        second_post_lecture5.addMinute(ProgrammingFundamentals.getDay3().Minute -25); //12:05 Friday

                        second_post_lecture5.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture5.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture5.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture5);

                }
            }
            //Second post-lecture questionnaire for Cyber Communication
            if(userCourses.contains(CyberCommunication.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the second part of Cyber Communication (1/6)");
                    esmRadio2.setTitle("Survey about the second part of Cyber Communication (2/6)");
                    esmRadio3.setTitle("Survey about the second part of Cyber Communication (3/6)");
                    esmRadio4.setTitle("Survey about the second part of Cyber Communication (4/6)");
                    esmRadio5.setTitle("Survey about the second part of Cyber Communication (5/6)");
                    esmRadio6.setTitle("Survey about the second part of Cyber Communication (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);
//                    esmFreeText.setTitle("Survey about the first lecture of Cyber Communication (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Cyber Communication (7/7)");


                        com.aware.utils.Scheduler.Schedule second_post_lecture6 = new com.aware.utils.Scheduler.Schedule("second_post_lecture6" + UserData.Username);
                        second_post_lecture6.addWeekday(CyberCommunication.getDay1().Day);
                        second_post_lecture6.addHour(CyberCommunication.getDay1().Hour +2);
                        second_post_lecture6.addMinute(CyberCommunication.getDay1().Minute -25); //12:05 Tuesday

                        second_post_lecture6.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture6.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture6.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture6);


                        com.aware.utils.Scheduler.Schedule second_post_lecture7 = new com.aware.utils.Scheduler.Schedule("second_post_lecture7" + UserData.Username);
                        second_post_lecture7.addWeekday(CyberCommunication.getDay2().Day);
                        second_post_lecture7.addHour(CyberCommunication.getDay2().Hour +2);
                        second_post_lecture7.addMinute(CyberCommunication.getDay2().Minute -25); //12:05 Wednesday

                        second_post_lecture7.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture7.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture7.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture7);


                        com.aware.utils.Scheduler.Schedule second_post_lecture8 = new com.aware.utils.Scheduler.Schedule("second_post_lecture8" + UserData.Username);
                        second_post_lecture8.addWeekday(CyberCommunication.getDay3().Day);
                        second_post_lecture8.addHour(CyberCommunication.getDay3().Hour +2);
                        second_post_lecture8.addMinute(CyberCommunication.getDay3().Minute -25); //12:05 Thursday

                        second_post_lecture8.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture8.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture8.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture8);

                }
            }

            //Second post-lecture questionnaire for Information Security
            if(userCourses.contains(InformationSecurity.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the second part of Information Security (1/6)");
                    esmRadio2.setTitle("Survey about the second part of Information Security (2/6)");
                    esmRadio3.setTitle("Survey about the second part of Information Security (3/6)");
                    esmRadio4.setTitle("Survey about the second part of Information Security (4/6)");
                    esmRadio5.setTitle("Survey about the second part of Information Security (5/6)");
                    esmRadio6.setTitle("Survey about the second part of Information Security (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);
//                    esmFreeText.setTitle("Survey about the first lecture of Information Security (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Information Security (7/7)");


                        com.aware.utils.Scheduler.Schedule second_post_lecture9 = new com.aware.utils.Scheduler.Schedule("second_post_lecture9" + UserData.Username);
                        second_post_lecture9.addWeekday(InformationSecurity.getDay1().Day);
                        second_post_lecture9.addHour(InformationSecurity.getDay1().Hour +2);
                        second_post_lecture9.addMinute(InformationSecurity.getDay1().Minute -25); //15:05 Monday -good

                        second_post_lecture9.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture9.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture9.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture9);


                        com.aware.utils.Scheduler.Schedule second_post_lecture10 = new com.aware.utils.Scheduler.Schedule("second_post_lecture10" + UserData.Username);
                        second_post_lecture10.addWeekday(InformationSecurity.getDay2().Day);
                        second_post_lecture10.addHour(InformationSecurity.getDay2().Hour +2);
                        second_post_lecture10.addMinute(InformationSecurity.getDay2().Minute -25); //17:05 Monday -BAD!!!!

                        second_post_lecture10.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture10.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture10.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture10);

                }
            }

            //Second post-lecture questionnaire for Software Architecture and Design
            if(userCourses.contains(SoftwareArchitecture.getName())) {
                if (!UserData.Username.equals("/")) {
                    esmRadio1.setTitle("Survey about the second part of Software Architecture and Design (1/6)");
                    esmRadio2.setTitle("Survey about the second part of Software Architecture and Design (2/6)");
                    esmRadio3.setTitle("Survey about the second part of Software Architecture and Design (3/6)");
                    esmRadio4.setTitle("Survey about the second part of Software Architecture and Design (4/6)");
                    esmRadio5.setTitle("Survey about the second part of Software Architecture and Design (5/6)");
                    esmRadio6.setTitle("Survey about the second part of Software Architecture and Design (6/6)");

                    factory.addESM(esmRadio1);
                    factory.addESM(esmRadio2);
                    factory.addESM(esmRadio3);
                    factory.addESM(esmRadio4);
                    factory.addESM(esmRadio5);
                    factory.addESM(esmRadio6);
//                    esmFreeText.setTitle("Survey about the first lecture of Software Architecture and Design (7/7)");
//                    esmQuickAnswer.setTitle("Survey about the first lecture of Software Architecture and Design (7/7)");


                        com.aware.utils.Scheduler.Schedule second_post_lecture11 = new com.aware.utils.Scheduler.Schedule("second_post_lecture11" + UserData.Username);
                        second_post_lecture11.addWeekday(SoftwareArchitecture.getDay1().Day);
                        second_post_lecture11.addHour(SoftwareArchitecture.getDay1().Hour +2);
                        second_post_lecture11.addMinute(SoftwareArchitecture.getDay1().Minute -25); //15:05 Tuesday

                        second_post_lecture11.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture11.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture11.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture11);


                        com.aware.utils.Scheduler.Schedule second_post_lecture12 = new com.aware.utils.Scheduler.Schedule("second_post_lecture12" + UserData.Username);
                        second_post_lecture12.addWeekday(SoftwareArchitecture.getDay2().Day);
                        second_post_lecture12.addHour(SoftwareArchitecture.getDay2().Hour +2);
                        second_post_lecture12.addMinute(SoftwareArchitecture.getDay2().Minute -25); //15:05 Thursday

                        second_post_lecture12.setActionType(com.aware.utils.Scheduler.ACTION_TYPE_BROADCAST);
                        second_post_lecture12.setActionIntentAction(ESM.ACTION_AWARE_QUEUE_ESM);
                        second_post_lecture12.addActionExtra(ESM.EXTRA_ESM, factory.build());
                        com.aware.utils.Scheduler.saveSchedule(context, second_post_lecture12);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
