package utils;

import java.sql.Time;

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

    public Reply(String reply, String rating, boolean endorsed, String time_stamp, String username, String userID){
        this.reply=reply;
        this.student_rating=rating;
        this.endorsed=endorsed;
        this.time_stamp=time_stamp;
        this.username=username;
        this.userID=userID;
    }

    public Reply(){
        this.reply=null;
        this.student_rating="0";
        this.endorsed=false;
        this.time_stamp=null;
        this.username=null;
        this.userID=null;
    }

    public String get_up_votes(Reply r){
        return r.student_rating;
    }

    public String get_reply(Reply r){ return r.reply; }

    public String get_userID(Reply r){ return r.userID;}

    public String get_username(Reply r){ return r.username;}

    public boolean get_endorsed(Reply r){
        return r.endorsed;
    }

    public String get_time_stamp(Reply r){
        return r.time_stamp;
    }

    public void set_time(Reply r, String time){ r.time_stamp=time; }

    public void set_reply(Reply r, String reply){ r.reply=reply; }

    public void set_rating(Reply r, String rating){ r.student_rating=rating; }

    public void set_userID(Reply r, String userID){ r.userID=userID; }

    public void set_username(Reply r, String username){ r.username=username; }

    public void set_endorsed(Reply r, Boolean endorsed){r.endorsed=endorsed;}

    public void add_to_database(Reply r, Question q){
     //TODO: add a method to push information to the database on a new reply written in the app
    }
}
