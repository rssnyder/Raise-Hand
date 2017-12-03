package RecyclerViews;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sae1.raisehand.R;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Utilities.LiveFeedVolley;

/**
 * This class handles how to show the list of items in the live feed.
 */
public class MyAdapterLiveFeed extends RecyclerView.Adapter<MyAdapterLiveFeed.ViewHolder>{

    private List<JSONObject> listItems;
    private Context context;

    /**
     * Constructor to make the page display the list of live feed elements.
     * @param listItems list of live feed questions
     * @param context the instance of the app
     */
    public MyAdapterLiveFeed(List<JSONObject> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    public MyAdapterLiveFeed(List<JSONObject> listItems) {
        this.listItems = listItems;
    }

    /**
     * This method inflates the view for the handler i.e. how the page will look like when the user
     * gets to that page
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.list_item_live_feed, parent, false);
        return new ViewHolder(v);
    }
    /**
     *
     *
     * @param holder the layout that holds the live feed question
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        JSONObject listItem = listItems.get(position);

//        holder.textViewHead.setText(listItem.getText());
        try {
            holder.textViewHead.setText(listItem.get("txt").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @return the number of notifications a user has
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    /**
     * This class defines all the attributes for a holder object
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;

        public ViewHolder(View itemView){
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);

        }
    }

}
