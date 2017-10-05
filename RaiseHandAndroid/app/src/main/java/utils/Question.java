package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question {

    // description of question
    private String questionDescription;

    // votes on questions
    private int studentRating;

    // Title of question
    private String questionTitle;

    // Time question was created
    private Date creationTime;

    // Array of replies to the
    private List<Reply> replies;

    // Owner ID
    private String ownerID;

    // Class ID that this question belongs to.
    private String classID;

    // University ID
    private String universityID;


    public Question(String questionDescription, int studentRating, String questionTitle, Date creationTime, List<Reply> replies, String ownerID, String classID, String universityID) {
        this.questionDescription = questionDescription;
        this.studentRating = studentRating;
        this.questionTitle = questionTitle;
        this.creationTime = creationTime;
        this.replies = replies;
        this.ownerID = ownerID;
        this.classID = classID;
        this.universityID = universityID;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public List<Reply> getReplies() {
        return replies;
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

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getUniversityID() {
        return universityID;
    }

    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    //Given a class, this method will return the questions from the database that have to do with that class
    public ArrayList<Question> get_question(int classID) {
        ArrayList<Question> q= new ArrayList<Question>();
        int existsQuestions;
        if(existsQuestions==1) {
            //todo: while loop to go through all of the questions
            String description, title, ownerID, universityID;
            int studentRating;
            Date creationTime;
            List<Reply> replies = new ArrayList<Reply>();
            int existReplies;
            //Return from the database information on whether or not there are replies to this question
            if (existReplies == 1) {
                //If there are replies, need to make a method that gets the information on all of the replies, similar to this question method
            }
            q.add(new Question(description, studentRating, title, creationTime, replies, ownerID, classID, universityID));
        }

        return q;
    }

    //Given a question, it will push this question to the database
    public void add_question_to_database(Question q){
        //TODO: add this question to the database
    }
}
