package app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import utils.Question;
import utils.Reply;
import utils.User;

/**
 * Created by sae1 on 10/29/17.
 * This a method that enables the user to make a reply, you can
 * only get to this through a question so that we have a question ID for the reply to
 * be put underneath
 *
 */

public class MakeReply extends AppCompatActivity {
    private Button submit;
    EditText textReply;
    private SharedPreferences mPreferences;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reply);

        submit = (Button) findViewById(R.id.submitReply);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        textReply = (EditText) findViewById(R.id.textReply);


        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        String question1 = mPreferences.getString("question", "");
        currentUser = gson.fromJson(json, User.class);
        final Question parentQuestion = gson.fromJson(question1, Question.class);

        String questionParentAsString = getIntent().getStringExtra("question");
        final Question question = gson.fromJson(questionParentAsString, Question.class);

        Bundle bundle = getIntent().getExtras();
        final String questionID = bundle.getString("questionID");

        // Click the submit button
        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final String inputDetails = textReply.getText().toString(); //question details
                Reply temp = new Reply();
                //current user's id
                temp.setReplyUserID(currentUser.getId());
                //current username
                temp.setReplyUsername(currentUser.getUsername());
                //topic this should fall under
                temp.setReplyQParent(question);
                temp.setReply(inputDetails);
                temp.addToDatabase();
            }
        });

    }

}
