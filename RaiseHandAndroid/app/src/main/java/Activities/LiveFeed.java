package Activities;

import android.app.ProgressDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sae1.raisehand.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import RecyclerViews.MyAdapterLiveFeed;
import Utilities.ActivitiesNames;
import Utilities.LiveFeedVolley;
import Utilities.NavUtil;

import static Utilities.URLS.URL_LIVE_FEED;

/**
 * Displays the live feed of a class.
 */
public class LiveFeed extends AppCompatActivity {
    private static String TAG = LiveFeedVolley.class.getSimpleName();
    private static String tag_string_req= "json_req";
    private JSONArray jArray;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<JSONObject> listItems;

    private NavigationView nv;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;

    private ProgressDialog pDialog;



    // TODO be able to choose the class
    // TODO submit questions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        LiveSessionVolley("7");
        //set up the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.liveFeedRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // list to hold the live feed questions
        listItems = new ArrayList<>();

        jArray = LiveFeedVolley.getJSON();
        System.out.println(jArray);

        if(null != jArray) {
            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject jObject = jArray.getJSONObject(i);
                    listItems.add(jObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            // Adapter to display the questions as recycler views. (cards on the screen)
            adapter = new MyAdapterLiveFeed(listItems, this);
            recyclerView.setAdapter(adapter);
        }

        // Get the nav menu
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        // create the drawer layout (the thing you swipe from the side)
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // add the menu items to the drawer
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ObjectMapper mapper = new ObjectMapper();


        System.out.println("JSON: " + jArray);

        // populate the navigation buttons to go to the correct place
        nv = (NavigationView) findViewById(R.id.nv1);
        NavUtil.setNavMenu(nv, ActivitiesNames.NONE, getApplicationContext(), mDrawerLayout);

    }

    private void showProgressDialog() {
        if(!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if(pDialog.isShowing()) {
            pDialog.hide();
            pDialog.dismiss();
        }
    }

    public JSONArray LiveSessionVolley(String classID){
        String url_final = URL_LIVE_FEED + "?class=" + classID;
        System.out.println(url_final);
        showProgressDialog();
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url_final, null,
                                                           new Response.Listener<JSONArray>() {
                                                               @Override
                                                               public void onResponse(JSONArray response) {
                                                                   Log.d(TAG, response.toString());

                                                                   try {
                                                                       // retrieves first JSON object in outer array
                                                                       JSONObject liveFeedObject = response.getJSONObject(0);

                                                                       // Retrieves the "result" array from the JSON object
//                                                          jArray = liveFeedObject.getJSONArray("result");
                                                                       jArray = response;


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
                                                                   hideProgressDialog();


                                                               }
                                                           }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                error.printStackTrace();
                hideProgressDialog();
            }
        }
        );

        VolleyMainActivityHandler.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
        return jArray;
    }

}
