package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import Teacher.TeacherClasses;
import Utilities.Question;
import Utilities.Reply;
import Utilities.User;

/**
 *
 * This is a page that enables the user to make a reply, you can
 * only get to this through a question so that we have a question ID for the reply to
 * be put underneath
 *@author sae1
 */

public class MakeReply extends AppCompatActivity {
    private Button submit;
    EditText textReply;
    private SharedPreferences mPreferences;
    private User currentUser;

    /**
     * This method starts the activity, initializes the activity view and gets the currentUser, and
     * adds functionality to add a new reply
     * @param savedInstanceState state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create the page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reply);
        submit = (Button) findViewById(R.id.submitReply);
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        textReply = (EditText) findViewById(R.id.textReply);

        //Get the information that was passed in from the previous activity
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);
        String questionParentAsString = getIntent().getStringExtra("question");
        final Question question = gson.fromJson(questionParentAsString, Question.class);

        // Click the submit button
        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //get the information that the user had submitted in the text box
                final String inputDetails = textReply.getText().toString();

                //Store information in a temporary reply object to enable adding it to the database
                Reply temp = new Reply();
                //current user's id
                temp.setReplyUserID(currentUser.getId());
                //current username
                temp.setReplyUsername(currentUser.getUsername());
                //topic this should fall under
                temp.setReplyQParent(question);
                //what the user inputted for the reply text
                temp.setReply(inputDetails);
                //calls the method from the Reply class (in Utilities) that adds the reply to the database
                temp.addToDatabase();
                onBackPressed();
                //Go to Classes (when we figure out the refreshing thing, this should redirect back to the previous reply)
                Intent teacherClasses = new Intent(getApplicationContext(), TeacherClasses.class);
                startActivity(teacherClasses);
            }
        });

    }

}
