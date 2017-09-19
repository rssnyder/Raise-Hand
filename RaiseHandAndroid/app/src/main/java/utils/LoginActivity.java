package utils;
import android.app.ProgressDialog;
import app.MainActivity;
import utils.URLS;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import com.android.volley.Request.Method;
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
    private TextView msgResponse;
    private ProgressDialog pDialog;
    private String tag_string_req= "string_req";
    /**
    EditText editTextUsername, editTextPassword;
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);
        msgResponse = (TextView) findViewById(R.id.msgResponse);

        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        /**
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);**/

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, com.example.sae1.raisehand.student_home_page.class);
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
        showProgressDialog();
        StringRequest strReq= new StringRequest(Method.GET, URLS.URL_STRING_REQ, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();
                    }}, new Response.ErrorListener(){
                        @Override
                            public void onErrorResponse(VolleyError error){
                                VolleyLog.d(TAG, "Error: "+ error.getMessage());
                                hideProgressDialog();;
                            }
        });

            /**
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

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
        }**/
        MainActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}