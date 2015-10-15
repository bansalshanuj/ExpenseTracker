package com.development.shanujbansal.ExpenseTracker.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.development.shanujbansal.ExpenseTracker.DatabaseHelper;
import com.development.shanujbansal.ExpenseTracker.Expense;
import com.development.shanujbansal.ExpenseTracker.R;
import com.development.shanujbansal.ExpenseTracker.Views.ExpenseDisplayAdapter;
import com.development.shanujbansal.ExpenseTracker.enums.Months;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanuj.bansal on 10/14/2015.
 */
public class CustomFilterResultFragment extends Fragment {
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(null);
    private static final String ARG_SECTION_NUMBER = "section_number";

    final List<String> categoriesList = new ArrayList<String>();
    final List<Integer> monthsList = new ArrayList<Integer>();
    final List<Integer> yearsList = new ArrayList<Integer>();

    public CustomFilterResultFragment newInstance(int sectionNumber) {
        CustomFilterResultFragment fragment = new CustomFilterResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // Checkbox listener for categories
    public CompoundButton.OnCheckedChangeListener categoryCheckBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String checkedValue = buttonView.getText().toString();
            if (isChecked) categoriesList.add(checkedValue);
            else if (categoriesList.indexOf(checkedValue) > -1)
                categoriesList.remove(categoriesList.indexOf(checkedValue));
        }
    };

    // Checkbox listener for months
    public CompoundButton.OnCheckedChangeListener monthsCheckBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Integer checkedValue = Months.valueOf(buttonView.getText().toString()).ordinal() + 1;
            if (isChecked) monthsList.add(checkedValue);
            else if (monthsList.indexOf(checkedValue) > -1)
                monthsList.remove(monthsList.indexOf(checkedValue));
        }
    };

    // Checkbox listener for years
    public CompoundButton.OnCheckedChangeListener yearsCheckBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Integer checkedValue = Integer.parseInt(buttonView.getText().toString());
            if (isChecked) yearsList.add(checkedValue);
            else if (yearsList.indexOf(checkedValue) > -1)
                yearsList.remove(yearsList.indexOf(checkedValue));
        }
    };

    public CustomFilterResultFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_custom_filter_results, container, false);

        final Drawable expandIcon = getResources().getDrawable(R.drawable.expand);
        final Drawable collapseIcon = getResources().getDrawable(R.drawable.collapse);

        final ListView lv = (ListView) rootView.findViewById(R.id.customResultsListView);

        final ScrollView categoryLayout = (ScrollView) rootView.findViewById(R.id.categoryLayout);
        final ScrollView yearsLayout = (ScrollView) rootView.findViewById(R.id.yearsLayout);
        final ScrollView monthsLayout = (ScrollView) rootView.findViewById(R.id.monthsLayout);

        categoryLayout.setVisibility(View.GONE);
        yearsLayout.setVisibility(View.GONE);
        monthsLayout.setVisibility(View.GONE);

        final Button categoryFilterHideShowBtn = (Button) rootView.findViewById(R.id.accordianCategoryFilter);
        final Button yearsFilterHideShowBtn = (Button) rootView.findViewById(R.id.accordianYearFilter);
        final Button monthsFilterHideShowBtn = (Button) rootView.findViewById(R.id.accordianMonthFilter);

        final Button showFiltersButton = (Button) rootView.findViewById(R.id.showFiltersBtn);
        showFiltersButton.setVisibility(View.GONE);
        showFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryFilterHideShowBtn.setVisibility(View.VISIBLE);
                yearsFilterHideShowBtn.setVisibility(View.VISIBLE);
                monthsFilterHideShowBtn.setVisibility(View.VISIBLE);

                showFiltersButton.setVisibility(View.GONE);
                lv.setVisibility(View.GONE);
            }
        });

        // Categories checkboxes
        final CheckBox categoryGroceries = (CheckBox) rootView.findViewById(R.id.categoryGroceries);
        final CheckBox categoryClothes = (CheckBox) rootView.findViewById(R.id.categoryClothes);
        final CheckBox categoryEducation = (CheckBox) rootView.findViewById(R.id.categoryEducation);
        final CheckBox categoryFood = (CheckBox) rootView.findViewById(R.id.categoryFood);
        final CheckBox categoryFuel = (CheckBox) rootView.findViewById(R.id.categoryFuel);
        final CheckBox categoryGifts = (CheckBox) rootView.findViewById(R.id.categoryGifts);
        final CheckBox categoryKids = (CheckBox) rootView.findViewById(R.id.categoryKids);
        final CheckBox categoryMovies = (CheckBox) rootView.findViewById(R.id.categoryMovies);
        final CheckBox categoryParty = (CheckBox) rootView.findViewById(R.id.categoryParty);
        final CheckBox categoryPubRestaurant = (CheckBox) rootView.findViewById(R.id.categoryPubRestaurant);
        final CheckBox categoryTransport = (CheckBox) rootView.findViewById(R.id.categoryTransport);
        final CheckBox categoryTravel = (CheckBox) rootView.findViewById(R.id.categoryTravel);
        final CheckBox categoryOthers = (CheckBox) rootView.findViewById(R.id.categoryOthers);

        categoryGroceries.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryClothes.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryEducation.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryFood.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryFuel.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryGifts.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryKids.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryMovies.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryParty.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryPubRestaurant.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryTransport.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryTravel.setOnCheckedChangeListener(categoryCheckBoxListener);
        categoryOthers.setOnCheckedChangeListener(categoryCheckBoxListener);

        // Months checkboxes
        final CheckBox monthsJanuary = (CheckBox) rootView.findViewById(R.id.monthsJanuary);
        final CheckBox monthsFebruary = (CheckBox) rootView.findViewById(R.id.monthsFebruary);
        final CheckBox monthsMarch = (CheckBox) rootView.findViewById(R.id.monthsMarch);
        final CheckBox monthsApril = (CheckBox) rootView.findViewById(R.id.monthsApril);
        final CheckBox monthsMay = (CheckBox) rootView.findViewById(R.id.monthsMay);
        final CheckBox monthsJune = (CheckBox) rootView.findViewById(R.id.monthsJune);
        final CheckBox monthsJuly = (CheckBox) rootView.findViewById(R.id.monthsJuly);
        final CheckBox monthsAugust = (CheckBox) rootView.findViewById(R.id.monthsAugust);
        final CheckBox monthsSeptember = (CheckBox) rootView.findViewById(R.id.monthsSeptember);
        final CheckBox monthsOctober = (CheckBox) rootView.findViewById(R.id.monthsOctober);
        final CheckBox monthsNovember = (CheckBox) rootView.findViewById(R.id.monthsNovember);
        final CheckBox monthsDecember = (CheckBox) rootView.findViewById(R.id.monthsDecember);

        monthsJanuary.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsFebruary.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsMarch.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsApril.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsMay.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsJune.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsJuly.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsAugust.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsSeptember.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsOctober.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsNovember.setOnCheckedChangeListener(monthsCheckBoxListener);
        monthsDecember.setOnCheckedChangeListener(monthsCheckBoxListener);

        // Years checkboxes
        final CheckBox year2015 = (CheckBox) rootView.findViewById(R.id.year2015);
        final CheckBox year2016 = (CheckBox) rootView.findViewById(R.id.year2016);
        final CheckBox year2017 = (CheckBox) rootView.findViewById(R.id.year2017);
        final CheckBox year2018 = (CheckBox) rootView.findViewById(R.id.year2018);
        final CheckBox year2019 = (CheckBox) rootView.findViewById(R.id.year2019);

        year2015.setOnCheckedChangeListener(yearsCheckBoxListener);
        year2016.setOnCheckedChangeListener(yearsCheckBoxListener);
        year2017.setOnCheckedChangeListener(yearsCheckBoxListener);
        year2018.setOnCheckedChangeListener(yearsCheckBoxListener);
        year2019.setOnCheckedChangeListener(yearsCheckBoxListener);

        Button retrieveDataBtn = (Button) rootView.findViewById(R.id.retrieveDataButton);
        retrieveDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(View.GONE);
                yearsLayout.setVisibility(View.GONE);
                monthsLayout.setVisibility(View.GONE);

                categoryFilterHideShowBtn.setVisibility(View.GONE);
                yearsFilterHideShowBtn.setVisibility(View.GONE);
                monthsFilterHideShowBtn.setVisibility(View.GONE);

                monthsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                categoryFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                yearsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);

                showFiltersButton.setVisibility(View.VISIBLE);

                if (categoriesList.size() == 0 && monthsList.size() == 0 && yearsList.size() == 0) {
                    Toast.makeText(getActivity(), "No filters have been applied. Showing all results.", Toast.LENGTH_SHORT).show();
                }

                if (dbHelper != null) {
                    final ArrayList<Expense> expenseList = dbHelper.getCustomResults(categoriesList, monthsList, yearsList);

                    if (expenseList.size() == 0)
                        Toast.makeText(getActivity(), "No expenses to show for the selected filters", Toast.LENGTH_SHORT).show();

                    lv.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) lv.getLayoutParams();
                    lParams.weight = 0.65f;
                    lv.setLayoutParams(lParams);

                    final ExpenseDisplayAdapter adapter = new ExpenseDisplayAdapter(getActivity(), expenseList);
                    lv.setAdapter(adapter);
                }
            }
        });

        final Button resetFilterButton = (Button) rootView.findViewById(R.id.resetFilterButton);
        resetFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseDisplayAdapter adapter = (ExpenseDisplayAdapter) lv.getAdapter();
                if (adapter != null) {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    lv.setVisibility(View.GONE);
                }

                categoryFilterHideShowBtn.setVisibility(View.VISIBLE);
                yearsFilterHideShowBtn.setVisibility(View.VISIBLE);
                monthsFilterHideShowBtn.setVisibility(View.VISIBLE);

                showFiltersButton.setVisibility(View.GONE);

                // clear all the filter's lists
                categoriesList.clear();
                monthsList.clear();
                yearsList.clear();

                // set all category checkboxes as unchecked
                categoryGroceries.setChecked(false);
                categoryClothes.setChecked(false);
                categoryEducation.setChecked(false);
                categoryFood.setChecked(false);
                categoryFuel.setChecked(false);
                categoryGifts.setChecked(false);
                categoryKids.setChecked(false);
                categoryMovies.setChecked(false);
                categoryParty.setChecked(false);
                categoryPubRestaurant.setChecked(false);
                categoryTransport.setChecked(false);
                categoryTravel.setChecked(false);
                categoryOthers.setChecked(false);

                // set all months checkboxes as unchecked
                monthsJanuary.setChecked(false);
                monthsFebruary.setChecked(false);
                monthsMarch.setChecked(false);
                monthsApril.setChecked(false);
                monthsMay.setChecked(false);
                monthsJune.setChecked(false);
                monthsJuly.setChecked(false);
                monthsAugust.setChecked(false);
                monthsSeptember.setChecked(false);
                monthsOctober.setChecked(false);
                monthsNovember.setChecked(false);
                monthsDecember.setChecked(false);

                // set all year checkboxes as unchecked
                year2015.setChecked(false);
                year2016.setChecked(false);
                year2017.setChecked(false);
                year2018.setChecked(false);
                year2019.setChecked(false);

                Toast.makeText(getActivity(), "All filters reset", Toast.LENGTH_SHORT).show();
            }
        });

        categoryFilterHideShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryLayout.setVisibility(categoryLayout.isShown() ? View.GONE : View.VISIBLE);
                if (categoryLayout.isShown()) {
                    categoryFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, collapseIcon, null);
                    yearsLayout.setVisibility(View.GONE);
                    monthsLayout.setVisibility(View.GONE);
                    yearsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                    monthsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                } else {
                    categoryFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                }
            }
        });
        yearsFilterHideShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearsLayout.setVisibility(yearsLayout.isShown() ? View.GONE : View.VISIBLE);
                if (yearsLayout.isShown()) {
                    yearsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, collapseIcon, null);
                    categoryLayout.setVisibility(View.GONE);
                    monthsLayout.setVisibility(View.GONE);
                    monthsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                    categoryFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                } else {
                    yearsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                }
            }
        });
        monthsFilterHideShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthsLayout.setVisibility(monthsLayout.isShown() ? View.GONE : View.VISIBLE);
                if (monthsLayout.isShown()) {
                    monthsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, collapseIcon, null);
                    yearsLayout.setVisibility(View.GONE);
                    categoryLayout.setVisibility(View.GONE);
                    yearsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                    categoryFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                } else {
                    monthsFilterHideShowBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, expandIcon, null);
                }
            }
        });

        return rootView;
    }
}
