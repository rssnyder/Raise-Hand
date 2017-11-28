package Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import RecyclerViews.MyAdapterTopicsStudent;
import Activities.MakeQuestion;
import Utilities.ActivitiesNames;
import Utilities.Classes;
import Activities.LoginActivity;
import Utilities.NavUtil;
import Utilities.Topics;
import Utilities.User;
/**
 *
 * This is a class for the topics in a student's class
 * @author jaggarwal
 */
public class StudentTopics extends AppCompatActivity {
    private String TAG = StudentClasses.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Topics> listItems;
    private Field mDragger;

    private SharedPreferences mPreferences;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;
    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, as
     * well as topics that is in the class the student is in
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_topics);

        // Bundle gets the classID from the class the user clicked on in the StudentClasses adapter (myAdapterClassesStudent)
        Bundle bundle = getIntent().getExtras();
        String classID = bundle.getString("classID");

        // Gets stored preferences. User is stored here.
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        // Set up recycler view
        recyclerView = (RecyclerView) findViewById(R.id.topicsRecyclerViewStudent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // list to hold items for recycler view.
        // i.e. The topics in the class
        listItems = new ArrayList<Topics>();

        // Converts the mPrferences's json data of the current user to a User object.
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        // loop until you find the Topics from the class you clicked on in TeacherClasses
        // Can probably do this with the getSingleClass method
        for(Classes c : currentUser.getClasses()){
            if(c.getClassID().equals(classID)){
                for (Topics t: c.getTopics()) {
                    listItems.add(t);
                }
                break;
            }
        }

        // Adapter to display the topics as recycler views. (cards on the screen)
        adapter = new MyAdapterTopicsStudent(listItems, this);

        // Set the adapter to the recycler view
        recyclerView.setAdapter(adapter);

        // Get the nav menu stuff
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        // create the drawer layout (the thing you swipe from the side)
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // add the menu items to the drawer
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //makes it so you can swipe the menu out from anywhere on screen
//        slideOutMenu();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv2);
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

//    private void slideOutMenu(){
//
//        try {
//            mDragger = mDrawerLayout.getClass().getDeclaredField(
//                    "mLeftDragger");//mRightDragger for right obviously
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        mDragger.setAccessible(true);
//        ViewDragHelper draggerObj = null;
//        try {
//            draggerObj = (ViewDragHelper) mDragger
//                    .get(mDrawerLayout);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        Field mEdgeSize = null;
//        try {
//            mEdgeSize = draggerObj.getClass().getDeclaredField(
//                    "mEdgeSize");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        mEdgeSize.setAccessible(true);
//        int edge = 0;
//        try {
//            edge = mEdgeSize.getInt(draggerObj);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            mEdgeSize.setInt(draggerObj, edge * 25);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
}
