package utils;

import java.sql.Time;

/**
 * Created by sae1 on 10/4/17.
 */

public class Question {

    // description of question
    private String questionDescription;

    // votes on questions
    private int studentRating;

    // Title of question
    private String questionTitle;

    // Time question was created
    private Time creationTime;

    private Reply[] replies;


    public Question(String questionDescription, int studentRating, String questionTitle, Time creationTime) {
        this.questionDescription = questionDescription;
        this.studentRating = studentRating;
        this.questionTitle = questionTitle;
        this.creationTime = creationTime;
    }


    public String getQuestionDescription() {
        return questionDescription;
    }

    public int getStudentRating() {
        return studentRating;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public Time getCreationTime() {
        return creationTime;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public void setStudentRating(int studentRating) {
        this.studentRating = studentRating;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setCreationTime(Time creationTime) {
        this.creationTime = creationTime;
    }
}
