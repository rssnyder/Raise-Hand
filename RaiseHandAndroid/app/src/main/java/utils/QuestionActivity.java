package utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sae1.raisehand.R;
import android.app.ProgressDialog;
import android.widget.EditText;
import android.widget.TextView;

import app.MainActivity;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * This is used to originally get the Question information
 * Created by sae1 on 10/4/17.
 */

public class QuestionActivity extends Activity {
    // Tag used to cancel the request
    private String TAG = QuestionActivity.class.getSimpleName();
    String tag_json_obj = "json_obj_req";
    String url = "https://api.androidhive.info/volley/person_object.json";
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        result = findViewById(R.id.textView8);
        get_question();

    }
    public static void main(String[] args){
        // Adding request to request queue
        String url = "https://api.androidhive.info/volley/person_object.json";
        String tag_json_obj = "json_obj_req";
        final String TAG = "QuestionActivity";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        MainActivity.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        Cache cache = MainActivity.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                System.out.println(data);
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{
            // Cached response doesn't exists. Make network call here
        }
    }







    public String get_question() {
        // Adding request to request queue
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("university_id", "1");
                params.put("class_id", "1");
                return params;
            }};
        MainActivity.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        Cache cache = MainActivity.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                result.setText(data);
                return data;
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{
        // Cached response doesn't exists. Make network call here
        }
        return null;
    }
}
