package utils;

import java.util.ArrayList;

/**
 * Created by sae1 on 9/17/17.
 * We will need to add to this later, but it's an okay start.
 */

public class User {
    private String id, role_id, username, first_name, last_name, reset;
    ArrayList<Classes> classes;
    private boolean logged_in;
    public User(String reset, String id, String role_id, String username, String first_name, String last_name, ArrayList<Classes> classes, boolean logged_in) {
        this.id = id;
        this.reset=reset;
        this.role_id = role_id;
        this.username = username;
        this.first_name= first_name;
        this.last_name = last_name;
        this.classes=classes;
        this.logged_in=logged_in;

    }
    public Boolean getReset(User u){
        //if it is a string of 0, then it doesn't need to have a password reset
        if(u.reset.equals("0"))
            return false;
        else
            return true;
    }
    public String getId() {
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }
    public String getRoleId() {
        return this.role_id;
    }
    public String getFirst_name(){
        return this.first_name;
    }
    public String get_last_name(){ return this.last_name;}
    public boolean is_logged_in(){return this.logged_in;}
    public void logout(){this.logged_in=false;}
    public ArrayList<Classes> get_classes(){return this.classes;}

    /**
     * returns the class matching the classID.
     * @param classID String of class ID
     * @return a class (Classes object), null otherwise
     */
    public Classes getSingleClass(String classID){
        for(Classes c : classes){
            if(c.getClassID().equals(classID)){
                return c;
            }
        }
        return null;
    }

    /**
     * returns the topic matching the topic ID
     * @param topicID Strings of topic ID
     * @return Topics, null otherwise
     */
    public Topics getSingleTopic(String topicID){
        for(Classes c : classes){
            for(Topics t : c.getTopics()){
                if(t.get_ID().equals(topicID)){
                    return t;
                }
            }
        }
        return null;
    }

    public Question getSingleQuestion(String questionID) {
        for (Classes c : classes) {
            for (Topics t : c.getTopics()) {
                for (Question q : t.get_questions()) {
                    if(q.getQuestionID().equals(questionID)){
                        return q;
                    }
                }
            }
        }
        return null;
    }

    public Reply getSingleReply(String replyID) {
        for (Classes c : classes) {
            for (Topics t : c.getTopics()) {
                for (Question q : t.get_questions()){
                    for (Reply r : q.getReplies()) {
                        if (r.getReplyID().equals(replyID)){
                            return r;
                        }
                    }
                }
            }
        }
        return null;
    }

}