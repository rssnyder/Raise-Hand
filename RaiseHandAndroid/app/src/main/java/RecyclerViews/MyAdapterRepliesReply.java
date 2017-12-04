package RecyclerViews;

import android.content.Context;
import android.content.Intent;
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
import Teacher.TeacherRepliesReply;
import Utilities.Reply;
import Utilities.StringParse;

/**
 * This is an adapter for reply to a reply. It represents each reply in the layout holder
 * @author jaggarwal
 */

public class MyAdapterRepliesReply extends RecyclerView.Adapter<MyAdapterRepliesReply.ViewHolder> {

    private List<Reply> listItems;
    private Context context;

    /**
     * constructor to make the page display the replies to a reply
     * @param listItems the list of replies
     * @param context this instance of the app
     */
    public MyAdapterRepliesReply(List<Reply> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    /**
     *
     * This method inflates the view for the handler i.e. how the page will look like when the user
     * gets to that page
     *
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_replies_reply, parent, false);
        return new ViewHolder(v);
    }

    /**
     *
     * This method is to get the size of the ArrayList of replies to a particular reply
     *
     * @return the size of number of replies
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    /**
     *
     * The functionality specific to a particular reply. It sets the timeStamp, the title, the points
     * and whether or not it is endorsed.
     *
     * @param holder the layout that holds the replies
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final Reply listItem = listItems.get(position);
        holder.textViewTimestamp.setText(StringParse.parseTimeStamp(listItem.getReplyTimestamp()));
        holder.textViewHead.setText(listItem.getReply());
        holder.textViewPoints.setText("Points: " + listItem.getReplyUpvotes());
        if (listItem.getReplyEndorsed()){
            holder.textViewEndorsed.setText("Endorsed!");
        }
        else{
            holder.textViewEndorsed.setText(" ");
        }

        Gson gson = new Gson();
        final String rep = gson.toJson(listItem);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reply = new Intent(context.getApplicationContext(), TeacherRepliesReply.class);
                reply.putExtra("replyID", listItem.getReplyID());
                reply.putExtra("reply", rep);
                context.getApplicationContext().startActivity(reply);
            }
        });
        holder.flagged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItem.report();
            }
        });

    }


    /**
     * This class defines all the attributes for a holder object i.e. reply
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewEndorsed;
        public TextView textViewPoints;
        public TextView textViewTimestamp;
        public Button flagged;
        public LinearLayout linearLayout;

        
        public ViewHolder(View itemView){
            super(itemView);
            textViewEndorsed = (TextView)itemView.findViewById(R.id.textViewEndrsed);
            textViewHead = (TextView)itemView.findViewById(R.id.textViewHea);
            textViewPoints = (TextView)itemView.findViewById(R.id.textViewPonts);
            textViewTimestamp = (TextView)itemView.findViewById(R.id.textViewTimeStap);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayoutRepliesReply);
            flagged= (Button) itemView.findViewById(R.id.flagrep);
            
        }
        
    }
}
