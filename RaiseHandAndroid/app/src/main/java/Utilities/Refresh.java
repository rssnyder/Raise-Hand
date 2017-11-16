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
 * This is a class of methods to enable swipe down for request
 * @author sae1
 */

public class Refresh {
    private String TAG= Refresh.class.getSimpleName();
    private String tag_string_req= "string_req";
    /**
     * Given a parent topicID, the list of questions will be refreshed using android volley
     * @param parentTopicID
     * @return an array list of questions in this topic
     */
    public ArrayList<Question> refreshQuestions(String parentTopicID){
        String urlSuffix= "?topicId="+parentTopicID;
        String url_final= URLS.URL_REFRESHQ+urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        //setTopics(StringParse.parseTopicsVolley(phpResponse));
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
        return null;
    }

    /**
     * Given a parent question id, it will return a list of questions that directly correspond
     * to the topic (not replies to replies)
     * @param parentQuestionID
     * @return an array list of replies directly to a question
     */
    public ArrayList<Reply> refreshReplies(String parentQuestionID){
        String urlSuffix= "?questionId="+parentQuestionID;
        String url_final= URLS.URL_REFRESHR+urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        //setTopics(StringParse.parseTopicsVolley(phpResponse));
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
        return null;
    }

    /**
     * Given a parent reply id, it will return a list of replies to that reply
     * @param parentReplyID
     * @return an array list of replies to a reply
     */
    public ArrayList<Reply> refreshRepliesOfReplies(String parentReplyID){
        String urlSuffix= "?replyId="+parentReplyID;
        String url_final= URLS.URL_REFRESH_REPLIES_TO_REPLIES+urlSuffix;
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        //setTopics(StringParse.parseTopicsVolley(phpResponse));
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
        return null;
    }
}
