package RecyclerViews;

/**
 * Created by jaggarwal on 10/9/17.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sae1.raisehand.R;

import java.util.List;

import utils.Classes;


public class MyAdapterClassesStudent extends RecyclerView.Adapter<MyAdapterClassesStudent.ViewHolder>{
    private List<ListItemStudentClasses> listItems;
    private Context context;

    public MyAdapterClassesStudent(List<Classes> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapterClassesStudent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_student_classes, parent, false);
        return new MyAdapterClassesStudent.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapterClassesStudent.ViewHolder holder, int position) {
        ListItemStudentClasses listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO go to the class' Topics
                Intent studentTopics = new Intent(context.getApplicationContext(), com.example.sae1.raisehand.student_topics.class);
                context.getApplicationContext().startActivity(studentTopics);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

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
