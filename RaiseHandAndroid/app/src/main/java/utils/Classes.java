package utils;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Scanner;

import app.MainActivity;

/**
 * Created by sae1 on 10/9/17.
 */

public class Classes {

    //need to add in the title thing
    private String title;
    private String classID;
    private ArrayList<Topics> topics;

    public Classes(String title, String classID){
        this.title=title;
        this.classID=classID;
        this.topics = new ArrayList<Topics>();
    }
    
    public ArrayList<Topics> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topics> topics) {
        this.topics = topics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
