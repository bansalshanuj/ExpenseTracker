package com.development.shanujbansal.ExpenseTracker.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.development.shanujbansal.ExpenseTracker.DatabaseHelper;
import com.development.shanujbansal.ExpenseTracker.Expense;
import com.development.shanujbansal.ExpenseTracker.R;
import com.development.shanujbansal.ExpenseTracker.Views.ExpenseDisplayAdapter;

import java.util.ArrayList;

/**
 * Created by shanuj.bansal on 10/14/2015.
 */
public class AllResultFragment extends Fragment {
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(null);
    private static final String ARG_SECTION_NUMBER = "section_number";

    public AllResultFragment newInstance(int sectionNumber) {
        AllResultFragment fragment = new AllResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public AllResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_results, container, false);

        Button retrieveDataBtn = (Button) rootView.findViewById(R.id.retrieveDataButton);
        retrieveDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper != null) {
                    final ArrayList<Expense> expenseList = dbHelper.getAllRecords();
                    ListView lv = (ListView) rootView.findViewById(R.id.allResultsListView);

                    if (expenseList.size() == 0)
                        Toast.makeText(getActivity(), "No expenses to show", Toast.LENGTH_SHORT).show();

                    final ExpenseDisplayAdapter adapter = new ExpenseDisplayAdapter(getActivity(), expenseList);
                    lv.setAdapter(adapter);
                }
            }
        });
        return rootView;
    }
}
