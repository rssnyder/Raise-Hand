package Utilities;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import Activities.VolleyMainActivityHandler;

/**
 * This is used to get recent activity to make notifications
 * @author sae1
 */

public class RecentActivity {
    private static String TAG= RecentActivity.class.getSimpleName();
    private static String tag_string_req= "string_req";
    private ArrayList<Question> recentQuestions;

    public RecentActivity(){

    }
    /**
     * This method will return an array list of questions that were recently posted in
     * his or her classes. It will return an empty array list if he or she is not in any classes
     * or if there is no recent activity
     * @param userClasses the classes the user is currently in
     * @return an array list of questions that were recently posted in his or her classes
     */
    public ArrayList<Question> getNotifications(ArrayList<Classes> userClasses){
        recentQuestions=new ArrayList<Question>();
        String urlSuffix="";
        if(!userClasses.isEmpty()) {
           urlSuffix = "?classId=" + userClasses.get(0).getClassID();
        }
        for(int i=1; i<userClasses.size()-1; i++) {
            urlSuffix+="+"+userClasses.get(i).getClassID();

        }
        if(urlSuffix.isEmpty()){
            //this means the student is not in any classes
            return recentQuestions;
        }
        String url_final = URLS.URL_NOTIFICATIONS + urlSuffix;
        System.out.println(url_final);
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        recentQuestions=StringParse.parseQuestions(phpResponse);
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
        return recentQuestions;

    }
}
