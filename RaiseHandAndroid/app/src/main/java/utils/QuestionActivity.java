package utils;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import android.app.ProgressDialog;
import app.MainActivity;


import org.json.JSONObject;

/**
 * This is used to originally get the Question information
 * Created by sae1 on 10/4/17.
 */

public class QuestionActivity {
    // Tag used to cancel the request
    private String TAG = QuestionActivity.class.getSimpleName();
    String tag_json_obj = "json_obj_req";
    String url = "https://api.androidhive.info/volley/person_object.json";

    private void getQ() {
        // Adding request to request queue
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
    }
}
