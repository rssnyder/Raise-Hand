package com.example.sae1.raisehand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import RecyclerViews.ListItemTeacherTopics;
import RecyclerViews.MyAdapterTopics;
import app.TeacherHomePage;
import app.TeacherNotifications;
import app.TeacherSettings;
import app.TeacherStudents;
import app.TeacherTopics;
import utils.LoginActivity;
import utils.URLS;

public class student_topics extends AppCompatActivity {
    private String TAG = TeacherTopics.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItemTeacherTopics> listItems;
    private Field mDragger;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_topics);

        // Set up recycler view
        recyclerView = (RecyclerView) findViewById(R.id.topicsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // list to hold items for recycler view.
        // i.e. The topics in the class
        listItems = new ArrayList<>();

        makeStringReq();

        for(int i = 0; i < 10; i++){
            ListItemTeacherTopics listItem = new ListItemTeacherTopics("Topic " + (i + 1),
                    "Dummy text. This is a topic!");
            listItems.add(listItem);
        }
//        adapter = new MyAdapterTopics(listItems, this);

        recyclerView.setAdapter(adapter);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        slideOutMenu();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv1);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case(R.id.nav_home_student):
                        Intent studentHome = new Intent(getApplicationContext(), student_home_page.class);
                        startActivity(studentHome);
                        break;
                    case (R.id.nav_classes):
                        mDrawerLayout.closeDrawers();
                        break;
                    case (R.id.nav_notifications):
                        Intent studentNotifications = new Intent(getApplicationContext(), student_notifications.class);
                        startActivity(studentNotifications);
                        break;
                    case (R.id.nav_settings):
                        Intent studentSettings = new Intent(getApplicationContext(), student_settings.class);
                        startActivity(studentSettings);
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

    private void makeStringReq(){

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URLS.URL_STUDENT_HOME,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        String phpResponse = response.toString();
                        System.out.println("YOU ARE HERE");
                        System.out.println("RESPONSE: \n"+phpResponse);
                        System.out.println("THE END");
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
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
