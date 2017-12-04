package RecyclerViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.sae1.raisehand.R;
import java.util.List;
import Utilities.Question;

/**
 *
 * How to view the list of notifications on the student side of the app
 * @author jaggarwal
 */
public class MyAdapterNotificationsStudent extends RecyclerView.Adapter<MyAdapterNotificationsStudent.ViewHolder>{

    private List<Question> listItems;
    private Context context;

    /**
     * constructor to make the page display the list of notifications
     * @param listItems the list of notifications
     * @param context this instance of the app
     */
    public MyAdapterNotificationsStudent(List<Question> listItems, Context context) {
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
    public MyAdapterNotificationsStudent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_student_notifications, parent, false);
        return new MyAdapterNotificationsStudent.ViewHolder(v);
    }
    /**
     *
     * The functionality specific to a particular notification. It sets the title and description
     *
     * @param holder the layout that holds the notification
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(MyAdapterNotificationsStudent.ViewHolder holder, int position) {
        Question listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getQuestionTitle());
        holder.textViewDesc.setText(listItem.getQuestionDescription());
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
