package com.example.shanujbansal.ExpenseTracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shanujbansal.ExpenseTracker.Views.ExpenseDisplayAdapter;
import com.example.shanujbansal.ExpenseTracker.Views.SlidingTabLayout;
import com.example.shanujbansal.ExpenseTracker.enums.Categories;
import com.example.shanujbansal.ExpenseTracker.enums.Months;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ViewResults extends ActionBarActivity implements ActionBar.TabListener {
    private SlidingTabLayout slidingTabLayout;
    private ArrayList<Fragment> fragments;
    SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(R.style.BlackTheme);
        setContentView(R.layout.activity_view_results);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);

        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add(new AllResultFragment());
        fragments.add(new CategoryResultFragment());
        fragments.add(new MonthResultFragment());
        fragments.add(new CustomFilterResultFragment());
        fragments.add(new YearResultFragment());

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), fragments);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.add_expense_settings) {
            Intent addExpenseIntent = new Intent(this, MainActivity.class);
            startActivity(addExpenseIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        public static final int AllRESULTS = 0;
        public static final int CATEGORYRESULTS = 1;
        public static final int MONTHRESULTS = 2;
        public static final int YEARRESULTS = 4;
        public static final int CUSTOMRESULTS = 3;
        public static final String UI_CATEGORY_RESULTS = "CATEGORY WISE";
        public static final String UI_MONTH_RESULTS = "MONTH WISE";
        public static final String UI_YEAR_RESULTS = "YEARWISE";
        public static final String UI_All_RESULTS = "ALL";
        public static final String UI_CUSTOM_RESULTS = "CUSTOM SEARCH";

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale currentLocale = Locale.getDefault();
            switch (position) {
                case AllRESULTS:
                    return UI_All_RESULTS.toUpperCase(currentLocale);
                case CATEGORYRESULTS:
                    return UI_CATEGORY_RESULTS.toUpperCase(currentLocale);
                case MONTHRESULTS:
                    return UI_MONTH_RESULTS.toUpperCase(currentLocale);
                case YEARRESULTS:
                    return UI_YEAR_RESULTS.toUpperCase(currentLocale);
                case CUSTOMRESULTS:
                    return UI_CUSTOM_RESULTS.toUpperCase(currentLocale);
                default:
                    break;
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class CategoryResultFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
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

            return rootView;
        }
    }

    public class MonthResultFragment extends Fragment {
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

            return rootView;
        }
    }

    public class YearResultFragment extends Fragment {
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

            return rootView;
        }
    }

    public class AllResultFragment extends Fragment {
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

    public class CustomFilterResultFragment extends Fragment {
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
                // System.out.println(buttonView.getText().toString());
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
                // System.out.println(buttonView.getText().toString());
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
                // System.out.println(buttonView.getText().toString());
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

            final ListView lv = (ListView) rootView.findViewById(R.id.customResultsListView);

            final LinearLayout categoryLayout = (LinearLayout) rootView.findViewById(R.id.categoryLayout);
            final LinearLayout yearsLayout = (LinearLayout) rootView.findViewById(R.id.yearsLayout);
            final LinearLayout monthsLayout = (LinearLayout) rootView.findViewById(R.id.monthsLayout);

            final Button categoryFilterHideShowBtn = (Button) rootView.findViewById(R.id.accordianCategoryFilter);
            final Button yearsFilterHideShowBtn = (Button) rootView.findViewById(R.id.accordianYearFilter);
            final Button monthsFilterHideShowBtn = (Button) rootView.findViewById(R.id.accordianMonthFilter);

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

                    if (categoriesList.size() == 0 && monthsList.size() == 0 && yearsList.size() == 0) {
                        Toast.makeText(getActivity(), "No filters have been applied. Showing all results.", Toast.LENGTH_SHORT).show();
                    }

                    if (dbHelper != null) {
                        final ArrayList<Expense> expenseList = dbHelper.getCustomResults(categoriesList, monthsList, yearsList);

                        if (expenseList.size() == 0)
                            Toast.makeText(getActivity(), "No expenses to show for the selected filters", Toast.LENGTH_SHORT).show();

                        final ExpenseDisplayAdapter adapter = new ExpenseDisplayAdapter(getActivity(), expenseList);
                        lv.setAdapter(adapter);
                    }
                }
            });

            final Button resetFilterButton = (Button) rootView.findViewById(R.id.resetFilterButton);
            resetFilterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpenseDisplayAdapter adapter = (ExpenseDisplayAdapter)lv.getAdapter();
                    adapter.clear();
                    adapter.notifyDataSetChanged();

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
                }
            });
            yearsFilterHideShowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yearsLayout.setVisibility(yearsLayout.isShown() ? View.GONE : View.VISIBLE);
                }
            });
            monthsFilterHideShowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monthsLayout.setVisibility(monthsLayout.isShown() ? View.GONE : View.VISIBLE);
                }
            });

            return rootView;
        }
    }
}
