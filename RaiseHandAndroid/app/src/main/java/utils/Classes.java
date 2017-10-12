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
                        Scanner s= new Scanner(phpResponse);
                        String current;
                        //The string can contain multiple parts to indicate when we start reading new information
                        while(s.hasNext()) {
                            current=s.next();
                            if(current.equals("NEWTOPIC") && s.hasNext()) {
                                //NEWTOPIC indicates the start of a new topic, make a new topic object
                                Topics tempTopic= new Topics();
                                ArrayList<Question> q= new ArrayList<Question>();
                                current=s.next();
                                while(!(current.equals("NEWTOPIC")) && s.hasNext()) {
                                    if(current.equals("CREATETIME") && s.hasNext()){
                                        current=s.next();
                                        String Time="";
                                        while(!(current.equals("TOPICNAME")) && s.hasNext()){
                                            //I'm not sure if we need to add a space here or not
                                            Time=Time+" "+current;
                                            current=s.next();
                                        }
                                        tempTopic.set_time(Time);
                                    }
                                    if(current.equals("TOPICNAME") && s.hasNext()){
                                        current=s.next();
                                        String Topic="";
                                        while(!(current.equals("ID")) && s.hasNext()){
                                            Topic=Topic+" "+current;
                                            current=s.next();
                                        }
                                        tempTopic.set_title(Topic);
                                    }
                                    if(current.equals("ID") && s.hasNext()){
                                        //id of the topics
                                        current=s.next();
                                        if(current.equals("DESCRIPTION")){
                                            //do nothing
                                        }
                                        else {
                                            tempTopic.set_ID(current);
                                            current = s.next();
                                        }
                                    }
                                    if(current.equals("DESCRIPTION") && s.hasNext()){
                                        current=s.next();
                                        String Description="";
                                        while(!(current.equals("NEWQUESTION")) && s.hasNext()){
                                            Description=Description+" "+current;
                                            current=s.next();
                                        }
                                        if(!s.hasNext()){
                                            Description = Description + " " + current;
                                        }
                                        tempTopic.set_description(Description);
                                    }
                                    if(current.equals("NEWQUESTION") && s.hasNext()) {
                                        //NEWQUESTION means the start of the new question within this topic, add to array list
                                        Question tempQuestion= new Question();
                                        ArrayList<Reply> replies= new ArrayList<Reply>();
                                        current=s.next();
                                        //cannot be a new topic or new question starting (maybe need to add in new reply too)?
                                        while(!(current.equals("NEWTOPIC")) && !(current.equals("NEWQUESTION")) && s.hasNext()){
                                            //Add new question to the array list for the topic
                                            if(current.equals("QUESTIONTITLE") && s.hasNext()){
                                                //header for question
                                                current=s.next();
                                                String title="";
                                                while(!(current.equals("QUESTIONDESCRIPTION")) && s.hasNext()){
                                                    title=title+current+ " ";
                                                    current=s.next();
                                                }
                                                tempQuestion.setQuestionTitle(title);

                                            }
                                            if(current.equals("QUESTIONDESCRIPTION") && s.hasNext()){
                                                //question
                                                current=s.next();
                                                String desc="";
                                                while(!(current.equals("QUESTIONUSER")) && s.hasNext()){
                                                    desc=desc+current+ " ";
                                                    current=s.next();
                                                }
                                                tempQuestion.setQuestionDescription(desc);

                                            }
                                            if(current.equals("QUESTIONUSER") && s.hasNext()){
                                                //username who created it
                                                current=s.next();
                                                if(current.equals("QUESTIONUSERID")) {
                                                    //do nothing
                                                }
                                                else{
                                                    tempQuestion.setQuestionUsername(current);
                                                    s.next();
                                                }
                                            }
                                            if(current.equals("QUESTIONUSERID") && s.hasNext()){
                                                //user id who created it
                                                current=s.next();
                                                if(current.equals("POINTS")){
                                                    //do nothing
                                                }
                                                else {
                                                    tempQuestion.setOwnerID(current);
                                                    current = s.next();
                                                }
                                            }
                                            if(current.equals("POINTS") && s.hasNext()){
                                                //upvotes
                                                current=s.next();
                                                if(current.equals("ENDORSED")){
                                                    //do nothing
                                                }
                                                else {
                                                    tempQuestion.setStudentRating(current);
                                                    current = s.next();
                                                }
                                            }
                                            if(current.equals("ENDORSED") && s.hasNext()){
                                                //if it is endorsed or not
                                                current=s.next();
                                                if(current.equals("Yes")){
                                                    //this question is endorsed
                                                    tempQuestion.setEndorsed(true);
                                                    current=s.next();
                                                }
                                                else if(current.equals("No")){
                                                    current=s.next();
                                                }
                                                else{
                                                    //do nothing
                                                }
                                            }
                                            if(current.equals("CREATION") && s.hasNext()){
                                                //timestamp
                                                current=s.next();
                                                String time="";
                                                while(!(current.equals("NEWREPLY"))&& !(current.equals("NEWQUESTION")) && !(current.equals("NEWTOPIC")) && s.hasNext()){
                                                    time=time+current+ " ";
                                                    current=s.next();
                                                }
                                                tempQuestion.setCreationTime(time);

                                            }
                                            if(current.equals("QUESITONID") && s.hasNext()){
                                                //ID for the question
                                                String questionid="";
                                                current=s.next();
                                                tempQuestion.setQuestionID(current);
                                                current=s.next();
                                            }

                                            if(current.equals("NEWREPLY") && s.hasNext()) {
                                                //Get all of the replies
                                                Reply tempR=new Reply();
                                                current=s.next();
                                                while(!current.equals("NEWREPLY") && s.hasNext()){
                                                    //Build a new reply
                                                    if(current.equals("REPLYTXT") && s.hasNext()){
                                                        current=s.next();
                                                        String reply="";
                                                        while(!(current.equals("REPLYUSER")) && s.hasNext()){
                                                            reply=reply+current+" ";
                                                            current=s.next();
                                                        }
                                                        tempR.set_reply(reply);

                                                    }
                                                    if(current.equals("REPLYUSER") && s.hasNext()){
                                                        //username of author
                                                        current=s.next();
                                                        if(current.equals("REPLYUSERID")){
                                                            //do nothing
                                                        }
                                                        else {
                                                            tempR.set_reply_username(current);
                                                            current = s.next();
                                                        }

                                                    }
                                                    if(current.equals("REPLYUSERID") && s.hasNext()){
                                                        //id of user
                                                        current=s.next();
                                                        if(current.equals("POINTS")) {
                                                            //do nothing
                                                        }
                                                        else{
                                                            tempR.set_reply_userID(current);
                                                            current=s.next();
                                                        }
                                                    }
                                                    if(current.equals("POINTS") && s.hasNext()){
                                                        current=s.next();
                                                        if(current.equals("ENDORSED")){
                                                            //do nothing
                                                        }
                                                        else {
                                                            tempR.set_reply_rating(current);
                                                            current = s.next();
                                                        }
                                                    }
                                                    if(current.equals("ENDORSED") && s.hasNext()){
                                                        current=s.next();
                                                        if(current.equals("Yes")){
                                                            tempR.set_reply_endorsed(true);
                                                            current=s.next();
                                                        }
                                                        else if(current.equals("No")){
                                                            current=s.next();
                                                        }
                                                        else{
                                                            //do nothing
                                                        }
                                                    }
                                                    if(current.equals("CREATION") && s.hasNext()){
                                                        //timestamp
                                                        current=s.next();
                                                        String time="";
                                                        while(!(current.equals("NEWREPLY"))&& !(current.equals("NEWQUESTION")) && !(current.equals("NEWTOPIC")) && s.hasNext()){
                                                            time=time+current+ " ";
                                                            current=s.next();
                                                        }
                                                        tempR.set_reply_time(time);
                                                    }
                                                }
                                                //NEWREPLY means the start of a new reply within this question, add to the question's array list
                                                replies.add(tempR);
                                            }
                                            q.add(tempQuestion);
                                        }
                                        tempQuestion.setReplies(replies);

                                    }

                                }
                                //add the temp topic to the array list that will be returned in the end
                                tempTopic.set_questions(q);
                                topics.add(tempTopic);
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
