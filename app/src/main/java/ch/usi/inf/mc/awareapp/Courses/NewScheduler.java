package ch.usi.inf.mc.awareapp.Courses;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.usi.inf.mc.awareapp.Database.SaveSharedPreference;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.RemoteStorage.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;


/**
 * Created by Danilo on 4/2/17.
 */

public class NewScheduler {


    /* Creation of the courses - BEGIN */

    //Linear Algebra on Monday and  Wednesday from 8:30 until 10:15
    public Weekday mondayLA = new Weekday(8, 30, 2);             //Exact schedule should be Monday 8:30     DAY - SUNDAY=1, MONDAY=2
    public Weekday wednesdayLA = new Weekday(8, 30, 4);       //Exact schedule should be Wednesday 8:30
    public Course LinearAlgebra = new Course(mondayLA, wednesdayLA, "Linear Algebra");

    //Programming Fundamentals on Monday, Wednesday and Friday from 10:30 until 12:15
    public Weekday mondayPF = new Weekday(10, 30, 2);            //Exact schedule should be Monday 10:30
    public Weekday wednesdayPF = new Weekday(10, 30, 4);      //Exact schedule should be Wednesday 10:30
    public Weekday fridayPF = new Weekday(10, 30, 6);            //Exact schedule should be Friday 10:30
    public Course ProgrammingFundamentals = new Course(mondayPF, wednesdayPF,fridayPF, "Programming Fundamentals");

    //Cyber Communication on Tuesday, Wednesday and Thursday from 10:30 until 12:15
    public Weekday tuesdayCC = new Weekday(10, 30, 3);          //Exact schedule should be Tuesday 10:30
    public Weekday wednesdayCC = new Weekday(10, 30, 4);      //Exact schedule should be Wednesday 10:30
    public Weekday thursdayCC = new Weekday(10, 30, 5);        //Exact schedule should be Thursday 10:30
    public Course CyberCommunication = new Course(tuesdayCC, wednesdayCC,thursdayCC, "Cyber Communication");

    //Information Security on Monday from 13:30 until 17:15
    public Weekday mondayInf1 = new Weekday(13, 30, 2);          //Exact schedule should be Monday 13:30
    public Weekday mondayInf2 = new Weekday(15, 30, 2);          //Exact schedule should be Monday 15:30
    public Course InformationSecurity = new Course(mondayInf1, mondayInf2, "Information Security");

    //Software Architecture on Tuesday and Thursday from 13:30 until 17:15
    public Weekday tuesdaySAD = new Weekday(13, 30, 3);         //Exact schedule should be Tuesday 13:30
    public Weekday thursdaySAD = new Weekday(13, 30, 5);       //Exact schedule should be Thursday 13:30
    public Course SoftwareArchitecture = new Course(tuesdaySAD, thursdaySAD, "Software Architecture and Design");

    /* Creation of the courses - END */

    /********** FIRST PAM **********/
    public void createFirstPAM(Context context, String courses) {
        SaveSharedPreference saveSharedPreference = new SaveSharedPreference(context);
        String username = saveSharedPreference.getUsername();

        /*LINEAR ALGEBRA*/
        if (courses.contains(LinearAlgebra.getName()) && !username.equals("/")) {
            setAlarm(context, "MondayLA", "PAM", 1, mondayLA, 0, 0);
            setAlarm(context, "WednesdayLA", "PAM", 2, wednesdayLA, 0, 0);
        }

        /*PROGRAMMING FUNDAMENTALS 2*/
        if (courses.contains(ProgrammingFundamentals.getName()) && !username.equals("/")) {
            setAlarm(context, "MondayPF", "PAM", 3, mondayPF, 0, 0);
            setAlarm(context, "WednesdayPF", "PAM", 4, wednesdayPF, 0, 0);
            setAlarm(context, "FridayPF", "PAM", 5, fridayPF, 0, 0);
        }

        /*CYBER COMMUNICATION*/
        if (courses.contains(CyberCommunication.getName())&& !username.equals("/")) {
            setAlarm(context, "TuesdayCC", "PAM", 6, tuesdayCC, 0, 0);
            setAlarm(context, "WednesdayCC", "PAM", 7, wednesdayCC, 0, 0);
            setAlarm(context, "ThursdayCC", "PAM", 8, thursdayCC, 0, 0);
        }

        /*INFORMATION SECURITY*/
        if (courses.contains(InformationSecurity.getName())&& !username.equals("/")) {
            setAlarm(context, "MondayInf1", "PAM", 9, mondayInf1, 0, 0);
            setAlarm(context, "MondayInf2", "PAM", 10, mondayInf2, 0, 0);
        }

        /*SOFTWARE ARCHITECTURE AND DESIGN*/
        if (courses.contains(SoftwareArchitecture.getName()) && !username.equals("/")) {
            setAlarm(context, "TuesdaySAD", "PAM", 11, tuesdaySAD, 0, 0);
            setAlarm(context, "ThursdaySAD", "PAM", 12, thursdaySAD, 0, 0);
        }
    }

