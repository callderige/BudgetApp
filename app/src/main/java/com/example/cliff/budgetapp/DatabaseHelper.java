package com.example.cliff.budgetapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import sqlite.model.Bill;
import sqlite.model.Expense;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BudgetDatabase.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Bill.CREATE_TABLE_BILLS);
        database.execSQL(Expense.CREATE_TABLE_EXPENSES);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE " + Bill.BILLS_TABLE_NAME);
        database.execSQL("DROP TABLE " + Expense.EXPENSES_TABLE_NAME);
    }

}
