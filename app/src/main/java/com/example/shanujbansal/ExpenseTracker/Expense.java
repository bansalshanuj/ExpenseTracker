package com.example.shanujbansal.ExpenseTracker;

import com.example.shanujbansal.ExpenseTracker.enums.Categories;

/**
 * Created by shanuj.bansal on 3/28/2015.
 */

public class Expense {
    private String amount;
    private String description;
    private int month;
    private int year;
    private int id;
    private int dayOfMonth;
    private Categories expenseCategory;

    public Expense() {
    }

    public Expense(String amount, String description, int dayOfMonth, int month, int year, String expenseCategory, int id) {
        this.amount = amount;
        this.description = description;
        this.month = month;
        this.year = year;
        this.expenseCategory = Categories.valueOf(expenseCategory);
        this.id = id;
        this.dayOfMonth = dayOfMonth;
    }

    public Categories getExpenseCategory() {
        return this.expenseCategory;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getDescription() {
        return this.description;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public int getId() {
        return this.id;
    }

    public void setExpenseCategory(Categories category) {
        this.expenseCategory = category;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
