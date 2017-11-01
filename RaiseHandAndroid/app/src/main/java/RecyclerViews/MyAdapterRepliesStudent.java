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

import org.w3c.dom.Text;

import java.util.List;

import app.TeacherReplies;
import app.TeacherTopics;
import utils.Classes;
import utils.Question;
import utils.Reply;

public class MyAdapterRepliesStudent extends RecyclerView.Adapter<MyAdapterRepliesStudent.ViewHolder> {

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

        holder.textViewTimestamp.setText(listItem.get_reply_time_stamp());
        holder.textViewHead.setText(listItem.get_reply());
        holder.textViewPoints.setText("Points: "+ listItem.get_reply_up_votes());
        if(listItem.get_reply_endorsed()){
            holder.textViewEndorsed.setText("Endorsed!");
        }

        Gson gson = new Gson();
        final String rep = gson.toJson(listItem);
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewEndorsed;
        public TextView textViewPoints;
        public TextView textViewTimestamp;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHe);
            textViewEndorsed = (TextView) itemView.findViewById(R.id.textViewEn);
            textViewPoints = (TextView) itemView.findViewById(R.id.textViewPo);
            textViewTimestamp = (TextView) itemView.findViewById(R.id.textViewTim);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutStudentReplies);
        }
    }

}
