package Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import Utilities.User;
import static Utilities.LiveFeedVolley.*;

public class MakeLiveFeedQuestion extends AppCompatActivity {
    private Button submit;
    EditText titleQuestion;
    private SharedPreferences mPreferences;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_live_feed_question);

        submit = (Button)  findViewById(R.id.submitQuestionLive);
        titleQuestion = (EditText) findViewById(R.id.titleQuestionLive);

        //Get the information that was passed in from the previous activity
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        Bundle bundle =getIntent().getExtras();
        final String classID = bundle.getString("classID");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String inputQuestion = titleQuestion.getText().toString();

                addToLiveFeed(classID,currentUser.getUsername() ,inputQuestion);

            }
        });
    }
    
}
