package com.example.myapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> implements Filterable {

    private Context context;
    private List<Task> originalList;   // all tasks
    private List<Task> filteredList;   // currently displayed list

    public TaskAdapter(Context context, List<Task> taskList) {
        super(context, 0, taskList);
        this.context = context;
        this.originalList = taskList;
        this.filteredList = new ArrayList<>(taskList);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Nullable
    @Override
    public Task getItem(int position) {
        return filteredList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        }

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        TextView textView = convertView.findViewById(R.id.taskText);

        Task task = getItem(position);
        if (task != null) {
            textView.setText(task.getText());
            checkBox.setChecked(task.isCompleted());

            // Strike-through when completed
            if (task.isCompleted()) {
                textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            // Toggle completed on checkbox click
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                task.setCompleted(isChecked);
                notifyDataSetChanged();
            });
        }

        return convertView;
    }

    // --- Add task helper ---
    public void addTask(Task task) {
        originalList.add(task);
        getFilter().filter(""); // refresh to show all
    }

    // --- Filter for search ---
    @NonNull
    @Override
    public Filter getFilter() {
        return taskFilter;
    }

    private Filter taskFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filtered = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(originalList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Task task : originalList) {
                    if (task.getText().toLowerCase().contains(filterPattern)) {
                        filtered.add(task);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList.clear();
            filteredList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}



