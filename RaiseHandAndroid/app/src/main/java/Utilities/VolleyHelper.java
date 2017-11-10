package Utilities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import Activities.LoginActivity;
import Activities.ResetPassword;
import Activities.VolleyMainActivityHandler;
import Student.StudentNotifications;
import Teacher.TeacherNotifications;

import static android.content.Context.MODE_PRIVATE;

public class VolleyHelper {
    private static ProgressDialog pDialog;
    private static String TAG= LoginActivity.class.getSimpleName();
    private static User currentUser;
    private static String tag_string_req= "string_req";
    private static SharedPreferences mPreferences;


    public static void showProgressDialog() {
        if(!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if(pDialog.isShowing()) {
            pDialog.hide();
            pDialog.dismiss();
        }
    }


    public static void userLogin(String username, String password, Context context) {
        mPreferences = context.getSharedPreferences("preferences", MODE_PRIVATE);

        //url to be used to get the user information via PHP/ volley
        String urlSuffix= "?username="+username+"&pass="+password;
        String url_final= URLS.URL_STRING_LOGIN+urlSuffix;

//        pDialog= new ProgressDialog(context);
//        pDialog.setMessage("Loading...");
//        pDialog.setCancelable(false);


//        showProgressDialog();
        StringRequest req = new StringRequest(Request.Method.GET, url_final,
                                              new Response.Listener<String>() {
                                                  @Override
                                                  public void onResponse(String response) {
                                                      Log.d(TAG, response.toString());
                                                      String phpResponse=response.toString();
                                                      currentUser = StringParse.parseUserVolley(phpResponse);
                                                      if(currentUser!=null) {
                                                          Toast.makeText(VolleyMainActivityHandler.getInstance(), "Welcome back, " + currentUser.getFirstName() + "!", Toast.LENGTH_LONG).show();

                                                          //store the user info on login
                                                          SharedPreferences.Editor editor = mPreferences.edit();
                                                          Gson gson = new Gson();
                                                          String json = gson.toJson(currentUser);
                                                          editor.putString("currentUser", json);
                                                          editor.commit();
                                                      }
                                                      else {
                                                          Toast.makeText(VolleyMainActivityHandler.getInstance(), "Logged In Failed", Toast.LENGTH_LONG).show();
                                                      }
//                                                      hideProgressDialog();
                                                  }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                hideProgressDialog();
            }
        }
        );
        // Adding request to request queue
        VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);
        String reset= mPreferences.getString("reset","");
        if(reset.equals("1")){
            //redirect to a reset password page
            Intent resetPassword= new Intent(context, ResetPassword.class);
            context.startActivity(resetPassword);
//            finish();
        }
        //Get the role of the user and direct the user to the correct page depending on their role
        String roleID = mPreferences.getString("role", "");
        if (roleID.equals(Roles.TEACHER.toString())) {
            Intent teacherNotifications =
                    new Intent(context, TeacherNotifications.class);
            context.startActivity(teacherNotifications);
            //finsih this activity so you can't press back to go to the login screen after already logging in
//            finish();
        } else if (roleID.equals(Roles.STUDENT.toString())) {
            Intent studentNotifications =
                    new Intent(context, StudentNotifications.class);
            context.startActivity(studentNotifications);
            //finsih this activity so you can't press back to go to the login screen after already logging in
//            finish();
        }
    }

}
