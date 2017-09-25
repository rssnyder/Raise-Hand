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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private String TAG= LoginActivity.class.getSimpleName();
    private Button buttonLogin;
    private ProgressDialog pDialog;
    private String tag_string_req= "string_req";
    private StringRequest strReq;
    EditText editTextUsername, editTextPassword;
    public User currentUser;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        mPreferences = getSharedPreferences("User", MODE_PRIVATE);
        //if the username is already stored, stay logged in.
        if(mPreferences.contains("username")){
            Intent teacherNotifications = new Intent(getApplicationContext(), TeacherNotifications.class);
            startActivity(teacherNotifications);
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userLogin();
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
        }
    }

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        String urlSuffix= "?username="+username+"&password="+password;
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

                            Log.d(TAG, response.toString());
                            String phpResponse=response.toString();
                            String[] seperated=phpResponse.split(":");

                            if(seperated[1].contains("true")) {
                                //concat strings to make it so that the array is properly read from the php response
                                String unique_id=seperated[2];
                                unique_id=unique_id.substring(1, unique_id.indexOf(",")-1);
                                String roleID=seperated[3];
                                roleID=roleID.substring(1,roleID.indexOf(",")-1);
                                String usern=seperated[4];
                                usern=usern.substring(1, usern.indexOf(",")-1);
                                String first=seperated[5];
                                first=first.substring(1, first.indexOf(",")-1);
                                String last=seperated[6];
                                last=last.substring(1,last.length()-2);
                                Toast.makeText(MainActivity.getInstance(), "Welcome back "+first+"!", Toast.LENGTH_LONG).show();
                                currentUser=new User(unique_id,roleID,usern,first,last,true);

                                //store the username on login
                                SharedPreferences.Editor editor = mPreferences.edit();
                                editor.putString("username", usern);
                                editor.commit();
                                
                                //TODO make it go to the student or teacher page depending on what kind of user logged in
                                //Go to the teacher notification page
                                Intent teacherNotifications = new Intent(getApplicationContext(), TeacherNotifications.class);
                                startActivity(teacherNotifications);
                                finish(); //finsih this activity so you can't press back to go to the login screen after already logging in
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
}