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
import Student.StudentRepliesReply;
import Utilities.Reply;
import Utilities.StringParse;

/**
 *
 * How to view the list of replies on the student side of the app
 * @author jaggarwal
 */
public class MyAdapterRepliesStudent extends RecyclerView.Adapter<MyAdapterRepliesStudent.ViewHolder> {

    private List<Reply> listItems;
    private Context context;

    /**
     * constructor to make the page display the replies of a question
     * @param listItems the list of replies
     * @param context this instance of the app
     */
    public MyAdapterRepliesStudent(List<Reply> listItems, Context context) {
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
                .inflate(R.layout.list_item_student_replies, parent, false);
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

        holder.textViewTimestamp.setText(StringParse.parseTimeStamp(listItem.getReplyTimestamp()));
        holder.textViewHead.setText(listItem.getReply());
        holder.textViewPoints.setText("Points: "+ listItem.getReplyUpvotes());
        if(listItem.getReplyEndorsed()){
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
                Intent reply = new Intent(context.getApplicationContext(), StudentRepliesReply.class);
                reply.putExtra("replyID", listItem.getReplyID());
                reply.putExtra("reply", rep);
                context.getApplicationContext().startActivity(reply);
            }
        });
        holder.reportIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        public TextView textViewHead;
        public TextView textViewEndorsed;
        public TextView textViewPoints;
        public TextView textViewTimestamp;
        public LinearLayout linearLayout;
        public Button reportIt;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHe);
            textViewEndorsed = (TextView) itemView.findViewById(R.id.textViewEn);
            textViewPoints = (TextView) itemView.findViewById(R.id.textViewPo);
            textViewTimestamp = (TextView) itemView.findViewById(R.id.textViewTim);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutStudentReplies);
            reportIt= (Button) itemView.findViewById(R.id.reportbutton);
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
