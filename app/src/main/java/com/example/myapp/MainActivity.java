package com.example.myapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView taskList;
    Button addTaskBtn;
    SearchView searchView;
    ArrayList<Task> taskArray;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = findViewById(R.id.taskList);
        addTaskBtn = findViewById(R.id.addTaskBtn);
        searchView = findViewById(R.id.searchView);

        // Initialize list & adapter
        taskArray = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskArray);
        taskList.setAdapter(taskAdapter);

        // Add task
        addTaskBtn.setOnClickListener(v -> showAddTaskDialog());

        // Delete task on long press
        taskList.setOnItemLongClickListener((parent, view, position, id) -> {
            Task selectedTask = (Task) parent.getItemAtPosition(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        taskArray.remove(selectedTask);
                        taskAdapter.getFilter().filter(""); // refresh
                        Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
            return true;
        });

        // Search filter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }
            @Override public boolean onQueryTextChange(String newText) {
                taskAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    // Dialog to add task
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Task");

        final EditText input = new EditText(this);
        input.setHint("Enter task...");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String taskText = input.getText().toString().trim();
            if (!taskText.isEmpty()) {
                taskAdapter.addTask(new Task(taskText));
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
}



