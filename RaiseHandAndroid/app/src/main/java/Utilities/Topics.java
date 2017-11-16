package Utilities;

import java.util.ArrayList;

/**
 *
 * This is an object, Topics
 * It has a title, a creation time, an id, a description, and an array list of questions
 * @author sae1
 */

public class Topics {
    //Needed to help track errors with android volley
    private String TAG= Topics.class.getSimpleName();

    // description of question
    private String description;

    // Title of question
    private String title;

    private String ID;

    private String time;
    //Array list of the questions in this topic
    private ArrayList<Question> questions;

    public Topics(String description, String title, String id, String time, ArrayList<Question> questions) {
        this.description=description;
        this.title = title;
        this.questions=questions;
        this.time=time;
        this.ID=id;
    }

    public Topics(){
        this.questions=new ArrayList<Question>();
        this.title=null;
        this.time=null;
        this.description=null;
        this.ID=null;
    }


    public String getDescription(){
        return description;
    }

    public String getTitle(){
        return title;
    }

    public String getTime(){ return time;}

    public String getID(){return ID;}

    public ArrayList<Question> getQuestions(){
        return questions;
    }

    public void setDescription(String des){
        this.description=des;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setTime(String time){this.time=time;}

    //DO NOT USE THIS- only to be used when important data
    public void setID(String ID){ this.ID=ID; }

    public void setQuestions(ArrayList<Question> q){
        this.questions=q;
    }
    //Given a class, this method will return the questions from the database that have to do with that class

    //Given a topic, it will push this question to the database
    public void addTopicToDatabase(){
        //TODO: add this question to the database
    }
}
