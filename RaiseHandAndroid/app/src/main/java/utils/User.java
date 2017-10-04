package utils;

/**
 * Created by sae1 on 9/17/17.
 * We will need to add to this later, but it's an okay start.
 */

public class User {
    private String id, role_id, username, first_name, last_name, classes, reset;
    private boolean loggedIn;
    public User(String reset, String id, String role_id, String username, String first_name, String last_name, String classes, boolean logged_in) {
        this.id = id;
        this.reset=reset;
        this.role_id = role_id;
        this.username = username;
        this.first_name= first_name;
        this.last_name = last_name;
        this.classes=classes;
        loggedIn=logged_in;

    }
    public Boolean getReset(User u){
        //if it is a string of 0, then it doesn't need to have a password reset
        if(u.reset.equals("0"))
            return false;
        else
            return true;
    }
    public String getId(User u) {
        return u.id;
    }
    public String getUsername(User u) {
        return u.username;
    }
    public String getRoleId(User u) {
        return u.role_id;
    }
    public String getFirst_name(User u){
        return u.first_name;
    }
    public String getLast_name(User u){ return last_name;}
    public boolean isLoggedIn(User u){return loggedIn;}
    public void logOut(User u){loggedIn=false;}
    public String getClasses(User u){return u.classes;}

}