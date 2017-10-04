package utils;

/**
 * Created by sae1 on 10/4/17.
 */

public class Question {
    private String original_post;
    private int student_rating;

    public Question(String question, int rating){
        this.original_post=question;
        this.student_rating=rating;
    }

    public int getUpVotes(Question q){
        return this.student_rating;
    }

    public String getQuestion(Question q){
        return this.original_post;
    }
}
