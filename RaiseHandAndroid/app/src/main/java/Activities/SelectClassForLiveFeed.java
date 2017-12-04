package Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import RecyclerViews.MyAdapterClassesForLiveFeed;
import Utilities.ActivitiesNames;
import Utilities.Classes;
import Utilities.LiveFeedVolley;
import Utilities.NavUtil;
import Utilities.User;

public class SelectClassForLiveFeed extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Classes> listItems;
    private SharedPreferences mPreferences;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;

    private Button startLive;
    private String classToStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class_for_live_feed);

        startLive = (Button) findViewById(R.id.startLive);

        LiveFeedVolley.clearJSONArray();

        // Gets stored preferences. User is stored here.
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        // Set up recycler view
        recyclerView = (RecyclerView) findViewById(R.id.classesForLiveFeedRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // list to hold items for recycler view.
        // i.e. The classes the teacher is in
        listItems = new ArrayList<>();

        // Converts the mPrferences's json data of the current user to a User object.
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        // Get the classes the current user is in
        listItems = currentUser.getClasses();

        // Adapter to display the classes as recycler views. (cards on the screen)
        adapter = new MyAdapterClassesForLiveFeed(listItems, this);

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

        // populate the navigation buttons to go to the correct place
        nv = (NavigationView) findViewById(R.id.nv1);
        NavUtil.setNavMenu(nv, ActivitiesNames.LIVE_SESSION, getApplicationContext(), mDrawerLayout);

        startLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();
            }
        });


    }

    /**
     * gets the class that the user wants to start a live session of.
     */
    private void getInput(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Class number");

        //set the input
        final EditText input = new EditText(this);
        //specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        //set up the buttons
        builder.setPositiveButton("Start!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                classToStart = input.getText().toString();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://proj-309-sa-b-3.cs.iastate.edu/teacher/liveSession.php?class=" + classToStart));
                startActivity(browserIntent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
