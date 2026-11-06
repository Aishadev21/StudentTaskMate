
package com.aisha.studenttaskmate;

/**
 * Task represents ONE task in the app.
 * Every item in our ArrayList<Task> is a Task object.
 */
public class Task {

    // The text the student types, e.g. "Finish HCI report"
    private String title;

    // "Low", "Medium" or "High"
    private String priority;

    // true = finished, false = still pending
    private boolean completed;

    // Constructor: runs when we create a new task with "new Task(...)".
    // A brand-new task always starts as NOT completed.
    public Task(String title, String priority) {
        this.title = title;
        this.priority = priority;
        this.completed = false;
    }

    // Getters: let other classes READ the values
    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    // Setter: lets other classes CHANGE the completed value
    // (used when the student ticks or unticks the checkbox)
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}