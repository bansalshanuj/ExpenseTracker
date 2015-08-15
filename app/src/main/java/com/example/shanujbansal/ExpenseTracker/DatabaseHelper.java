package com.example.shanujbansal.ExpenseTracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by shanuj.bansal on 3/28/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ExpenseTracker";

    // expenses table name
    private static final String TABLE_EXPENSES = "expenses";

    // expenses Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EXPENSE_AMOUNT = "amount";
    private static final String KEY_EXPENSE_DESC = "description";
    private static final String KEY_EXPENSE_MONTH = "month";
    private static final String KEY_EXPENSE_YEAR = "year";
    private static final String KEY_EXPENSE_CATEGORY = "category";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EXPENSE_AMOUNT + " TEXT,"
                + KEY_EXPENSE_DESC + " TEXT," + KEY_EXPENSE_CATEGORY + " TEXT," + KEY_EXPENSE_MONTH + " INTEGER," + KEY_EXPENSE_YEAR + " INTEGER" + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<Expense> getAllRecords() {
        ArrayList<Expense> expensesList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String amount = cursor.getString(1);
                String desc = cursor.getString(2);
                String category = cursor.getString(3);
                int month = Integer.parseInt(cursor.getString(4));
                int year = Integer.parseInt(cursor.getString(5));

                Expense expense = new Expense(amount, desc.trim(), month, year, category, id);

                // Adding expense to list
                expensesList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expensesList;
    }

    public void deleteRecordById(int keyId) {
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_ID + "=" + keyId;

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, KEY_ID + "=" + keyId, null);
    }

    public ArrayList<Expense> getRecordsById(int searchId) {
        ArrayList<Expense> expensesList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_ID + "=" + searchId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String amount = cursor.getString(1);
                String desc = cursor.getString(2);
                String category = cursor.getString(3);
                int month = Integer.parseInt(cursor.getString(4));
                int year = Integer.parseInt(cursor.getString(5));

                Expense expense = new Expense(amount, desc.trim(), month, year, category, id);

                // Adding expense to list
                expensesList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expensesList;
    }

    public ArrayList<Expense> getYearRecords(int searchYear) {
        ArrayList<Expense> expensesList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_YEAR + "=" + searchYear;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String amount = cursor.getString(1);
                String desc = cursor.getString(2);
                String category = cursor.getString(3);
                int month = Integer.parseInt(cursor.getString(4));
                int year = Integer.parseInt(cursor.getString(5));

                Expense expense = new Expense(amount, desc.trim(), month, year, category, id);

                // Adding expense to list
                expensesList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expensesList;
    }

    public ArrayList<Expense> getMonthRecords(int searchMonth) {
        ArrayList<Expense> expensesList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_MONTH + "=" + searchMonth;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String amount = cursor.getString(1);
                String desc = cursor.getString(2);
                String category = cursor.getString(3);
                int month = Integer.parseInt(cursor.getString(4));
                int year = Integer.parseInt(cursor.getString(5));

                Expense expense = new Expense(amount, desc.trim(), month, year, category, id);

                // Adding expense to list
                expensesList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expensesList;
    }

    public ArrayList<Expense> getCategoryRecords(String categoryName) {
        ArrayList<Expense> expensesList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_CATEGORY + "=\"" + categoryName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String amount = cursor.getString(1);
                String desc = cursor.getString(2);
                String category = cursor.getString(3);
                int month = Integer.parseInt(cursor.getString(4));
                int year = Integer.parseInt(cursor.getString(5));

                Expense expense = new Expense(amount, desc.trim(), month, year, category, id);

                // Adding expense to list
                expensesList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expensesList;
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, expense.getId());
        values.put(KEY_EXPENSE_AMOUNT, expense.getAmount()); // Expense Amount
        values.put(KEY_EXPENSE_DESC, expense.getDescription()); // Expense Description
        values.put(KEY_EXPENSE_MONTH, expense.getMonth()); // Expense Month
        values.put(KEY_EXPENSE_YEAR, expense.getYear()); // Expense Year
        values.put(KEY_EXPENSE_CATEGORY, expense.getExpenseCategory().toString()); // Expense category

        // Inserting Row
        db.insert(TABLE_EXPENSES, null, values);
        db.close(); // Closing database connection
    }
}
