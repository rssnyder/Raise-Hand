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
 * This is an object, question
 * It has the text that it contains, an amount of points, if it is endorsed or not,
 * a timestamp, a creation username, a userid, an id, the topic it belongs to
 * @author sae1
 */

public class Question {

    // description of question
    private String questionDescription;

    // votes on questions
    private String studentRating;

    // Title of question
    private String questionTitle;

    // Time question was created
    private String creationTime;

    // Array of replies to the
    private ArrayList<Reply> replies;

    // Owner ID
    private String ownerID;

    //username of creator
    private String username;

    //if question has been endorsed
    private Boolean endorsed;

    //Topic the quesiton falls into
    private Topics parent;

    //ID set by the database- never set this yourself
    private String questionID;
    //For inserting into the database these are needed
    private String TAG= Question.class.getSimpleName();
    private String tag_string_req= "question_req";


    /**
     * Constructor for a question object
     * @param questionDescription the actual quesiton text
     * @param studentRating the number of upvotes it has
     * @param questionTitle the title of the question
     * @param creationTime when the question was made
     * @param replies the replies that relate to a question
     * @param ownerID the user id of the person who wrote the question
     * @param username the username of the person who wrote the question
     * @param endorsed if the question is endorsed by a teacher or TA
     * @param parent the topic that it corresponds to
     * @param questionID the id of this quesiton
     */
    public Question(String questionDescription, String studentRating, String questionTitle, String creationTime,
                    ArrayList<Reply> replies, String ownerID, String username, Boolean endorsed, Topics parent, String questionID) {
        this.questionDescription = questionDescription;
        this.studentRating = studentRating;
        this.questionTitle = questionTitle;
        this.creationTime = creationTime;
        this.replies = replies;
        this.ownerID = ownerID;
        this.username=username;
        this.endorsed=endorsed;
        this.parent=parent;
        this.questionID=questionID;
    }

    /**
     * A constructor to make a new question from the app
     */
    public Question(){
        this.questionDescription = null;
        this.studentRating = "0";
        this.questionTitle = null;
        this.creationTime = null;
        this.replies = new ArrayList<Reply>();
        this.ownerID = null;
        this.username=null;
        this.endorsed=false;
        this.questionID=null;
    }

    /**
     *
     * @return question id
     */
    public String getQuestionID(){ return questionID; }

    /**
     *
     * @return true if it is endorsed, false otherwise
    */
    public Boolean questionEndorsemenet(){ return endorsed; }

    /**
     *
     * @return username of the person who created the question
     */
    public String getQuestionUsername(){ return username; }

    /**
     *
     * @return the actual question text
     */
    public String getQuestionDescription() {
        return questionDescription;
    }

    /**
     *
     * @return the number of upvotes that a question has
     */
    public String getStudentRating() {
        return studentRating;
    }

    /**
     *
     * @return title of question
     */
    public String getQuestionTitle() {
        return questionTitle;
    }

    /**
     *
     * @return when the question was created
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     *
     * @return the replies to this question
     */
    public ArrayList<Reply> getReplies() {
        return replies;
    }

    /**
     *
     * @return the replies to this quesiton that are NOT replies of replies
     */
    public ArrayList<Reply> getParentRepliesOnly() {
        ArrayList<Reply> result= new ArrayList<Reply>();
        for(Reply r: replies){
            if (r.getReplyParent()==null){
                result.add(r);
            }
        }
        return result;
    }

    /**
     *
     * @return the user id of the person who created this question
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     *
     * @return the topic this question corresponds to
     */
    public Topics getParent(){ return parent; }

    /**
     * set the question text
     * @param questionDescription the text of the question
     */
    public void setQuestionDescription(String questionDescription) { this.questionDescription = questionDescription; }

    /**
     * set the student rating
     * @param studentRating how many upvotes the question has
     */
    public void setStudentRating(String studentRating) { this.studentRating = studentRating; }

    /**
     * set the question's creator's username
     * @param username the person's username who created the question
     */
    public void setQuestionUsername(String username){this.username=username;}

    /**
     * set if the question is endoresed or not
     * @param endorsed if the question is endorsed or not
     */
    public void setEndorsed(Boolean endorsed){this.endorsed=endorsed; }

    /**
     * set the question title
     * @param questionTitle question title
     */
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    /**
     * set when the question was made
     * @param creationTime when the question was made
     */
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * set the list of replies that a question has
     * @param replies replies to the question
     */
    public void setReplies(ArrayList<Reply> replies) {
        this.replies = replies;
    }

    /**
     * the creator's user id
     * @param ownerID the person's, who created the question, id
     */
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * set the topic that this question is nested under
     * @param t the topic that the question corresponds to
     */
    public void setParent(Topics t){this.parent=t;}

    /**
     * set the question id, only used when interpretting android volley's response
     * @param s the question id pulled from the database
     */
    public void setQuestionID(String s){this.questionID=s;}

    /**
     * add a reply to the already existing list of replies
     * @param r the reply to be added to the list of replies
     */
    public void addReply(Reply r){replies.add(r);}

    /**
     * Given a question, it will push this question to the database
     *
     */
    public void addQuestionToDatabase(){
        //Description
        String desc=this.getQuestionDescription();
        //encoding spaces with a + sign for the url
        desc=desc.replaceAll(" ","+");
        //Title
        String title=this.getQuestionTitle();
        title=title.replaceAll(" ","+");
        //Owner ID
        String OID=this.getOwnerID();

        //Username
        String username=this.username;
        String url=URLS.URL_QUESTIONS+"?desc="+desc+"&title="+title+"&OID="+OID+"&username="+username+"&TID="+parent.getID();
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: question added", Toast.LENGTH_LONG).show();
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
     * Increment the number of upvotes of this question by 1
     */
    public void upVote(){
        String url=URLS.URL_UPVOTE+"?QID="+this.questionID;
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
     * Make this question endorsed
     */
    public void endorse(){
        String url=URLS.URL_ENDORSEMENTQ+"?QID="+this.questionID;
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
     * Report the question to the admin
     */
    public void report(){
        String url=URLS.URL_REPORTQ+"?QID="+this.questionID;
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        //in the php file, the user information is stored in an array with : as a delimiter between the variable name and actual value
                        if (phpResponse.contains("Done")) {
                            Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: Question flagged", Toast.LENGTH_LONG).show();
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
