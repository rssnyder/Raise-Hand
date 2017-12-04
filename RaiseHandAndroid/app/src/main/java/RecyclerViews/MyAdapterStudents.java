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
 * @author jaggarwal
 */
public class MyAdapterStudents extends RecyclerView.Adapter<MyAdapterStudents.ViewHolder> {

    private List<ListItemTeacherStudents> listItems;
    private Context context;
    /**
     * constructor to make the page display the list of students in a class
     * @param listItems the list of students in the class
     * @param context this instance of the app
     */
    public MyAdapterStudents(List<ListItemTeacherStudents> listItems, Context context) {
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
                .inflate(R.layout.list_item_teacher_students, parent, false);
        return new ViewHolder(v);
    }
    /**
     *
     * The functionality specific to a particular student.
     *
     * @param holder the layout that holds the student
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItemTeacherStudents listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
    }
    /**
     *
     * @return the number of students a class has
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    /**
     * This class defines all the attributes for a holder object i.e. a student
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
