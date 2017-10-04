package utils;

/**
 * Created by sae1 on 10/4/17.
 */

public class Reply {
    private String reply;
    private int student_rating;
    private boolean endorsed;


    public Reply(String reply, int rating, boolean endorsed){
        this.reply=reply;
        this.student_rating=rating;
        this.endorsed=endorsed;
    }

    public int getUpVotes(Reply r){
        return r.student_rating;
    }

    public String getQuestion(Reply r){
        return r.reply;
    }

    public boolean endorsed(Reply r){
        return r.endorsed;
    }
}
