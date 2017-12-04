package Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.sae1.raisehand.R;
import com.google.gson.Gson;
import Teacher.TeacherClasses;
import Utilities.Question;
import Utilities.Topics;
import Utilities.User;


/**
 *
 * This a method that enables the user to make a question, you can
 * only get to this through a topic so that we have a topic ID for the question to
 * be put underneath
 * @author joel2
 */
public class MakeQuestion extends AppCompatActivity {

    private Button submit;
    EditText textQuestion, titleQuestion;
    private SharedPreferences mPreferences;
    private User currentUser;

    /**
     * This method starts the activity, initializes the activity view and gets the currentUser, and
     * adds functionality to add a new question
     * @param savedInstanceState state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //make the activity page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);
        submit = (Button) findViewById(R.id.submitQuestion);
        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        titleQuestion = (EditText) findViewById(R.id.titleQuestion);
        textQuestion = (EditText) findViewById(R.id.enterQuestion);

        //Get the information that was passed in from the previous activity
        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);
        String topicParentAsString = getIntent().getStringExtra("topic");
        final Topics topic = gson.fromJson(topicParentAsString, Topics.class);

        // Click the submit button
        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //get the information that the user had submitted in the text boxes
                final String inputTitle = titleQuestion.getText().toString();
                final String inputDetails = textQuestion.getText().toString();

                //Store information in a temporary question object to enable adding it to the database
                Question temp = new Question();
                //current user's id
                temp.setOwnerID(currentUser.getId());
                //current username
                temp.setQuestionUsername(currentUser.getUsername());
                //topic this should fall under
                temp.setParent(topic);
                //title that was inserted by user
                temp.setQuestionTitle(inputTitle);
                //actual text of question that was entered by the user
                temp.setQuestionDescription(inputDetails);
                //calls the method from the Question class (in Utilities) that adds the question to the database
                temp.addQuestionToDatabase();

                //Go to Classes
                Intent teacherClasses = new Intent(getApplicationContext(), TeacherClasses.class);
                startActivity(teacherClasses);

            }
        });

    }

}
