package com.example.cliff.budgetapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sqlite.model.Bill;
import sqlite.model.Expense;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database name
    private static final String DATABASE_NAME = "BudgetDatabase.db";

    //Bill table column names
    private static final String BILLS_TABLE_NAME = "bills";
    private static final String COLUMN_BILL_ID = "bill_id";
    private static final String COLUMN_BILL_NAME = "bill_name";
    private static final String COLUMN_BILL_COST = "bill_cost";
    private static final String COLUMN_BILL_FUND = "bill_fund";
    private static final String COLUMN_BILL_DUE = "bill_due";
    private static final String COLUMN_BILL_PAID = "bill_paid";

    //Create bill table SQL statement
    private static final String CREATE_TABLE_BILLS =
            "CREATE TABLE IF NOT EXISTS " + BILLS_TABLE_NAME + "("
                    + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_BILL_NAME + " TEXT,"
                    + COLUMN_BILL_COST + " INTEGER,"
                    + COLUMN_BILL_FUND + " INTEGER,"
                    + COLUMN_BILL_DUE + " TEXT,"
                    + COLUMN_BILL_PAID + " INTEGER"
                    + ")";

    //Drop bill table SQL statement
    private static final String DROP_TABLE_BILLS = "DROP TABLE IF EXISTS " + BILLS_TABLE_NAME;


    //Expense table column names
    private static final String EXPENSES_TABLE_NAME = "expenses";
    private static final String COLUMN_EXPENSE_ID = "expense_id";
    private static final String COLUMN_EXPENSE_NAME = "expense_name";
    private static final String COLUMN_EXPENSE_SPENT = "expense_spent";
    private static final String COLUMN_EXPENSE_LIMIT = "expense_limit";
    private static final String COLUMN_EXPENSE_TYPE = "expense_type";

    //Create expense table SQL statement
    private static final String CREATE_TABLE_EXPENSES =
            "CREATE TABLE IF NOT EXISTS " + EXPENSES_TABLE_NAME + "("
                    + COLUMN_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EXPENSE_NAME + " TEXT,"
                    + COLUMN_EXPENSE_SPENT + " NUMERIC,"
                    + COLUMN_EXPENSE_LIMIT + " NUMERIC,"
                    + COLUMN_EXPENSE_TYPE + " TEXT"
                    + ")";

    //Drop expense table SQL statement
    private static final String DROP_TABLE_EXPENSE = "DROP TABLE IF EXISTS " + EXPENSES_TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_BILLS);
        database.execSQL(CREATE_TABLE_EXPENSES);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(DROP_TABLE_BILLS);
        database.execSQL(DROP_TABLE_EXPENSE);
    }

    public void recreateTables() {
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL(DROP_TABLE_BILLS);
        database.execSQL(DROP_TABLE_EXPENSE);

        database.execSQL(CREATE_TABLE_BILLS);
        database.execSQL(CREATE_TABLE_EXPENSES);
    }

    /**
     * Create a new bill to be paid
     * @param name The name of the bill
     * @param cost How much the bill will cost when do
     * @param fund Current money set aside for bill
     * @param due Date bill is due
     * @param paid If bill is paid; 0 for no, 1 for yes
     * @return the Id of the newly created bill, returns -1 if an error occurred
     */
    public long createBill(String name, double cost, double fund, String due, int paid) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BILL_NAME, name);
        contentValues.put(COLUMN_BILL_COST, cost);
        contentValues.put(COLUMN_BILL_FUND, fund);
        contentValues.put(COLUMN_BILL_DUE, due);
        contentValues.put(COLUMN_BILL_PAID, paid);

        //Id of inserted row, if Id equals -1 then an error occurred on insert
        long billId = database.insert(BILLS_TABLE_NAME, null, contentValues);
        database.close();

        return billId;
    }

    /**
     * Returns all bills
     * @return A List of all bills as Bill objects
     */
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String selectAllBills = "SELECT * FROM " + BILLS_TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAllBills, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Bill bill = new Bill(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BILL_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_BILL_COST)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_BILL_FUND)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_BILL_DUE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_PAID))
                );

                bills.add(bill);

                cursor.moveToNext();
            }
        }

        cursor.close();

        return bills;
    }

    /**
     * Updates a single bill using the bill's ID
     * @param bill The bill to be updated
     * @return the number of rows affected
     */
    public int updateBill(Bill bill) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BILL_NAME, bill.getName());
        contentValues.put(COLUMN_BILL_COST, bill.getCost());
        contentValues.put(COLUMN_BILL_FUND, bill.getFund());
        contentValues.put(COLUMN_BILL_DUE, bill.getDue());
        contentValues.put(COLUMN_BILL_PAID, bill.getPaid());

        return database.update(BILLS_TABLE_NAME, contentValues, COLUMN_BILL_ID + " = ?",
                new String[] { String.valueOf(bill.getId()) } );
    }

    /**
     * Deletes a single bill using the bill's ID
     * @param bill The bill to be updated
     * @return the number of rows affected
     */
    public int deleteBill(Bill bill) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(BILLS_TABLE_NAME, COLUMN_BILL_ID + " = ?",
                new String[] { String.valueOf(bill.getId()) } );
    }

    /**
     *
     * @param name The name of the expense
     * @param spent The money spent so far on the expense
     * @param limit The limit of how much can be spent on the expense
     * @param type The type of expense, e.g. food, entertainment, etc.
     * @return the Id of the newly created expense, returns -1 if an error occurred
     */
    public long createExpense(String name, double spent, double limit, String type) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSE_NAME, name);
        contentValues.put(COLUMN_EXPENSE_SPENT, spent);
        contentValues.put(COLUMN_EXPENSE_LIMIT, limit);
        contentValues.put(COLUMN_EXPENSE_TYPE, type);

        //Id of inserted row, if Id equals -1 then an error occurred on insert
        long expenseId = database.insert(EXPENSES_TABLE_NAME, null, contentValues);
        database.close();

        return expenseId;
    }

    /**
     * Returns all expenses
     * @return a List of all expenses as Expense objects
     */
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String selectAllExpenses = "SELECT * FROM " + EXPENSES_TABLE_NAME;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAllExpenses, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Expense expense = new Expense(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_EXPENSE_SPENT)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_EXPENSE_LIMIT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_TYPE))
                );

                expenses.add(expense);

                cursor.moveToNext();
            }
        }

        cursor.close();

        return expenses;
    }

    /**
     *
     * @param expense The expense to be updated
     * @return the number of rows affected
     */
    public int updateExpense(Expense expense) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EXPENSE_NAME, expense.getName());
        contentValues.put(COLUMN_EXPENSE_SPENT, expense.getSpent());
        contentValues.put(COLUMN_EXPENSE_LIMIT, expense.getLimit());
        contentValues.put(COLUMN_EXPENSE_TYPE, expense.getType());

        return database.update(EXPENSES_TABLE_NAME, contentValues, COLUMN_EXPENSE_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) } );
    }

    /**
     * Deletes a single expense using the expense's ID
     * @param expense The expense to be deleted
     * @return the number of rows affected
     */
    public int deleteExpense(Expense expense) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(EXPENSES_TABLE_NAME, COLUMN_EXPENSE_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) } );
    }
}
