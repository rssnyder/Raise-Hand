package RecyclerViews;

/**
 * Created by jaggarwal on 10/9/17.
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sae1.raisehand.R;
import com.example.sae1.raisehand.StudentTopics;

import java.util.List;

import utils.Classes;


public class MyAdapterClassesStudent extends RecyclerView.Adapter<MyAdapterClassesStudent.ViewHolder>{
    private List<Classes> listItems;
    private Context context;

    public MyAdapterClassesStudent(List<Classes> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_student_classes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Classes listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getTitle());
        holder.textViewDesc.setText(listItem.getClassID());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO go to the class' Topics
                Intent studentTopics = new Intent(context.getApplicationContext(), StudentTopics.class);
                studentTopics.putExtra("classID", listItem.getClassID());
                Bundle bundle = new Bundle();
                bundle.putString("classID", listItem.getClassID());
                listItem.get_topics();
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
