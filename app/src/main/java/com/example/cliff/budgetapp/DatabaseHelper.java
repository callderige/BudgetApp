package com.example.cliff.budgetapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import sqlite.model.Bill;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BudgetDatabase.db";

    private static final String EXPENSES_TABLE_NAME = "expenses";
    private static final String COLUMN_EXPENSE_ID = "expense_id";
    private static final String COLUMN_EXPENSE_NAME = "expense_name";
    private static final String COLUMN_EXPENSE_SPENT = "expense_spent";
    private static final String COLUMN_EXPENSE_LIMIT = "expense_limit";
    private static final String COLUMN_EXPENSE_TYPE = "expense_type";

    private static final String CREATE_TABLE_EXPENSES =
            "CREATE TABLE " + EXPENSES_TABLE_NAME + "("
                    + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EXPENSE_NAME + " TEXT,"
                    + COLUMN_EXPENSE_SPENT + " NUMERIC,"
                    + COLUMN_EXPENSE_LIMIT + " NUMERIC,"
                    + COLUMN_EXPENSE_TYPE + " TEXT,"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Bill.CREATE_TABLE_BILLS);
        database.execSQL(CREATE_TABLE_EXPENSES);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE " + Bill.BILLS_TABLE_NAME);
        database.execSQL("DROP TABLE " + EXPENSES_TABLE_NAME);
    }

}
