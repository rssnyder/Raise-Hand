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
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        String[] seperated=phpResponse.split(":");

                        if(seperated[1].contains("true")) {
                            //concat strings to make it so that the array is properly read from the php response
                            String reset=seperated[2];
                            reset=reset.substring(1, reset.indexOf(",")-1);
                            String unique_id=seperated[3];
                            unique_id=unique_id.substring(1, unique_id.indexOf(",")-1);
                            String roleID=seperated[4];
                            roleID=roleID.substring(1,roleID.indexOf(",")-1);
                            String usern=seperated[5];
                            usern=usern.substring(1, usern.indexOf(",")-1);
                            String first=seperated[6];
                            first=first.substring(1, first.indexOf(",")-1);
                            String last=seperated[7];
                            last=last.substring(1,last.indexOf(",")-1);
                            String class_ids=seperated[8];
                            class_ids=class_ids.substring(1, class_ids.length()-2);
                            //In the php file, if someone is not in a class, I made it return 0 (meaning no classes for this user)
                            if(class_ids=="0") {
                                class_ids = "None available";
                            }
                            Toast.makeText(MainActivity.getInstance(), "Welcome back, "+first+"!", Toast.LENGTH_LONG).show();
                            currentUser=new User(reset, unique_id,roleID,usern,first,last,class_ids, true);

                            //store the username on login
                            SharedPreferences.Editor editor = mPreferences.edit();
                            editor.putString("reset", reset);
                            editor.putString("username", usern);
                            editor.putString("role", roleID);
                            editor.putString("unique_id", unique_id);
                            editor.putString("first_name", first);
                            editor.putString("last_name", last);
                            //editor.putString("classes",class_ids);
                            editor.commit();

                            //TODO make it go to the student or teacher page depending on what kind of user logged in
                            //Go to the teacher notification page
                            if(roleID.equals(Roles.TEACHER.toString())) {
                                Intent teacherNotifications =
                                        new Intent(getApplicationContext(), TeacherNotifications.class);
                                startActivity(teacherNotifications);
                                finish(); //finsih this activity so you can't press back to go to the login screen after already logging in
                            }

                        }
                        else {
                            Toast.makeText(MainActivity.getInstance(), "Logged In Failed", Toast.LENGTH_LONG).show();
                        }
                        hideProgressDialog();
                    }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }
        );
        // Adding request to request queue
        MainActivity.getInstance().addToRequestQueue(req, tag_string_req);

        if(existsQuestions==1) {
            //todo: while loop to go through all of the questions
            String description, title, ownerID, universityID;
            int studentRating;
            Date creationTime;
            List<Reply> replies = new ArrayList<Reply>();
            int existReplies;
            //Return from the database information on whether or not there are replies to this question
            if (existReplies == 1) {
                //If there are replies, need to make a method that gets the information on all of the replies, similar to this question method
            }
            q.add(new Question(description, studentRating, title, creationTime, replies, ownerID, classID, universityID));
        }

        if(existsTopics==1) {
            //todo: while loop to go through all of the questions

        }

        return t;
    }
    //Given a topic, it will push this question to the database
    public void add_topic_to_database(Topics t){
        //TODO: add this question to the database
    }
}
