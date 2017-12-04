package Teacher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Activities.VolleyMainActivityHandler;
import RecyclerViews.ListItemTeacherNotifications;
import RecyclerViews.MyAdapterNotifications;
import RecyclerViews.MyAdapterQuestions;
import Utilities.ActivitiesNames;
import Utilities.Classes;
import Utilities.NavUtil;
import Utilities.Question;
import Utilities.StringParse;
import Utilities.URLS;
import Utilities.User;

/**
 *
 * This class has notifications for the teacher.
 * Notifications will include recent questions and replies submitted.
 * @author sae1
 */
public class TeacherNotifications extends Activity {
    private static String TAG= TeacherNotifications.class.getSimpleName();
    private static String tag_string_req= "string_req";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Question> listItems;

    private User currentUser;
    private SharedPreferences mPreferences;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private ProgressDialog pDialog;
    private Field mDragger;
    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, as
     * well as the notifications a teacher has
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_questions);
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        listItems=new ArrayList<Question>();
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);
        //getNotifications(currentUser.getClasses());
        for(Classes c: currentUser.getClasses()){
            listItems.addAll(c.getNotifications());
            System.out.println(c.getNotifications().size());
        }
        // Adapter to display the questions as recycler views. (cards on the screen)
        adapter = new MyAdapterQuestions(listItems,this);
        // Setting up the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.questionsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Get the nav menu
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        //setSupportActionBar(mToolbar);

        // create the drawer layout (the thing you swipe from the side)
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // add the menu items to the drawer
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // populate the navigation buttons to go to the correct place
        nv = (NavigationView) findViewById(R.id.nv1);
        NavUtil.setNavMenu(nv, ActivitiesNames.NONE, getApplicationContext(), mDrawerLayout);

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
    /**
     * This method will return an array list of questions that were recently posted in
     * his or her classes. It will return an empty array list if he or she is not in any classes
     * or if there is no recent activity
     * @param userClasses the classes the user is currently in
     * @return an array list of questions that were recently posted in his or her classes
     */
    public void getNotifications(ArrayList<Classes> userClasses){
        showProgressDialog();
        listItems=new ArrayList<Question>();
        String urlSuffix="";
        if(!userClasses.isEmpty()) {
            urlSuffix = "?classId=" + userClasses.get(0).getClassID();
        }
        for(int i=1; i<userClasses.size()-1; i++) {
            urlSuffix+=","+userClasses.get(i).getClassID();

        }
        if(urlSuffix.isEmpty()){
            //this means the student is not in any classes
            SharedPreferences.Editor editor = mPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(listItems);
            editor.putString("notifications", json);
            editor.commit();
            return;
        }
        String url_final = URLS.URL_NOTIFICATIONS + urlSuffix;
        System.out.println(url_final);

        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse=response.toString();
                        listItems= StringParse.parseQuestions(phpResponse);
                        SharedPreferences.Editor editor = mPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(listItems);
                        editor.putString("notifications", json);
                        editor.commit();
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }
        );
        // Adding request to request queue
        VolleyMainActivityHandler.getInstance().addToRequestQueue(req, tag_string_req);
    }
}
