package utils;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
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

public class Question {

    // description of question
    private String questionDescription;

    // votes on questions
    private int studentRating;

    // Title of question
    private String questionTitle;

    // Time question was created
    private Date creationTime;

    // Array of replies to the
    private List<Reply> replies;

    // Owner ID
    private String ownerID;

    // Class ID that this question belongs to.
    private String classID;

    // University ID
    private String universityID;

    //Topic ID
    private String topicID;

    //For inserting into the database these are needed
    private String TAG= Question.class.getSimpleName();
    private String tag_string_req= "question_req";

    public Question(String questionDescription, String topicID, int studentRating, String questionTitle, Date creationTime, List<Reply> replies, String ownerID, String classID, String universityID) {
        this.questionDescription = questionDescription;
        this.studentRating = studentRating;
        this.questionTitle = questionTitle;
        this.creationTime = creationTime;
        this.replies = replies;
        this.ownerID = ownerID;
        this.classID = classID;
        this.universityID = universityID;
        this.topicID=topicID;
    }


    public String getQuestionDescription() {
        return questionDescription;
    }

    public int getStudentRating() {
        return studentRating;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getTopicID(){ return topicID;}

    public List<Reply> getReplies() {
        return replies;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public void setStudentRating(int studentRating) {
        this.studentRating = studentRating;

    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String setTopicID(){ return topicID;}

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getUniversityID() {
        return universityID;
    }

    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    //Given a question, it will push this question to the database
    public void add_question_to_database(Question q){
        //Description
        String desc=q.getQuestionDescription();
        //Title
        String title=q.getQuestionTitle();
        //Owner ID
        String OID=q.getOwnerID();
        //Class ID
        String CID=q.getClassID();
        //University ID
        String UID=q.getUniversityID();
        //Topic ID
        String TID=q.getTopicID();

        //TODO: HOW TO DO THIS WITH SPACES IN THE URL?!?
        String url=URLS.URL_QUESTIONS+"?desc="+desc+"&title="+title+"&OID="+OID+"&CID="+CID+"&UID="+UID+"&TID="+TID;
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(MainActivity.getInstance(), "Success: question added", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.getInstance(), "Error", Toast.LENGTH_LONG).show();
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

    }
}
