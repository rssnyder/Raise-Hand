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
import com.google.gson.Gson;
import java.util.List;
import Teacher.TeacherQuestions;
import Utilities.Topics;

/**
 * This class handles how to show the list of topics to a teacher
 * @author joel2
 */
public class MyAdapterTopics extends RecyclerView.Adapter<MyAdapterTopics.ViewHolder> {

    private List<Topics> listItems;
    private Context context;
    /**
     * constructor to make the page display the list of topics in a class
     * @param listItems the list of topics in the class
     * @param context this instance of the app
     */
    public MyAdapterTopics(List<Topics> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }
    // Clean all elements of the recycler
    public void clear() {
        listItems.clear();
        notifyDataSetChanged();
    }


// Add a list of items -- change to type used
    public void addAll(List<Topics> list) {
        listItems.addAll(list);
        notifyDataSetChanged();
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
                .inflate(R.layout.list_item_teacher_topics, parent, false);
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
        Gson gson2 = new Gson();
        final String topic = gson2.toJson(listItem);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to the topics' questions
                Intent teacherQuestions = new Intent(context.getApplicationContext(), TeacherQuestions.class);
                teacherQuestions.putExtra("topicsID", listItem.getID());
                teacherQuestions.putExtra("topic", topic);
                context.getApplicationContext().startActivity(teacherQuestions);
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
     * This class defines all the attributes for a holder object i.e. a topic
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutTeacherTopics);
        }
    }

}
