package ch.usi.inf.mc.awareapp.Database;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by Danilo on 2/18/17.
 */

public class ESMObserver extends ContentObserver{

    public ESMObserver(Handler handler) {
        super(handler);
    }
    
    public void onCreate(boolean selfChange){
        this.onChange(selfChange, null);


    }


}
