package ch.usi.inf.mc.awareapp.RemoteStorage;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by denzil on 19/02/2017.
 */

public class ESMUploader extends IntentService {

    public static String EXTRA_ESM_DATA = "esm_JSON";

    public ESMUploader() {
        super("ESMUploader");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String esm_JSON = intent.getStringExtra(EXTRA_ESM_DATA);

        //UPLOAD here
    }
}
