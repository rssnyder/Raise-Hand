package Teacher;

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
import android.view.MenuItem;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import java.util.ArrayList;
import RecyclerViews.MyAdapterQuestions;
import Utilities.ActivitiesNames;
import Utilities.Classes;
import Utilities.NavUtil;
import Utilities.Question;
import Utilities.Topics;
import Utilities.User;

/**
 *
 * This class has all of the most upvoted questions for the teacher.
 * The cut off is 5 upvotes or more
 * @author sae1
 */
public class TeacherUpvotedQuestions extends AppCompatActivity {
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
        setContentView(R.layout.activity_teacher_notifications);
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        listItems=new ArrayList<Question>();
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);
        for(Classes c: currentUser.getClasses()){
            for(Topics t: c.getTopics()){
                for(Question q: t.getQuestions()) {
                    if (Integer.parseInt(q.getStudentRating())>5) {
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
        NavUtil.setNavMenu(nv, ActivitiesNames.FAQ, getApplicationContext(), mDrawerLayout);

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
