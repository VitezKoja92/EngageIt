package ch.usi.inf.mc.awareapp.Courses;

/**
 * Created by Danilo on 2/11/17.
 */

public class Weekday {

    public int Hour;
    public int Minute;
    public int Day;

    public Weekday(int h, int m, int d){
        setHour(h);
        setMinute(m);
        setDay(d);
    }


    public void setHour(int hour){
        this.Hour = hour;
    }
    public void setMinute(int minute){
        this.Minute = minute;
    }
    public void setDay(int day){
        this.Day = day;
    }

    public int getHour(){
        return this.Hour;
    }
    public int getMinute(){
        return this.Minute;
    }
    public int getDay(){
        return this.Day;
    }

}
