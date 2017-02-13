//package ch.usi.inf.mc.awareapp;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.provider.Settings;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import ch.usi.inf.mc.awareapp.Database.RegistrationDatabase.RegDatabaseHandler;
//import ch.usi.inf.mc.awareapp.Database.RegistrationDatabase.RegistrationClass;
//import ch.usi.inf.mc.awareapp.Database.UserData;
//import ch.usi.inf.mc.awareapp.PhotoGrid.GridReceiver;
//import ch.usi.inf.mc.awareapp.PhotoGrid.PhotoGridActivity;
//
//public class LoginActivity extends AppCompatActivity {
//
//    Button next_btn;
//    Button signupBtn;
//    Spinner courseSpinner;
//    String androidID;
//    RegDatabaseHandler dbHandler;
//    List<RegistrationClass> allRegs;
//    Boolean signUpDone;
//    String course;
//    int hours;
//    int minutes;
//    int count;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        /* TEST */
////        Intent intent=new Intent(getApplicationContext(), PhotoGridActivity.class);
////        startActivity(intent);
//
//
//
//        androidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
//        dbHandler = RegDatabaseHandler.getInstance(getApplicationContext());
//
//        //Course spinner
//        final ArrayList<String> courses = new ArrayList<>();
//        courses.add("Mobile Computing");
//        courses.add("Software Quality");
//        courses.add("Data Analytics");
//        courses.add("Cyber Communication");
//
//        courseSpinner = (Spinner)findViewById(R.id.loginCourse_spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, courses);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        courseSpinner.setAdapter(adapter);
//
//
//        //Next button
//        next_btn = (Button)findViewById(R.id.sign_in_btn);
//        next_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                course = courseSpinner.getSelectedItem().toString();
//                count = 0;
//                for(RegistrationClass registration: dbHandler.getAllRegistrations()){
//                    count ++;
//                      System.out.println("LoginActivity, registration._course = "+registration._course);
//                      System.out.println("LoginActivity, courseSpinner.getSelectedItem().toString() = "+courseSpinner.getSelectedItem().toString());
//                    if(registration._course.equals(course)){ //if not do nothing, wait for to finish
//                        System.out.println("LoginActivity, database has user with selected course.");
//                       if(registration._termsCompleted){
//                           //finish();
//                           System.out.println("LoginActivity, user has already accepted terms and conditions");
//                           if(registration._usersPhone.equals("No")){
//                               System.out.println("LoginActivity, going to PhotoGridActivity: user is not using his own phone, but professor's; terms are completed");
//                               Intent i = new Intent(getApplicationContext(), PhotoGridActivity.class);
//                               i.putExtra("course", course);
//                               i.putExtra("onButton", false);
//                               startActivity(i);
//                               return;
//                           }else{
//                               //Creating AlarmManager
//                               System.out.println("LoginActivity: user is using his phone, terms are completed");
//
//                               AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                               Intent myIntent = new Intent(getApplicationContext(), GridReceiver.class);
//                               PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent, 0);
//
//                               Calendar calend = Calendar.getInstance();
//                               int day = calend.get(Calendar.DAY_OF_WEEK);
//                               //SUNDAY = 1 , MONDAY = 2 ... SATURDAY=7
//
//                               switch (registration._course){
//                                   case "Mobile Computing":
//                                       hours = 10;
//                                       minutes = 15;
//                                       break;
//                                   case "Cyber Communication":
//                                       hours = 10;
//                                       minutes = 59;
//                                       break;
//                                   case "Software Quality":
//                                       hours = 15;
//                                       minutes = 15;
//                                       break;
//                                   case "Data Analytics":
//                                       hours = 17;
//                                       minutes = 15;
//                                       break;
//                                   default:
//                                       hours = 23;
//                                       minutes=0;
//                                       break;
//                               }
//
//                               Calendar calendar = Calendar.getInstance();
//                               calendar.setTimeInMillis(System.currentTimeMillis());
//
//                               System.out.println("LoginActivity, hours = "+hours+", minutes = "+minutes+", course = "+
//                                       registration._course+", day in the week = "+day);
//
//                               if(registration._course.equals("Mobile Computing")  && (day == 2 || day == 4 )){ //monday and wednesday
//                                   calendar.set(Calendar.HOUR_OF_DAY, hours);
//                                   calendar.set(Calendar.MINUTE, minutes);
//                                   alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                               }else if(registration._course.equals("Cyber Communication")  && (day == 3 || day == 5 )){ //tuesday and thursday
//                                   calendar.set(Calendar.HOUR_OF_DAY, hours);
//                                   calendar.set(Calendar.MINUTE, minutes);
//                                   alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                               }else if(registration._course.equals("Software Quality")  && (day == 3 || day == 4 )){ //tuesday and wednesday
//                                   calendar.set(Calendar.HOUR_OF_DAY, hours);
//                                   calendar.set(Calendar.MINUTE, minutes);
//                                   alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                               }else if(registration._course.equals("Data Analytics")  && (day == 5 || day == 6 )){ //thursday and friday
//                                   calendar.set(Calendar.HOUR_OF_DAY, hours);
//                                   calendar.set(Calendar.MINUTE, minutes);
//                                   alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                               }
//                               System.out.println("LoginActivity, alarm is set, going to WelcomeActivity");
//                               Intent i =new Intent(getApplicationContext(), WelcomeActivity.class);
//                               i.putExtra("course", course);
//                               startActivity(i);
//                               return;
//                           }
//                       }else{
//                           //finish();
//                           System.out.println("LoginActivity, going to TermsActivity: terms and conditions are not accepted");
//
//                           Intent i =new Intent(getApplicationContext(), TermsActivity.class);
//                           i.putExtra("course", course);
//                           startActivity(i);
//                           return;
//                       }
//                    }
//                }
//
//
//                //finish();
//                System.out.println("LoginActivity, going to RegistrationActivity: for loop is done - database doesn't have user with selected course");
//
//                Intent i =new Intent(getApplicationContext(), RegistrationActivity.class);
//                i.putExtra("onButton", false);
//                i.putExtra("course", course);
//                startActivity(i);
////                Intent i = new Intent(getApplicationContext(), SurveysActivity.class);
////                startActivity(i);
//
//            }
//        });
//    }
//}
