package Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sae1.raisehand.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Utilities.LiveFeedPOJO;
import Utilities.LiveFeedVolley;

/**
 * Displays the live feed of a class.
 */
public class LiveFeed extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<LiveFeedPOJO> listItems;

    // TODO be able to choose the class
    // TODO submit questions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);

        //set up the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.liveFeedRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // list to hold the live feed questions
        listItems = new ArrayList<>();

        JSONArray jArray = LiveFeedVolley.LiveSessionVolley("7");

        JSONArray json = LiveFeedVolley.getJSON();


        ObjectMapper mapper = new ObjectMapper();


        System.out.println("JSON: " + json);
    }
}
