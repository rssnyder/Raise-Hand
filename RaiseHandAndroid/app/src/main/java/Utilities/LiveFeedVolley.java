package Utilities;


import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import Activities.VolleyMainActivityHandler;

import static Utilities.URLS.URL_LIVE_FEED;

/**
 * A Volley class that retrieves the live feed data in a JSON format.
 */
public class LiveFeedVolley {
    private static String TAG = LiveFeedVolley.class.getSimpleName();
    private static String tag_json_req = "json_req";
    private static String tag_string_req = "string_req";
    private static JSONArray jArray;

    /**
     * retrieves the live feed comments for the classID. Puts them in a JSON object
     * @param classID ID of the class
     */
    public static JSONArray LiveSessionVolley(String classID){
        String url_final = URL_LIVE_FEED + "?class=" + classID;
//        System.out.println(url_final);
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url_final, null,
                                                            new Response.Listener<JSONArray>() {
                                                  @Override
                                                  public void onResponse(JSONArray response) {
                                                      Log.d(TAG, response.toString());

                                                          // Retrieves the "result" array from the JSON object
                                                          jArray = response;

                                                  }
                                              }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();            }
        });

        VolleyMainActivityHandler.getInstance().addToRequestQueue(jsonObjReq, tag_json_req);
        return jArray;
    }

    /**
     * Getter for json file returned from volley
     * @return json object from volley
     */
    public static JSONArray getJSON(){
        return jArray;
    }

    public static void clearJSONArray(){
        if(null != jArray) {
            for (int i = 0; i < jArray.length(); i++) {
                jArray.remove(i);
            }
        }
    }

    public static void addToLiveFeed(String classID, String username, String comment){
        //replace spaces with +s
        comment = comment.replaceAll(" ","+");
        System.out.println(comment);
        String url = URLS.URL_SUMBIT_LIVE_FEED+"?class="+classID +"&username="+username +"&comment="+comment;
        Log.d("URL", url);
        StringRequest req2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, response.toString());
                String phpResponse = response.toString();

                if (phpResponse.contains("Done")) {
                    Toast.makeText(VolleyMainActivityHandler.getInstance(), "Success: question added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(VolleyMainActivityHandler.getInstance(), "No Live Session currently", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        VolleyMainActivityHandler.getInstance().addToRequestQueue(req2, tag_string_req);
    }
}
