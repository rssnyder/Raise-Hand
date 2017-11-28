package Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sae1.raisehand.R;

import org.json.JSONObject;

import Utilities.LiveFeedVolley;


public class LiveFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);

        LiveFeedVolley.LiveSessionVolley("7");

        JSONObject json = LiveFeedVolley.getJSON();

        System.out.println("JSON: " + json);
    }
}
