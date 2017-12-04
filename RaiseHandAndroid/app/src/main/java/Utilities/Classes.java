package Utilities;

import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    private ArrayList<Question> notifications;

    /**
     * Constructor for a class
     * @param title title of class
     * @param classID class id
     */
    public Classes(String title, String classID){
        this.title=title;
        this.classID=classID;
        this.topics = new ArrayList<Topics>();
        this.notifications=new ArrayList<Question>();
    }

    /**
     * clear all of the topics that are in the class
     */
    public void emptyTopics(){
        topics.clear();
    }

    /**
     *
     * @return array list of topics in the class
     */
    public ArrayList<Topics> getTopics() {
        return topics;
    }

    /**
     * set the topics for the class
     * @param topics arraylist containing topic objects in the class
     */
    public void setTopics(ArrayList<Topics> topics) {
        this.topics = topics;
    }

    /**
     *
     * @return title of the class
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title set the title to the class
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the class id
     */
    public String getClassID() {
        return classID;
    }

    /**
     *  Only used when interpreting the android volley information
     * @param classID set the class id
     */
    public void setClassID(String classID) {
        this.classID = classID;
    }

    public void setNotification(ArrayList<Question> q){ this.notifications=q;}

    public ArrayList<Question> getNotifications(){return notifications;}
    /**
     * This interfaces with android volley and gets all of the topics that
     * relate to a class
     */
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
