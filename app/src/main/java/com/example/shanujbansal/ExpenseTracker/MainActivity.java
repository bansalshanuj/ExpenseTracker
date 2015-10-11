package com.example.shanujbansal.ExpenseTracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shanujbansal.ExpenseTracker.enums.Categories;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends ActionBarActivity {
    private static Random random = new Random();
    DatabaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(R.style.BlackTheme);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setIcon(R.drawable.ic_launcher);
        }
        if (getActionBar() != null) {
            getActionBar().setIcon(R.drawable.ic_launcher);
        }

        dbHelper = new DatabaseHelper(this);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(buttonListener);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(buttonListener);

        Spinner categorySpinner = (Spinner) findViewById(R.id.categoryComboBox);
        categorySpinner.setAdapter(new ArrayAdapter<Categories>(this, android.R.layout.simple_spinner_dropdown_item, Categories.values()));
        categorySpinner.setOnItemSelectedListener(comboboxListener);

        //Test Purpose
        Button retrieveButton = (Button) findViewById(R.id.retrieveDataButton);
        resetButton.setOnClickListener(buttonListener);

    }

    AdapterView.OnItemSelectedListener comboboxListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView amountText = (TextView) findViewById(R.id.expenseAmount);
            TextView descriptionText = (TextView) findViewById(R.id.expenseDescription);
            AdapterView<?> categoryComboBox = (Spinner) findViewById(R.id.categoryComboBox);
            switch (v.getId()) {
                case R.id.submitButton: {

                    String expenseAmt = amountText.getText().toString();
                    String expenseDesc = descriptionText.getText().toString();
                    if (expenseAmt.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please provide the expense amount.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (expenseDesc.isEmpty())
                        expenseDesc = "N.A.";

                    final DatePicker expenseDate = (DatePicker) findViewById(R.id.expenseDatePicker);
                    // here we need to make the entry that user has done.
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH) + 1;
                    year = expenseDate.getYear();
                    month = expenseDate.getMonth() + 1;
                    int date = expenseDate.getDayOfMonth();
                    String categorySelected = categoryComboBox.getSelectedItem().toString();
                    Expense objExpense = new Expense(expenseAmt, expenseDesc, date, month, year, categorySelected, random.nextInt(100000));
                    if (dbHelper != null) {
                        dbHelper.addExpense(objExpense);
                    }

                    Toast.makeText(getApplicationContext(), "Expense added", Toast.LENGTH_SHORT).show();

                    // after the entry is made again take it to the default view
                    amountText.setText("");
                    descriptionText.setText("");
                    categoryComboBox.setSelection(categoryComboBox.getCount() - 1); // this is done as others is last in the list.
                    break;
                }

                case R.id.resetButton: {
                    // here handle the reset of the details.
                    amountText.setText("");
                    descriptionText.setText("");
                    categoryComboBox.setSelection(categoryComboBox.getCount() - 1);
                    break;
                }

                case R.id.retrieveDataButton: {
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.add_expense_action) {
//            return true;
//        }
        if (id == R.id.view_expense_action) {
            // we need to open the different page for this.
            Intent resultsIntent = new Intent(this, ViewResults.class);
            resultsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(resultsIntent);
            return true;
        } else if (id == R.id.menu_item_share) {
            //create the send intent
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            //set the type
            shareIntent.setType("text/plain");
            //add a subject
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hey, have you tried this expense tracking application yet?");
            //build the body of the message to be shared
            String shareMessage = "You could very well track your expenses. I think you would like it. \n" +
                    "https://play.google.com/store/apps/details?id=com.example.shanujbansal.ExpenseTracker&hl=en";
            //add the message
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
            //start the chooser for sharing
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        }

        return super.onOptionsItemSelected(item);
    }
}
