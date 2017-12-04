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
 * This class handles how to show the list of replies to a teacher
 * @author joel2
 */
public class MyAdapterReplies extends RecyclerView.Adapter<MyAdapterReplies.ViewHolder> {

    private List<Reply> listItems;
    private Context context;
    /**
     * constructor to make the page display the replies of a question
     * @param listItems the list of replies
     * @param context this instance of the app
     */
    public MyAdapterReplies(List<Reply> listItems, Context context) {
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
                .inflate(R.layout.list_item_teacher_replies, parent, false);
        return new ViewHolder(v);
    }
    /**
     *
     * The functionality specific to a particular reply. It sets the timestamp,
     * text, upvotes, and if it is endorsed or not
     *
     * @param holder the layout that holds the reply
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Reply listItem = listItems.get(position);
        holder.textViewTime.setText(StringParse.parseTimeStamp(listItem.getReplyTimestamp()));
        holder.textViewH.setText(listItem.getReply());
        holder.textViewP.setText("Points: "+ listItem.getReplyUpvotes());
        if(listItem.getReplyEndorsed()){
            holder.textViewE.setText("Endorsed!");
        }
        else{
            holder.textViewE.setText(" ");
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
        holder.reportb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //report the reply
                listItem.report();
            }
        });
    }

    /**
     *
     * @return the number of replies a question has
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    /**
     * This class defines all the attributes for a holder object i.e. reply
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewH, textViewE, textViewP, textViewTime, textViewReplies;
        public LinearLayout linearLayout;
        public Button reportb;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewH = (TextView) itemView.findViewById(R.id.textViewH);
            textViewE= (TextView) itemView.findViewById(R.id.textViewE);
            textViewP= (TextView) itemView.findViewById(R.id.textViewP);
            textViewTime= (TextView) itemView.findViewById(R.id.textViewTime);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutTeacherReplies);
            reportb= (Button) itemView.findViewById(R.id.reportB);
        }
    }

    // Clean all elements of the recycler
    public void clear(){
        listItems.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Reply> list) {
        listItems.addAll(list);
        notifyDataSetChanged();
    }

}
