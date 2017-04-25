package ch.usi.inf.mc.awareapp.RemoteStorage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import ch.usi.inf.mc.awareapp.Courses.AlarmNotificationReceiver;
import ch.usi.inf.mc.awareapp.Database.UserData;

/**
 * Created by Danilo on 2/24/17.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("I am in alarm receiver. I call recall receiver every day!");
        setAlarm(context, 2);
        //Start UploadService
        Intent intent1 = new Intent(context, AlarmService.class);
        context.startService(intent1);
    }
    public void setAlarm(Context context, int requestCode){

        Intent intent = new Intent(context, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+86400000, PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_ONE_SHOT)); //86 400 000 (1 day)

    }
}
