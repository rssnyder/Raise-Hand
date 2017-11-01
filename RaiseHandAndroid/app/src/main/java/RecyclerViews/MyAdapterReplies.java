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

import app.RepliesReply;
import utils.Reply;

public class MyAdapterReplies extends RecyclerView.Adapter<MyAdapterReplies.ViewHolder> {

    private List<Reply> listItems;
    private Context context;

    public MyAdapterReplies(List<Reply> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_teacher_replies, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Reply listItem = listItems.get(position);
        holder.textViewTime.setText(listItem.get_reply_time_stamp());
        holder.textViewH.setText(listItem.get_reply());
        holder.textViewP.setText("Points: "+ listItem.get_reply_up_votes());
        if(listItem.get_reply_endorsed()){
            holder.textViewE.setText("Endorsed!");
        }
        Gson gson = new Gson();
        final String rep = gson.toJson(listItem);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reply = new Intent(context.getApplicationContext(), RepliesReply.class);
                reply.putExtra("replyID", listItem.get_replyID());
                reply.putExtra("reply", rep);
                Bundle bundle = new Bundle();
                bundle.putString("replyID", listItem.get_replyID());
                bundle.putString("reply", rep);
                context.getApplicationContext().startActivity(reply);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewH, textViewE, textViewP, textViewTime;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewH = (TextView) itemView.findViewById(R.id.textViewH);
            textViewE= (TextView) itemView.findViewById(R.id.textViewE);
            textViewP= (TextView) itemView.findViewById(R.id.textViewP);
            textViewTime= (TextView) itemView.findViewById(R.id.textViewTime);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutTeacherReplies);
        }
    }

}
