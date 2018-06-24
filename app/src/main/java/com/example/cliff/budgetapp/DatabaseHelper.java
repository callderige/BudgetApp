package com.example.cliff.budgetapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BudgetDatabase.db";

    private static final String BILLS_TABLE_NAME = "bills";
    private static final String COLUMN_BILL_ID = "bill_id";
    private static final String COLUMN_BILL_NAME = "bill_name";
    private static final String COLUMN_BILL_COST = "bill_cost";
    private static final String COLUMN_BILL_FUND = "bill_fund";
    private static final String COLUMN_BILL_DUE = "bill_due";
    private static final String COLUMN_BILL_PAID = "bill_paid";

    private static final String CREATE_TABLE_BILLS =
            "CREATE TABLE " + BILLS_TABLE_NAME + "("
                    + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BILL_NAME + " TEXT,"
                    + COLUMN_BILL_COST + " NUMERIC,"
                    + COLUMN_BILL_FUND + " NUMERIC,"
                    + COLUMN_BILL_DUE + " TEXT,"
                    + COLUMN_BILL_PAID + " INTEGER,"
                    + ")";

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
        database.execSQL(CREATE_TABLE_BILLS);
        database.execSQL(CREATE_TABLE_EXPENSES);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE " + BILLS_TABLE_NAME);
        database.execSQL("DROP TABLE " + EXPENSES_TABLE_NAME);
    }
}
