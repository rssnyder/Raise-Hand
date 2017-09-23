package utils;
import android.app.ProgressDialog;
import app.MainActivity;
import android.app.Activity;
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
        showProgressDialog();

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

        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                                                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d(TAG, response.toString());
<<<<<<< HEAD
                            msgResponse.setText(response.toString());
                            if (response.toString() == username)
=======
                            String phpResponse=response.toString();
                            String[] seperated=phpResponse.split(":");

                            if(seperated[2]=="true") {
>>>>>>> 1d44ace97d47d4ff83b51cb8b5556e4d52295a23
                                Toast.makeText(MainActivity.getInstance(), "Logged In Successfully", Toast.LENGTH_LONG).show();
                                currentUser=new User(1,1,seperated[2],seperated[4],seperated[6],true);
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