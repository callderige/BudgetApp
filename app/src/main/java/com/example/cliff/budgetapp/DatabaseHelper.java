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
    public static final String DATABASE_NAME = "BudgetDatabase.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(Bill.CREATE_TABLE_BILLS);
        database.execSQL(Expense.CREATE_TABLE_EXPENSES);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(Bill.CREATE_TABLE_BILLS);
        database.execSQL(Expense.CREATE_TABLE_EXPENSES);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE " + Bill.BILLS_TABLE_NAME);
        database.execSQL("DROP TABLE " + Expense.EXPENSES_TABLE_NAME);
    }

    public void dropTables() {
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("DROP TABLE IF EXISTS " + Bill.BILLS_TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + Expense.EXPENSES_TABLE_NAME);
    }

    /**
     * Create a new bill to be paid
     * @param name The name of the bill
     * @param cost How much the bill will cost when do
     * @param fund Current money set aside for bill
     * @param due Date bill is due
     * @param paid If bill is paid
     * @return the Id of the newly created bill, returns -1 if an error occurred
     */
    public long createBill(String name, double cost, double fund, String due, int paid) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Bill.COLUMN_BILL_NAME, name);
        contentValues.put(Bill.COLUMN_BILL_COST, cost);
        contentValues.put(Bill.COLUMN_BILL_FUND, fund);
        contentValues.put(Bill.COLUMN_BILL_DUE, due);
        contentValues.put(Bill.COLUMN_BILL_PAID, paid);

        //Id of inserted row, if Id equals -1 then an error occurred on insert
        long billId = database.insert(Bill.BILLS_TABLE_NAME, null, contentValues);
        database.close();

        return billId;
    }

    /**
     * Returns all bills
     * @return A List of all bills as Bill objects
     */
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();

        final String SELECT_ALL_BILLS = "SELECT * FROM " + Bill.BILLS_TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(SELECT_ALL_BILLS, null);

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Bill bill = new Bill(
                        cursor.getInt(cursor.getColumnIndex(Bill.COLUMN_BILL_ID)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_BILL_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_BILL_FUND)),
                        cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_BILL_COST)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_BILL_DUE)),
                        cursor.getInt(cursor.getColumnIndex(Bill.COLUMN_BILL_PAID))
                );

                bills.add(bill);
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
        contentValues.put(Bill.COLUMN_BILL_NAME, bill.getName());
        contentValues.put(Bill.COLUMN_BILL_COST, bill.getCost());
        contentValues.put(Bill.COLUMN_BILL_FUND, bill.getFund());
        contentValues.put(Bill.COLUMN_BILL_DUE, bill.getDue());
        contentValues.put(Bill.COLUMN_BILL_PAID, bill.getPaid());

        return database.update(Bill.BILLS_TABLE_NAME, contentValues, Bill.COLUMN_BILL_ID + " = ?",
                new String[] { String.valueOf(bill.getId()) } );
    }

    /**
     * Deletes a single bill using the bill's ID
     * @param bill The bill to be updated
     * @return the number of rows affected
     */
    public int deleteBill(Bill bill) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(Bill.BILLS_TABLE_NAME, Bill.COLUMN_BILL_ID + " = ?",
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
        contentValues.put(Expense.COLUMN_EXPENSE_NAME, name);
        contentValues.put(Expense.COLUMN_EXPENSE_SPENT, spent);
        contentValues.put(Expense.COLUMN_EXPENSE_LIMIT, limit);
        contentValues.put(Expense.COLUMN_EXPENSE_TYPE, type);

        //Id of inserted row, if Id equals -1 then an error occurred on insert
        long expenseId = database.insert(Expense.EXPENSES_TABLE_NAME, null, contentValues);
        database.close();

        return expenseId;
    }

    /**
     * Returns all expenses
     * @return a List of all expenses as Expense objects
     */
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String selectAllExpenses = "SELECT * FROM " + Expense.EXPENSES_TABLE_NAME;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAllExpenses, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Expense expense = new Expense(
                        cursor.getInt(cursor.getColumnIndex(Expense.COLUMN_EXPENSE_ID)),
                        cursor.getString(cursor.getColumnIndex(Expense.COLUMN_EXPENSE_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(Expense.COLUMN_EXPENSE_SPENT)),
                        cursor.getDouble(cursor.getColumnIndex(Expense.COLUMN_EXPENSE_LIMIT)),
                        cursor.getString(cursor.getColumnIndex(Expense.COLUMN_EXPENSE_TYPE))
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
        contentValues.put(Expense.COLUMN_EXPENSE_NAME, expense.getName());
        contentValues.put(Expense.COLUMN_EXPENSE_SPENT, expense.getSpent());
        contentValues.put(Expense.COLUMN_EXPENSE_LIMIT, expense.getLimit());
        contentValues.put(Expense.COLUMN_EXPENSE_TYPE, expense.getType());

        return database.update(Expense.EXPENSES_TABLE_NAME, contentValues, Expense.COLUMN_EXPENSE_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) } );
    }

    /**
     * Deletes a single expense using the expense's ID
     * @param expense The expense to be deleted
     * @return the number of rows affected
     */
    public int deleteExpense(Expense expense) {
        SQLiteDatabase database = this.getWritableDatabase();

        return database.delete(Expense.EXPENSES_TABLE_NAME, Expense.COLUMN_EXPENSE_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) } );
    }
}
