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

import app.MainActivity;
import app.TeacherNotifications;

/**
 * Created by sae1 on 10/6/17.
 */

public class Topics {
    //Needed to help track errors with android volley
    private String tag_string_req= "topics_req";
    private String TAG= Topics.class.getSimpleName();

    // description of question
    private String description;

    // Title of question
    private String title;

    //Array list of the questions in this topic
    private ArrayList<Question> questions;

    public Topics(String description, String title, String class_id, ArrayList<Question> questions) {
        this.description=description;
        this.title = title;
        this.questions=questions;
    }

    public String get_description(Topics t){
        return description;
    }

    public String get_title(Topics t){
        return title;
    }

    public ArrayList<Question> get_questions(Topics t){
        return questions;
    }

    public void set_description(Topics t, String des){
        this.description=des;
    }

    public void set_title(Topics t, String title){
        this.title=title;
    }

    public void get_questions(Topics t, ArrayList<Question> q){
        this.questions=q;
    }
    //Given a class, this method will return the questions from the database that have to do with that class
    public ArrayList<Topics> get_topics(int classID) {
        ArrayList<Topics> t= new ArrayList<Topics>();
        ArrayList<Question> q= new ArrayList<Question>();
        ArrayList<Reply> replies= new ArrayList<Reply>();
        int existsQuestions;
        int existsTopics;
        String urlSuffix= "?classId="+classID;
        String url_final= URLS.URL_TOPICS+urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        //The string can contain multiple parts to indicate when we start reading new information

                        //NEWTOPIC indicates the start of a new topic, make a new topic object

                            //NEWQUESTION means the start of the new question within this topic, add to array list

                                //NEWREPLY means the start of a new reply within this question, add to the question's array list
                    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }
        );
        // Adding request to request queue
        MainActivity.getInstance().addToRequestQueue(req, tag_string_req);
        return t;
    }
    //Given a topic, it will push this question to the database
    public void add_topic_to_database(Topics t){
        //TODO: add this question to the database
    }
}
