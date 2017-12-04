package RecyclerViews;

/**
 * This class handles how to show the list of topics to a student
 * @author jaggarwal
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.sae1.raisehand.R;
import Student.StudentQuestions;
import com.google.gson.Gson;
import java.util.List;
import Utilities.Topics;

public class MyAdapterTopicsStudent extends RecyclerView.Adapter<MyAdapterTopicsStudent.ViewHolder> {

    private List<Topics> listItems;
    private Context context;

    /**
     * constructor to make the page display the list of topics in a class
     * @param listItems the list of topics in the class
     * @param context this instance of the app
     */
    public MyAdapterTopicsStudent(List<Topics> listItems, Context context) {
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
                .inflate(R.layout.list_item_student_topics, parent, false);
        return new ViewHolder(v);
    }
    /**
     *
     * The functionality specific to a particular topic. It sets title and description
     *
     * @param holder the layout that holds the topic
     * @param position what item in the holder was clicked
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Topics listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getTitle());
        holder.textViewDesc.setText(listItem.getDescription());
        Gson gson = new Gson();
        final String topic = gson.toJson(listItem);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to the topics' questions
                Intent studentQuestions = new Intent(context.getApplicationContext(), StudentQuestions.class);
                studentQuestions.putExtra("topicsID", listItem.getID());
                studentQuestions.putExtra("topic", topic);
                context.getApplicationContext().startActivity(studentQuestions);
            }
        });
    }
    /**
     *
     * @return the number of topics a class has
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    /**
     * This class defines all the attributes for a holder object i.e. topic
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutStudentTopics);
        }
    }
}
