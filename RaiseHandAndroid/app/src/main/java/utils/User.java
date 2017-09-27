package utils;

/**
 * Created by sae1 on 9/17/17.
 * We will need to add to this later, but it's an okay start.
 */

public class User {
    private String id, role_id, username, email, first_name, last_name, pass, classes;
    private boolean loggedIn;
    public User(String id, String role_id, String username, String first_name, String last_name, String classes, boolean logged_in) {
        this.id = id;
        this.role_id = role_id;
        this.username = username;
        this.first_name= first_name;
        this.last_name = last_name;
        this.classes=classes;
        loggedIn=logged_in;

    }

    public String getId() {
        return id;
    }
    public String getUsername() { return username; }
    public String getRoleId() {return role_id;}
    public String getFirst_name(){ return first_name;}
    public String getLast_name(){ return last_name;}
    public boolean isLoggedIn(){return loggedIn;}
    public void logOut(){loggedIn=false;}
    public String getClasses(){return classes;}


}