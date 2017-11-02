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
 * Created by jaggarwal on 11/1/17.
 * This a method that enables the user to make a reply, you can
 * only get to this through a reply so that we have a reply ID for the reply to
 * be put underneath
 */

public class MakeReplyReply extends AppCompatActivity {

    private Button submit;
    EditText textReply;
    private SharedPreferences mPreferences;
    private User currentUser;

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

        final String replyParentAsString = getIntent().getStringExtra("reply");
        final Reply reply = gson.fromJson(replyParentAsString, Reply.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String inputDetails = textReply.getText().toString();
                Reply temp = new Reply();
                temp.setReplyUserID(currentUser.getId());
                temp.setReplyUsername(currentUser.getUsername());

                temp.setReplyParent(replyParentAsString);
                temp.setReply(inputDetails);
                temp.addToDatabase();
            }
        });
    }
}
