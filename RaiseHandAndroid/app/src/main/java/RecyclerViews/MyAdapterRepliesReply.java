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

import Activities.RepliesReply;
import Utilities.Reply;

/**
 * Created by jaggarwal on 11/1/17.
 */

public class MyAdapterRepliesReply extends RecyclerView.Adapter<MyAdapterRepliesReply.ViewHolder> {

    private List<Reply> listItems;
    private Context context;

    public MyAdapterRepliesReply(List<Reply> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_replies_reply, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final Reply listItem = listItems.get(position);
        holder.textViewTimestamp.setText(listItem.getReplyTimestamp());
        holder.textViewHead.setText(listItem.getReply());
        holder.textViewPoints.setText("Points: " + listItem.getReplyUpvotes());
        if (listItem.getReplyEndorsed()){
            holder.textViewEndorsed.setText("Endorsed!");
        }

        Gson gson = new Gson();
        final String rep = gson.toJson(listItem);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reply = new Intent(context.getApplicationContext(), RepliesReply.class);
                reply.putExtra("replyID", listItem.getReplyID());
                reply.putExtra("reply", rep);
                context.getApplicationContext().startActivity(reply);
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewEndorsed;
        public TextView textViewPoints;
        public TextView textViewTimestamp;
        
        public LinearLayout linearLayout;

        
        public ViewHolder(View itemView){
            super(itemView);
            textViewEndorsed = (TextView)itemView.findViewById(R.id.textViewEndrsed);
            textViewHead = (TextView)itemView.findViewById(R.id.textViewHea);
            textViewPoints = (TextView)itemView.findViewById(R.id.textViewPonts);
            textViewTimestamp = (TextView)itemView.findViewById(R.id.textViewTimeStap);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayoutRepliesReply);
            
        }
        
    }
}
