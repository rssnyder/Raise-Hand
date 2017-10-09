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
    //TODO: Change this, class info auto made for now
    private String title;
    private String classID;
    private String tag_string_req;
    public static final String TAG= Classes.class.getSimpleName();

    public Classes(String title, String classID){
        this.title=title;
        this.classID=classID;
        this.topics=this.get_topics();
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
                                        tempTopic.set_time(Time);
                                    }
                                    if(current.equals("TOPICNAME")){
                                        current=s.next();
                                        String Topic="";
                                        while(!(current.equals("DESCRIPTION"))){
                                            Topic=Topic+" "+current;
                                            current=s.next();
                                        }
                                        tempTopic.set_title(Topic);
                                    }
                                    if(current.equals("DESCRIPTION")){
                                        current=s.next();
                                        String Description="";
                                        while(!(current.equals("NEWQUESTION"))){
                                            Description=Description+" "+current;
                                            current=s.next();
                                        }
                                        tempTopic.set_description(Description);
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
                                                current=s.next();
                                                String title="";
                                                while(!(current.equals("QUESTIONDESCRIPTION"))){
                                                    title=title+current+ " ";
                                                    current=s.next();
                                                }
                                                tempQuestion.setQuestionTitle(title);

                                            }
                                            if(current.equals("QUESTIONDESCRIPTION")){
                                                //question
                                                current=s.next();
                                                String desc="";
                                                while(!(current.equals("QUESTIONDESCRIPTION"))){
                                                    desc=desc+current+ " ";
                                                    current=s.next();
                                                }
                                                tempQuestion.setQuestionDescription(desc);

                                            }
                                            if(current.equals("QUESTIONUSER")){
                                                //username who created it
                                                current=s.next();
                                                tempQuestion.setQuestionUsername(current);
                                                s.next();
                                            }
                                            if(current.equals("QUESTIONUSERID")){
                                                //user id who created it
                                                current=s.next();
                                                tempQuestion.setOwnerID(current);
                                                current=s.next();
                                            }
                                            if(current.equals("POINTS")){
                                                //upvotes
                                                current=s.next();
                                                tempQuestion.setStudentRating(current);
                                                current=s.next();

                                            }
                                            if(current.equals("ENDORSED")){
                                                //if it is endorsed or not
                                                current=s.next();
                                                if(current.equals("Yes")){
                                                    //this question is endorsed
                                                    tempQuestion.setEndorsed(true);
                                                }
                                                current=s.next();
                                            }
                                            if(current.equals("CREATION")){
                                                //timestamp
                                                current=s.next();
                                                String time="";
                                                while(!(current.equals("NEWREPLY"))&& !(current.equals("NEWQUESTION")) && !(current.equals("NEWTOPIC"))){
                                                    time=time+current+ " ";
                                                    current=s.next();
                                                }
                                                tempQuestion.setCreationTime(time);

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
                                                    if(current.equals("CREATION")){
                                                        //timestamp
                                                        current=s.next();
                                                        String time="";
                                                        while(!(current.equals("NEWREPLY"))&& !(current.equals("NEWQUESTION")) && !(current.equals("NEWTOPIC"))){
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
}
