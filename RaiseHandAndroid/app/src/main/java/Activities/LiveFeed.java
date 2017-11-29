package Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sae1.raisehand.R;

import org.json.JSONObject;

import Utilities.LiveFeedVolley;

/**
 * Displays the live feed of a class.
 */
public class LiveFeed extends AppCompatActivity {

    // TODO be able to choose the class
    // TODO submit questions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);

        LiveFeedVolley.LiveSessionVolley("7");

        JSONObject json = LiveFeedVolley.getJSON();

        System.out.println("JSON: " + json);
    }
}
