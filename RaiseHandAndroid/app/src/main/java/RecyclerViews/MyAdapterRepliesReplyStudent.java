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
 * Created by jaggarwal on 11/28/17.
 */

public class MyAdapterRepliesReplyStudent extends RecyclerView.Adapter<MyAdapterRepliesReplyStudent.ViewHolder> {
    private List<Reply> listItems;
    private Context context;

    /**
     * constructor to make the page display the replies to a reply
     * @param listItems the list of replies
     * @param context this instance of the app
     */
    public MyAdapterRepliesReplyStudent(List<Reply> listItems, Context context){
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final Reply listItem = listItems.get(position);
        holder.textViewTimestam.setText(StringParse.parseTimeStamp(listItem.getReplyTimestamp()));
        holder.textViewHea.setText(listItem.getReply());
        holder.textViewPoint.setText("Points: " + listItem.getReplyUpvotes());
        if (listItem.getReplyEndorsed()){
            holder.textViewEndorse.setText("Endorsed!");
        }
        else{
            holder.textViewEndorse.setText(" ");
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
        holder.flagge.setOnClickListener(new View.OnClickListener() {
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
        public TextView textViewHea;
        public TextView textViewEndorse;
        public TextView textViewPoint;
        public TextView textViewTimestam;
        public Button flagge;
        public LinearLayout linearLayout;


        public ViewHolder(View itemView){
            super(itemView);
            textViewEndorse = (TextView)itemView.findViewById(R.id.textViewEndrsed);
            textViewHea = (TextView)itemView.findViewById(R.id.textViewHea);
            textViewPoint = (TextView)itemView.findViewById(R.id.textViewPonts);
            textViewTimestam = (TextView)itemView.findViewById(R.id.textViewTimeStap);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayoutRepliesReply);
            flagge = (Button) itemView.findViewById(R.id.flagrep);

        }

    }

}
