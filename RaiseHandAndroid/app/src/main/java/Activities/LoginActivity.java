package Activities;
import android.app.ProgressDialog;

import Teacher.TeacherNotifications;
import Utilities.Roles;
import Utilities.StringParse;
import Utilities.URLS;
import Utilities.User;

import android.app.Activity;
import android.content.Context;
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
import Student.StudentNotifications;
import Utilities.VolleyHelper;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private String TAG= LoginActivity.class.getSimpleName();
    private Button buttonLogin, textViewRegister, textViewReset;
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
        textViewReset= (Button) findViewById(R.id.textViewReset);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        final String usernameString = editTextUsername.getText().toString();
        final String passwordString = editTextPassword.getText().toString();

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //validating inputs are there
//                if (TextUtils.isEmpty(usernameString)) {
//                    editTextUsername.setError("Please enter your username");
//                    editTextUsername.requestFocus();
//                    return;
//                }
//                if (TextUtils.isEmpty(passwordString)) {
//                    editTextPassword.setError("Please enter your password");
//                    editTextPassword.requestFocus();
//                    return;
//                }

                VolleyHelper.userLogin(usernameString, passwordString, getApplicationContext());
//                userLogin();
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent SignupActivity = new Intent(getApplicationContext(), Activities.SignupActivity.class);
                startActivity(SignupActivity);
                finish();
            }
        });
        textViewReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                Intent ForgotPassActivity = new Intent(getApplicationContext(), Activities.ForgotPassword.class);
                startActivity(ForgotPassActivity);
                finish();
            }
        });
    }


    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //url to be used to get the user information via PHP/ volley
        String urlSuffix= "?username="+username+"&pass="+password;
        String url_final= URLS.URL_STRING_LOGIN+urlSuffix;


        //validating inputs are there
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
                        currentUser = StringParse.parseUserVolley(phpResponse);
                        if(currentUser!=null) {
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Welcome back, " + currentUser.getFirstName() + "!", Toast.LENGTH_LONG).show();

                            //store the user info on login
                            SharedPreferences.Editor editor = mPreferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(currentUser);
                            editor.putString("currentUser", json);
                            editor.commit();
                        }
                        else {
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Logged In Failed", Toast.LENGTH_LONG).show();
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
        VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);
        String reset= mPreferences.getString("reset","");
        if(reset.equals("1")){
            //redirect to a reset password page
            Intent resetPassword= new Intent(getApplicationContext(), ResetPassword.class);
            startActivity(resetPassword);
            finish();
        }
        //Get the role of the user and direct the user to the correct page depending on their role
        String roleID = mPreferences.getString("role", "");
        if (roleID.equals(Roles.TEACHER.toString())) {
            Intent teacherNotifications =
                    new Intent(getApplicationContext(), TeacherNotifications.class);
            startActivity(teacherNotifications);
            //finsih this activity so you can't press back to go to the login screen after already logging in
            finish();
        } else if (roleID.equals(Roles.STUDENT.toString())) {
            Intent studentNotifications =
                    new Intent(getApplicationContext(), StudentNotifications.class);
            startActivity(studentNotifications);
            //finsih this activity so you can't press back to go to the login screen after already logging in
            finish();
        }
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

    public User getCurrentUser() {
        return currentUser;
    }
}

