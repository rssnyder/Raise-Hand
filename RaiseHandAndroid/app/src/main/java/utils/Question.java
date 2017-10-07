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


    public Question(String questionDescription, int studentRating, String questionTitle, Date creationTime, List<Reply> replies, String ownerID, String classID, String universityID) {
        this.questionDescription = questionDescription;
        this.studentRating = studentRating;
        this.questionTitle = questionTitle;
        this.creationTime = creationTime;
        this.replies = replies;
        this.ownerID = ownerID;
        this.classID = classID;
        this.universityID = universityID;
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
        //TODO: add this question to the database
    }
}
