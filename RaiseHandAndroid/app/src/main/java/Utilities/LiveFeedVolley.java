package Utilities;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import Activities.VolleyMainActivityHandler;

import static Utilities.URLS.URL_LIVE_FEED;

public class LiveFeedVolley {
    private static String TAG = LiveFeedVolley.class.getSimpleName();
    private static String tag_string_req= "string_req";
    private static JSONObject json;

    public static void LiveSessionVolley(String classID){
        String url_final = URL_LIVE_FEED + "?class=" + classID;
        System.out.println(url_final);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url_final, null,
                                                             new Response.Listener<JSONObject>() {
                                                  @Override
                                                  public void onResponse(JSONObject response) {
                                                      Log.d(TAG, response.toString());
//                                                      try{

                                                          json = response;
//                                                            String username = response.getString("username");
//                                                            String ID = response.getString("ID");
//                                                            String classID = response.getString("class_id");
//                                                            String date = response.getString("creation");
//                                                            String text = response.getString("txt");
//
//                                                            System.out.println(username+
//                                                                              ID+
//                                                                              classID+
//                                                                              date+
//                                                                              text);
//                                                      }
//                                                      catch (JSONException e) {
//                                                          e.printStackTrace();
//                                                      }
                                                  }
                                              }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();            }
        });

        VolleyMainActivityHandler.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    public static JSONObject getJSON(){
        return json;
    }

    public static void parseString(String phpResponse){
        String[] seperated = phpResponse.split("\",\"");
    }
}
