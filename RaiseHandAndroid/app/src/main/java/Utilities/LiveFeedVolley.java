package Utilities;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Activities.VolleyMainActivityHandler;

import static Utilities.URLS.URL_LIVE_FEED;

/**
 * A Volley class that retrieves the live feed data in a JSON format.
 */
public class LiveFeedVolley {
    private static String TAG = LiveFeedVolley.class.getSimpleName();
    private static String tag_string_req= "json_req";
    private static JSONArray jArray;

    /**
     * retrieves the live feed comments for the classID. Puts them in a JSON object
     * @param classID ID of the class
     */
    public static JSONArray LiveSessionVolley(String classID){
        String url_final = URL_LIVE_FEED + "?class=" + classID;
        System.out.println(url_final);
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url_final, null,
                                                            new Response.Listener<JSONArray>() {
                                                  @Override
                                                  public void onResponse(JSONArray response) {
                                                      Log.d(TAG, response.toString());

                                                      try {
                                                          // retrieves first JSON object in outer array
                                                          JSONObject liveFeedObject = response.getJSONObject(0);

                                                          // Retrieves the "result" array from the JSON object
                                                          jArray = liveFeedObject.getJSONArray("result");


//                                                          // iterates through the JSON array getting objects and adding them
//                                                          // to the list view until there are no more objects in the array
//                                                          for(int i = 0; i < result.length(); i++){
//                                                              //gets each JSON onject within the JSON array
//                                                              JSONObject jsonObject = result.getJSONObject(i);
//
//                                                              String text = jsonObject.getString("txt");
//                                                              String username = jsonObject.getString("username");
//
//                                                          }
                                                      } catch (JSONException e) {
                                                          e.printStackTrace();
                                                      }

                                                  }
                                              }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();            }
        });

        VolleyMainActivityHandler.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
        return jArray;
    }

    /**
     * Getter for json file returned from volley
     * @return json object from volley
     */
    public static JSONArray getJSON(){
        return jArray;
    }
}
