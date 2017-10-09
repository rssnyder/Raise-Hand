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

    private void showProgressDialog() {
        if(!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
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
                                class_ids = class_ids.substring(1, class_ids.length() - 2);
                                Scanner s= new Scanner(class_ids);
                                s.useDelimiter(",");
                                while(s.hasNext()) {
                                    String id = s.next();
                                    id=id.trim();
                                    if(id.equals("0")){
                                        //do nothing, this is not a class, just a place holder
                                    }
                                    else{
                                       Classes c=new Classes("Class", id);
                                        classes.add(c);
                                    }
                                }
                                Toast.makeText(MainActivity.getInstance(), "Welcome back, " + first + "!", Toast.LENGTH_LONG).show();
                                currentUser = new User(reset, unique_id, roleID, usern, first, last, classes, true);

                                //store the username on login
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
                                //editor.putString("classes",class_ids);
                                editor.commit();

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

    }

    public User getCurrentUser() {
        return currentUser;
    }
}