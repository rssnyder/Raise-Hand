package RecyclerViews;

/**
 * Created by jaggarwal on 10/9/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import java.util.List;

import app.TeacherQuestions;
import utils.Topics;

public class MyAdapterTopicsStudent extends RecyclerView.Adapter<MyAdapterTopicsStudent.ViewHolder> {

    private List<Topics> listItems;
    private Context context;

    public MyAdapterTopicsStudent(List<Topics> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapterTopicsStudent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_student_topics, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Topics listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.get_title());
        holder.textViewDesc.setText(listItem.get_description());
        Gson gson2 = new Gson();
        final String topic = gson2.toJson(listItem);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO go to the topics' questions
                Intent studentQuestions = new Intent(context.getApplicationContext(), com.example.sae1.raisehand.student_questions.class);
                studentQuestions.putExtra("topicsID", listItem.get_ID());
                studentQuestions.putExtra("topic", topic);
                // pass topic ID to the question activity
                Bundle bundle = new Bundle();
                bundle.putString("topicsID", listItem.get_ID());
                bundle.putString("topic", topic);
                context.getApplicationContext().startActivity(studentQuestions);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutStudentTopics);
        }
    }
}
