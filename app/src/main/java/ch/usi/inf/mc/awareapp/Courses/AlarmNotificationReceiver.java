package ch.usi.inf.mc.awareapp.Courses;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.Random;

import ch.usi.inf.mc.awareapp.Database.SaveSharedPreference;
import ch.usi.inf.mc.awareapp.Database.UserData;
import ch.usi.inf.mc.awareapp.R;

/**
 * Created by Danilo on 4/6/17.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {

//    /* Creation of the courses - BEGIN */
//
//    //Linear Algebra on Monday and  Wednesday from 8:30 until 10:15
//    public Weekday mondayLA = new Weekday(10, 51, 4);             //Exact schedule should be Monday 8:30     DAY - SUNDAY=1, MONDAY=2
//    public Weekday wednesdayLA = new Weekday(23, 57, 7);       //Exact schedule should be Wednesday 8:30
//    public Course LinearAlgebra = new Course(mondayLA, wednesdayLA, "Linear Algebra");
//
//    //Programming Fundamentals on Monday, Wednesday and Friday from 10:30 until 12:15
//    public Weekday mondayPF = new Weekday(22, 59, 7);            //Exact schedule should be Monday 10:30
//    public Weekday wednesdayPF = new Weekday(22, 49, 7);      //Exact schedule should be Wednesday 10:30
//    public Weekday fridayPF = new Weekday(22, 50, 7);            //Exact schedule should be Friday 10:30
//    public Course ProgrammingFundamentals = new Course(mondayPF, wednesdayPF,fridayPF, "Programming Fundamentals");
//
//    //Cyber Communication on Tuesday, Wednesday and Thursday from 10:30 until 12:15
//    public Weekday tuesdayCC = new Weekday(21, 49, 3);          //Exact schedule should be Tuesday 10:30
//    public Weekday wednesdayCC = new Weekday(21, 10, 4);      //Exact schedule should be Wednesday 10:30
//    public Weekday thursdayCC = new Weekday(21, 10, 5);        //Exact schedule should be Thursday 10:30
//    public Course CyberCommunication = new Course(tuesdayCC, wednesdayCC,thursdayCC, "Cyber Communication");
//
//    //Information Security on Monday from 13:30 until 17:15
//    public Weekday mondayInf1 = new Weekday(21, 50, 2);          //Exact schedule should be Monday 13:30
//    public Weekday mondayInf2 = new Weekday(21, 10, 2);          //Exact schedule should be Monday 15:30
//    public Course InformationSecurity = new Course(mondayInf1, mondayInf2, "Information Security");
//
//    //Software Architecture on Tuesday and Thursday from 13:30 until 17:15
//    public Weekday tuesdaySAD = new Weekday(21, 51, 3);         //Exact schedule should be Tuesday 13:30
//    public Weekday thursdaySAD = new Weekday(21, 10, 5);       //Exact schedule should be Thursday 13:30
//    public Course SoftwareArchitecture = new Course(tuesdaySAD, thursdaySAD, "Software Architecture and Design");
//
//    /* Creation of the courses - END */


    @Override
    public void onReceive(Context context, Intent intent) {

        String course = intent.getExtras().get("course").toString();
        String questionnaire = intent.getExtras().get("questionnaire").toString();
        System.out.println("Course: "+intent.getExtras().get("course"));
        System.out.println("Questionnaire: "+intent.getExtras().get("questionnaire"));
        System.out.println("I am in receiver!");
        SaveSharedPreference saveSharedPreference= new SaveSharedPreference(context);
        String username = saveSharedPreference.getUsername();

        //here for each case we need to reschedule the alarm for next week, and set the notification
        if(!username.equals("/")){
            if(course.equals("MondayLA")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "MondayLA", "PAM", 37); //try with requestCode 61
                    setNotification(context, "MondayLA", "PAM", 1, "Questionnaire!", "Time to answer pre-lecture PAM!", 1);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "MondayLA", "FirstPostlecture", 38);
                    setNotification(context, "MondayLA", "FirstPostlecture", 2, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 2);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "MondayLA", "SecondPostlecture", 39);
                    setNotification(context, "MondayLA", "SecondPostlecture", 3, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 3);
                }
            }

            if(course.equals("WednesdayLA")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "WednesdayLA", "PAM", 40);
                    setNotification(context, "WednesdayLA", "PAM", 4, "Questionnaire!", "Time to answer pre-lecture PAM!", 4);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "WednesdayLA", "FirstPostlecture", 41);
                    setNotification(context, "WednesdayLA", "FirstPostlecture", 5, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 5);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "WednesdayLA", "SecondPostlecture", 42);
                    setNotification(context, "WednesdayLA", "SecondPostlecture", 6, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 6);
                }
            }

            if(course.equals("MondayPF")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "MondayPF", "PAM", 43);
                    setNotification(context, "MondayPF", "PAM", 7, "Questionnaire!", "Time to answer pre-lecture PAM!", 7);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "MondayPF", "FirstPostlecture", 44);
                    setNotification(context, "MondayPF", "FirstPostlecture", 8, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 8);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "MondayPF", "SecondPostlecture", 45);
                    setNotification(context, "MondayPF", "SecondPostlecture", 9, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 9);
                }
            }

            if(course.equals("WednesdayPF")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "WednesdayPF", "PAM", 46);
                    setNotification(context, "WednesdayPF", "PAM", 10, "Questionnaire!", "Time to answer pre-lecture PAM!", 10);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "WednesdayPF", "FirstPostlecture", 47);
                    setNotification(context, "WednesdayPF", "FirstPostlecture", 11, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 11);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "WednesdayPF", "SecondPostlecture", 48);
                    setNotification(context, "WednesdayPF", "SecondPostlecture", 12, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 12);
                }
            }

            if(course.equals("FridayPF")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "FridayPF", "PAM", 49);
                    setNotification(context, "FridayPF", "PAM", 13, "Questionnaire!", "Time to answer pre-lecture PAM!", 13);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "FridayPF", "FirstPostlecture", 50);
                    setNotification(context, "FridayPF", "FirstPostlecture", 14, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 14);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "FridayPF", "SecondPostlecture", 51);
                    setNotification(context, "FridayPF", "SecondPostlecture", 15, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 15);
                }
            }

            if(course.equals("TuesdayCC")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "TuesdayCC", "PAM", 52);
                    setNotification(context, "TuesdayCC", "PAM", 16, "Questionnaire!", "Time to answer pre-lecture PAM!", 16);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "TuesdayCC", "FirstPostlecture", 53);
                    setNotification(context, "TuesdayCC", "FirstPostlecture", 17, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 17);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "TuesdayCC", "SecondPostlecture", 54);
                    setNotification(context, "TuesdayCC", "SecondPostlecture", 18, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 18);
                }
            }

            if(course.equals("WednesdayCC")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "WednesdayCC", "PAM", 55);
                    setNotification(context, "WednesdayCC", "PAM", 19, "Questionnaire!", "Time to answer pre-lecture PAM!", 19);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "WednesdayCC", "FirstPostlecture", 56);
                    setNotification(context, "WednesdayCC", "FirstPostlecture", 20, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 20);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "WednesdayCC", "SecondPostlecture", 57);
                    setNotification(context, "WednesdayCC", "SecondPostlecture", 21, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 21);
                }
            }

            if(course.equals("ThursdayCC")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "ThursdayCC", "PAM", 58);
                    setNotification(context, "ThursdayCC", "PAM", 22, "Questionnaire!", "Time to answer pre-lecture PAM!", 22);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "ThursdayCC", "FirstPostlecture", 59);
                    setNotification(context, "ThursdayCC", "FirstPostlecture", 23, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 23);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "ThursdayCC", "SecondPostlecture", 60);
                    setNotification(context, "ThursdayCC", "SecondPostlecture", 24, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 24);
                }
            }


            if(course.equals("MondayInf1")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "MondayInf1", "PAM", 61);
                    setNotification(context, "MondayInf1", "PAM", 25, "Questionnaire!", "Time to answer pre-lecture PAM!", 25);
                }

                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "MondayInf1", "FirstPostlecture", 62);
                    setNotification(context, "MondayInf1", "FirstPostlecture", 26, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 26);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "MondayInf1", "SecondPostlecture", 63);
                    setNotification(context, "MondayInf1", "SecondPostlecture", 27, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 27);
                }
            }

            if(course.equals("MondayInf2")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "MondayInf2", "PAM", 64);
                    setNotification(context, "MondayInf2", "PAM", 28, "Questionnaire!", "Time to answer pre-lecture PAM!", 28);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "MondayInf2", "FirstPostlecture", 65);
                    setNotification(context, "MondayInf2", "FirstPostlecture", 29, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 29);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "MondayInf2", "SecondPostlecture", 66);
                    setNotification(context, "MondayInf2", "SecondPostlecture", 30, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 30);
                }
            }

            if(course.equals("TuesdaySAD")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "TuesdaySAD", "PAM", 67);
                    setNotification(context, "TuesdaySAD", "PAM", 31, "Questionnaire!", "Time to answer pre-lecture PAM!", 31);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "TuesdaySAD", "FirstPostlecture", 68);
                    setNotification(context, "TuesdaySAD", "FirstPostlecture", 32, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 32);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "TuesdaySAD", "SecondPostlecture", 69);
                    setNotification(context, "TuesdaySAD", "SecondPostlecture", 33, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 33);
                }
            }

            if(course.equals("ThursdaySAD")){
                if(questionnaire.equals("PAM")){
                    setAlarm(context, "ThursdaySAD", "PAM", 70);
                    setNotification(context, "ThursdaySAD", "PAM", 34, "Questionnaire!", "Time to answer pre-lecture PAM!", 34);
                }
                if(questionnaire.equals("FirstPostlecture")){
                    setAlarm(context, "ThursdaySAD", "FirstPostlecture", 71);
                    setNotification(context, "ThursdaySAD", "FirstPostlecture", 35, "Questionnaire!", "Time to answer first post-lecture questionnaire!", 35);
                }
                if(questionnaire.equals("SecondPostlecture")){
                    setAlarm(context, "ThursdaySAD", "SecondPostlecture", 72);
                    setNotification(context, "ThursdaySAD", "SecondPostlecture", 36, "Questionnaire!", "Time to answer second post-lecture questionnaire!", 36);
                }
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

    public void setAlarm(Context context, String course, String questionnaire, int requestCode){

        Intent myIntent = new Intent(context, AlarmNotificationReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        myIntent.putExtra("course", course);
        myIntent.putExtra("questionnaire", questionnaire);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode, myIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+604800000, pendingIntent1); //604 800 000 (7 days)
        System.out.println("alarm set for the future");
    }

    public void setNotification(Context context, String course, String questionnaire, int requestCode, String title, String content, int notificationID){
        Random rand = new Random();
        int code = rand.nextInt(100000000);
        System.out.println("code: "+code);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);

        Intent intent = new Intent(context, QuestionnaireActivity.class);
        intent.putExtra("questionnaire", questionnaire);
        intent.putExtra("course", course);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(QuestionnaireActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(code, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.logo_shku); //bad logo

        //System.out.println("in setNotification");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(code, builder.build());

    }
}
