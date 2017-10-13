package utils;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Scanner;

import app.MainActivity;

/**
 * Created by sae1 on 10/9/17.
 */

public class Classes {
    private String TAG= Classes.class.getSimpleName();
    private String tag_string_req= "string_req";
    //need to add in the title thing
    private String title;
    private String classID;
    private ArrayList<Topics> topics;

    public Classes(String title, String classID){
        this.title=title;
        this.classID=classID;
        this.topics = new ArrayList<Topics>();
    }
    
    public ArrayList<Topics> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topics> topics) {
        this.topics = topics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public ArrayList<Topics> get_topics() {
        topics=new ArrayList<Topics>();
        String urlSuffix= "?classId="+classID;
        String url_final= URLS.URL_TOPICS+urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        String[] seperated=phpResponse.split(" ");
                        int max=seperated.length;
                        int i=0;
                        //The string can contain multiple parts to indicate when we start reading new information
                        while(i<max) {
                            if(seperated[i].equals("NEWTOPIC") && i<max) {
                                //NEWTOPIC indicates the start of a new topic, make a new topic object
                                Topics tempTopic= new Topics();
                                ArrayList<Question> q= new ArrayList<Question>();
                                tempTopic.set_questions(q);
                                i++;
                                while(!(seperated[i].equals("NEWTOPIC")) && i<max) {
                                    if(seperated[i].equals("CREATETIME") && i<max){
                                        i++;
                                        String Time="";
                                        while(!(seperated[i].equals("TOPICNAME")) && i<max){
                                            //I'm not sure if we need to add a space here or not
                                            Time=Time+" "+seperated[i];
                                            i++;
                                        }
                                        tempTopic.set_time(Time);
                                    }
                                    if(seperated[i].equals("TOPICNAME") && i<max){
                                        i++;
                                        String Topic="";
                                        while(!(seperated[i].equals("ID")) && i<max){
                                            Topic=Topic+" "+seperated[i];
                                            i++;
                                        }
                                        tempTopic.set_title(Topic);
                                    }
                                    if(seperated[i].equals("ID") && i<max){
                                        //id of the topics
                                        i++;
                                        if(seperated[i].equals("DESCRIPTION")){
                                            //do nothing
                                        }
                                        else {
                                            tempTopic.set_ID(seperated[i]);
                                            i++;
                                        }
                                    }
                                    if(seperated[i].equals("DESCRIPTION") && i<max){
                                        i++;
                                        String Description="";
                                        while(!(seperated[i].equals("NEWQUESTION")) && !(seperated[i].equals("NEWTOPIC")) && i<max){
                                            Description=Description+" "+seperated[i];
                                            i++;
                                        }
                                        tempTopic.set_description(Description);
                                    }
                                    topics.add(tempTopic);
                                    if(seperated[i].equals("NEWQUESTION") && i<max) {
                                        //NEWQUESTION means the start of the new question within this topic, add to array list
                                        Question tempQuestion= new Question();
                                        ArrayList<Reply> replies= new ArrayList<Reply>();
                                        tempQuestion.setReplies(replies);
                                        i++;
                                        //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                                        while(!(seperated[i].equals("NEWTOPIC")) && !(seperated[i].equals("NEWQUESTION")) && i<max){
                                            //Add new question to the array list for the topic
                                            if(seperated[i].equals("QUESTIONTITLE") && i<max){
                                                //header for question
                                                i++;
                                                String title="";
                                                while(!(seperated[i].equals("QUESTIONDESCRIPTION")) && i<max){
                                                    title=title+seperated[i]+ " ";
                                                    i++;
                                                }
                                                tempQuestion.setQuestionTitle(title);

                                            }
                                            if(seperated[i].equals("QUESTIONDESCRIPTION") && i<max){
                                                //question
                                                i++;
                                                String desc="";
                                                while(!(seperated[i].equals("QUESTIONUSER")) && i<max){
                                                    desc=desc+seperated[i]+ " ";
                                                    i++;
                                                }
                                                tempQuestion.setQuestionDescription(desc);

                                            }
                                            if(seperated[i].equals("QUESTIONUSER") && i<max){
                                                //username who created it
                                                i++;
                                                if(seperated[i].equals("QUESTIONUSERID")) {
                                                    //do nothing
                                                }
                                                else{
                                                    tempQuestion.setQuestionUsername(seperated[i]);
                                                    i++;
                                                }
                                            }
                                            if(seperated[i].equals("QUESTIONUSERID") && i<max){
                                                //user id who created it
                                                i++;
                                                if(seperated[i].equals("POINTS")){
                                                    //do nothing
                                                }
                                                else {
                                                    tempQuestion.setOwnerID(seperated[i]);
                                                    i++;
                                                }
                                            }
                                            if(seperated[i].equals("POINTS") && i<max){
                                                //upvotes
                                                i++;
                                                if(seperated[i].equals("ENDORSED")){
                                                    //do nothing
                                                }
                                                else {
                                                    tempQuestion.setStudentRating(seperated[i]);
                                                    i++;
                                                }
                                            }
                                            if(seperated[i].equals("ENDORSED") && i<max){
                                                //if it is endorsed or not
                                                i++;
                                                if(seperated[i].equals("Yes")){
                                                    //this question is endorsed
                                                    tempQuestion.setEndorsed(true);
                                                    i++;
                                                }
                                                else if(seperated[i].equals("No")){
                                                    i++;
                                                }
                                                else{
                                                    //do nothing
                                                }
                                            }
                                            if(seperated[i].equals("CREATION") && i<max){
                                                //timestamp
                                                i++;
                                                String time="";
                                                while(!(seperated[i].equals("NEWREPLY"))&& !(seperated[i].equals("NEWQUESTION")) && !(seperated[i].equals("NEWTOPIC")) && i<max){
                                                    time=time+seperated[i]+ " ";
                                                    i++;
                                                }
                                                tempQuestion.setCreationTime(time);

                                            }
                                            if(seperated[i].equals("QUESITONID") && i<max){
                                                //ID for the question
                                                String questionid="";
                                                i++;
                                                tempQuestion.setQuestionID(seperated[i]);
                                                i++;
                                            }
                                            q.add(tempQuestion);
                                            if(seperated[i].equals("NEWREPLY") && i<max) {
                                                //Get all of the replies
                                                Reply tempR=new Reply();
                                                i++;
                                                while(!(seperated[i].equals("NEWREPLY")) && !(seperated[i].equals("NEWTOPIC")) && !(seperated[i].equals("NEWQUESTION")) && i<max){
                                                    //Build a new reply
                                                    if(seperated[i].equals("REPLYTXT") && i<max){
                                                        i++;
                                                        String reply="";
                                                        while(!(seperated[i].equals("REPLYUSER")) && i<max){
                                                            reply=reply+seperated[i]+" ";
                                                            i++;
                                                        }
                                                        tempR.set_reply(reply);

                                                    }
                                                    if(seperated[i].equals("REPLYUSER") && i<max){
                                                        //username of author
                                                        i++;
                                                        if(seperated[i].equals("REPLYUSERID")){
                                                            //do nothing
                                                        }
                                                        else {
                                                            tempR.set_reply_username(seperated[i]);
                                                            i++;
                                                        }

                                                    }
                                                    if(seperated[i].equals("REPLYUSERID") && i<max){
                                                        //id of user
                                                        i++;
                                                        if(seperated[i].equals("POINTS")) {
                                                            //do nothing
                                                        }
                                                        else{
                                                            tempR.set_reply_userID(seperated[i]);
                                                            i++;
                                                        }
                                                    }
                                                    if(seperated[i].equals("POINTS") && i<max){
                                                        i++;
                                                        if(seperated[i].equals("ENDORSED")){
                                                            //do nothing
                                                        }
                                                        else {
                                                            tempR.set_reply_rating(seperated[i]);
                                                            i++;
                                                        }
                                                    }
                                                    if(seperated[i].equals("ENDORSED") && i<max){
                                                        i++;
                                                        if(seperated[i].equals("Yes")){
                                                            tempR.set_reply_endorsed(true);
                                                            i++;
                                                        }
                                                        else if(seperated[i].equals("No")){
                                                            i++;
                                                        }
                                                        else{
                                                            //do nothing
                                                        }
                                                    }
                                                    if(seperated[i].equals("CREATION") && i<max){
                                                        //timestamp
                                                        i++;
                                                        String time="";
                                                        while(!(seperated[i].equals("NEWREPLY"))&& !(seperated[i].equals("NEWQUESTION")) && !(seperated[i].equals("NEWTOPIC")) && i<max){
                                                            time=time+seperated[i]+ " ";
                                                            i++;
                                                        }
                                                        tempR.set_reply_time(time);
                                                    }
                                                }
                                                //NEWREPLY means the start of a new reply within this question, add to the question's array list
                                                replies.add(tempR);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }
        );
        // Adding request to request queue
        MainActivity.getInstance().addToRequestQueue(req, tag_string_req);
        setTopics(topics);
        return topics;
    }
}
