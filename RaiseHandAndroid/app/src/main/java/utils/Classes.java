package utils;

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
    private ArrayList<Topics> topics;
    //need to add in the title thing
    private String title;
    private String classID;
    private String tag_string_req="class_req";
    public static final String TAG= Classes.class.getSimpleName();

    public Classes(String title, String classID){
        this.title=title;
        this.classID=classID;
        this.topics = new ArrayList<Topics>();
    }

    public static void main(String[] args){
        Classes c=new Classes("Math 167", "7");
        c.setTopics(c.get_topics());
        System.out.println(c.getTopics().get(0));
    }
    public ArrayList<Topics> get_topics() {
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
                        String[] seperated=phpResponse.split(" ");
                        int i=0;
                        //The string can contain multiple parts to indicate when we start reading new information
                        while(i<seperated.length) {
                            if(seperated[i].equals("NEWTOPIC") && i<seperated.length) {
                                //NEWTOPIC indicates the start of a new topic, make a new topic object
                                Topics tempTopic= new Topics();
                                ArrayList<Question> q= new ArrayList<Question>();
                                tempTopic.set_questions(q);
                                i++;
                                while(!(seperated[i].equals("NEWTOPIC")) && i<seperated.length) {
                                    if(seperated[i].equals("CREATETIME") && i<seperated.length){
                                        i++;
                                        String Time="";
                                        while(!(seperated[i].equals("TOPICNAME")) && i<seperated.length){
                                            //I'm not sure if we need to add a space here or not
                                            Time=Time+" "+seperated[i];
                                            i++;
                                        }
                                        tempTopic.set_time(Time);
                                    }
                                    if(seperated[i].equals("TOPICNAME") && i<seperated.length){
                                        i++;
                                        String Topic="";
                                        while(!(seperated[i].equals("ID")) && i<seperated.length){
                                            Topic=Topic+" "+seperated[i];
                                            i++;
                                        }
                                        tempTopic.set_title(Topic);
                                    }
                                    if(seperated[i].equals("ID") && i<seperated.length){
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
                                    if(seperated[i].equals("DESCRIPTION") && i<seperated.length){
                                        i++;
                                        String Description="";
                                        while(!(seperated[i].equals("NEWQUESTION")) && i<seperated.length){
                                            Description=Description+" "+seperated[i];
                                            i++;
                                        }
                                        //TODO: is this really correct??
                                        if(!(i<seperated.length)){
                                            Description = Description + " " + seperated[i];
                                        }
                                        tempTopic.set_description(Description);
                                    }
                                    topics.add(tempTopic);
                                    if(seperated[i].equals("NEWQUESTION") && i<seperated.length) {
                                        //NEWQUESTION means the start of the new question within this topic, add to array list
                                        Question tempQuestion= new Question();
                                        ArrayList<Reply> replies= new ArrayList<Reply>();
                                        tempQuestion.setReplies(replies);
                                        i++;
                                        //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                                        while(!(seperated[i].equals("NEWTOPIC")) && !(seperated[i].equals("NEWQUESTION")) && i<seperated.length){
                                            //Add new question to the array list for the topic
                                            if(seperated[i].equals("QUESTIONTITLE") && i<seperated.length){
                                                //header for question
                                                i++;
                                                String title="";
                                                while(!(seperated[i].equals("QUESTIONDESCRIPTION")) && i<seperated.length){
                                                    title=title+seperated[i]+ " ";
                                                    i++;
                                                }
                                                tempQuestion.setQuestionTitle(title);

                                            }
                                            if(seperated[i].equals("QUESTIONDESCRIPTION") && i<seperated.length){
                                                //question
                                                i++;
                                                String desc="";
                                                while(!(seperated[i].equals("QUESTIONUSER")) && i<seperated.length){
                                                    desc=desc+seperated[i]+ " ";
                                                    i++;
                                                }
                                                tempQuestion.setQuestionDescription(desc);

                                            }
                                            if(seperated[i].equals("QUESTIONUSER") && i<seperated.length){
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
                                            if(seperated[i].equals("QUESTIONUSERID") && i<seperated.length){
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
                                            if(seperated[i].equals("POINTS") && i<seperated.length){
                                                //upvotes
                                                i++;
                                                //TODO: Should I be echoing 0 here instead??
                                                if(seperated[i].equals("ENDORSED")){
                                                    //do nothing
                                                }
                                                else {
                                                    tempQuestion.setStudentRating(seperated[i]);
                                                    i++;
                                                }
                                            }
                                            if(seperated[i].equals("ENDORSED") && i<seperated.length){
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
                                            if(seperated[i].equals("CREATION") && i<seperated.length){
                                                //timestamp
                                                i++;
                                                String time="";
                                                while(!(seperated[i].equals("NEWREPLY"))&& !(seperated[i].equals("NEWQUESTION")) && !(seperated[i].equals("NEWTOPIC")) && i<seperated.length){
                                                    time=time+seperated[i]+ " ";
                                                    i++;
                                                }
                                                tempQuestion.setCreationTime(time);

                                            }
                                            if(seperated[i].equals("QUESITONID") && i<seperated.length){
                                                //ID for the question
                                                String questionid="";
                                                i++;
                                                tempQuestion.setQuestionID(seperated[i]);
                                                i++;
                                            }
                                            q.add(tempQuestion);
                                            if(seperated[i].equals("NEWREPLY") && i<seperated.length) {
                                                //Get all of the replies
                                                Reply tempR=new Reply();
                                                i++;
                                                while(!seperated[i].equals("NEWREPLY") && i<seperated.length){
                                                    //Build a new reply
                                                    if(seperated[i].equals("REPLYTXT") && i<seperated.length){
                                                        i++;
                                                        String reply="";
                                                        while(!(seperated[i].equals("REPLYUSER")) && i<seperated.length){
                                                            reply=reply+seperated[i]+" ";
                                                            i++;
                                                        }
                                                        tempR.set_reply(reply);

                                                    }
                                                    if(seperated[i].equals("REPLYUSER") && i<seperated.length){
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
                                                    if(seperated[i].equals("REPLYUSERID") && i<seperated.length){
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
                                                    if(seperated[i].equals("POINTS") && i<seperated.length){
                                                        i++;
                                                        if(seperated[i].equals("ENDORSED")){
                                                            //do nothing
                                                        }
                                                        else {
                                                            tempR.set_reply_rating(seperated[i]);
                                                            i++;
                                                        }
                                                    }
                                                    if(seperated[i].equals("ENDORSED") && i<seperated.length){
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
                                                    if(seperated[i].equals("CREATION") && i<seperated.length){
                                                        //timestamp
                                                        i++;
                                                        String time="";
                                                        while(!(seperated[i].equals("NEWREPLY"))&& !(seperated[i].equals("NEWQUESTION")) && !(seperated[i].equals("NEWTOPIC")) && i<seperated.length){
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
                    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }
        );
        // Adding request to request queue
        MainActivity.getInstance().addToRequestQueue(req, tag_string_req);
        return topics;
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
}
