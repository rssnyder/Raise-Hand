package com.example.sae1.raisehand;

/**
 * Created by sae1 on 9/17/17.
 * We will need to add to this later, but it's an okay start.
 */

public class User {
    private int id, role_id;
    private String username, email, first_name, last_name, pass;

    public User(int id, int role_id, String username, String email, String first_name, String last_name, String pass) {
        this.id = id;
        this.role_id = role_id;
        this.username = username;
        this.email = email;
        this.first_name= first_name;
        this.last_name = last_name;
        this.pass=pass;
    }

    public int getId() {
        return id;
    }
    public String getUsername() { return username; }
    public int getRoleId() {return role_id;}
    public String getFirst_name(){ return first_name;}
    public String getLast_name(){ return last_name;}
    public String getEmail() {
        return email;
    }
    public String getPass() {return pass;}
}