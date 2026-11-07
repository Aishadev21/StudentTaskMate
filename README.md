# StudentTaskMate



A simple task-management app for university students, built as an

interactive prototype for the Human-Computer Interaction (HCI) mini project.



- **Student:** Aisha Siraj

- **Course:** Human Computer Interaction

- **Lecturer:** Ayamie Kundando



## What the app does



StudentTaskMate helps students keep track of assignments and study tasks.



- The main screen shows a welcome message, a list of tasks, and how many

&#x20; tasks are pending and completed.

- Each task shows its title, its priority (Low, Medium or High) and its status.

- Students can add a task, choose its priority, and tick a checkbox to mark it

&#x20; as completed.

- Empty titles are rejected with a clear error message.

- Short on-screen messages confirm when a task is added or its status changes.

- A helpful message appears when there are no tasks yet.



## Target users



University students who want a quick, uncluttered way to list their

assignments and study tasks and see what is still left to do. The app is

designed for people who are busy and do not want to learn a complicated tool.



## Screens



1. **Main screen (MainActivity):** header, task counts, task list, Add Task button.

2. **Add Task screen (AddTaskActivity):** title field, priority choice, Save and Cancel buttons.



## Human-centered design choices



- Clear labels and readable text sizes.

- Consistent spacing and a simple purple colour scheme.

- Large touch targets (buttons are 56dp tall; checkboxes and radio buttons are at least 48dp).

- Priority is shown in words as well as colour, so meaning does not depend on colour alone.

- Immediate feedback after adding a task or ticking a checkbox.



## Technology



- Android Studio

- Java (no Kotlin, no Jetpack Compose)

- XML layouts

- AndroidX AppCompatActivity and Material Components

- Tasks are stored in an in-memory ArrayList

- No database, Firebase, server, or paid services



## How to run the app



1. Open the project folder in Android Studio.

2. Wait for Gradle sync to finish (a progress bar appears at the bottom).

3. Choose a device in the toolbar: an emulator created in Tools > Device Manager,

&#x20;  or a real Android phone with USB debugging turned on.

4. Click the green Run button.

5. The app opens on the main screen.



The app requires Android 7.0 (API 24) or newer.



## How to use the app



1. Tap **Add Task**.

2. Type a task title and choose Low, Medium or High priority.

3. Tap **Save** to add the task, or **Cancel** to go back without adding it.

4. Tick a task's checkbox to mark it as completed. Untick it to mark it pending again.



## Known limitations



- Tasks are kept in memory only, so they are lost when the app is fully closed.

&#x20; This is intentional for this prototype, because a database was outside the

&#x20; assignment requirements.

- There is no way yet to edit or delete a task.



## Testing



- Tested by the developer on a Pixel 6 Pro emulator (Android API 37).

- Testing with another student: [to be added after the test session]



## Possible future improvements



- Save tasks permanently so they survive closing the app.

- Edit and delete tasks.

- Due dates and reminders.

