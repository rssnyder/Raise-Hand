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
 * Created by sae1 on 11/6/17.
 */

public class ForgotPassword extends AppCompatActivity {
    private String TAG= ForgotPassword.class.getSimpleName();
    private String tag_string_req= "string_req";
    EditText Email;
    Button buttonSubmit;

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
