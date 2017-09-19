package com.example.sae1.raisehand;
import android.app.ProgressDialog;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.EditText;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static android.Manifest.permission.INTERNET;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private String TAG= LoginActivity.class.getSimpleName();
    private Button buttonLogin;
    private TextView msgResponse;
    private ProgressDialog pDialog;
    private String tag_string_req= "string_req";
    /**
    EditText editTextUsername, editTextPassword;
    //ProgressBar progressBar;
    private String tag_json_obj= "jobj_req", tag_json_arry= "jarray_req";
    private ProgressDialog pDialog;**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);
        //msgResponse= (TextView) findViewById(R.id.msgResponse);
        /**
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }**/
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        /**
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);**/

        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
               finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
        showProgressDialog();;
        StringRequest strReq= new StringRequest(Method.GET, URLS.URL_LOGIN, new Response.Listener<String>(){
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

        //if everything is okay
        MainActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}