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
 *
 * This is an object, class
 * Each class has an array list of topics, a class id, and a title
 * @author sae1
 */

public class Classes {
    private String TAG= Classes.class.getSimpleName();
    private String tag_string_req= "string_req";
    private String title;
    private String classID;
    private static ArrayList<Topics> topics;

    public Classes(String title, String classID){
        this.title=title;
        this.classID=classID;
        this.topics = new ArrayList<Topics>();
    }

    public void emptyTopics(){
        topics.clear();
    }

    public ArrayList<Topics> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topics> topics) {
        this.topics = topics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public void get_topics() {
        String urlSuffix= "?classId="+classID;
        String url_final= URLS.URL_TOPICS+urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        emptyTopics();
                        System.out.println("IN ON RESPONSE");
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        setTopics(StringParse.parseTopicsVolley(phpResponse));
                        Log.d(TAG, "Size of topics: " + topics.size());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("IN ERROR RESPONSE");
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }
        );
        // Adding request to request queue
        Log.d(TAG, "Size of topics (outside of volley): " + topics.size());
        VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);
    }

}
