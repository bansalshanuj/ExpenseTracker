package com.example.shanujbansal.ExpenseTracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
    private static final String KEY_EXPENSE_DAY_OF_YEAR = "dayOfYear";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EXPENSE_AMOUNT + " TEXT,"
                + KEY_EXPENSE_DESC + " TEXT," + KEY_EXPENSE_CATEGORY + " TEXT," + KEY_EXPENSE_MONTH + " INTEGER," + KEY_EXPENSE_YEAR + " INTEGER," + KEY_EXPENSE_DAY_OF_YEAR + " INTEGER" + ")";
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
                int dayOfYear = Integer.parseInt(cursor.getString(6));

                Expense expense = new Expense(amount, desc.trim(), dayOfYear, month, year, category, id);

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
                int dayOfYear = Integer.parseInt(cursor.getString(6));

                Expense expense = new Expense(amount, desc.trim(), dayOfYear, month, year, category, id);

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
                int dayOfYear = Integer.parseInt(cursor.getString(6));

                Expense expense = new Expense(amount, desc.trim(), dayOfYear, month, year, category, id);

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
                int dayOfYear = Integer.parseInt(cursor.getString(6));

                Expense expense = new Expense(amount, desc.trim(), dayOfYear, month, year, category, id);

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
                int dayOfYear = Integer.parseInt(cursor.getString(6));

                Expense expense = new Expense(amount, desc.trim(), dayOfYear, month, year, category, id);

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
        values.put(KEY_EXPENSE_DAY_OF_YEAR, expense.getDayOfMonth()); // Expense Day of Year
        values.put(KEY_EXPENSE_CATEGORY, expense.getExpenseCategory().toString()); // Expense category

        // Inserting Row
        db.insert(TABLE_EXPENSES, null, values);
        db.close(); // Closing database connection
    }

    public double calcTotalExpByCategoryAndMonth(String category, int month) {
        double expenditure = 0;

        // Select All Query
        String selectQuery = "SELECT  SUM(" + KEY_EXPENSE_AMOUNT + ") FROM " + TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_MONTH + "=" + month
                + " AND " + KEY_EXPENSE_CATEGORY + "=\'" + category + "\'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String exp = cursor.getString(0);
                if (exp == null || exp.isEmpty())
                    exp = "0.0";
                expenditure = Double.parseDouble(exp);
            } while (cursor.moveToNext());
        }

        return expenditure;
    }

    public double monthTotalExpenditure(int month) {
        double expenditure = 0;

        // Select All Query
        String selectQuery = "SELECT  SUM(" + KEY_EXPENSE_AMOUNT + ") FROM " + TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_MONTH + "=" + month;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String exp = cursor.getString(0);
                if (exp == null || exp.isEmpty())
                    exp = "0.0";
                expenditure = Double.parseDouble(exp);
            } while (cursor.moveToNext());
        }

        return expenditure;
    }

    public double yearTotalExpenditure(int year) {
        System.out.println("In method yearTotalExpenditure");
        double expenditure = 0.0;

        String selectQuery = "SELECT  SUM(" + KEY_EXPENSE_AMOUNT + ") FROM " + TABLE_EXPENSES + " WHERE " + KEY_EXPENSE_YEAR + "=" + year;
        System.out.println("select query:" + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String exp = cursor.getString(0);
                if (exp == null || exp.isEmpty())
                    exp = "0.0";
                expenditure = Double.parseDouble(exp);
            } while (cursor.moveToNext());
        }

        return expenditure;
    }

    public ArrayList<Expense> getCustomResults(List<String> categoryList, List<Integer> monthsList, List<Integer> yearsList) {
        boolean isFirst = true, isWhereClauseAlreadySet = false;
        ArrayList<Expense> expensesList = new ArrayList<Expense>();
        String queryParam = "SELECT * FROM " + TABLE_EXPENSES + " ";

        if (categoryList != null && categoryList.size() > 0) {
            isWhereClauseAlreadySet = true;
            queryParam += "WHERE " + KEY_EXPENSE_CATEGORY + " IN (";
            for (String str : categoryList) {
                if (isFirst) {
                    queryParam += "'" + str + "'";
                    isFirst = false;
                } else
                    queryParam += ",'" + str + "'";
            }
            queryParam += ")";
        }

        isFirst = true;
        if (monthsList != null && monthsList.size() > 0) {
            queryParam += isWhereClauseAlreadySet ? " AND " + KEY_EXPENSE_MONTH + " IN (" : "WHERE " + KEY_EXPENSE_MONTH + " IN (";
            isWhereClauseAlreadySet = true;
            for (Integer str : monthsList) {
                if (isFirst) {
                    queryParam += str;
                    isFirst = false;
                } else
                    queryParam += "," + str;

            }
            queryParam += ")";
        }

        isFirst = true;
        if (yearsList != null && yearsList.size() > 0) {
            queryParam += isWhereClauseAlreadySet ? " AND " + KEY_EXPENSE_YEAR + " IN (" : "WHERE " + KEY_EXPENSE_YEAR + " IN (";
            for (Integer str : yearsList) {
                if (isFirst) {
                    queryParam += str;
                    isFirst = false;
                } else
                    queryParam += "," + str;

            }
            queryParam += ")";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryParam, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String amount = cursor.getString(1);
                String desc = cursor.getString(2);
                String category = cursor.getString(3);
                int month = Integer.parseInt(cursor.getString(4));
                int year = Integer.parseInt(cursor.getString(5));
                int dayOfYear = Integer.parseInt(cursor.getString(6));

                Expense expense = new Expense(amount, desc.trim(), dayOfYear, month, year, category, id);

                // Adding expense to list
                expensesList.add(expense);
            } while (cursor.moveToNext());
        }
        return expensesList;
    }
}
