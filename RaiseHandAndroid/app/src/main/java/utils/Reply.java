package utils;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.sql.Time;

import app.MainActivity;

/**
 * Created by sae1 on 10/4/17.
 */

public class Reply {
    //What the student says
    private String reply;

    //How many people upvoted this reply
    private String student_rating;

    //If the teacher endorsed the answer
    private boolean endorsed;

    //Time this reply was made
    private String time_stamp;

    //authors username
    private String username;

    //authors user ID
    private String userID;

    //Question this reply falls under
    private Question parent;

    public String replyID;

    //Reply id if this a reply of a reply
    private String replyParent;
    private String tag_string_req="reply_req";
    public static final String TAG= Reply.class.getSimpleName();

    public Reply(String reply, String rating, boolean endorsed, String time_stamp, String username, String userID, Question q, String replyParent){
        this.reply=reply;
        this.student_rating=rating;
        this.endorsed=endorsed;
        this.time_stamp=time_stamp;
        this.username=username;
        this.userID=userID;
        this.parent=q;
        this.replyParent=replyParent;
    }

    public Reply(){
        this.reply=null;
        this.student_rating="0";
        this.endorsed=false;
        this.time_stamp=null;
        this.username=null;
        this.userID=null;
        this.replyParent=null;
        this.replyID=null;
    }

    public String get_reply_up_votes(){
        return student_rating;
    }

    public String get_reply(){ return reply; }

    public String get_reply_userID(){ return userID;}

    public String get_reply_username(){ return username;}

    public boolean get_reply_endorsed(){
        return endorsed;
    }

    public String get_reply_time_stamp(){
        return time_stamp;
    }

    public Question get_parent_question(){return this.parent;}

    public String getReplyParent(){ return this.replyParent; }

    public String get_replyID(){ return this.replyID; }

    public void set_reply_time(String time){ this.time_stamp=time; }

    public void set_reply(String reply){ this.reply=reply; }

    public void set_reply_rating(String rating){ this.student_rating=rating; }

    public void set_reply_userID(String userID){ this.userID=userID; }

    public void set_reply_username(String username){ this.username=username; }

    public void set_reply_endorsed(Boolean endorsed){this.endorsed=endorsed;}

    public void set_replyParent(String replyParent){ this.replyParent=replyParent; }

    public void set_replyID(String replyID){ this.replyID=replyID; }


    //TODO: Update to enable the reply to be a reply
    public void add_to_database(){
        //Description
        String reply2=this.reply;
        //encoding spaces with a + sign for the url
        reply2=reply2.replaceAll(" ","+");
        String url=URLS.URL_REPLY+"?txt="+reply2+"&username="+this.username+"&OID="+this.userID+"&TID="+parent.getQuestionID();
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(MainActivity.getInstance(), "Success: reply added", Toast.LENGTH_LONG).show();
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

    public void upVote(){
        String url=URLS.URL_UPVOTER+"?RID="+this.replyID;
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(MainActivity.getInstance(), "Success: upvote completed", Toast.LENGTH_LONG).show();
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
