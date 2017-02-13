package ch.usi.inf.mc.awareapp.Courses;

/**
 * Created by Danilo on 2/11/17.
 */

public class Course {

    public Weekday Day1;
    public Weekday Day2;
    public Weekday Day3;
    public String Name;


    public Course(Weekday d1, Weekday d2, String name){
        setDay1(d1);
        setDay2(d2);
        setName(name);
    }
    public Course(Weekday d1, Weekday d2, Weekday d3, String name){
        setDay1(d1);
        setDay2(d2);
        setDay3(d3);
        setName(name);
    }

    public void setDay1(Weekday d1){
        this.Day1 = d1;
    }
    public void setDay2(Weekday d2){
        this.Day2 = d2;
    }
    public void setDay3(Weekday d3){
        this.Day3 = d3;
    }
    public void setName(String name){
        this.Name = name;
    }

    public Weekday getDay1(){
        return this.Day1;
    }
    public Weekday getDay2(){
        return this.Day2;
    }
    public Weekday getDay3(){
        return this.Day3;
    }
    public String getName(){
        return this.Name;
    }

}
