package Utilities;

import java.util.ArrayList;

/**
 *
 * This is an object that holds information about the current user
 * @author sae1
 */

public class User {
    private String id, roleID, username, firstName, lastName, reset;
    ArrayList<Classes> classes;
    private boolean loggedIn;
    public User(String reset, String id, String roleID, String username, String firstName, String lastName, ArrayList<Classes> classes, boolean loggedIn) {
        this.id = id;
        this.reset=reset;
        this.roleID = roleID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classes=classes;
        this.loggedIn = loggedIn;

    }

    public String getReset(){
        //if it is a string of 0, then it doesn't need to have a password reset
        return reset;
    }
    public String getId() {
        return this.id;
    }
    public String getUsername() {
        return this.username;
    }
    public String getRoleId() {
        return this.roleID;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){ return this.lastName;}
    public boolean isLoggedIn(){return this.loggedIn;}
    public void logout(){this.loggedIn =false;}
    public ArrayList<Classes> getClasses(){return this.classes;}

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
                if(t.getID().equals(topicID)){
                    return t;
                }
            }
        }
        return null;
    }

    public Question getSingleQuestion(String questionID) {
        for (Classes c : classes) {
            for (Topics t : c.getTopics()) {
                for (Question q : t.getQuestions()) {
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
                for (Question q : t.getQuestions()){
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