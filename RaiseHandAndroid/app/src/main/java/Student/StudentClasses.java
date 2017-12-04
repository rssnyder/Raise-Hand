package Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import Activities.AddClass;
import Activities.MakeQuestion;
import RecyclerViews.MyAdapterClassesStudent;
import Utilities.ActivitiesNames;
import Utilities.Classes;
import Utilities.NavUtil;
import Utilities.User;
/**
 * This is a class for all of the classes that a student is in
 * @author jaggarwal
 */
public class StudentClasses extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Classes> listItems;
    private SharedPreferences mPreferences;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;

    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, as
     * well as the classes the student is in
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_classes);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        // Set up recycler view
        recyclerView = (RecyclerView) findViewById(R.id.classesRecyclerViewStudent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // list to hold items for recycler view.
        // i.e. The classes the student is in
        listItems = new ArrayList<>();

        // FAB that makes a new question
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonClass);

        // Converts the mPrferences's json data of the current user to a User object.
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        final User currentUser = gson.fromJson(json, User.class);

        // Get the classes the current user is in
        listItems = currentUser.getClasses();

        // Adapter to display the classes as recycler views. (cards on the screen)
        adapter = new MyAdapterClassesStudent(listItems, this);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Go to make a new question page on FAB click
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addClass= new Intent(getApplicationContext().getApplicationContext(), AddClass.class);
                startActivity(addClass);
            }
        });

        nv = (NavigationView) findViewById(R.id.nv2);
        NavUtil.setNavMenu(nv, ActivitiesNames.CLASSES, getApplicationContext(), mDrawerLayout);
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
