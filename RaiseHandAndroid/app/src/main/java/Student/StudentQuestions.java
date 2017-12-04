package Student;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Activities.VolleyMainActivityHandler;
import RecyclerViews.MyAdapterQuestionsStudent;
import Activities.MakeQuestion;
import Teacher.TeacherTopics;
import Utilities.ActivitiesNames;
import Utilities.NavUtil;
import Utilities.Question;
import Utilities.SwipeController;
import Utilities.SwipeControllerActions;
import Utilities.Topics;
import Utilities.User;

/**
 *
 * This is a class for the questions in a student's class
 * @author jaggarwal
 */
public class StudentQuestions extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;
    private SwipeController swipeController = null;
    SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Question> listItems;
    private SharedPreferences mPreferences;

    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, as
     * well as the question to the topic that is in the class the student is in
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_questions);

        // Bundle gets the topicsID from the topic that the user clicked on in the teacherTopics adapter
        Bundle bundle = getIntent().getExtras();
        final String topicID = bundle.getString("topicsID");

        // FAB that makes a new question
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonStudent);

        // Gets stored preferences. User is stored here.
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        // List to hold the questions
        listItems = new ArrayList<>();

        // Converts the mPrferences's json data of the current user to a User object.
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        // Get the Topic that the user clicked on,
        // then the questions in that topic.
        final Topics usersTopic = currentUser.getSingleTopic(topicID);
        ArrayList<Question> topicQuestions = usersTopic.getQuestions();

        // after getting the questions, assign it to listItemsOfQuestions
        listItems = topicQuestions;
        Collections.reverse(listItems);
        // Adapter to display the questions as recycler views. (cards on the screen)
        adapter = new MyAdapterQuestionsStudent(listItems, this);

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
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String topic = gson.toJson(usersTopic);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer4);

        // Go to make a new question page on FAB click
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeQuestion = new Intent(getApplicationContext().getApplicationContext(), MakeQuestion.class);
                makeQuestion.putExtra("topicID", topicID);
                makeQuestion.putExtra("topic", topic);
                startActivity(makeQuestion);
            }
        });

        nv = (NavigationView) findViewById(R.id.nv2);
        NavUtil.setNavMenu(nv, ActivitiesNames.NONE, getApplicationContext(), mDrawerLayout);

        // Swipe to refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent i = new Intent(getApplicationContext(), TeacherTopics.class);
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

    /**
     * sets up the recycler view
     */
    public void setUpRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.questionsRecyclerViewStudent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Be able to swipe the items
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                listItems.get(position).upVote();
            }

            @Override
            public void onLeftClicked(int position){
                // Think of what needs to be done maybe go to reply or just do
                // nothing

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
