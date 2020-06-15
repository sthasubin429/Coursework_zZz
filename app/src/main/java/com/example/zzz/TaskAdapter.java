package com.example.zzz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private Activity context;
    private List<Task> taskList;


    /**
     * Adapter class that returns a view from items in list view
     * @param context
     * @param singerList
     */
    public TaskAdapter(Activity context, List<Task> singerList) {
        super(context, R.layout.tasks_item_layout, singerList);
        this.context = context;
        this.taskList = singerList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.tasks_item_layout, null , true);

        TextView taskName = view.findViewById(R.id.task_list_name);
        TextView taskDueDate = view.findViewById(R.id.task_list_due_date);
        TextView taskCreatedBy = view.findViewById(R.id.task_list_created_by);
        TextView taskStatus = view.findViewById(R.id.task_list_status);

        Task taskObj = taskList.get(position);

        taskName.setText(taskObj.getName());
        taskDueDate.setText(taskObj.getDueDate());
        taskCreatedBy.setText(taskObj.getCreatedBy());
        taskStatus.setText(taskObj.getStatus());

        return view;
    }
}
