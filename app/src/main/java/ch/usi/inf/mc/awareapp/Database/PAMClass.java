package ch.usi.inf.mc.awareapp.Database;

import java.io.Serializable;

/**
 * Created by Danilo on 1/17/17.
 */

public class PAMClass implements Serializable {
    public int _id;
    public String _username;
    public String _android_id;
    public Boolean _attendance;
    public String _pam_answer;
    public String _currentDateAndTime;
}
