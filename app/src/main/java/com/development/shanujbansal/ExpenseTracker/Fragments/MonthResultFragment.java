package com.development.shanujbansal.ExpenseTracker.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.development.shanujbansal.ExpenseTracker.DatabaseHelper;
import com.development.shanujbansal.ExpenseTracker.Expense;
import com.development.shanujbansal.ExpenseTracker.R;
import com.development.shanujbansal.ExpenseTracker.Utils;
import com.development.shanujbansal.ExpenseTracker.Views.ExpenseDisplayAdapter;
import com.development.shanujbansal.ExpenseTracker.enums.Categories;
import com.development.shanujbansal.ExpenseTracker.enums.Months;

import java.util.ArrayList;

/**
 * Created by shanuj.bansal on 10/14/2015.
 */
public class MonthResultFragment extends Fragment {
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(null);
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MonthResultFragment newInstance(int sectionNumber) {
        MonthResultFragment fragment = new MonthResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MonthResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_monthly_results, container, false);
        final Utils utilsClass = new Utils();

        final Spinner monthSpinner = (Spinner) rootView.findViewById(R.id.monthComboBox);
        monthSpinner.setAdapter(new ArrayAdapter<Months>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Months.values()));

        Button showDataBtn = (Button) rootView.findViewById(R.id.showMonthResultsBtn);
        showDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper != null) {
                    final ArrayList<Expense> expenseList = dbHelper.getMonthRecords(monthSpinner.getSelectedItemPosition() + 1);
                    ListView lv = (ListView) rootView.findViewById(R.id.monthlyResultsListView);

                    if (expenseList.size() == 0)
                        Toast.makeText(getActivity(), "No records for the selected month", Toast.LENGTH_SHORT).show();

                    final ExpenseDisplayAdapter adapter = new ExpenseDisplayAdapter(getActivity(), expenseList);
                    lv.setAdapter(adapter);
                }
            }
        });

        Button pictorialDataShowBtn = (Button) rootView.findViewById(R.id.showMonthpictorialResultsBtn);
        pictorialDataShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int month = monthSpinner.getSelectedItemPosition() + 1;
                Categories[] vals = Categories.values();
                String[] code = new String[vals.length];
                for (int i = 0; i < vals.length; i++) {
                    code[i] = vals[i].toString();
                }

                double[] distribution = new double[vals.length];
                for (int i = 0; i < vals.length; i++) {
                    distribution[i] = dbHelper.calcTotalExpByCategoryAndMonth(vals[i].toString(), month);
                }

                int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                        Color.GRAY, Color.YELLOW, Color.rgb(253, 105, 89), Color.WHITE, Color.rgb(189, 153, 188),
                        Color.rgb(146, 197, 147), Color.rgb(250, 187, 92), Color.rgb(199, 157, 143), Color.rgb(94, 249, 241), Color.rgb(251, 91, 180),
                        Color.rgb(147, 143, 199)};
                startActivity(utilsClass.openChart(code, distribution, colors, getActivity()));
            }
        });

        return rootView;
    }
}
