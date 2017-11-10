package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import Student.StudentNotifications;
import Teacher.TeacherNotifications;
import Utilities.Roles;
import Utilities.URLS;
import Utilities.User;

/**
 * Created by sae1 on 11/6/17.
 * This class is used when a user logins after having submitted a
 * request for a temp password (thus their password is the temp password).
 * The app should auto-redirect to this page to allow the user to reset
 * their password to something new.
 */

public class ResetPassword  extends AppCompatActivity {
    private String TAG= ResetPassword.class.getSimpleName();
    private String tag_string_req= "string_req";
    EditText Password, PasswordConfirm;
    Button buttonSubmit;
    private User currentUser;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        //Fields for the user to input
        Password= (EditText) findViewById(R.id.Password);
        PasswordConfirm = (EditText) findViewById(R.id.PasswordConfirm);
        buttonSubmit= (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }
    public void resetPassword() {
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);
        final String username= currentUser.getUsername();
        final String password = Password.getText().toString();
        final String passwordConfirm = PasswordConfirm.getText().toString();
        if(!password.equals(passwordConfirm)) {
            Toast.makeText(this.getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }
        String url_final = "";
        //url to be used to get the user information via PHP/ volley
        String urlSuffix = "?username=" + username + "&pass=" + password;
        url_final = URLS.URL_RESETPASS + urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET, url_final,
                new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response.toString());
                            String phpResponse = response.toString();
                            if (phpResponse.contains("Done")) {
                                Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: password changed", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(VolleyMainActivityHandler.getInstance(), "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });
            // Adding request to request queue
        VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);
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

}
