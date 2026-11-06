
package com.aisha.studenttaskmate;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * AddTaskActivity: the second screen.
 * The student types a title, picks a priority, and taps Save or Cancel.
 * On Save, the title and priority are sent back to MainActivity.
 */
public class AddTaskActivity extends AppCompatActivity {

    // "Keys" used to pack the data into the result and unpack it in MainActivity
    public static final String EXTRA_TITLE = "task_title";
    public static final String EXTRA_PRIORITY = "task_priority";

    private TextInputLayout layoutTaskTitle;
    private TextInputEditText editTaskTitle;
    private RadioGroup radioGroupPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        // Keep content clear of the status bar and navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the views (IDs come from activity_add_task.xml)
        layoutTaskTitle = findViewById(R.id.layoutTaskTitle);
        editTaskTitle = findViewById(R.id.editTaskTitle);
        radioGroupPriority = findViewById(R.id.radioGroupPriority);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        // Remove the red error message as soon as the student starts typing
        editTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutTaskTitle.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Save button
        buttonSave.setOnClickListener(v -> saveTask());

        // Cancel button: go back without saving anything
        buttonCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    // Checks the input, then sends the new task back to MainActivity
    private void saveTask() {
        // Read the title and remove spaces at the start and end
        String title = "";
        if (editTaskTitle.getText() != null) {
            title = editTaskTitle.getText().toString().trim();
        }

        // Reject an empty title and show a helpful message
        if (title.isEmpty()) {
            layoutTaskTitle.setError(getString(R.string.error_empty_title));
            editTaskTitle.requestFocus();
            return;  // stop here: nothing is saved
        }

        // Find out which priority is selected.
        // These words must match exactly what TaskAdapter expects.
        String priority;
        int checkedId = radioGroupPriority.getCheckedRadioButtonId();
        if (checkedId == R.id.radioLow) {
            priority = "Low";
        } else if (checkedId == R.id.radioHigh) {
            priority = "High";
        } else {
            priority = "Medium";
        }

        // Pack the data and send it back to MainActivity
        Intent result = new Intent();
        result.putExtra(EXTRA_TITLE, title);
        result.putExtra(EXTRA_PRIORITY, priority);
        setResult(RESULT_OK, result);
        finish();  // close this screen
    }
}