
package com.aisha.studenttaskmate;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * TaskAdapter connects our ArrayList<Task> to the ListView.
 * For every task in the list, Android calls getView() to build one row.
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    /**
     * MainActivity implements this interface so it is told
     * whenever a checkbox is ticked or unticked.
     * It can then update the counts and show a feedback message.
     */
    public interface OnTaskCheckedListener {
        void onTaskCheckedChanged(Task task);
    }

    private final OnTaskCheckedListener listener;

    // Constructor: receives the screen (context), the task list,
    // and the listener (MainActivity).
    public TaskAdapter(Context context, ArrayList<Task> tasks,
                       OnTaskCheckedListener listener) {
        // 0 = we inflate the row layout ourselves in getView()
        super(context, 0, tasks);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        // Reuse an old row if Android gives us one; otherwise inflate a new one.
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_task, parent, false);
        }

        // The task that belongs to this row
        final Task task = getItem(position);

        // Find the views inside the row (IDs come from item_task.xml)
        CheckBox checkBox = row.findViewById(R.id.checkboxCompleted);
        TextView titleText = row.findViewById(R.id.textTaskTitle);
        TextView priorityText = row.findViewById(R.id.textTaskPriority);
        TextView statusText = row.findViewById(R.id.textTaskStatus);

        // Safety check: if there is no task, just return the row unchanged
        if (task == null) {
            return row;
        }

        // --- Title ---
        titleText.setText(task.getTitle());

        // --- Priority: written in words AND colored ---
        priorityText.setText(getContext().getString(
                R.string.priority_label, task.getPriority()));
        priorityText.setTextColor(ContextCompat.getColor(
                getContext(), getPriorityColor(task.getPriority())));

        // --- Status and strikethrough ---
        if (task.isCompleted()) {
            statusText.setText(R.string.status_completed);
            // Draw a line through the title
            titleText.setPaintFlags(
                    titleText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // Completed tasks use the grey (secondary) color
            titleText.setTextColor(ContextCompat.getColor(
                    getContext(), R.color.text_grey));
        } else {
            statusText.setText(R.string.status_pending);
            // Remove the line through the title
            titleText.setPaintFlags(
                    titleText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            titleText.setTextColor(ContextCompat.getColor(
                    getContext(), R.color.text_dark));
        }

        // --- Checkbox ---
        // Remove the old listener first. Rows are reused, so an old listener
        // could fire by mistake when we call setChecked() below.
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(task.isCompleted());

        // Now listen for the student ticking or unticking the box
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);   // save the change in the Task object
            notifyDataSetChanged();         // redraw the rows (strikethrough, status)
            if (listener != null) {
                listener.onTaskCheckedChanged(task);  // tell MainActivity
            }
        });

        return row;
    }

    // Chooses the color for the priority word (colors.xml from Step 3).
    // The text must match exactly what AddTaskActivity saves:
    // "Low", "Medium" or "High".
    private int getPriorityColor(String priority) {
        if ("High".equals(priority)) {
            return R.color.priority_high_color;
        } else if ("Medium".equals(priority)) {
            return R.color.priority_medium_color;
        } else {
            return R.color.priority_low_color;
        }
    }
}