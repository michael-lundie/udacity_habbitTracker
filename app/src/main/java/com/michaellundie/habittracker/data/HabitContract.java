package com.michaellundie.habittracker.data;

import android.provider.BaseColumns;

public class HabitContract {

    private HabitContract() {
    }

    /**
     * Inner class representing the tasks table.
     */
    public static final class TaskEntry implements BaseColumns {

        /**
         * Name of database table for pets
         */
        public final static String TABLE_NAME = "tasks";

        /**
         * Unique ID number for the pet (only for use in the database table).
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the task.
         * Type: TEXT
         */
        public final static String COLUMN_TASK_TITLE = "title";

        /**
         * Task description
         * Type: INT
         */
        public final static String COLUMN_TASK_PRIORITY = "priority";

        /**
         * The date the task was started.
         * Type: DATETIME
         */
        public final static String COLUMN_TASK_SETTIME = "time_submitted";

        /**
         * Time for task completion.
         * can be null
         * Type: INTEGER
         */
        public final static String COLUMN_TASK_ALLOCTIME = "time_alloc";

        /**
         * Create priority values
         */
        public static final int PRIORITY_DEFAULT = 0;
        public static final int PRIORITY_HIGH = 2;
        public static final int PRIORITY_LOW = 1;

        /**
         * Check for valid priority values
         */
        public static boolean isValidPriority(int priority) {
            if (priority == PRIORITY_DEFAULT || priority == PRIORITY_HIGH || priority == PRIORITY_LOW) {
                return true;
            }
            return false;
        }
    }
}