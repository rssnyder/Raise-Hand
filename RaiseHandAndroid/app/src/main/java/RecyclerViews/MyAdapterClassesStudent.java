package RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.sae1.raisehand.R;
import Student.StudentTopics;
import java.util.List;
import Utilities.Classes;

/**
 *
 * How to view the list of classes on the student side of the app
 * @author jaggarwal
 */

public class MyAdapterClassesStudent extends RecyclerView.Adapter<MyAdapterClassesStudent.ViewHolder>{
    private List<Classes> listItems;
    private Context context;

    /**
     * constructor to make the page display the list of classes a student is in
     * @param listItems the list of classes a student is in
     * @param context this instance of the app
     */
    public MyAdapterClassesStudent(List<Classes> listItems, Context context) {
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
                .inflate(R.layout.list_item_student_classes, parent, false);
        return new ViewHolder(v);
    }
    /**
     *
     * The functionality specific to a particular class. It sets the class id and title
     *
     * @param holder the layout that holds the class
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Classes listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getTitle());
        holder.textViewDesc.setText(listItem.getClassID());

        //get the classID of the class that the user clicked on
        //pass it to the TeacherTopics intent. It is retrieved with a Bundle object
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent studentTopics = new Intent(context.getApplicationContext(), StudentTopics.class);
                studentTopics.putExtra("classID", listItem.getClassID());
                listItem.get_topics();
                context.getApplicationContext().startActivity(studentTopics);
            }
        });
    }

    /**
     * @return the number of classes that should be listed
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    /**
     * This class defines all the attributes for a holder object i.e. class
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutStudentClasses);
        }
    }
}
