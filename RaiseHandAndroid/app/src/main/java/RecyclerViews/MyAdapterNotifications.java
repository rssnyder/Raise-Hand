package RecyclerViews;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sae1.raisehand.R;

import java.util.List;
/**
 * This class handles how to show the list of notifications to a teacher
 * @author joel2
 */
public class MyAdapterNotifications extends RecyclerView.Adapter<MyAdapterNotifications.ViewHolder> {

    private List<ListItemTeacherNotifications> listItems;
    private Context context;

    /**
     * constructor to make the page display the list of notifications
     * @param listItems the list of notifications
     * @param context this instance of the app
     */
    public MyAdapterNotifications(List<ListItemTeacherNotifications> listItems, Context context) {
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
                .inflate(R.layout.list_item_teacher_notifications, parent, false);
        return new ViewHolder(v);
    }
    /**
     *
     * The functionality specific to a particular notification. It sets the title and description
     *
     * @param holder the layout that holds the notification
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItemTeacherNotifications listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
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
     * This class defines all the attributes for a holder object i.e. notification
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
        }
    }

}
