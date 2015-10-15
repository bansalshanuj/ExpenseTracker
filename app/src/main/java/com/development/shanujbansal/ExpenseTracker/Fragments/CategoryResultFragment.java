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
public class CategoryResultFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(null);
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public CategoryResultFragment newInstance(int sectionNumber) {
        CategoryResultFragment fragment = new CategoryResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Utils utilsClass = new Utils();
        final View rootView = inflater.inflate(R.layout.fragment_view_category_results, container, false);

        final Spinner categorySpinner = (Spinner) rootView.findViewById(R.id.categoryComboBox);
        categorySpinner.setAdapter(new ArrayAdapter<Categories>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Categories.values()));

        Button showDataBtn = (Button) rootView.findViewById(R.id.showCategoryResultsBtn);
        showDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper != null) {
                    final ArrayList<Expense> expenseList = dbHelper.getCategoryRecords(categorySpinner.getSelectedItem().toString());
                    ListView lv = (ListView) rootView.findViewById(R.id.categoryResultsListView);

                    if (expenseList.size() == 0)
                        Toast.makeText(getActivity(), "No records for the selected category", Toast.LENGTH_SHORT).show();
                    final ExpenseDisplayAdapter adapter = new ExpenseDisplayAdapter(getActivity(), expenseList);
                    lv.setAdapter(adapter);
                }
            }
        });

        Button pictorialDataShowBtn = (Button) rootView.findViewById(R.id.showCategorypictorialResultsBtn);
        pictorialDataShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenCategory = categorySpinner.getSelectedItem().toString();
                Months[] vals = Months.values();
                String[] code = new String[vals.length];
                for (int i = 0; i < vals.length; i++) {
                    code[i] = vals[i].toString();
                }

                double[] distribution = new double[vals.length];
                for (int i = 0; i < vals.length; i++) {
                    distribution[i] = dbHelper.calcTotalExpByCategoryAndMonth(chosenCategory, i + 1);
                }

                int[] colors = {Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
                        Color.GRAY, Color.YELLOW, Color.rgb(253, 105, 89), Color.WHITE, Color.rgb(189, 153, 188),
                        Color.rgb(146, 197, 147), Color.rgb(250, 187, 92)};
                startActivity(utilsClass.openChart(code, distribution, colors, getActivity()));
            }
        });

        return rootView;
    }
}
