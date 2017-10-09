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
    private String tag_string_req= "topics_req";
    private String TAG= Topics.class.getSimpleName();

    // description of question
    private String description;

    // Title of question
    private String title;

    private String time;
    //Array list of the questions in this topic
    private ArrayList<Question> questions;

    public Topics(String description, String title, String class_id,String time, ArrayList<Question> questions) {
        this.description=description;
        this.title = title;
        this.questions=questions;
        this.time=time;
    }

    public Topics(){
        this.questions=new ArrayList<Question>();
        this.title=null;
        this.time=null;
        this.description=null;
    }
    public String get_description(Topics t){
        return description;
    }

    public String get_title(Topics t){
        return title;
    }

    public String get_time(Topics t){ return time;}

    public ArrayList<Question> get_questions(Topics t){
        return questions;
    }

    public void set_description(Topics t, String des){
        this.description=des;
    }

    public void set_title(Topics t, String title){
        this.title=title;
    }

    public void set_time(Topics t, String time){this.time=time;}

    public void set_questions(Topics t, ArrayList<Question> q){
        this.questions=q;
    }
    //Given a class, this method will return the questions from the database that have to do with that class
    public ArrayList<Topics> get_topics(int classID) {
        ArrayList<Topics> t= new ArrayList<Topics>();

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
                        ArrayList<Topics> temp= new ArrayList<Topics>();
                        Scanner s= new Scanner(phpResponse);
                        String current;
                        //The string can contain multiple parts to indicate when we start reading new information
                        while(s.hasNext()) {
                            current=s.next();
                            if(current.equals("NEWTOPIC")) {
                                //NEWTOPIC indicates the start of a new topic, make a new topic object
                                Topics tempTopic=null;
                                ArrayList<Question> q= new ArrayList<Question>();
                                current=s.next();
                                while(!(current.equals("NEWTOPIC"))) {
                                    if(current.equals("CREATETIME")){
                                        current=s.next();
                                        String Time="";
                                        while(!(current.equals("TOPICNAME"))){
                                            //I'm not sure if we need to add a space here or not
                                            Time=Time+" "+current;
                                            current=s.next();
                                        }
                                        set_time(tempTopic,Time);
                                    }
                                    if(current.equals("TOPICNAME")){
                                        current=s.next();
                                        String Topic="";
                                        while(!(current.equals("DESCRIPTION"))){
                                            Topic=Topic+" "+current;
                                            current=s.next();
                                        }
                                        set_title(tempTopic,Topic);
                                    }
                                    if(current.equals("DESCRIPTION")){
                                        current=s.next();
                                        String Description="";
                                        while(!(current.equals("NEWQUESTION"))){
                                            Description=Description+" "+current;
                                            current=s.next();
                                        }
                                        set_description(tempTopic,Description);
                                    }
                                    if(current.equals("NEWQUESTION")) {
                                        //NEWQUESTION means the start of the new question within this topic, add to array list
                                        Question tempQuestion=null;
                                        ArrayList<Reply> replies= new ArrayList<Reply>();
                                        current=s.next();
                                        //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                                        while(!(current.equals("NEWTOPIC")) && !(current.equals("NEWQUESTION"))){
                                            //Add new question to the array list for the topic
                                            if(current.equals("QUESTIONTITLE")){
                                                //header for question

                                            }
                                            if(current.equals("QUESTIONDESCRIPTION")){
                                                //question

                                            }
                                            if(current.equals("QUESTIONUSER")){
                                                //username who created it

                                            }
                                            if(current.equals("QUESTIONUSERID")){
                                                //user id who created it
                                            }
                                            if(current.equals("POINTS")){
                                                //upvotes

                                            }
                                            if(current.equals("ENDORSED")){
                                                //if it is endorsed or not
                                                current=s.next();
                                                if(current.equals("Yes")){
                                                    //this question is endorsed
                                                }
                                            }
                                            if(current.equals("CREATION")){
                                                //timestamp
                                            }

                                            if(current.equals("NEWREPLY")) {
                                                //Get all of the replies
                                                Reply tempR=new Reply();
                                                current=s.next();
                                                while(!current.equals("NEWREPLY")){
                                                    //Build a new reply
                                                    if(current.equals("REPLYTXT")){
                                                        current=s.next();
                                                        String reply="";
                                                        while(!(current.equals("REPLYUSER"))){
                                                            reply=reply+current+" ";
                                                            current=s.next();
                                                        }
                                                        tempR.set_reply(reply);

                                                    }
                                                    if(current.equals("REPLYUSER")){
                                                        //username of author
                                                        current=s.next();
                                                        tempR.set_reply_username(current);
                                                        current=s.next();

                                                    }
                                                    if(current.equals("REPLYUSERID")){
                                                        //id of user
                                                        current=s.next();
                                                        tempR.set_reply_userID(current);
                                                        current=s.next();
                                                    }
                                                    if(current.equals("POINTS")){
                                                        current=s.next();
                                                        tempR.set_reply_rating(current);
                                                        current=s.next();
                                                    }
                                                    if(current.equals("ENDORSED")){
                                                        current=s.next();
                                                        if(current.equals("Yes")){
                                                            tempR.set_reply_endorsed(true);
                                                        }
                                                    }
                                                }
                                                replies.add(tempR);
                                            }
                                            q.add(tempQuestion);
                                        }
                                        tempQuestion.setReplies(replies);
                                    //NEWREPLY means the start of a new reply within this question, add to the question's array list
                                    }

                                }
                                //add the temp topic to the array list that will be returned in the end
                                set_questions(tempTopic, q);
                                temp.add(tempTopic);
                            }
                        }
                        //TODO:HOW CAN I DO THIS BELOW
                        //t=temp;
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
