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

    /**
     * Constructor a topic object
     * @param description description of the topic
     * @param title the title of the topic
     * @param id id that corresponds to the topic
     * @param time time that the topic was created
     * @param questions questions that fall under this topic
     */
    public Topics(String description, String title, String id, String time, ArrayList<Question> questions) {
        this.description=description;
        this.title = title;
        this.questions=questions;
        this.time=time;
        this.ID=id;
    }

    /**
     * Empty constructor that allows for addition of topics from the app
     */
    public Topics(){
        this.questions=new ArrayList<Question>();
        this.title=null;
        this.time=null;
        this.description=null;
        this.ID=null;
    }


    /**
     *
     * @return the description of the topic
     */
    public String getDescription(){
        return description;
    }

    /**
     *
     * @return the title of the topic
     */
    public String getTitle(){
        return title;
    }

    /**
     *
     * @return the time the topic was created
     */
    public String getTime(){ return time;}

    /**
     *
     * @return the id that corresponds to the topic
     */
    public String getID(){return ID;}

    /**
     *
     * @return an array list of the questions that go with this topic
     */
    public ArrayList<Question> getQuestions(){
        return questions;
    }

    /**
     *
     * @param des set the description of the topic
     */
    public void setDescription(String des){
        this.description=des;
    }

    /**
     *
     * @param title set the title of the topic
     */
    public void setTitle(String title){
        this.title=title;
    }

    /**
     *
     * @param time set the time that this topic was made
     */
    public void setTime(String time){this.time=time;}

    /**
     * Only to be used when parsing through the response given by
     * android volley
     * @param ID set the id of the topic
     */
    public void setID(String ID){ this.ID=ID; }

    /**
     *
     * @param q the questions underneath this topic
     */
    public void setQuestions(ArrayList<Question> q){
        this.questions=q;
    }

}
