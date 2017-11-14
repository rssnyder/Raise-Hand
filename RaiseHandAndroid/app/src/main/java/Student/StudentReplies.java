package Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import RecyclerViews.MyAdapterRepliesStudent;
import Activities.MakeQuestion;
import Activities.MakeReply;
import Activities.LoginActivity;
import Utilities.Question;
import Utilities.Reply;
import Utilities.SwipeController;
import Utilities.SwipeControllerActions;
import Utilities.User;
/**
 * @author jaggarwal
 * This is a class for the replies in a student's class
 */
public class StudentReplies extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_replies);

        //get question ID with a bundle
        Bundle bundle = getIntent().getExtras();
        final String questionID = bundle.getString("questionID");

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        // Get the question the user clicked on,
        // then the replies in that question.
        final Question userQuestion = currentUser.getSingleQuestion(questionID);
        listItems=userQuestion.getParentRepliesOnly();

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

        adapter = new MyAdapterRepliesStudent(listItems, this);

        setUpRecyclerView();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String question = gson.toJson(userQuestion);

        // Go to make a new question page on FAB click
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeReply = new Intent(getApplicationContext().getApplicationContext(), MakeReply.class);
                makeReply.putExtra("questionID", questionID);
                makeReply.putExtra("question", question);
                Bundle bun = new Bundle();
                bun.putString("topicID", questionID);
                bun.putString("topic", question);
                startActivity(makeReply);
            }
        });

        nv = (NavigationView) findViewById(R.id.nv2);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.nav_home_student):
                        Intent studentHome = new Intent(getApplicationContext(), StudentHomePage.class);
                        startActivity(studentHome);
                        break;
                    case (R.id.nav_classes_student):
                        mDrawerLayout.closeDrawers();
                        break;
                    case (R.id.nav_notifications_student):
                        Intent studentNotifications = new Intent(getApplicationContext(), StudentNotifications.class);
                        startActivity(studentNotifications);
                        break;
                    case (R.id.nav_question_student):
                        Intent studentQuestion = new Intent(getApplicationContext(), MakeQuestion.class);
                        startActivity(studentQuestion);
                        break;
                    case (R.id.nav_settings_student):
                        Intent studentSettings = new Intent(getApplicationContext(), StudentSettings.class);
                        startActivity(studentSettings);
                        break;
                    case (R.id.nav_logout_student):
                        Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginPage);
                        finish();
                        break;
                }
                return true;
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

    private void slideOutMenu(){

        try {
            mDragger = mDrawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");//mRightDragger for right obviously
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mDragger.setAccessible(true);
        ViewDragHelper draggerObj = null;
        try {
            draggerObj = (ViewDragHelper) mDragger
                    .get(mDrawerLayout);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field mEdgeSize = null;
        try {
            mEdgeSize = draggerObj.getClass().getDeclaredField(
                    "mEdgeSize");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mEdgeSize.setAccessible(true);
        int edge = 0;
        try {
            edge = mEdgeSize.getInt(draggerObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            mEdgeSize.setInt(draggerObj, edge * 25);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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
