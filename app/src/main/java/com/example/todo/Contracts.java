package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class Contracts {

    private Contracts(){}

    public static class TasksContract implements BaseColumns {
        public static final String TABLE_NAME = "Tasks";
        public static final String task_name = "Name";
        public static final String isChecked = "Checked";
    }

    private static final String sql_addTable =
            "CREATE TABLE " + TasksContract.TABLE_NAME + " (" +
                    TasksContract._ID + " INTEGER PRIMARY KEY," +
                    TasksContract.task_name + " TEXT," +
                    TasksContract.isChecked + " INTEGER)";


    private static final String sql_delTable =
            "DROP TABLE IF EXISTS " + TasksContract.TABLE_NAME;

    public static class ContractDBHelper extends SQLiteOpenHelper{

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Contract.db";

        public ContractDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sql_addTable);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(sql_delTable);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }
}