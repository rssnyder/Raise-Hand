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

    public void set_reply_time(String time){ this.time_stamp=time; }

    public void set_reply(String reply){ this.reply=reply; }

    public void set_reply_rating(String rating){ this.student_rating=rating; }

    public void set_reply_userID(String userID){ this.userID=userID; }

    public void set_reply_username(String username){ this.username=username; }

    public void set_reply_endorsed(Boolean endorsed){this.endorsed=endorsed;}

    public void add_to_database(Question q){
     //TODO: add a method to push information to the database on a new reply written in the app
    }
}
