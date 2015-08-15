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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shanujbansal.ExpenseTracker.Views.ExpenseDisplayAdapter;
import com.example.shanujbansal.ExpenseTracker.Views.SlidingTabLayout;
import com.example.shanujbansal.ExpenseTracker.enums.Categories;
import com.example.shanujbansal.ExpenseTracker.enums.Months;

import java.util.ArrayList;
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
        setContentView(R.layout.activity_view_results);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);

        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add(new AllResultFragment());
        fragments.add(new CategoryResultFragment());
        fragments.add(new MonthResultFragment());
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
        public static final int YEARRESULTS = 3;
        public static final String UI_CATEGORY_RESULTS = "CATEGORY WISE";
        public static final String UI_MONTH_RESULTS = "MONTH WISE";
        public static final String UI_YEAR_RESULTS = "YEARWISE";
        public static final String UI_All_RESULTS = "ALL";

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
            categorySpinner.setAdapter(new ArrayAdapter<Categories>(getActivity(), android.R.layout.simple_spinner_item, Categories.values()));

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

                        // setting the event handler on click of the listview item.
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view,
                                                    int position, long id) {
                                Toast.makeText(getActivity(), "yes something could be done", Toast.LENGTH_SHORT).show();
                                final Expense item = (Expense) parent.getItemAtPosition(position);
                                view.animate().setDuration(2000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                expenseList.remove(item);
                                                Toast.makeText(getActivity(), "Expense deleted", Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                            }
                                        });
                            }
                        });
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
            monthSpinner.setAdapter(new ArrayAdapter<Months>(getActivity(), android.R.layout.simple_spinner_item, Months.values()));

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

                        // setting the event handler on click of the listview item.
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view,
                                                    int position, long id) {
                                Toast.makeText(getActivity(), "yes something could be done", Toast.LENGTH_SHORT).show();
                                final Expense item = (Expense) parent.getItemAtPosition(position);
                                view.animate().setDuration(2000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                expenseList.remove(item);
                                                Toast.makeText(getActivity(), "Expense deleted", Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                            }
                                        });
                            }
                        });
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

            String[] arraySpinner = new String[]{"2015", "2016", "2017", "2018", "2019", "2020"};
            final Spinner categorySpinner = (Spinner) rootView.findViewById(R.id.yearComboBox);
            categorySpinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arraySpinner));

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

                        // setting the event handler on click of the listview item.
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view,
                                                    int position, long id) {
                                Toast.makeText(getActivity(), "yes something could be done", Toast.LENGTH_SHORT).show();
                                final Expense item = (Expense) parent.getItemAtPosition(position);
                                view.animate().setDuration(2000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                expenseList.remove(item);
                                                Toast.makeText(getActivity(), "Expense deleted", Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                            }
                                        });
                            }
                        });
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

                        // setting the event handler on click of the listview item.
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view,
                                                    int position, long id) {
                                Toast.makeText(getActivity(), "yes something could be done", Toast.LENGTH_SHORT).show();
                                final Expense item = (Expense) parent.getItemAtPosition(position);
                                view.animate().setDuration(2000).alpha(0)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                expenseList.remove(item);
                                                Toast.makeText(getActivity(), "Expense deleted", Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                                view.setAlpha(1);
                                            }
                                        });
                            }
                        });

                    }
                }
            });
            return rootView;
        }
    }
}
