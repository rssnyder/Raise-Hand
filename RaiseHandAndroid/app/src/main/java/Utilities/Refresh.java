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
    private static String TAG= Refresh.class.getSimpleName();
    private static String tag_string_req= "string_req";
    private static ArrayList<Question> questions= new ArrayList<Question>();
    private static ArrayList<Reply> replies= new ArrayList<Reply>();
    /**
     * Given a parent topicID, the list of questions will be refreshed using android volley
     * @param parentTopic
     * @return an array list of questions in this topic
     */
    public ArrayList<Question> refreshQuestions(final Topics parentTopic){
        //TODO: Get all of the replies to this question as well
        String urlSuffix= "?topicId="+parentTopic.getID();
        String url_final= URLS.URL_REFRESHQ+urlSuffix;
        questions.clear();
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        questions=StringParse.parseQuestions(phpResponse, parentTopic);
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
        return questions;
    }



    /**
     * Given a parent reply id, it will return a list of replies to that reply
     * @param parentReply
     * @return an array list of replies to a reply
     */
    public ArrayList<Reply> refreshRepliesOfReplies(final Reply parentReply){
        String urlSuffix= "?replyId="+parentReply.getReplyID();
        String url_final= URLS.URL_REFRESH_REPLIES_TO_REPLIES+urlSuffix;
        replies.clear();
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        replies=StringParse.parseReplies(phpResponse, parentReply);
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
        return replies;
    }
}
