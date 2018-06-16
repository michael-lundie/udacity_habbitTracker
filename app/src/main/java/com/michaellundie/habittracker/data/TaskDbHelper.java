package com.michaellundie.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.michaellundie.habittracker.data.HabitContract.TaskEntry;

public class TaskDbHelper extends SQLiteOpenHelper {

    /** Name of database file */
    private static String DATABASE_NAME = "habittracker.db";

    /**
     * Database version. Increment version number upon any change to the schema.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Construct new instance of {@link TaskDbHelper}
     *
     * @param context of the app
     */
    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Let create our database (or skip this step if it already exists)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Let@s build a string which contains our sql to form the initial table/s.
        String SQL_CREATE_TASKS_TABLE =  "CREATE TABLE " + TaskEntry.TABLE_NAME + " ("
                + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TaskEntry.COLUMN_TASK_TITLE + " TEXT NOT NULL, "
                + TaskEntry.COLUMN_TASK_PRIORITY + " INTEGER NOT NULL DEFAULT 0, "
                + TaskEntry.COLUMN_TASK_SETTIME + " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                + TaskEntry.COLUMN_TASK_ALLOCTIME + " INTEGER);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    /**
     * Method called in the case that the database version needs to be updated.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nothing to do here right now, since the database is at version 1.
    }
}