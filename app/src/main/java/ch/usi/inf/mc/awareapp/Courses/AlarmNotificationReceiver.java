package ch.usi.inf.mc.awareapp.Courses;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v4.app.NotificationCompat;

import ch.usi.inf.mc.awareapp.R;

/**
 * Created by Danilo on 4/6/17.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {

    /* Creation of the courses - BEGIN */

    //Linear Algebra on Monday and  Wednesday from 8:30 until 10:15
    public Weekday mondayLA = new Weekday(23, 57, 7);             //Exact schedule should be Monday 8:30     DAY - SUNDAY=1, MONDAY=2
    public Weekday wednesdayLA = new Weekday(23, 57, 7);       //Exact schedule should be Wednesday 8:30
    public Course LinearAlgebra = new Course(mondayLA, wednesdayLA, "Linear Algebra");

    //Programming Fundamentals on Monday, Wednesday and Friday from 10:30 until 12:15
    public Weekday mondayPF = new Weekday(22, 59, 7);            //Exact schedule should be Monday 10:30
    public Weekday wednesdayPF = new Weekday(22, 49, 7);      //Exact schedule should be Wednesday 10:30
    public Weekday fridayPF = new Weekday(22, 50, 7);            //Exact schedule should be Friday 10:30
    public Course ProgrammingFundamentals = new Course(mondayPF, wednesdayPF,fridayPF, "Programming Fundamentals");

    //Cyber Communication on Tuesday, Wednesday and Thursday from 10:30 until 12:15
    public Weekday tuesdayCC = new Weekday(21, 49, 3);          //Exact schedule should be Tuesday 10:30
    public Weekday wednesdayCC = new Weekday(21, 10, 4);      //Exact schedule should be Wednesday 10:30
    public Weekday thursdayCC = new Weekday(21, 10, 5);        //Exact schedule should be Thursday 10:30
    public Course CyberCommunication = new Course(tuesdayCC, wednesdayCC,thursdayCC, "Cyber Communication");

    //Information Security on Monday from 13:30 until 17:15
    public Weekday mondayInf1 = new Weekday(21, 50, 2);          //Exact schedule should be Monday 13:30
    public Weekday mondayInf2 = new Weekday(21, 10, 2);          //Exact schedule should be Monday 15:30
    public Course InformationSecurity = new Course(mondayInf1, mondayInf2, "Information Security");

    //Software Architecture on Tuesday and Thursday from 13:30 until 17:15
    public Weekday tuesdaySAD = new Weekday(21, 51, 3);         //Exact schedule should be Tuesday 13:30
    public Weekday thursdaySAD = new Weekday(21, 10, 5);       //Exact schedule should be Thursday 13:30
    public Course SoftwareArchitecture = new Course(tuesdaySAD, thursdaySAD, "Software Architecture and Design");

    /* Creation of the courses - END */


    @Override
    public void onReceive(Context context, Intent intent) {

        String course = intent.getExtras().get("course").toString();
        String questionnaire = intent.getExtras().get("questionnaire").toString();
        System.out.println("Course: "+intent.getExtras().get("course"));
        System.out.println("Questionnaire: "+intent.getExtras().get("questionnaire"));
        System.out.println("I am in receiver!");

        //here for each case we need to reschedule the alarm for next week, and set the notification

        if(course.equals("MondayLA")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayLA", "FirstPAM", 61, mondayLA, 0, 0);
                setNotification(context, "MondayLA", "FirstPAM", 1, "Questionnaire!", "Time to answer pre-lecture PAM!", 1);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "MondayLA", "SecondPAM", 62, mondayLA, 1, 15);
                setNotification(context, "MondayLA", "SecondPAM", 2, "Questionnaire!", "Time to answer first post-lecture PAM!", 2);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "MondayLA", "ThirdPAM", 63, mondayLA, 2, 15);
                setNotification(context, "MondayLA", "ThirdPAM", 3, "Questionnaire!", "Time to answer second post-lecture PAM!", 3);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayLA", "FirstPostlecture", 64, mondayLA, 1, 15);
                setNotification(context, "MondayLA", "FirstPostlecture", 4, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 4);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayLA", "SecondPostlecture", 65, mondayLA, 2, 15);
                setNotification(context, "MondayLA", "SecondPostlecture", 5, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 5);
            }
        }

        if(course.equals("WednesdayLA")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "WednesdayLA", "FirstPAM", 66, mondayLA, 0, 0);
                setNotification(context, "WednesdayLA", "FirstPAM", 6, "Questionnaire!", "Time to answer pre-lecture PAM!", 6);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "WednesdayLA", "SecondPAM", 67, mondayLA, 1, 15);
                setNotification(context, "WednesdayLA", "SecondPAM", 7, "Questionnaire!", "Time to answer first post-lecture PAM!", 7);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "WednesdayLA", "ThirdPAM", 68, mondayLA, 2, 15);
                setNotification(context, "WednesdayLA", "ThirdPAM", 8, "Questionnaire!", "Time to answer second post-lecture PAM!", 8);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "WednesdayLA", "FirstPostlecture", 69, mondayLA, 1, 15);
                setNotification(context, "WednesdayLA", "FirstPostlecture", 9, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 9);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "WednesdayLA", "SecondPostlecture", 70, mondayLA, 2, 15);
                setNotification(context, "WednesdayLA", "SecondPostlecture", 10, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 10);
            }
        }

        if(course.equals("MondayPF")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayPF", "FirstPAM", 71, mondayLA, 0, 0);
                setNotification(context, "MondayPF", "FirstPAM", 11, "Questionnaire!", "Time to answer pre-lecture PAM!", 11);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "MondayPF", "SecondPAM", 72, mondayLA, 1, 15);
                setNotification(context, "MondayPF", "SecondPAM", 12, "Questionnaire!", "Time to answer first post-lecture PAM!", 12);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "MondayPF", "ThirdPAM", 73, mondayLA, 2, 15);
                setNotification(context, "MondayPF", "ThirdPAM", 13, "Questionnaire!", "Time to answer second post-lecture PAM!", 13);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayPF", "FirstPostlecture", 74, mondayLA, 1, 15);
                setNotification(context, "MondayPF", "FirstPostlecture", 14, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 14);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayPF", "SecondPostlecture", 75, mondayLA, 2, 15);
                setNotification(context, "MondayPF", "SecondPostlecture", 15, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 15);
            }
        }

        if(course.equals("WednesdayPF")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "WednesdayPF", "FirstPAM", 76, mondayLA, 0, 0);
                setNotification(context, "WednesdayPF", "FirstPAM", 16, "Questionnaire!", "Time to answer pre-lecture PAM!", 16);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "WednesdayPF", "SecondPAM", 77, mondayLA, 1, 15);
                setNotification(context, "WednesdayPF", "SecondPAM", 17, "Questionnaire!", "Time to answer first post-lecture PAM!", 17);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "WednesdayPF", "ThirdPAM", 78, mondayLA, 2, 15);
                setNotification(context, "WednesdayPF", "ThirdPAM", 18, "Questionnaire!", "Time to answer second post-lecture PAM!", 18);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "WednesdayPF", "FirstPostlecture", 79, mondayLA, 1, 15);
                setNotification(context, "WednesdayPF", "FirstPostlecture", 19, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 19);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "WednesdayPF", "SecondPostlecture", 80, mondayLA, 2, 15);
                setNotification(context, "WednesdayPF", "SecondPostlecture", 20, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 20);
            }
        }

        if(course.equals("FridayPF")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "FridayPF", "FirstPAM", 81, mondayLA, 0, 0);
                setNotification(context, "FridayPF", "FirstPAM", 21, "Questionnaire!", "Time to answer pre-lecture PAM!", 21);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "FridayPF", "SecondPAM", 82, mondayLA, 1, 15);
                setNotification(context, "FridayPF", "SecondPAM", 22, "Questionnaire!", "Time to answer first post-lecture PAM!", 22);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "FridayPF", "ThirdPAM", 83, mondayLA, 2, 15);
                setNotification(context, "FridayPF", "ThirdPAM", 23, "Questionnaire!", "Time to answer second post-lecture PAM!", 23);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "FridayPF", "FirstPostlecture", 84, mondayLA, 1, 15);
                setNotification(context, "FridayPF", "FirstPostlecture", 24, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 24);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "FridayPF", "SecondPostlecture", 85, mondayLA, 2, 15);
                setNotification(context, "FridayPF", "SecondPostlecture", 25, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 25);
            }
        }

        if(course.equals("TuesdayCC")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "TuesdayCC", "FirstPAM", 86, mondayLA, 0, 0);
                setNotification(context, "TuesdayCC", "FirstPAM", 26, "Questionnaire!", "Time to answer pre-lecture PAM!", 26);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "TuesdayCC", "SecondPAM", 87, mondayLA, 1, 15);
                setNotification(context, "TuesdayCC", "SecondPAM", 27, "Questionnaire!", "Time to answer first post-lecture PAM!", 27);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "TuesdayCC", "ThirdPAM", 88, mondayLA, 2, 15);
                setNotification(context, "TuesdayCC", "ThirdPAM", 28, "Questionnaire!", "Time to answer second post-lecture PAM!", 28);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "TuesdayCC", "FirstPostlecture", 89, mondayLA, 1, 15);
                setNotification(context, "TuesdayCC", "FirstPostlecture", 29, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 29);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "TuesdayCC", "SecondPostlecture", 90, mondayLA, 2, 15);
                setNotification(context, "TuesdayCC", "SecondPostlecture", 30, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 30);
            }
        }

        if(course.equals("WednesdayCC")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "WednesdayCC", "FirstPAM", 91, mondayLA, 0, 0);
                setNotification(context, "WednesdayCC", "FirstPAM", 31, "Questionnaire!", "Time to answer pre-lecture PAM!", 31);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "WednesdayCC", "SecondPAM", 92, mondayLA, 1, 15);
                setNotification(context, "WednesdayCC", "SecondPAM", 32, "Questionnaire!", "Time to answer first post-lecture PAM!", 32);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "WednesdayCC", "ThirdPAM", 93, mondayLA, 2, 15);
                setNotification(context, "WednesdayCC", "ThirdPAM", 33, "Questionnaire!", "Time to answer second post-lecture PAM!", 33);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "WednesdayCC", "FirstPostlecture", 94, mondayLA, 1, 15);
                setNotification(context, "WednesdayCC", "FirstPostlecture", 34, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 34);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "WednesdayCC", "SecondPostlecture", 95, mondayLA, 2, 15);
                setNotification(context, "WednesdayCC", "SecondPostlecture", 35, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 35);
            }
        }

        if(course.equals("ThursdayCC")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "ThursdayCC", "FirstPAM", 96, mondayLA, 0, 0);
                setNotification(context, "ThursdayCC", "FirstPAM", 36, "Questionnaire!", "Time to answer pre-lecture PAM!", 36);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "ThursdayCC", "SecondPAM", 97, mondayLA, 1, 15);
                setNotification(context, "ThursdayCC", "SecondPAM", 37, "Questionnaire!", "Time to answer first post-lecture PAM!", 37);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "ThursdayCC", "ThirdPAM", 98, mondayLA, 2, 15);
                setNotification(context, "ThursdayCC", "ThirdPAM", 38, "Questionnaire!", "Time to answer second post-lecture PAM!", 38);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "ThursdayCC", "FirstPostlecture", 99, mondayLA, 1, 15);
                setNotification(context, "ThursdayCC", "FirstPostlecture", 39, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 39);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "ThursdayCC", "SecondPostlecture", 100, mondayLA, 2, 15);
                setNotification(context, "ThursdayCC", "SecondPostlecture", 40, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 40);
            }
        }


        if(course.equals("MondayInf1")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayInf1", "FirstPAM", 101, mondayLA, 0, 0);
                setNotification(context, "MondayInf1", "FirstPAM", 41, "Questionnaire!", "Time to answer pre-lecture PAM!", 41);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "MondayInf1", "SecondPAM", 102, mondayLA, 1, 15);
                setNotification(context, "MondayInf1", "SecondPAM", 42, "Questionnaire!", "Time to answer first post-lecture PAM!", 42);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "MondayInf1", "ThirdPAM", 103, mondayLA, 2, 15);
                setNotification(context, "MondayInf1", "ThirdPAM", 43, "Questionnaire!", "Time to answer second post-lecture PAM!", 33);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayInf1", "FirstPostlecture", 104, mondayLA, 1, 15);
                setNotification(context, "MondayInf1", "FirstPostlecture", 44, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 44);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayInf1", "SecondPostlecture", 105, mondayLA, 2, 15);
                setNotification(context, "MondayInf1", "SecondPostlecture", 45, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 45);
            }
        }

        if(course.equals("MondayInf2")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "MondayInf2", "FirstPAM", 106, mondayLA, 0, 0);
                setNotification(context, "MondayInf2", "FirstPAM", 46, "Questionnaire!", "Time to answer pre-lecture PAM!", 46);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "MondayInf2", "SecondPAM", 107, mondayLA, 1, 15);
                setNotification(context, "MondayInf2", "SecondPAM", 47, "Questionnaire!", "Time to answer first post-lecture PAM!", 47);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "MondayInf2", "ThirdPAM", 108, mondayLA, 2, 15);
                setNotification(context, "MondayInf2", "ThirdPAM", 48, "Questionnaire!", "Time to answer second post-lecture PAM!", 48);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "MondayInf2", "FirstPostlecture", 109, mondayLA, 1, 15);
                setNotification(context, "MondayInf2", "FirstPostlecture", 49, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 49);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "MondayInf2", "SecondPostlecture", 110, mondayLA, 2, 15);
                setNotification(context, "MondayInf2", "SecondPostlecture", 50, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 50);
            }
        }

        if(course.equals("TuesdaySAD")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "TuesdaySAD", "FirstPAM", 111, mondayLA, 0, 0);
                setNotification(context, "TuesdaySAD", "FirstPAM", 51, "Questionnaire!", "Time to answer pre-lecture PAM!", 51);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "TuesdaySAD", "SecondPAM", 112, mondayLA, 1, 15);
                setNotification(context, "TuesdaySAD", "SecondPAM", 52, "Questionnaire!", "Time to answer first post-lecture PAM!", 52);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "TuesdaySAD", "ThirdPAM", 113, mondayLA, 2, 15);
                setNotification(context, "TuesdaySAD", "ThirdPAM", 53, "Questionnaire!", "Time to answer second post-lecture PAM!", 53);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "TuesdaySAD", "FirstPostlecture", 114, mondayLA, 1, 15);
                setNotification(context, "TuesdaySAD", "FirstPostlecture", 54, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 54);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "TuesdaySAD", "SecondPostlecture", 115, mondayLA, 2, 15);
                setNotification(context, "TuesdaySAD", "SecondPostlecture", 55, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 55);
            }
        }

        if(course.equals("ThursdaySAD")){
            if(questionnaire.equals("FirstPAM")){
                setAlarm(context, "ThursdaySAD", "FirstPAM", 116, mondayLA, 0, 0);
                setNotification(context, "ThursdaySAD", "FirstPAM", 56, "Questionnaire!", "Time to answer pre-lecture PAM!", 56);
            }
            if(questionnaire.equals("SecondPAM")){
                setAlarm(context, "ThursdaySAD", "SecondPAM", 117, mondayLA, 1, 15);
                setNotification(context, "ThursdaySAD", "SecondPAM", 57, "Questionnaire!", "Time to answer first post-lecture PAM!", 57);
            }
            if(questionnaire.equals("ThirdPAM")){
                setAlarm(context, "ThursdaySAD", "ThirdPAM", 118, mondayLA, 2, 15);
                setNotification(context, "ThursdaySAD", "ThirdPAM", 58, "Questionnaire!", "Time to answer second post-lecture PAM!", 58);
            }
            if(questionnaire.equals("FirstPostlecture")){
                setAlarm(context, "ThursdaySAD", "FirstPostlecture", 119, mondayLA, 1, 15);
                setNotification(context, "ThursdaySAD", "FirstPostlecture", 59, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 59);
            }
            if(questionnaire.equals("SecondPostlecture")){
                setAlarm(context, "ThursdaySAD", "SecondPostlecture", 120, mondayLA, 2, 15);
                setNotification(context, "ThursdaySAD", "SecondPostlecture", 60, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 60);
            }
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
        //Linear Algebra monday
        myIntent.putExtra("course", course);
        myIntent.putExtra("questionnaire", questionnaire);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode, myIntent, PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar1 = createCalendar(weekday.getDay(),weekday.getHour()+hourAddition, weekday.getMinute()+minuteAddition);
        calendar1.add(java.util.Calendar.DAY_OF_MONTH, 7);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
        System.out.println("alarm set for the future");
    }

    public void setNotification(Context context, String course, String questionnaire, int requestCode, String title, String content, int notificationID){
        Intent intent = new Intent(context, QuestionnaireActivity.class);
        intent.putExtra("questionnaire", questionnaire);
        intent.putExtra("course", course);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        builder.setOngoing(true);  //????
        builder.setContentIntent(pendingIntent);
        //builder.addAction(1,"Questionnaire", pendingIntent);
        builder.setSmallIcon(R.drawable.rsz_1logo1_50); //bad logo

        //System.out.println("in setNotification");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationID, builder.build());

    }
}
