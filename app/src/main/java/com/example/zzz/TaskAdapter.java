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
        TextView taskDescription = view.findViewById(R.id.task_list_Description);
        TextView taskDueDate = view.findViewById(R.id.task_list_due_date);
        TextView taskCreatedBy = view.findViewById(R.id.task_list_created_by);

        Task taskObj = taskList.get(position);

        taskName.setText(taskObj.getName());
        taskDescription.setText(taskObj.getDescription());
        taskDueDate.setText(taskObj.getDueDate());
        taskCreatedBy.setText(taskObj.getCreatedBy());

        return view;
    }
}
