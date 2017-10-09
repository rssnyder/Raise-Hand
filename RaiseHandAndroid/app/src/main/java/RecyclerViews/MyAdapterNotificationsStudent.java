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

import utils.Question;

public class MyAdapterNotificationsStudent extends RecyclerView.Adapter<MyAdapterNotificationsStudent.ViewHolder>{

    private List<Question> listItems;
    private Context context;

    public MyAdapterNotificationsStudent(List<Question> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapterNotificationsStudent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_student_notifications, parent, false);
        return new MyAdapterNotificationsStudent.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapterNotificationsStudent.ViewHolder holder, int position) {
        Question listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getQuestionTitle());
        holder.textViewDesc.setText(listItem.getQuestionDescription());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

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
