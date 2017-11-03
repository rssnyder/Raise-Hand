package Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import Utils.Question;
import Utils.Topics;
import Utils.User;


/**
 * This a method that enables the user to make a question, you can
 * only get to this through a topic so that we have a topic ID for the question to
 * be put underneath
 */
public class MakeQuestion extends AppCompatActivity {

    private Button submit;
    EditText textQuestion, titleQuestion;
    private SharedPreferences mPreferences;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);

        submit = (Button) findViewById(R.id.submitQuestion);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        titleQuestion = (EditText) findViewById(R.id.titleQuestion);
        textQuestion = (EditText) findViewById(R.id.enterQuestion);


        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        String topic1 = mPreferences.getString("topic", "");
        currentUser = gson.fromJson(json, User.class);
        final Topics parentTopic = gson.fromJson(topic1, Topics.class);

        String topicParentAsString = getIntent().getStringExtra("topic");
        final Topics topic = gson.fromJson(topicParentAsString, Topics.class);

        Bundle bundle = getIntent().getExtras();
        final String topicID = bundle.getString("topicsID");

        // Click the submit button
        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                final String inputTitle = titleQuestion.getText().toString(); //question title
                final String inputDetails = textQuestion.getText().toString(); //question details

                Question temp = new Question();

                //current user's id
                temp.setOwnerID(currentUser.getId());
                //current username
                temp.setQuestionUsername(currentUser.getUsername());
                //topic this should fall under
                temp.setParent(topic);
                temp.setQuestionTitle(inputTitle);
                temp.setQuestionDescription(inputDetails);
                temp.addQuestionToDatabase();
            }
        });

    }

}
