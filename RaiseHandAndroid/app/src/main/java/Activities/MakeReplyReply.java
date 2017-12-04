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
import Utilities.Reply;
import Utilities.User;

/**
 *
 * This a page that enables the user to make a reply, you can
 * only get to this through a reply so that we have a reply ID for the reply to
 * be put underneath
 * @author jaggarwal
 */

public class MakeReplyReply extends AppCompatActivity {

    private Button submit;
    EditText textReply;
    private SharedPreferences mPreferences;
    private User currentUser;

    /**
     *
     * This method starts the activity, initializes the activity view and gets the currentUser, and
     * adds functionality to add a new reply to a reply
     *
     * @param savedInstanceState the current state of the activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_replies_reply);

        submit = (Button)findViewById(R.id.submitReply);

        mPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        textReply = (EditText) findViewById(R.id.textReply);

        Gson gson = new Gson();
        String json = mPreferences.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);
        final String replyParentIDAsString = getIntent().getStringExtra("replyID");
        //final Reply reply = gson.fromJson(replyParentAsString, Reply.class);

        // On click listener to add a reply to that reply.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String inputDetails = textReply.getText().toString();
                Reply temp = new Reply();
                temp.setReplyUserID(currentUser.getId());
                temp.setReplyUsername(currentUser.getUsername());
                temp.setReplyIDParent(replyParentIDAsString);
                temp.setReply(inputDetails);
                temp.addToDatabase();

                //Go to Classes (when we figure out the refreshing thing, this should redirect back to the previous reply)
                Intent teacherClasses = new Intent(getApplicationContext(), TeacherClasses.class);
                startActivity(teacherClasses);


            }
        });
    }
}
