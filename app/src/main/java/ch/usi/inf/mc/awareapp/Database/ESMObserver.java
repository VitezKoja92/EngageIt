package ch.usi.inf.mc.awareapp.Database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Handler;

import com.aware.ESM;
import com.aware.providers.ESM_Provider;
import com.aware.utils.DatabaseHelper;
import com.aware.utils.WebserviceHelper;

import ch.usi.inf.mc.awareapp.RemoteStorage.ESMUploader;

/**
 * Created by Danilo on 2/18/17.
 */

public class ESMObserver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_ANSWERED)) {
            Cursor latest_answered = context.getContentResolver().query(ESM_Provider.ESM_Data.CONTENT_URI, null, ESM_Provider.ESM_Data.STATUS + "=" + ESM.STATUS_ANSWERED, null, ESM_Provider.ESM_Data.TIMESTAMP + " DESC LIMIT 1");
//            System.out.println("JSON String: "+ DatabaseHelper.cursorToString(latest_answered));
            if (latest_answered != null && latest_answered.moveToFirst()) {
                Intent esmoffload = new Intent(context, ESMUploader.class);
                esmoffload.putExtra(ESMUploader.EXTRA_ESM_DATA, DatabaseHelper.cursorToString(latest_answered)); //this converts the cursor to a JSON you can sent anywhere.
                context.startService(esmoffload);
            }
            if (latest_answered != null && ! latest_answered.isClosed()) latest_answered.close();
        }
    }
}
