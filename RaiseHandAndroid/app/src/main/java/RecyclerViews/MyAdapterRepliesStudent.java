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

import app.TeacherReplies;
import app.TeacherTopics;
import utils.Classes;
import utils.Question;
import utils.Reply;
/**
 * Created by sae1 on 10/31/17.
 */

public class MyAdapterRepliesStudent  extends RecyclerView.Adapter<MyAdapterRepliesStudent.ViewHolder> {
    private List<Reply> listItems;
    private Context context;

    public MyAdapterRepliesStudent(List<Reply> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_student_replies, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Reply listItem = listItems.get(position);
        holder.textViewTim.setText(listItem.get_reply_time_stamp());
        holder.textViewHe.setText(listItem.get_reply());
        holder.textViewPo.setText("Points: "+ listItem.get_reply_up_votes());
        holder.textViewEn.setText("Endorsed? " + listItem.get_reply_endorsed());
        Gson gson = new Gson();
        final String rep = gson.toJson(listItem);
        holder.upvoteR.setOnClickListener(new View.OnClickListener() {
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

        public TextView textViewHe, textViewEn, textViewPo, textViewTim;
        public Button upvoteR;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            upvoteR= (Button) itemView.findViewById(R.id.upvoteR);
            textViewHe = (TextView) itemView.findViewById(R.id.textViewHe);
            textViewEn= (TextView) itemView.findViewById(R.id.textViewEn);
            textViewPo= (TextView) itemView.findViewById(R.id.textViewPo);
            textViewTim= (TextView) itemView.findViewById(R.id.textViewTim);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutStudentReplies);

        }
    }
}
