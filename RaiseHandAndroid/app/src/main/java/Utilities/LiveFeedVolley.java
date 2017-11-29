package Utilities;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import Activities.VolleyMainActivityHandler;

import static Utilities.URLS.URL_LIVE_FEED;

/**
 * A Volley class that retrieves the live feed data in a JSON format.
 */
public class LiveFeedVolley {
    private static String TAG = LiveFeedVolley.class.getSimpleName();
    private static String tag_string_req= "json_req";
    private static JSONObject json;

    /**
     * retrieves the live feed comments for the classID. Puts them in a JSON object
     * @param classID ID of the class
     */
    public static void LiveSessionVolley(String classID){
        String url_final = URL_LIVE_FEED + "?class=" + classID;
        System.out.println(url_final);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url_final, null,
                                                             new Response.Listener<JSONObject>() {
                                                  @Override
                                                  public void onResponse(JSONObject response) {
                                                      Log.d(TAG, response.toString());

                                                      // The response is stored in a JSON object.
                                                      json = response;
                                                  }
                                              }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();            }
        });

        VolleyMainActivityHandler.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    /**
     * Getter for json file returned from volley
     * @return json object from volley
     */
    public static JSONObject getJSON(){
        return json;
    }
}
