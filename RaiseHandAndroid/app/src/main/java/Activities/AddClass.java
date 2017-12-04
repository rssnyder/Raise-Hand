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

import Student.StudentHomePage;
import Teacher.TeacherClasses;
import Utilities.Classes;
import Utilities.Question;
import Utilities.StringParse;
import Utilities.Topics;
import Utilities.URLS;
import Utilities.User;

/**
 * Created by sae1 on 12/4/17.
 */

public class AddClass  extends AppCompatActivity {
        private Button submit;
        EditText accessCode;
        private SharedPreferences mPreferences;
        private User currentUser;
        private String TAG= AddClass.class.getSimpleName();
        private String tag_string_req= "string_req";
    /**
         * This method starts the activity, initializes the activity view and gets the currentUser, and
         * adds functionality to add a new question
         * @param savedInstanceState state of the app
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            //make the activity page
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_class);
            submit = (Button) findViewById(R.id.submitClass);
            mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
            accessCode = (EditText) findViewById(R.id.accessCode);

            //Get the information that was passed in from the previous activity
            Gson gson = new Gson();
            String json = mPreferences.getString("currentUser", "");
            currentUser = gson.fromJson(json, User.class);

            // Click the submit button
            submit.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    //get the information that the user had submitted in the text boxes
                    final String code = accessCode.getText().toString();
                    String url= Utilities.URLS.URL_ADD_CLASS;
                    url=url+"?code="+code+"&userID="+currentUser.getId();
                    StringRequest req = new StringRequest(Request.Method.GET,url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d(TAG, response.toString());
                                    String phpResponse=response.toString();
                                    String classTitle=phpResponse.substring(11,phpResponse.indexOf("ID="));
                                    String classID=phpResponse.substring(phpResponse.indexOf("ID=")+3);
                                    Toast.makeText(VolleyMainActivityHandler.getInstance(), "You have been added to "+classTitle, Toast.LENGTH_LONG).show();
                                    SharedPreferences.Editor editor = mPreferences.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(currentUser);
                                    currentUser.getClasses().add(new Classes(classTitle,classID));
                                    editor.putString("currentUser", json);
                                    editor.commit();

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("IN ERROR RESPONSE");
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Invaldid access code", Toast.LENGTH_LONG).show();
                        }
                    }
                    );
                    // Adding request to request queue
                    VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);

                    Intent home = new Intent(getApplicationContext(), StudentHomePage.class);
                    startActivity(home);

                }
            });
        }

}
