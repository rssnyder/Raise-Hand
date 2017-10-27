package RecyclerViews;


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

import app.TeacherReplies;
import utils.Question;

public class MyAdapterQuestions extends RecyclerView.Adapter<MyAdapterQuestions.ViewHolder> {

    private List<Question> listItems;
    private Context context;

    public MyAdapterQuestions(List<Question> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_teacher_questions, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Question listItem = listItems.get(position);
        holder.textViewHead.setText(listItem.getQuestionTitle());
        holder.textViewDesc.setText(listItem.getQuestionDescription());
        holder.textViewPoints.setText("Points: "+ listItem.getStudentRating());
        holder.textViewEndorsed.setText("Endorsed? " + listItem.questionEndorsemenet());

        Gson gson = new Gson();
        final String question = gson.toJson(listItem);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to the questions' replies
                Intent teacherReplies = new Intent(context.getApplicationContext(), TeacherReplies.class);
                //TODO: this getQuestionID() is returning null
                teacherReplies.putExtra("questionID", listItem.getQuestionID());
                teacherReplies.putExtra("question", question);

                //pass question ID to the replies activity
                //Do I need this?
                Bundle bundle = new Bundle();
                bundle.putString("questionID", listItem.getQuestionID());
                bundle.putString("question", question);
                context.getApplicationContext().startActivity(teacherReplies);
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
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewPoints= (TextView) itemView.findViewById(R.id.textViewPoints);
            textViewEndorsed= (TextView) itemView.findViewById(R.id.textViewEndorsed);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutTeacherQuestions);
        }
    }

}
