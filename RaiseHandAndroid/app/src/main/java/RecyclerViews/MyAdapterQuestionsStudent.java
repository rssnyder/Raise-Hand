package RecyclerViews;

/**
 * Created by jaggarwal on 10/9/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import java.util.List;

import app.TeacherReplies;
import utils.Question;

public class MyAdapterQuestionsStudent extends RecyclerView.Adapter<MyAdapterQuestionsStudent.ViewHolder> {

    private List<Question> listItems;
    private Context context;

    public MyAdapterQuestionsStudent(List<Question> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapterQuestionsStudent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_student_questions, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapterQuestionsStudent.ViewHolder holder, int position) {
        final Question listItem = listItems.get(position);
        holder.textViewTimestamp.setText(listItem.getCreationTime());
        holder.textViewHead.setText(listItem.getQuestionTitle());
        holder.textViewDesc.setText(listItem.getQuestionDescription());
        holder.textViewPoints.setText("Points: "+ listItem.getStudentRating());
        holder.textViewEndorsed.setText("Endorsed? " + listItem.questionEndorsemenet());

        Gson gson = new Gson();
        final String question = gson.toJson(listItem);
        holder.upvoteQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.upVote();
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
        public TextView textViewPoints;
        public TextView textViewEndorsed;
        public TextView textViewTimestamp;
        public Button upvoteQ;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            upvoteQ= (Button) itemView.findViewById(R.id.upvoteQ);
            textViewTimestamp= (TextView) itemView.findViewById(R.id.textViewTimestamp);
            textViewPoints= (TextView) itemView.findViewById(R.id.textViewPoints);
            textViewEndorsed= (TextView) itemView.findViewById(R.id.textViewEndorsed);
        }
    }
}
