package com.example.myapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.*;
import android.widget.*;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.task_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.taskCheckBox);
        TextView textView = convertView.findViewById(R.id.taskTextView);

        textView.setText(task.getText());
        checkBox.setChecked(task.isCompleted());

        // Update text appearance when checked
        if (task.isCompleted()) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            textView.setTextColor(0xFF888888);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            textView.setTextColor(0xFF000000);
        }

        // Handle checkbox change
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            notifyDataSetChanged();
        });

        return convertView;
    }
}


