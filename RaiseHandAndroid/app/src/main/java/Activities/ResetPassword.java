package Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class ResetPassword  extends AppCompatActivity {
    private String TAG= ResetPassword.class.getSimpleName();
    private String tag_string_req= "string_req";
    EditText Email, TempPassword, Password, PasswordConfirm;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        TempPassword= (EditText) findViewById(R.id.TempPassword);
        Password= (EditText) findViewById(R.id.Password);
        PasswordConfirm = (EditText) findViewById(R.id.PasswordConfirm);
        Email = (EditText) findViewById(R.id.Email);
        buttonSubmit= (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }
    public void resetPassword() {
        final String password = Password.getText().toString();
        final String passwordConfirm = PasswordConfirm.getText().toString();
        final String email = Email.getText().toString();
        String url_final = "";
        //url to be used to get the user information via PHP/ volley

        if (passwordConfirm.equals(password)) {
            String urlSuffix = "?email=" + email;
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
            }
            );
            // Adding request to request queue
            VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);
        }

        else{
            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            PasswordConfirm.setText("");
            Password.setText("");
        }
    }

}
