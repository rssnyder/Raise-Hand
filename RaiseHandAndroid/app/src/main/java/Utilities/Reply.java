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
 *
 * This is an object, reply
 * It has the text that it contains, an amount of points, if it is endorsed or not,
 * a timestamp, a creation username, a userid, an id, a reply parent (for if it is a reply of a reply)
 * and a question that it was a reply to
 * @author sae1
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

    /**
     * Constructor for a reply
     * @param reply the text of the reply
     * @param rating the number of upvotes the reply has gotten
     * @param endorsed if the reply is endorsed or not
     * @param time_stamp the time the reply was created
     * @param username the username of the person who wrote the reply
     * @param userID the user id of the person who wrote the reply
     * @param q the parent question that this reply corresponds to
     * @param replyParent the reply parent that this reply corresponds to (if it is a reply of a reply)
     * @param replies a list of replies that this reply has to it
     */
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

    /**
     * Create a reply object from the android app
     */
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

    /**
     *
     * @return the number of upvotes this reply has
     */
    public String getReplyUpvotes(){
        return studentRating;
    }

    /**
     *
     * @return the reply text
     */
    public String getReply(){ return reply; }

    /**
     *
     * @return the creator's user id
     */
    public String getReplyUserID(){ return userID;}

    /**
     *
     * @return the creator's username
     */
    public String getReplyUsername(){ return username;}

    /**
     *
     * @return  true if it is endorsed, false otherwise
     */
    public boolean getReplyEndorsed(){
        return endorsed;
    }

    /**
     *
     * @return the time this reply was created
     */
    public String getReplyTimestamp(){
        return timestamp;
    }

    /**
     *
     * @return the question this reply is replying to
     */
    public Question getParentQuestion(){return this.parent;}

    /**
     *
     * @return the reply this reply is replying to (null if it is not a reply of a reply)
     */
    public String getReplyParent(){ return this.replyParent; }

    /**
     *
     * @return the reply's id
     */
    public String getReplyID(){ return this.replyID; }

    /**
     *
     * @return a list of replies made to this reply
     */
    public ArrayList<Reply> getReplies() { return replies; }

    /**
     *
     * @param time the time this reply was made
     */
    public void setReplyTimestamp(String time){ this.timestamp =time; }

    /**
     *
     * @param reply the text of the actual reply
     */
    public void setReply(String reply){ this.reply=reply; }

    /**
     *
     * @param rating the number of upvotes this question has
     */
    public void setReplyRating(String rating){ this.studentRating =rating; }

    /**
     *
     * @param userID the user id of the person who wrote the reply
     */
    public void setReplyUserID(String userID){ this.userID=userID; }

    /**
     *
     * @param username the username of the person who wrote the reply
     */
    public void setReplyUsername(String username){ this.username=username; }

    /**
     *
     * @param endorsed if it is endorsed or not
     */
    public void setReplyEndorsed(Boolean endorsed){this.endorsed=endorsed;}

    /**
     *
     * @param replyParent the reply's, that this reply is replying to, id
     */
    public void setReplyIDParent(String replyParent){ this.replyParent=replyParent; }

    /**
     * This is only used when initializing a reply after getting information from
     * android volley.
     * @param replyID this reply id
     */
    public void setReplyID(String replyID){ this.replyID=replyID; }

    /**
     *
     * @param q the question that this reply is replying to
     */
    public void setReplyQParent(Question q){this.parent=q;}

    /**
     *
     * @param replies a list of replies that this reply has to it
     */
    public void setReplies(ArrayList<Reply> replies) {
        this.replies = replies;
    }

    /**
     * Add the reply to a question or the reply to a reply to the database
     */
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

    /**
     * Increment the number of upvotes this reply has by one
     */
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

    /**
     * Endorse a reply
     */
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

    /**
     * Report this reply to an admin
     */
    public void report(){
        String url=URLS.URL_REPORTR+"?RID="+this.replyID;
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: reply flagged", Toast.LENGTH_LONG).show();
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
}
