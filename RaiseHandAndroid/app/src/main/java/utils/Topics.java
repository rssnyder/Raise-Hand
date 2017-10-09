package utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import app.MainActivity;
import app.TeacherNotifications;

/**
 * Created by sae1 on 10/6/17.
 */

public class Topics {




    //Needed to help track errors with android volley

    private String TAG= Topics.class.getSimpleName();

    // description of question
    private String description;

    // Title of question
    private String title;

    private String ID;

    private String time;
    //Array list of the questions in this topic
    private ArrayList<Question> questions;

    public Topics(String description, String title, String id, String time, ArrayList<Question> questions) {
        this.description=description;
        this.title = title;
        this.questions=questions;
        this.time=time;
        this.ID=id;
    }

    public Topics(){
        this.questions=new ArrayList<Question>();
        this.title=null;
        this.time=null;
        this.description=null;
        this.ID=null;
    }


    public String get_description(){
        return description;
    }

    public String get_title(){
        return title;
    }

    public String get_time(){ return time;}

    public String get_ID(){return ID;}

    public ArrayList<Question> get_questions(){
        return questions;
    }

    public void set_description(String des){
        this.description=des;
    }

    public void set_title( String title){
        this.title=title;
    }

    public void set_time(String time){this.time=time;}

    public void set_ID(String classID){ this.ID=classID; }

    public void set_questions(ArrayList<Question> q){
        this.questions=q;
    }
    //Given a class, this method will return the questions from the database that have to do with that class

    //Given a topic, it will push this question to the database
    public void add_topic_to_database(){
        //TODO: add this question to the database
    }
}
