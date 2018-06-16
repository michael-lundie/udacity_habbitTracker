package com.michaellundie.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.michaellundie.habittracker.data.HabitContract.TaskEntry;
import com.michaellundie.habittracker.data.TaskDbHelper;

public class ReadWriteActivity extends AppCompatActivity{

    //Initialise database helper variable
    private TaskDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);

        dbHelper = new TaskDbHelper(this);
        readTask();

        final Button insertData = (Button) findViewById(R.id.button_insert);
        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTask();
                readTask();
            }
        });

        final Button emptyData = (Button) findViewById(R.id.button_empty);
        emptyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAllTasks();
                readTask();
            }
        });
    }

    /**
     * A simple method for inserting some dummy test data.
     */
    private void insertTask() {

        // Create or open a writable database using our dbHelper object.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues test object
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TASK_TITLE, "Complete Udacity Project");
        values.put(TaskEntry.COLUMN_TASK_PRIORITY, TaskEntry.PRIORITY_DEFAULT);
        values.put(TaskEntry.COLUMN_TASK_ALLOCTIME, 120);

        // insert a new row to the tasks database. Return the row ID for the new insertion.
        long newRowId = db.insert(TaskEntry.TABLE_NAME, null, values);

        // Show an error/success toast depending on the update status
        if (newRowId == -1) {
            // Error.
            Toast.makeText(this, "Error saving new task.", Toast.LENGTH_SHORT).show();
        } else {
            // Success!
            Toast.makeText(this, "New task successfully saved!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A simple method to read data currently held in the database.
     * The code includes a cursor iterator for testing purposes.
     */
    private void readTask() {

        Cursor cursor = read(null, null);

        TextView viewDataInfo = (TextView) findViewById(R.id.text_test);

        //NOTE: The following code is modified from the Udacity pets example code.
        //goo.gl/5YfNi2

        try {
            // Create a header in the Text View that looks like this:
            // _id - title - priority - settime - alloctime
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            viewDataInfo.setText("The tasks table contains " + cursor.getCount() + " tasks.\n\n");
            viewDataInfo.append(TaskEntry._ID + " - " +
                    TaskEntry.COLUMN_TASK_TITLE + " - " +
                    TaskEntry.COLUMN_TASK_PRIORITY + " - " +
                    TaskEntry.COLUMN_TASK_SETTIME + " - " +
                    TaskEntry.COLUMN_TASK_ALLOCTIME + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(TaskEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_TITLE);
            int priorityColumnIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_PRIORITY);
            int settimeColumnIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_SETTIME);
            int alloctimeColumnIndex = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_ALLOCTIME);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentTask = cursor.getString(titleColumnIndex);
                String currentPriority = cursor.getString(priorityColumnIndex);
                int currentSetTime = cursor.getInt(settimeColumnIndex);
                int currentAllocTime = cursor.getInt(alloctimeColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                viewDataInfo.append(("\n" + currentID + " - " +
                        currentTask + " - " +
                        currentPriority + " - " +
                        currentSetTime + " - " +
                        currentAllocTime));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * A simple method returning a cursor object.
     * @param whereColumns optional WHERE (column id) SQL argument
     * @param whereValues option WHERE values SQL argument
     * @return Cursor object
     */
    private Cursor read(String whereColumns, String whereValues[]) {
        // Create or open a readable database using our dbHelper object.
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies required columns from database
        String[] projection = {
                TaskEntry._ID,
                TaskEntry.COLUMN_TASK_TITLE,
                TaskEntry.COLUMN_TASK_PRIORITY,
                TaskEntry.COLUMN_TASK_SETTIME,
                TaskEntry.COLUMN_TASK_ALLOCTIME};

        // Execute read query on the tasks table.

        Cursor cursor = db.query(
                TaskEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                whereColumns,                  // The columns for the WHERE clause
                whereValues,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        return cursor;
    }

    /**
     * A simple method for emptying out TASKS table.
     */
    private void removeAllTasks() {

        // Create or open a writable database using our dbHelper object.
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //SQL Query to empty database
        db.execSQL("DELETE FROM " + TaskEntry.TABLE_NAME);
        Toast.makeText(this, "All tasks deleted!", Toast.LENGTH_SHORT).show();
    }
}