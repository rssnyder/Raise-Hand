package utils;

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

import app.MainActivity;

/**
 * Created by sae1 on 9/27/17.
 * This activity allows users to create a new user in the database and sign up
 */

public class SignupActivity extends AppCompatActivity {
    private String TAG= SignupActivity.class.getSimpleName();
    private String tag_string_req= "string_req";
    EditText FirstName,LastName, Username, Password, Email, PasswordConfirm;
    Button buttonRegister;
    Spinner university;
    int university_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        university=(Spinner) findViewById(R.id.University);
        university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object selected= adapterView.getItemAtPosition(i);
                if(selected.toString().equals("Iowa State University")){
                    //university id for ISU is 1
                    university_id=1;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
            }
        });
        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName= (EditText) findViewById(R.id.LastName);
        Username = (EditText) findViewById(R.id.Username);
        Password= (EditText) findViewById(R.id.Password);
        PasswordConfirm = (EditText) findViewById(R.id.PasswordConfirm);
        Email = (EditText) findViewById(R.id.Email);
        buttonRegister= (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }
    public void signUp() {
        boolean isValidSignUp = true;
        final String first = FirstName.getText().toString();
        final String last = LastName.getText().toString();
        final String username = Username.getText().toString();
        final String password = Password.getText().toString();
        final String passwordConfirm = PasswordConfirm.getText().toString();
        final String email = Email.getText().toString();

        final String university="Iowa State University";
        String email_user = "";
        try {
            email_user = email.substring(0, email.indexOf("@"));
            isValidSignUp = true;
        } catch(StringIndexOutOfBoundsException e){
            Toast.makeText(MainActivity.getInstance(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
            isValidSignUp = false;
        }
        final String email_domain=email.substring(email.indexOf("@")+1);
        String url_final = "";
        //url to be used to get the user information via PHP/ volley

        if (passwordConfirm.equals(password)) {
            String urlSuffix = "?first=" + first + "&last=" + last + "&pass=" + password + "&username=" + username + "&university=" + university_id + "&emailU=" + email_user + "&emailD=" + email_domain;
            url_final = URLS.URL_REGISTER + urlSuffix;
        }

        else{
            Toast.makeText(MainActivity.getInstance(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            PasswordConfirm.setText("");
            Password.setText("");
        }

        boolean allFieldsFilled = !first.isEmpty() && !last.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty();

        if(!allFieldsFilled){
            isValidSignUp = false;
            Toast.makeText(MainActivity.getInstance(), "Please fill out all fields", Toast.LENGTH_LONG).show();
        }
        if((email_domain.isEmpty() || !email_domain.contains(".")) && allFieldsFilled){
            Toast.makeText(MainActivity.getInstance(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
            isValidSignUp = false;
        }

        if(isValidSignUp) {
            //url to be used to get the user information via PHP/ volley
            String urlSuffix =
                    "?first=" + first + "&last=" + last + "&pass=" + password + "&username=" +
                    username + "&university=" + university_id + "&emailU=" + email_user +
                    "&emailD=" + email_domain;
            url_final = URLS.URL_REGISTER + urlSuffix;
     /*
        //validating inputs
        if (TextUtils.isEmpty(FirstName)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        showProgressDialog();*/
            StringRequest req = new StringRequest(Request.Method.GET, url_final,
                                                  new Response.Listener<String>() {
                                                      @Override
                                                      public void onResponse(String response) {

                                                          Log.d(TAG, response.toString());
                                                          String phpResponse = response.toString();
                                                          //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                                                          if (phpResponse.contains("User exists")) {
                                                              Toast.makeText(MainActivity.getInstance(), "User exists, please try again", Toast.LENGTH_LONG).show();
                                                          } else if (phpResponse.contains("Done")) {
                                                              Toast.makeText(MainActivity.getInstance(), "Success: user created", Toast.LENGTH_LONG).show();
                                                          } else {
                                                              Toast.makeText(MainActivity.getInstance(), "Error", Toast.LENGTH_LONG).show();
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
            MainActivity.getInstance().addToRequestQueue(req, tag_string_req);
        }
    }
    public void universityID(String result){
        //need to keep adding universities here
        if(result=="Iowa State University")
            university_id=1;
    }

}
