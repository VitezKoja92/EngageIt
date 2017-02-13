package ch.usi.inf.mc.awareapp.PhotoGrid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danilo on 1/6/17.
 */

public class GridReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        System.out.println("GridReceiver is fired at: "+currentDateandTime);
        Intent i = new Intent(context, PhotoGridActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
