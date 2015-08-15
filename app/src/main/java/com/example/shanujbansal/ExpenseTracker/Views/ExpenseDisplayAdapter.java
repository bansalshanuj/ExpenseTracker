package com.example.shanujbansal.ExpenseTracker.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shanujbansal.ExpenseTracker.DatabaseHelper;
import com.example.shanujbansal.ExpenseTracker.Expense;
import com.example.shanujbansal.ExpenseTracker.R;

import java.util.ArrayList;

/**
 * Created by shanuj.bansal on 4/3/2015.
 */
public class ExpenseDisplayAdapter extends ArrayAdapter<Expense> {

    private final Context context;
    private final ArrayList<Expense> values;
    private DatabaseHelper dbHelper = new DatabaseHelper(getContext());

    public ExpenseDisplayAdapter(Context context, ArrayList<Expense> values) {
        super(context, R.layout.expense_display_layout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.expense_display_layout, parent, false);
        TextView desctextView = (TextView) rowView.findViewById(R.id.expense_description);
        TextView amttextView = (TextView) rowView.findViewById(R.id.expense_amount);
        TextView cattextView = (TextView) rowView.findViewById(R.id.expense_category);

        desctextView.setText(((Expense) values.get(position)).getDescription());
        amttextView.setText(((Expense) values.get(position)).getAmount());
        cattextView.setText(((Expense) values.get(position)).getExpenseCategory().toString());

        final View dialogView = inflater.inflate(R.layout.expense_details_layout, parent, false);
        LinearLayout containerListView = (LinearLayout) rowView.findViewById(R.id.mainListContainer);
        containerListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show the details of this expense.
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.expense_details_layout);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setTitle("Expense Detail");
                dialog.setCancelable(true);

//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setView(dialogView);

                TextView expenseDescText = (TextView) dialogView.findViewById(R.id.expenseDetailDesc);
                TextView expenseAmtText = (TextView) dialogView.findViewById(R.id.expenseDetailAmt);
                TextView expenseCategoryText = (TextView) dialogView.findViewById(R.id.expenseDetailCategory);

                expenseDescText.setText(((Expense) values.get(position)).getDescription().trim());
                expenseAmtText.setText(((Expense) values.get(position)).getAmount().trim());
                expenseCategoryText.setText(((Expense) values.get(position)).getExpenseCategory().toString().trim());

                dialog.show();
//                AlertDialog alert = alertDialogBuilder.create();
//                alert.show();
            }
        });

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton) rowView.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set title
                alertDialogBuilder.setTitle("Delete Expense");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Are you sure you want to delete this expense.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                Expense expense = (Expense) values.get(position);
                                //remove the value from the list.
                                values.remove(position);
                                notifyDataSetChanged();
                                // also delete the same from the database.
                                if (expense != null && dbHelper != null) {
                                    dbHelper.deleteRecordById(expense.getId());
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });

        return rowView;
    }
}
