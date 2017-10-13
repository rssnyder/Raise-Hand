package utils;
import android.app.ProgressDialog;
import app.MainActivity;
import app.TeacherNotifications;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private String TAG= LoginActivity.class.getSimpleName();
    private Button buttonLogin;
    private Button textViewRegister;
    private ProgressDialog pDialog;
    private String tag_string_req= "string_req";
    EditText editTextUsername, editTextPassword;
    private ArrayList<Topics> topics;
    @Expose
    private User currentUser;
    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);
        textViewRegister= (Button) findViewById(R.id.textViewRegister);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        //if the username is already stored, stay logged in.
      /*  if(mPreferences.contains("username")){
            Intent teacherNotifications = new Intent(getApplicationContext(), TeacherNotifications.class);
            startActivity(teacherNotifications);
            finish();
        }*/

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent SignupActivity = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(SignupActivity);
                finish();
            }
        });
    }

    public void showProgressDialog() {
        if(!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    public void hideProgressDialog() {
        if(pDialog.isShowing()) {
            pDialog.hide();
            pDialog.dismiss();
        }
    }

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        //url to be used to get the user information via PHP/ volley
        String urlSuffix= "?username="+username+"&pass="+password;
        String url_final= URLS.URL_STRING_LOGIN+urlSuffix;


        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        showProgressDialog();
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // TODO used for testing teacher app. Remove before deployment!
                        if(username.equals("teacher") && password.equals("teacher")){
                            Intent teacherNotifications =
                                    new Intent(getApplicationContext(), TeacherNotifications.class);
                            startActivity(teacherNotifications);
                            finish();
                        }

                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        String[] seperated=phpResponse.split(":");

                            if(seperated[1].contains("true")) {
                                //concat strings to make it so that the array is properly read from the php response
                                String reset = seperated[2];
                                reset = reset.substring(1, reset.indexOf(",") - 1);
                                String unique_id = seperated[3];
                                unique_id = unique_id.substring(1, unique_id.indexOf(",") - 1);
                                String roleID = seperated[4];
                                roleID = roleID.substring(1, roleID.indexOf(",") - 1);
                                String usern = seperated[5];
                                usern = usern.substring(1, usern.indexOf(",") - 1);
                                String first = seperated[6];
                                first = first.substring(1, first.indexOf(",") - 1);
                                String last = seperated[7];
                                last = last.substring(1, last.indexOf(",") - 1);
                                String class_ids = seperated[8];
                                ArrayList<Classes> classes=new ArrayList<Classes>();
                                class_ids = class_ids.substring(1, class_ids.indexOf("}"));
                                Scanner s= new Scanner(class_ids);
                                s.useDelimiter(",");
                                while(s.hasNext()) {
                                    String id = s.next();
                                    id=id.trim();
                                    if(id.equals("0")){
                                        //do nothing, this is not a class, just a place holder
                                    }
                                    else{
                                       Classes c =new Classes("Class", id);
                                        classes.add(c);
                                        c.setTopics(get_topics(c));
                                    }
                                }
                                Toast.makeText(MainActivity.getInstance(), "Welcome back, " + first + "!", Toast.LENGTH_LONG).show();
                                currentUser = new User(reset, unique_id, roleID, usern, first, last, classes, true);

                                //store the user info on login
                                SharedPreferences.Editor editor = mPreferences.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(currentUser);
                                editor.putString("currentUser", json);

                                editor.putString("reset", reset);
                                editor.putString("username", usern);
                                editor.putString("role", roleID);
                                editor.putString("unique_id", unique_id);
                                editor.putString("first_name", first);
                                editor.putString("last_name", last);
                                editor.commit();


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

        String roleID = mPreferences.getString("role", "");
        //TODO make it go to the student or teacher page depending on what kind of user logged in
        //Go to the teacher notification page
        if (roleID.equals(Roles.TEACHER.toString())) {
            Intent teacherNotifications =
                    new Intent(getApplicationContext(), TeacherNotifications.class);
            startActivity(teacherNotifications);
            finish(); //finsih this activity so you can't press back to go to the login screen after already logging in
        } else if (roleID.equals(Roles.STUDENT.toString())) {
            Intent studentNotifications =
                    new Intent(getApplicationContext(), com.example.sae1.raisehand.student_notifications.class);
            startActivity(studentNotifications);
            finish(); //finsih this activity so you can't press back to go to the login screen after already logging in
        }
    }

    public ArrayList<Topics> get_topics(Classes c) {
        topics=new ArrayList<Topics>();
        int existsQuestions;
        int existsTopics;
        String urlSuffix= "?classId="+c.getClassID();
        String url_final= URLS.URL_TOPICS+urlSuffix;
        showProgressDialog();
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
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }
        );
        // Adding request to request queue
        MainActivity.getInstance().addToRequestQueue(req, tag_string_req);
        return topics;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