    /********** FIRST POSTLECTURE **********/
    public void createFirstPostlecture(Context context, String courses) {
        SaveSharedPreference saveSharedPreference = new SaveSharedPreference(context);
        String username = saveSharedPreference.getUsername();

        /*LINEAR ALGEBRA*/
        if (courses.contains(LinearAlgebra.getName()) && !username.equals("/")) {
            setAlarm(context, "MondayLA", "FirstPostlecture", 13, mondayLA, 1, -15);  //hour and time addition 1 and 15
            setAlarm(context, "WednesdayLA", "FirstPostlecture", 14, wednesdayLA, 1, -15);
        }

        /*PROGRAMMING FUNDAMENTALS 2*/
        if (courses.contains(ProgrammingFundamentals.getName()) && !username.equals("/")) {
            setAlarm(context, "MondayPF", "FirstPostlecture", 15, mondayPF, 1, -15);
            setAlarm(context, "WednesdayPF", "FirstPostlecture", 16, wednesdayPF, 1, -15);
            setAlarm(context, "FridayPF", "FirstPostlecture", 17, fridayPF, 1, -15);
        }

        /*CYBER COMMUNICATION*/
        if (courses.contains(CyberCommunication.getName()) && !username.equals("/")) {
            setAlarm(context, "TuesdayCC", "FirstPostlecture", 18, tuesdayCC, 1, -15);
            setAlarm(context, "WednesdayCC", "FirstPostlecture", 19, wednesdayCC, 1, -15);
            setAlarm(context, "ThursdayCC", "FirstPostlecture", 20, thursdayCC, 1, -15);
        }

        /*INFORMATION SECURITY*/
        if (courses.contains(InformationSecurity.getName()) && !username.equals("/")) {
            setAlarm(context, "MondayInf1", "FirstPostlecture", 21, mondayInf1, 1, -15);
            setAlarm(context, "MondayInf2", "FirstPostlecture", 22, mondayInf2, 1, -15);
        }

        /*SOFTWARE ARCHITECTURE AND DESIGN*/
        if (courses.contains(SoftwareArchitecture.getName()) && !username.equals("/")) {
            setAlarm(context, "TuesdaySAD", "FirstPostlecture", 23, tuesdaySAD, 1, -15);
            setAlarm(context, "ThursdaySAD", "FirstPostlecture", 24, thursdaySAD, 1, -15);
        }
    }

    /********** SECOND POSTLECTURE **********/
    public void createSecondPostlecture(Context context, String courses) {
        SaveSharedPreference saveSharedPreference = new SaveSharedPreference(context);
        String username = saveSharedPreference.getUsername();

        /*LINEAR ALGEBRA*/
        if (courses.contains(LinearAlgebra.getName()) && !username.equals("/")){
            setAlarm(context, "MondayLA", "SecondPostlecture", 25, mondayLA, 2, -15);  //hour and time addition 2 and 15
            setAlarm(context, "WednesdayLA", "SecondPostlecture", 26, wednesdayLA, 2, -15);
        }

        /*PROGRAMMING FUNDAMENTALS 2*/
        if (courses.contains(ProgrammingFundamentals.getName()) && !username.equals("/")) {
            setAlarm(context, "MondayPF", "SecondPostlecture", 27, mondayPF, 2, -15);
            setAlarm(context, "WednesdayPF", "SecondPostlecture", 28, wednesdayPF, 2, -15);
            setAlarm(context, "FridayPF", "SecondPostlecture", 29, fridayPF, 2, -15);
        }

        /*CYBER COMMUNICATION*/
        if (courses.contains(CyberCommunication.getName()) && !username.equals("/")) {
            setAlarm(context, "TuesdayCC", "SecondPostlecture", 30, tuesdayCC, 2, -15);
            setAlarm(context, "WednesdayCC", "SecondPostlecture", 31, wednesdayCC, 2, -15);
            setAlarm(context, "ThursdayCC", "SecondPostlecture", 32, thursdayCC, 2, -15);
        }

        /*INFORMATION SECURITY*/
        if (courses.contains(InformationSecurity.getName()) && !username.equals("/")) {
            setAlarm(context, "MondayInf1", "SecondPostlecture", 33, mondayInf1, 2, -15);
            setAlarm(context, "MondayInf2", "SecondPostlecture", 34, mondayInf2, 2, -15);
        }

        /*SOFTWARE ARCHITECTURE AND DESIGN*/
        if (courses.contains(SoftwareArchitecture.getName()) && !username.equals("/")) {
            setAlarm(context, "TuesdaySAD", "SecondPostlecture", 35, tuesdaySAD, 2, -15);
            setAlarm(context, "ThursdaySAD", "SecondPostlecture", 36, thursdaySAD, 2, -15);
        }
    }

    public Calendar createCalendar(int day, int hour, int minute){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    public void setAlarm(Context context, String course, String questionnaire, int requestCode, Weekday weekday, int hourAddition, int minuteAddition){
        Intent myIntent = new Intent(context, AlarmNotificationReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        myIntent.putExtra("course", course);
        myIntent.putExtra("questionnaire", questionnaire);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode, myIntent, PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar1 = createCalendar(weekday.getDay(),weekday.getHour()+hourAddition, weekday.getMinute()+minuteAddition);

        if (calendar1.getTimeInMillis() > System.currentTimeMillis()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        } else {
            calendar1.add(java.util.Calendar.DAY_OF_MONTH, 7);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        }

    }

}

