package Student;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import Utilities.Topics;
import Utilities.URLS;
import Utilities.User;

/**
 *
 * This class has notifications for the teacher.
 * Notifications will include recent questions and replies submitted.
 * @author sae1
 */
public class StudentNotifications extends AppCompatActivity {
    private static String TAG= StudentNotifications.class.getSimpleName();
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
            for(Topics t: c.getTopics()){
                for(Question q: t.getQuestions()) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                    if (q.getCreationTime().substring(0, 10).contains(mdformat.format(calendar.getTime()))) {
                        if(!listItems.contains(q))
                            listItems.add(q);
                    }
                }
            }
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
        setSupportActionBar(mToolbar);

        // create the drawer layout (the thing you swipe from the side)
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // add the menu items to the drawer
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}
