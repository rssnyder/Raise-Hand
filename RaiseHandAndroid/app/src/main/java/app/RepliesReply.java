package app;

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
import RecyclerViews.MyAdapterRepliesReply;
import utils.LoginActivity;
import utils.Reply;
import utils.SwipeController;
import utils.SwipeControllerActions;
import utils.User;

/**
 * Created by jaggarwal on 11/1/17.
 */

public class RepliesReply extends AppCompatActivity{
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies_reply);

        Bundle bundle = getIntent().getExtras();
        final String replyID = bundle.getString("replyID");

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        User currentUser = gson.fromJson(json, User.class);

        final Reply userReply = currentUser.getSingleReply(replyID);

        // TODO: figure how to get this
        listItems = userReply.getReplies();


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

        // Go to make a new reply page on FAB click

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyReply = new Intent(getApplicationContext().getApplicationContext(), MakeReplyReply.class);
                replyReply.putExtra("replyID", replyID);
                replyReply.putExtra("reply", reply);
                Bundle bundle = new Bundle();
                bundle.putString("replyID", replyID);
                bundle.putString("reply", reply);
                startActivity(replyReply);
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

                    case (R.id.nav_logout):
                        Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginPage);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

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
