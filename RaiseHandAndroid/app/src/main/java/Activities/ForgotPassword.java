package Activities;

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

import Utilities.URLS;

/**
 *
 * This will be used when a user initially tries to
 * reset his or her password. This is the first form that they
 * fill out and then will get an email with a temporary password
 * @author sae1
 */

public class ForgotPassword extends AppCompatActivity {
    private String TAG= ForgotPassword.class.getSimpleName();
    private String tag_string_req= "string_req";
    EditText Email;
    Button buttonSubmit;

    /**
     * This method starts the activity, initializes the activity view and gets the currentUser, and
     * adds functionality to request a new password
     * @param savedInstanceState the state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_password_reset);
        Email = (EditText) findViewById(R.id.Email);
        buttonSubmit= (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startPasswordReset();
            }
        });

    }

    /** Given an email from the user, a volley request is sent to
     * give the user a temporary password and email it to them. It
     * also saves that they are in the "reset" stage on the database
     **/
    public void startPasswordReset() {
        final String email = Email.getText().toString();
        String url_final = "";
        //url to be used to get the user information via PHP/ volley
        String urlSuffix = "?email=" + email;
        url_final = URLS.URL_FORGOTPASS + urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET, url_final,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response.toString());
                            String phpResponse = response.toString();
                            if (phpResponse.contains("Done")) {
                                Toast.makeText(VolleyMainActivityHandler.getInstance(), "Look in your email for a temporary password", Toast.LENGTH_LONG).show();
                            }
                            else{
                                    Toast.makeText(VolleyMainActivityHandler.getInstance(), "Error", Toast.LENGTH_LONG).show();
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
            VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);
    }
}
