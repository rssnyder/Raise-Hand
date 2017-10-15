package RecyclerViews;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sae1.raisehand.R;
import com.google.gson.Gson;

import java.util.List;

import app.TeacherClasses;
import app.TeacherQuestions;
import app.TeacherTopics;
import utils.Classes;
import utils.Topics;
import utils.User;

import static android.content.Context.MODE_PRIVATE;

public class MyAdapterClasses extends RecyclerView.Adapter<MyAdapterClasses.ViewHolder> {

    private List<Classes> listItems;
    private Context context;

    public MyAdapterClasses(List<Classes> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_teacher_classes, parent, false);
        return new ViewHolder(v);
    }

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
                Intent teacherTopics = new Intent(context.getApplicationContext(), TeacherTopics.class);
                teacherTopics.putExtra("classID", listItem.getClassID());
                Bundle bundle = new Bundle();
                bundle.putString("classID", listItem.getClassID());
                listItem.get_topics();
                context.getApplicationContext().
                context.getApplicationContext().startActivity(teacherTopics);
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
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutTeacherClasses);
        }
    }

}
