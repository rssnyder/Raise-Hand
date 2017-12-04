package Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.sae1.raisehand.R;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import Activities.MakeReplyReply;
import Activities.VolleyMainActivityHandler;
import RecyclerViews.MyAdapterRepliesReply;
import Utilities.ActivitiesNames;
import Utilities.NavUtil;
import Utilities.Reply;
import Utilities.SwipeController;
import Utilities.SwipeControllerActions;
import Utilities.User;
import com.google.gson.Gson;

/**
 *
 *
 * This is a student activity for the reply to reply page. It contains the Recycler View and the the adapter
 * for the activity.
 * @author jaggarwal
 */

public class StudentRepliesReply extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Reply> listItems;
    private Field mDragger;
    SwipeController swipeController = null;
    SwipeRefreshLayout swipeContainer;

    private SharedPreferences mPreferences;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;

    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, and
     * adds functionality to add a new reply to a reply
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_replies_reply);
        Bundle bundle = getIntent().getExtras();
        final String replyID = bundle.getString("replyID");

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        final Reply userReply = currentUser.getSingleReply(replyID);

        listItems = userReply.getReplies();
        Collections.reverse(listItems);

        adapter = new MyAdapterRepliesReply(listItems, this);

        setUpRecyclerView();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String reply = gson.toJson(userReply);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer5);
        // Go to make a new reply page on FAB click

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyReply = new Intent(getApplicationContext().getApplicationContext(), MakeReplyReply.class);
                replyReply.putExtra("replyID", replyID);
                replyReply.putExtra("reply", reply);
                startActivity(replyReply);
            }
        });
        nv = (NavigationView) findViewById(R.id.nv2);
        NavUtil.setNavMenu(nv, ActivitiesNames.NONE, getApplicationContext(), mDrawerLayout);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent i = new Intent(getApplicationContext(), StudentTopics.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * Private method to set the recycler view to view replies to replies
     *
     */
    private void setUpRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.replyRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //Upboat here
                listItems.get(position).upVote();
            }
            @Override
            public void onLeftClicked(int position){
                //Think what can be done, maybe nothing
                Toast.makeText(VolleyMainActivityHandler.getInstance(), "Only professor can endorse", Toast.LENGTH_LONG).show();
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }



}
