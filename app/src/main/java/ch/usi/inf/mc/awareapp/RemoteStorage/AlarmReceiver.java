package ch.usi.inf.mc.awareapp.RemoteStorage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Danilo on 2/24/17.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        //Start UploadService
        Intent intent1 = new Intent(context, AlarmService.class);
        context.startService(intent1);
    }
}
