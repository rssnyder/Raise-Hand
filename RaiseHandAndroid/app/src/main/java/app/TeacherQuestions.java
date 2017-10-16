package app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import RecyclerViews.ListItemTeacherClasses;
import RecyclerViews.MyAdapterQuestions;
import utils.Question;
import utils.Reply;
import utils.Topics;
import utils.User;

public class TeacherQuestions extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Question> listItems;
    private Field mDragger;

    private SharedPreferences mPreferences;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_questions);

        Bundle bundle = getIntent().getExtras();
        final String topicID = bundle.getString("topicsID");

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        recyclerView = (RecyclerView) findViewById(R.id.questionsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        adapter = new MyAdapterQuestions(listItems, this);

        recyclerView.setAdapter(adapter);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        slideOutMenu();

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
                    case(R.id.nav_home):
                        Intent teacherHome = new Intent(getApplicationContext(), TeacherHomePage.class);
                        startActivity(teacherHome);
                        break;
                    case (R.id.nav_classes):
                        mDrawerLayout.closeDrawers();
                        break;
                    case (R.id.nav_notifications):
                        Intent teacherNotifications = new Intent(getApplicationContext(), TeacherNotifications.class);
                        startActivity(teacherNotifications);
                        break;
                    case (R.id.nav_students):
                        Intent teacherStudents = new Intent(getApplicationContext(), TeacherStudents.class);
                        startActivity(teacherStudents);
                        break;
                    case (R.id.nav_settings):
                        Intent teacherSettings = new Intent(getApplicationContext(), TeacherSettings.class);
                        startActivity(teacherSettings);
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


}
