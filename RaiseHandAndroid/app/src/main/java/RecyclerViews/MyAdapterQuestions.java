package RecyclerViews;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import Teacher.TeacherReplies;
import Utilities.Question;
import Utilities.StringParse;
/*
@author joel2
This class handles how to show the list of questions to a teacher
 */
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
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Question listItem = listItems.get(position);
        holder.textViewTimestamp.setText(StringParse.parseTimeStamp(listItem.getCreationTime()));
        holder.textViewHead.setText(listItem.getQuestionTitle());
        holder.textViewDesc.setText(listItem.getQuestionDescription());
        holder.textViewPoints.setText("Points: "+ listItem.getStudentRating());
        if(listItem.questionEndorsemenet()){
            holder.textViewEndorsed.setText("Endorsed!");
        }
        else{
            holder.textViewEndorsed.setText(" ");
        }

        Gson gson = new Gson();
        final String question = gson.toJson(listItem);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to the questions' replies
                Intent teacherReplies = new Intent(context.getApplicationContext(), TeacherReplies.class);
                teacherReplies.putExtra("questionID", listItem.getQuestionID());
                teacherReplies.putExtra("question", question);
                context.getApplicationContext().startActivity(teacherReplies);
            }
        });
        holder.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //report the question
                listItem.report();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewPoints;
        public TextView textViewEndorsed;
        public TextView textViewTimestamp;
        public Button reportButton;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewTimestamp= (TextView) itemView.findViewById(R.id.textViewTimestamp);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewPoints= (TextView) itemView.findViewById(R.id.textViewPoints);
            textViewEndorsed= (TextView) itemView.findViewById(R.id.textViewEndorsed);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutTeacherQuestions);
            reportButton= (Button) itemView.findViewById(R.id.buttonReport);
        }
    }

}
