package utils;

import java.util.ArrayList;

/**
 * Created by sae1 on 10/6/17.
 */

public class Topics {
    // description of question
    private String description;

    // Title of question
    private String title;

    //Array list of the questions in this topic
    private ArrayList<Question> questions;



    public Topics(String description, String title, String class_id, ArrayList<Question> questions) {
        this.description=description;
        this.title = title;
        this.questions=questions;
    }

    public String get_description(Topics t){
        return description;
    }

    public String get_title(Topics t){
        return title;
    }

    public ArrayList<Question> get_questions(Topics t){
        return questions;
    }

    public void set_description(Topics t, String des){
        this.description=des;
    }

    public void set_title(Topics t, String title){
        this.title=title;
    }

    public void get_questions(Topics t, ArrayList<Question> q){
        this.questions=q;
    }
}
