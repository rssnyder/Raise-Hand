package Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
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
import java.util.List;

import RecyclerViews.MyAdapterQuestionsStudent;
import Activities.MakeQuestion;
import Activities.LoginActivity;
import Utils.Question;
import Utils.Reply;
import Utils.SwipeController;
import Utils.SwipeControllerActions;
import Utils.Topics;
import Utils.User;

public class StudentQuestions extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;
    private Field mDragger;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Question> listItems;
    private SharedPreferences mPreferences;

    private SwipeController swipeController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_questions);

        Bundle bundle = getIntent().getExtras();
        final String topicID = bundle.getString("topicsID");

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButtonStudent);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        listItems = new ArrayList<>();
        List<Reply> replyList = new ArrayList<>();

        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        // Get the Topic that the user clicked on,
        // then the questions in that topic.
        final Topics usersTopic = currentUser.getSingleTopic(topicID);
        ArrayList<Question> topicQuestions = usersTopic.get_questions();

        listItems = topicQuestions;
        /*
        // Used to test scrolling and the FAB
        for(int i = 0; i < 10; i++){
            listItems.add(topicQuestions.get(0));
        }
        */

        adapter = new MyAdapterQuestionsStudent(listItems, this);

        setUpRecyclerView();

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //slideOutMenu();
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String topic = gson.toJson(usersTopic);

        // Go to make a new question page on FAB click
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makeQuestion = new Intent(getApplicationContext().getApplicationContext(), MakeQuestion.class);
                makeQuestion.putExtra("topicID", topicID);
                makeQuestion.putExtra("topic", topic);
                Bundle bun = new Bundle();
                bun.putString("topicID", topicID);
                bun.putString("topic", topic);
                startActivity(makeQuestion);
            }
        });

        nv = (NavigationView) findViewById(R.id.nv1);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.nav_home_student):
                        Intent studentHome = new Intent(getApplicationContext(), StudentHomePage.class);
                        startActivity(studentHome);
                        break;
                    case (R.id.nav_classes):
                        mDrawerLayout.closeDrawers();
                        break;
                    case (R.id.nav_notifications):
                        Intent studentNotifications = new Intent(getApplicationContext(), StudentNotifications.class);
                        startActivity(studentNotifications);
                        break;
                    case (R.id.nav_settings):
                        Intent studentSettings = new Intent(getApplicationContext(), StudentSettings.class);
                        startActivity(studentSettings);
                        break;
                    case (R.id.nav_logout):
                        Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginPage);
                        finish();
                        break;
                    case (R.id.nav_question):
                        Intent questionStudent = new Intent(getApplicationContext(), MakeQuestion.class);
                        startActivity(questionStudent);
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

    public void setUpRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.questionsRecyclerViewStudent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                listItems.get(position).upVote();
            }

            @Override
            public void onLeftClicked(int position){
                // Think of what needs to be done maybe go to reply or just do
                // nothing

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
    /*
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
    */
}
