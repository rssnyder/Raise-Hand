package app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sae1.raisehand.R;

public class MakeQuestion extends AppCompatActivity {

    private Button submit;
    EditText textQuestion, titleQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);

        submit = (Button) findViewById(R.id.submitQuestion);
        titleQuestion = (EditText) findViewById(R.id.titleQuestion);
        textQuestion = (EditText) findViewById(R.id.enterQuestion);

        String inputTitle = titleQuestion.getText().toString();
        String inputDetails = textQuestion.getText().toString();

        

    }
}
