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

import java.util.ArrayList;

/**
 * Created by shanuj.bansal on 10/14/2015.
 */

public class YearResultFragment extends Fragment {
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(null);
    private static final String ARG_SECTION_NUMBER = "section_number";

    public YearResultFragment newInstance(int sectionNumber) {
        YearResultFragment fragment = new YearResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public YearResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Utils utilsClass = new Utils();
        final View rootView = inflater.inflate(R.layout.fragment_view_yearly_results, container, false);


        String[] arraySpinner = new String[]{"2015", "2016", "2017", "2018", "2019"};
        final Spinner categorySpinner = (Spinner) rootView.findViewById(R.id.yearComboBox);
        categorySpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arraySpinner));

        Button showDataBtn = (Button) rootView.findViewById(R.id.showYrResultsBtn);
        showDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper != null) {
                    final ArrayList<Expense> expenseList = dbHelper.getYearRecords(Integer.parseInt(categorySpinner.getSelectedItem().toString()));
                    ListView lv = (ListView) rootView.findViewById(R.id.yearlyResultsListView);

                    if (expenseList.size() == 0)
                        Toast.makeText(getActivity(), "No records for the selected year", Toast.LENGTH_SHORT).show();

                    final ExpenseDisplayAdapter adapter = new ExpenseDisplayAdapter(getActivity(), expenseList);
                    lv.setAdapter(adapter);
                }
            }
        });

        Button pictorialDataShowBtn = (Button) rootView.findViewById(R.id.showYrpictorialResultsBtn);
        pictorialDataShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] code = new String[]{"2015", "2016", "2017", "2018", "2019"};
                double[] distribution = {};
                if (dbHelper != null) {
                    distribution = new double[]{dbHelper.yearTotalExpenditure(2015),
                            dbHelper.yearTotalExpenditure(2016),
                            dbHelper.yearTotalExpenditure(2017),
                            dbHelper.yearTotalExpenditure(2018),
                            dbHelper.yearTotalExpenditure(2019)};
                }
                int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED};
                startActivity(utilsClass.openChart(code, distribution, colors, getActivity()));
            }
        });

        return rootView;
    }
}
