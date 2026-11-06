package com.aisha.studenttaskmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

/**
 * MainActivity: the main screen.
 * It shows the task list, the pending/completed counts,
 * and the Add Task button.
 */
public class MainActivity extends AppCompatActivity {

    // All tasks live here, in memory only (no database).
    // They are lost when the app is fully closed.
    private final ArrayList<Task> taskList = new ArrayList<>();

    private TaskAdapter adapter;
    private TextView textPendingCount;
    private TextView textCompletedCount;

    // Opens AddTaskActivity and receives the result when it closes
    private ActivityResultLauncher<Intent> addTaskLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Keep content clear of the status bar and navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the views (IDs come from activity_main.xml)
        textPendingCount = findViewById(R.id.textPendingCount);
        textCompletedCount = findViewById(R.id.textCompletedCount);
        ListView listViewTasks = findViewById(R.id.listViewTasks);
        TextView textEmpty = findViewById(R.id.textEmpty);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);

        // Create the adapter. The last part (a lambda) runs every time
        // a checkbox is ticked or unticked.
        adapter = new TaskAdapter(this, taskList, task -> {
            updateCounts();
            // Feedback for the student
            if (task.isCompleted()) {
                Toast.makeText(this, R.string.toast_task_completed,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.toast_task_pending,
                        Toast.LENGTH_SHORT).show();
            }
        });
        listViewTasks.setAdapter(adapter);

        // Show the "No tasks yet" message whenever the list is empty
        listViewTasks.setEmptyView(textEmpty);

        // Prepare to receive the new task from AddTaskActivity
        addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Only add a task if the student tapped Save (RESULT_OK)
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String title = result.getData()
                                .getStringExtra(AddTaskActivity.EXTRA_TITLE);
                        String priority = result.getData()
                                .getStringExtra(AddTaskActivity.EXTRA_PRIORITY);

                        if (title != null && priority != null) {
                            taskList.add(new Task(title, priority));
                            adapter.notifyDataSetChanged();  // redraw the list
                            updateCounts();
                            Toast.makeText(this, R.string.toast_task_added,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Add Task button: open the second screen
        buttonAddTask.setOnClickListener(v ->
                addTaskLauncher.launch(new Intent(this, AddTaskActivity.class)));

        // Show "Pending: 0" and "Completed: 0" at the start
        updateCounts();
    }

    // Counts the tasks and updates the two text labels
    private void updateCounts() {
        int pending = 0;
        int completed = 0;
        for (Task task : taskList) {
            if (task.isCompleted()) {
                completed++;
            } else {
                pending++;
            }
        }
        textPendingCount.setText(getString(R.string.pending_count, pending));
        textCompletedCount.setText(getString(R.string.completed_count, completed));
    }
}