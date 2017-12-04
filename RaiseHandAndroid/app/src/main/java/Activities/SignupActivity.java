package Activities;

import android.content.Intent;
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
 * This activity allows users to create a new user in the database and sign up
 * @author sae1
 */

public class SignupActivity extends AppCompatActivity {
    private String TAG= SignupActivity.class.getSimpleName();
    private String tag_string_req= "string_req";
    EditText FirstName,LastName, Username, Password, Email, PasswordConfirm;
    Button buttonRegister;
    Spinner university;
    int university_id;
    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, and
     * adds functionality to create an account
     *
     * @param savedInstanceState the current state of the activity
     */
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
    /** This method enables users to create an account by providing
     * a first name, last name, username, password (twice), email, and
     * university. Then it calls on volley to insert into the database a
     * new user.
     **/
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
            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
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
            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            PasswordConfirm.setText("");
            Password.setText("");
        }

        boolean allFieldsFilled = !first.isEmpty() && !last.isEmpty() && !username.isEmpty() && !password.isEmpty() && !email.isEmpty();

        if(!allFieldsFilled){
            isValidSignUp = false;
            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Please fill out all fields", Toast.LENGTH_LONG).show();
        }
        if((email_domain.isEmpty() || !email_domain.contains(".")) && allFieldsFilled){
            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Please enter a valid email address", Toast.LENGTH_LONG).show();
            isValidSignUp = false;
        }

        if(isValidSignUp) {
            //url to be used to get the user information via PHP/ volley
            String urlSuffix =
                    "?first=" + first + "&last=" + last + "&pass=" + password + "&username=" +
                    username + "&university=" + university_id + "&emailU=" + email_user +
                    "&emailD=" + email_domain;
            url_final = URLS.URL_REGISTER + urlSuffix;
            StringRequest req = new StringRequest(Request.Method.GET, url_final,
                                                  new Response.Listener<String>() {
                                                      @Override
                                                      public void onResponse(String response) {

                                                          Log.d(TAG, response.toString());
                                                          String phpResponse = response.toString();
                                                          //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                                                          if (phpResponse.contains("User exists")) {
                                                              Toast.makeText(VolleyMainActivityHandler.getInstance(), "User exists, please try again", Toast.LENGTH_LONG).show();
                                                          } else if (phpResponse.contains("Done")) {
                                                              Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: user created", Toast.LENGTH_LONG).show();
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
            Intent navigateLogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(navigateLogin);
            finish();
        }
    }
    public void universityID(String result){
        //need to keep adding universities here
        if(result=="Iowa State University")
            university_id=1;
    }

}
