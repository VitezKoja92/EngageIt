package ch.usi.inf.mc.awareapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import ch.usi.inf.mc.awareapp.Database.PAMClass;
import ch.usi.inf.mc.awareapp.Database.RegistrationClass;

/**
 * Created by Danilo on 2/2/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database information
    private static final String TAG = "DbHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EngageItDatabase";

    public static final String TABLE_REGISTRATION = "RegistrationTable";
    public static final String TABLE_PAM = "PAMTable";
    public static final String TABLE_ESM = "ESMTable";


    //Registration Table columns names
    private static final String USERNAME = "Username";
    private static final String ANDROID_ID = "AndroidID";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";
    private static final String FACULTY = "Faculty";
    private static final String LEVEL_OF_STUDIES = "LevelOfStudies";
    private static final String COURSES = "Courses";
    private static final String REGISTRATION_DONE = "RegistrationDone";
    private static final String TERMS_COMPLETED = "TermsCompleted";
    private static final String CURRENT_DATE_AND_TIME = "CurrentDateAndTime";

    public static String[] getColumnsRegistration(){
        String[] columns = {USERNAME, ANDROID_ID, AGE, GENDER, FACULTY, LEVEL_OF_STUDIES, COURSES, REGISTRATION_DONE, TERMS_COMPLETED, CURRENT_DATE_AND_TIME};
        return   columns;
    }
    public static String[] getColumnsESM(){
        String[] columns = {_ID, USERNAME, ANDROID_ID, ESM_JSON};
        return columns;
    }


    //PAM Table columns names
    private static final String _ID = "_id";
    //private static final String USERNAME = "Username";
    //private static final String ANDROID_ID = "AndroidID";
    private static final String ATTENDANCE = "Attendance";
    private static final String ANSWER = "PAMAnswer";
    //private static final String CURRENT_DATE_AND_TIME = "CurrentDateAndTime";


    //ESM Table column names
    //private static final String USERNAME = "Username";
    private static final String ESM_JSON = "ESM_json";
    //private static final String ANDROID_ID = "AndroidID";

//    private static final String ESM_JSON = "ESM_json";
//    private static final String ESM_STATUS = "ESM_status";
//    private static final String ESM_EXPIRATION_THRESHOLD = "ESM_expiration_threshold";
//    private static final String ESM_NOTIFICATION_TIMEOUT = "ESM_notification_timeout";
//    private static final String DOUBLE_ESM_USER_ANSWER_TIMESTAMP = "Double_ESM_user_answer_timestamp";
//    private static final String ESM_USER_ANSWER = "ESM_user_answer";
//    private static final String ESM_TRIGGER = "ESM_trigger";


    @Override
    public void onCreate(SQLiteDatabase db) {
        //String for creation of the registration table
        String CREATE_REGISTRATION_TABLE = "CREATE TABLE "+ TABLE_REGISTRATION +
                "(" +
                USERNAME + " TEXT PRIMARY KEY, " +
                ANDROID_ID + " TEXT," +
                AGE + " TEXT," +
                GENDER + " TEXT," +
                FACULTY + " TEXT," +
                LEVEL_OF_STUDIES + " TEXT," +
                COURSES + " TEXT," +
                REGISTRATION_DONE + " BOOLEAN," +
                TERMS_COMPLETED + " BOOLEAN," +
                CURRENT_DATE_AND_TIME +" TEXT"+
                ")";

        //String for creation of the PAM table
        String CREATE_PAM_TABLE = "CREATE TABLE "+ TABLE_PAM +
                "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " TEXT," +
                ANDROID_ID + " TEXT," +
                ATTENDANCE + " BOOLEAN," +
                ANSWER + " TEXT," +
                CURRENT_DATE_AND_TIME + " TEXT"+
                ")";

        //String for creation of the ESM table
        String CREATE_ESM_TABLE = "CREATE TABLE "+TABLE_ESM +
                "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " TEXT," +
//                TIMESTAMP + " REAL," +
                ANDROID_ID + " TEXT," +
                ESM_JSON + " TEXT" +
//                ESM_STATUS + " INTEGER," +
//                ESM_EXPIRATION_THRESHOLD + " INTEGER," +
//                ESM_NOTIFICATION_TIMEOUT + " INTEGER," +
//                DOUBLE_ESM_USER_ANSWER_TIMESTAMP + " REAL," +
//                ESM_USER_ANSWER + " TEXT," +
//                ESM_TRIGGER + " TEXT" +
                ")";


        db.execSQL(CREATE_REGISTRATION_TABLE);
        db.execSQL(CREATE_PAM_TABLE);
        db.execSQL(CREATE_ESM_TABLE);

        //creating utility table
        db.execSQL(UploaderUtilityTable.getCreateQuery());
        insertRecords(db, UploaderUtilityTable.TABLE_UPLOADER_UTILITY, UploaderUtilityTable.getRecords());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop all old tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_REGISTRATION);
        onCreate(db);
    }
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        //drop all old tables and recreate them
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PAM);
//        onCreate(db);
//    }
    //Constructor
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static DatabaseHandler DbHandler;

    //getInstance method used where we need instance of RegDatabaseHandler to insert, update or delete data
    public static synchronized DatabaseHandler getInstance(Context context){
        if(DbHandler == null){
            DbHandler = new DatabaseHandler(context.getApplicationContext());
        }
        return DbHandler;
    }


    //Method for inserting registration details in the database
    public void addRegistration(RegistrationClass registration) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(USERNAME, registration._username);
            values.put(ANDROID_ID, registration._android_id);
            values.put(AGE, registration._age);
            values.put(GENDER, registration._gender);
            values.put(FACULTY, registration._faculty);
            values.put(LEVEL_OF_STUDIES, registration._levelOfStudies);
            values.put(COURSES, registration._courses);
            values.put(REGISTRATION_DONE, registration._registrationDone);
            values.put(TERMS_COMPLETED, registration._termsCompleted);
            values.put(CURRENT_DATE_AND_TIME, registration._currentDateAndTime);

            db.insertOrThrow(TABLE_REGISTRATION, null, values);
            db.setTransactionSuccessful();
            System.out.println("good "+values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add registration to database");
        } finally {
            db.endTransaction();
        }
    }

    //Method for inserting PAM details in the database
    public void addPAM(PAMClass pam) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(_ID, pam._id);
            values.put(USERNAME, pam._username);
            values.put(ANDROID_ID, pam._android_id);
            values.put(ATTENDANCE, pam._attendance);
            values.put(ANSWER, pam._pam_answer);
            values.put(CURRENT_DATE_AND_TIME, pam._currentDateAndTime);

            db.insertOrThrow(TABLE_PAM, null, values);
            db.setTransactionSuccessful();
            System.out.println("good "+values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add PAM to database");
        } finally {
            db.endTransaction();
        }
    }
    //Method for inserting ESM details in the database
    public void addESM(ESMClass esm) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

//            values.put(_ID, esm._id);
            values.put(USERNAME, esm._username);
//            values.put(TIMESTAMP, esm._timestamp);
            values.put(ANDROID_ID, esm._android_id);
            values.put(ESM_JSON, esm._esm_json);
//            values.put(ESM_STATUS, esm._esm_status);
//            values.put(ESM_EXPIRATION_THRESHOLD, esm._esm_expiration_threshold);
//            values.put(ESM_NOTIFICATION_TIMEOUT, esm._esm_notification_timeout);
//            values.put(DOUBLE_ESM_USER_ANSWER_TIMESTAMP, esm._double_esm_user_answer_timestamp);
//            values.put(ESM_USER_ANSWER, esm._esm_user_answer);
//            values.put(ESM_TRIGGER, esm._esm_trigger);

            db.insertOrThrow(TABLE_ESM, null, values);
            db.setTransactionSuccessful();
            System.out.println("good "+values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while trying to add ESM to database");
        } finally {
            db.endTransaction();
        }
    }


    //Method for getting all data from registration table
    public List<RegistrationClass> getAllRegistrations(){
        List<RegistrationClass> registrationList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_REGISTRATION;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    RegistrationClass registration = new RegistrationClass();

                    registration._android_id= cursor.getString(cursor.getColumnIndex(ANDROID_ID));
                    registration._age= cursor.getString(cursor.getColumnIndex(AGE));
                    registration._gender= cursor.getString(cursor.getColumnIndex(GENDER));
                    registration._faculty= cursor.getString(cursor.getColumnIndex(FACULTY));
                    registration._levelOfStudies= cursor.getString(cursor.getColumnIndex(LEVEL_OF_STUDIES));
                    registration._username= cursor.getString(cursor.getColumnIndex(USERNAME));
                    registration._courses = cursor.getString(cursor.getColumnIndex(COURSES));
                    registration._registrationDone= Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(REGISTRATION_DONE)));
                    registration._termsCompleted= Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(TERMS_COMPLETED)));
                    registration._currentDateAndTime = cursor.getString(cursor.getColumnIndex(CURRENT_DATE_AND_TIME));

                    registrationList.add(registration);
                }while(cursor.moveToNext());

            }
        }catch (Exception e){
            Log.d(TAG, "Error while trying to get posts from database");
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return registrationList;
    }

    //Method for getting all data from registration table
    public List<PAMClass> getAllPAM(){
        List<PAMClass> PAMList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_PAM;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    PAMClass pam = new PAMClass();
                    pam._id = cursor.getInt(cursor.getColumnIndex(_ID));
                    pam._username= cursor.getString(cursor.getColumnIndex(USERNAME));
                    pam._android_id= cursor.getString(cursor.getColumnIndex(ANDROID_ID));
                    pam._attendance= Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(ATTENDANCE)));
                    pam._pam_answer= cursor.getString(cursor.getColumnIndex(ANSWER));
                    pam._currentDateAndTime = cursor.getString(cursor.getColumnIndex(CURRENT_DATE_AND_TIME));

                    PAMList.add(pam);
                }while(cursor.moveToNext());

            }
        }catch (Exception e){
            Log.d(TAG, "Error while trying to get posts from database");
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return PAMList;
    }

    //Method for getting all data from ESM table
    public List<ESMClass> getAllESMs(){
        List<ESMClass> esmsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+TABLE_ESM;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        try{
            if(cursor.moveToFirst()){
                do{
                    ESMClass esm = new ESMClass();

                    esm._username = cursor.getString(cursor.getColumnIndex(USERNAME));
                    esm._android_id= cursor.getString(cursor.getColumnIndex(ANDROID_ID));
                    esm._esm_json = cursor.getString(cursor.getColumnIndex(ESM_JSON));

                    esmsList.add(esm);
                }while(cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG, "Error while trying to get posts from database");
        }finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return esmsList;
    }


    //Method for updating registration
    public void updateRegistration(String age, String gender, String faculty, String level, String courses, Boolean regDone, Boolean terms, String username, String currentTime){

        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_REGISTRATION +
                    " SET Username='"+username+"', Age='"+ age +"', Gender='"+gender+"', Faculty='"+faculty+"', LevelOfStudies='"+level+"', Courses='"+courses+
                    "', RegistrationDone='"+regDone+"', TermsCompleted='"+terms+"', CurrentDateAndTime='"+currentTime+
                    "' WHERE Username='"+username+"';");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d(TAG, "Error while trying to update registration detail");
        }finally {
            db.endTransaction();
        }
    }

    //Method for updating PAM
    public void updatePAM(int id, String username, Boolean attendance, String answer, String timestamp){

        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_PAM +
                    " SET Username='"+ username +"', Attendance='"+attendance+"', PAMAnswer='"+answer+"', CurrentDateAndTime='"+timestamp+
                    "' WHERE _id ='"+id+"';");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d(TAG, "Error while trying to update PAM detail");
        }finally {
            db.endTransaction();
        }

    }

    //Method for updating ESM
    public void updateESM(int id, String username, String android_id, String esm_json){

        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("UPDATE " + TABLE_ESM +
                    " SET Username='"+username+"', AndroidID='"+android_id+"', ESM_json='"+esm_json+
                    "' WHERE _id ='"+id+"';");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d(TAG, "Error while trying to update PAM detail");
        }finally {
            db.endTransaction();
        }

    }

    public void deleteESM(){
        SQLiteDatabase db = getWritableDatabase();

        try{
            db.beginTransaction();
            db.execSQL("DELETE FROM "+TABLE_ESM+";");
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d(TAG, "Error while trying to delete record from ESMTable");
        }finally {
            db.endTransaction();
        }
    }

    private void insertRecords(SQLiteDatabase db, String tableName, List<ContentValues> records) {
        for(ContentValues record: records) {
            db.insert(tableName, null, record);
        }
    }


}
