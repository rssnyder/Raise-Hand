package Utilities;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import Activities.VolleyMainActivityHandler;

/**
 * @author sae1
 * This is an object, reply
 * It has the text that it contains, an amount of points, if it is endorsed or not,
 * a timestamp, a creation username, a userid, an id, a reply parent (for if it is a reply of a reply)
 * and a question that it was a reply to
 *
 */

public class Reply {
    //What the student says
    private String reply;

    //How many people upvoted this reply
    private String studentRating;

    //If the teacher endorsed the answer
    private boolean endorsed;

    //Time this reply was made
    private String timestamp;

    //authors username
    private String username;

    //authors user ID
    private String userID;

    //Question this reply falls under
    private Question parent;

    public String replyID;

    //Reply id if this a reply of a reply
    private String replyParent;

    //Array of replies to the reply
    private ArrayList<Reply> replies;

    private String tag_string_req="reply_req";
    public static final String TAG= Reply.class.getSimpleName();

    public Reply(String reply, String rating, boolean endorsed, String time_stamp, String username, String userID, Question q, String replyParent, ArrayList<Reply> replies){
        this.reply=reply;
        this.studentRating =rating;
        this.endorsed=endorsed;
        this.timestamp =time_stamp;
        this.username=username;
        this.userID=userID;
        this.parent=q;
        this.replyParent=replyParent;
        this.replies = replies;
    }

    public Reply(){
        this.reply=null;
        this.studentRating ="0";
        this.endorsed=false;
        this.timestamp =null;
        this.username=null;
        this.userID=null;
        this.replyParent=null;
        this.replyID=null;
        this.replies = new ArrayList<>();
    }

    public String getReplyUpvotes(){
        return studentRating;
    }

    public String getReply(){ return reply; }

    public String getReplyUserID(){ return userID;}

    public String getReplyUsername(){ return username;}

    //if this returns false then it isn't endorsed
    public boolean getReplyEndorsed(){
        return endorsed;
    }

    public String getReplyTimestamp(){
        return timestamp;
    }

    public Question getParentQuestion(){return this.parent;}

    //if this returns null then it isn't a reply of a reply
    public String getReplyParent(){ return this.replyParent; }

    public String getReplyID(){ return this.replyID; }

    public ArrayList<Reply> getReplies() {


        return replies;
    }

    public void setReplyTimestamp(String time){ this.timestamp =time; }

    public void setReply(String reply){ this.reply=reply; }

    public void setReplyRating(String rating){ this.studentRating =rating; }

    public void setReplyUserID(String userID){ this.userID=userID; }

    public void setReplyUsername(String username){ this.username=username; }

    public void setReplyEndorsed(Boolean endorsed){this.endorsed=endorsed;}

    public void setReplyIDParent(String replyParent){ this.replyParent=replyParent; }

    public void setReplyID(String replyID){ this.replyID=replyID; }

    public void setReplyQParent(Question q){this.parent=q;}

    public void setReplies(ArrayList<Reply> replies) {
        this.replies = replies;
    }

    public void addToDatabase(){
        //Description
        String reply2=this.reply;
        //encoding spaces with a + sign for the url
        reply2=reply2.replaceAll(" ","+");
        String url="";
        if(this.replyParent!=null){
            url = URLS.URL_REPLY + "?txt=" + reply2 + "&username=" + this.username + "&OID=" + this.userID + "&TID=0" + "&replyParent=" + this.getReplyParent();
        }
        else {
            url=URLS.URL_REPLY+"?txt="+reply2+"&username="+this.username+"&OID="+this.userID+"&TID="+parent.getQuestionID()+"&replyParent=0";
        }
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: reply added", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: upvote completed", Toast.LENGTH_LONG).show();
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
    }
    public void endorse(){
        String url=URLS.URL_ENDORSEMENT+"?RID="+this.replyID;
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: endorsement completed", Toast.LENGTH_LONG).show();
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
    }

    public void report(){

    }
}
