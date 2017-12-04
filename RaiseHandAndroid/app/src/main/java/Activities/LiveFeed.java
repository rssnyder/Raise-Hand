package Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import RecyclerViews.MyAdapterLiveFeed;
import Utilities.ActivitiesNames;
import Utilities.LiveFeedVolley;
import Utilities.NavUtil;
import static Utilities.URLS.URL_LIVE_FEED;
import static java.lang.Thread.interrupted;

/**
 * Displays the live feed of a class.
 */
public class LiveFeed extends AppCompatActivity {
    private static String TAG = LiveFeed.class.getSimpleName();
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
    private SwipeRefreshLayout swipeContainer;

    Thread thread;
    Handler handler;
    boolean stopThread = false;
    // TODO submit questions
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        LiveFeedVolley.clearJSONArray();
        if(null != jArray) {
            for (int i = 0; i < jArray.length(); i++) {
                jArray.remove(i);
            }
        }
        // Bundle gets the classID from the class the user clicked on in the TeacherClasses adapter (myAdapterClasses)
        Bundle bundle = getIntent().getExtras();
        final String classID = bundle.getString("classID");

        // FAB that makes a new question
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonLive);


        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        LiveSessionVolley(classID);

        //set up the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.liveFeedRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // list to hold the live feed questions
        listItems = new ArrayList<>();


        // Adapter to display the questions as recycler views. (cards on the screen)
        adapter = new MyAdapterLiveFeed(listItems, this);
        recyclerView.setAdapter(adapter);

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

        // Swipe to refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

//        System.out.println("JSON: " + jArray);

        // populate the navigation buttons to go to the correct place
        nv = (NavigationView) findViewById(R.id.nv1);
        NavUtil.setNavMenu(nv, ActivitiesNames.NONE, getApplicationContext(), mDrawerLayout);


        // This starts a new thread which looks for new updates on the live feed server.
        thread = new Thread(new MyThread());
        thread.start();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = new Bundle();
                // get the message data (new live feed messages)
                bundle = msg.getData();
                String list = bundle.getString("listString");
                Gson gson = new Gson();
                listItems = gson.fromJson(list, new TypeToken<List<JSONObject>>(){}.getType());

                // reset the recycler view and update the adapter with the new info
                recyclerView.setAdapter(new MyAdapterLiveFeed(listItems));
                recyclerView.invalidate();
                adapter.notifyDataSetChanged();
            }
        };

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeLiveFeedQuestion = new Intent(getApplicationContext(), MakeLiveFeedQuestion.class);
                makeLiveFeedQuestion.putExtra("classID", classID);
                startActivity(makeLiveFeedQuestion);
            }
        });
    }

    @Override
    protected void onDestroy() {
        //stop the background thread
        super.onDestroy();
        thread.interrupt();
        stopThread = true;
    }

    @Override
    protected void onPause() {
        //stop the background thread
        super.onPause();
        thread.interrupt();
        stopThread = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopThread = false;
    }

    @Override
    public void onBackPressed() {
        //stop the background thread
        super.onBackPressed();
        thread.interrupt();
        stopThread = true;
        LiveFeedVolley.clearJSONArray();
    }

    /**
     * This class checks for updates in the live feed.
     */
    class MyThread implements Runnable {
        @Override
        public void run(){
            boolean go = true;
            // stop your thread
            if (interrupted()) {
                go = false;
                return;
            }
            while (!stopThread){
                Message message = Message.obtain();
                int size = listItems.size();

                //sleep for .5 seconds
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Bundle gets the classID from the class the user clicked on in the TeacherClasses adapter (myAdapterClasses)
                Bundle bundle2 = getIntent().getExtras();
                String classID = bundle2.getString("classID");


                // get the live feed data
                jArray = LiveFeedVolley.LiveSessionVolley(classID);

                listItems.clear();
                if(null != jArray) {
                    for (int i = 0; i < jArray.length(); i++) {
                        try {
                            JSONObject jObject = jArray.getJSONObject(i);
                            listItems.add(jObject);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // if there's new data (the list is bigger than before)
                if (listItems.size() > size){
                    Collections.reverse(listItems);
                    //put the list in a string gson object
                    Gson gson = new Gson();
                    String listString = gson.toJson(listItems);
                    //put the gson in a bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("listString" , listString);
                    //add the bundle to the message
                    message.setData(bundle);
                    //send it to the main thread
                    handler.sendMessage(message);
                }

            }
        }
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
        final String url_final = URL_LIVE_FEED + "?class=" + classID;
        System.out.println(url_final);
        showProgressDialog();
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET, url_final, null,
                                                           new Response.Listener<JSONArray>() {
                                                               @Override
                                                               public void onResponse(JSONArray response) {
                                                                   Log.d(TAG, response.toString());

                                                                   // Retrieves the "result" array from the JSON object
                                                                   jArray = response;
                                                                   for (int i = 0; i < jArray.length(); i++) {
                                                                       try {
                                                                           JSONObject jObject = jArray.getJSONObject(i);
                                                                           listItems.add(jObject);

                                                                       } catch (JSONException e) {
                                                                           e.printStackTrace();
                                                                       }
                                                                   }
                                                                   Collections.reverse(listItems);
                                                                   adapter.notifyDataSetChanged();

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

    /**
     * if an item in the pull out menu is selected, navigate to a new page
     * @param item the list item that was selected
     * @return if the item is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
