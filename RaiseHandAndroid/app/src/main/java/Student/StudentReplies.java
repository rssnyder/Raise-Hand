package Student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import Activities.VolleyMainActivityHandler;
import RecyclerViews.MyAdapterRepliesStudent;
import Activities.MakeReply;
import Utilities.ActivitiesNames;
import Utilities.NavUtil;
import Utilities.Question;
import Utilities.Reply;
import Utilities.StringParse;
import Utilities.SwipeController;
import Utilities.SwipeControllerActions;
import Utilities.URLS;
import Utilities.User;

/**
 *
 * This is a class for the replies in a student's class
 * @author jaggarwal
 */
public class StudentReplies extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapterRepliesStudent adapter;
    private ArrayList<Reply> listItems;
    SwipeController swipeController = null;
    private ProgressDialog pDialog;
    private SharedPreferences mPreferences;
    private SwipeRefreshLayout swipeContainer;
    private ArrayList<Reply> newItems;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;
    private static String TAG= StudentReplies.class.getSimpleName();
    private static String tag_string_req= "string_req";
    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, as
     * well as the reply to a question to the topic that is in the class the student is in
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_replies);
        newItems=new ArrayList<Reply>();
        //get question ID with a bundle
        Bundle bundle = getIntent().getExtras();
        final String questionID = bundle.getString("questionID");

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer6);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
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
        listItems=userQuestion.getParentRepliesOnly();
        Collections.reverse(listItems);
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
        adapter = new MyAdapterRepliesStudent(listItems, this);

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

        // Go to make a new question page on FAB click
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
        nv = (NavigationView) findViewById(R.id.nv2);
        NavUtil.setNavMenu(nv, ActivitiesNames.NONE, getApplicationContext(), mDrawerLayout);
        System.out.println("Outside volley # of replies " + userQuestion.getReplies().size());

        // Swipe to refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent i = new Intent(getApplicationContext(), StudentTopics.class);
                startActivity(i);
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

    private void setUpRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.repliesRecyclerViewStudent);
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

    /**
     * Given a parent question id, it will return a list of questions that directly correspond
     * to the topic (not replies to replies)
     * @param parentQuestion
     * @return an array list of replies directly to a question
     */
    public void refreshReplies(final Question parentQuestion){
        String urlSuffix= "?questionId="+parentQuestion.getQuestionID();
        String url_final= URLS.URL_REFRESHR+urlSuffix;
        showProgressDialog();
        newItems.clear();
        StringRequest req = new StringRequest(Request.Method.GET,url_final,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        //parse replies makes sure that we do not add duplicates
                        ArrayList<Reply> temp= StringParse.parseReplies(response, parentQuestion);
                        Gson gson = new Gson();
                        String json = mPreferences.getString("currentUser", "");
                        User currentUser = gson.fromJson(json, User.class);
                        Question userQuestion = currentUser.getSingleQuestion(parentQuestion.getQuestionID());
                        userQuestion.setReplies(temp);
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
