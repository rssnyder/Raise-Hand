package Teacher;

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

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;

import Activities.MakeReply;
import RecyclerViews.MyAdapterReplies;
import Utilities.ActivitiesNames;
import Utilities.NavUtil;
import Utilities.Question;
import Utilities.Refresh;
import Utilities.Reply;
import Utilities.SwipeController;
import Utilities.SwipeControllerActions;
import Utilities.User;
/**
 *
 * This class has replies for the teacher's class.
 * @author joel2
 */
public class TeacherReplies extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Reply> listItems;
    private Field mDragger;
    SwipeController swipeController = null;

    private SharedPreferences mPreferences;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;

    private SwipeRefreshLayout swipeContainer;

    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, as
     * well as the replies to a question to the topic that is in the class the teacher teaches
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_replies);

        //get question ID with a bundle
        Bundle bundle = getIntent().getExtras();
        final String questionID = bundle.getString("questionID");

        // FABulous way to add a reply
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        // Gets stored preferences. User is stored here.
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        // Converts the mPrferences's json data of the current user to a User object.
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        // Get the question the user clicked on,
        // then the replies in that question.
        final Question userQuestion = currentUser.getSingleQuestion(questionID);
        //only get original replies, not replies to replies
        listItems = userQuestion.getParentRepliesOnly();

        //go through every parent reply and find the replies to replies
        for(Reply r: listItems) {
            ArrayList<Reply> temp = new ArrayList<Reply>();
            if (r.getReplies() == null || r.getReplies().size() == 0) {
                //go through all replies to find the ones that are children of the parent reply
                for (Reply rep : userQuestion.getReplies()) {
                    if (rep.getReplyParent() != null && r.getReplyID().equals(rep.getReplyParent())) {
                        temp.add(rep);
                    }
                }
                r.setReplies(temp);
            }
        }

        // Adapter to display the questions as recycler views. (cards on the screen)
        adapter = new MyAdapterReplies(listItems, this);

        // Makes the recycler view
        setUpRecyclerView();

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

        // put the questions into a string from its JSON
        final String question = gson.toJson(userQuestion);

        // Go to make a new reply page on FAB click
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeReply = new Intent(getApplicationContext().getApplicationContext(), MakeReply.class);
                // Pass the question ID and question array
                makeReply.putExtra("questionID", questionID);
                makeReply.putExtra("question", question);
                startActivity(makeReply);
            }
        });

        // populate the navigation buttons to go to the correct place
        nv = (NavigationView) findViewById(R.id.nv1);
        NavUtil.setNavMenu(nv, ActivitiesNames.NONE, getApplicationContext(), mDrawerLayout);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                adapter.addAll(Refresh.refreshReplies(questionID));
                swipeContainer.setRefreshing(false);
            }
        });
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

    /**
     * Sets up the recycler view
     */
    private void setUpRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.repliesRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Be able to swipe the items
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //shows the upvote on right click
                listItems.get(position).upVote();
            }
            @Override
            public void onLeftClicked(int position){
                // Endorses on left click
                listItems.get(position).endorse();
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
