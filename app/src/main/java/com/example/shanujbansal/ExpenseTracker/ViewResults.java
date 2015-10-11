package com.example.shanujbansal.ExpenseTracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shanujbansal.ExpenseTracker.Views.ExpenseDisplayAdapter;
import com.example.shanujbansal.ExpenseTracker.Views.SlidingTabLayout;
import com.example.shanujbansal.ExpenseTracker.enums.Categories;
import com.example.shanujbansal.ExpenseTracker.enums.Months;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ViewResults extends ActionBarActivity implements ActionBar.TabListener {
    private SlidingTabLayout slidingTabLayout;
    private ArrayList<Fragment> fragments;
    SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    private void openChart(String[] code, double[] distribution, int[] colors) {

        // Pie Chart Section Names
//        String[] code = new String[] {
//                "Eclair & Older", "Froyo", "Gingerbread", "Honeycomb",
//                "IceCream Sandwich", "Jelly Bean"
//        };

        // Pie Chart Section Value
//        double[] distribution = { 3.9, 12.9, 55.8, 1.9, 23.7, 1.8 } ;

        // Color of each Pie Chart Sections
//        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
//                Color.YELLOW };

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
        for (int i = 0; i < distribution.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int i = 0; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Categories Distribution ");
        defaultRenderer.setChartTitleTextSize(20);
        defaultRenderer.setLegendTextSize(30);
        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);
        defaultRenderer.setLabelsColor(Color.WHITE);
        defaultRenderer.setLabelsTextSize(35);
        defaultRenderer.setShowLabels(true);

        defaultRenderer.setZoomButtonsVisible(true);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries, defaultRenderer, "Categorization");
        // Start Activity
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(R.style.BlackTheme);
        setContentView(R.layout.activity_view_results);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setIcon(R.drawable.ic_launcher);
        }
        if (getActionBar() != null) {
            getActionBar().setIcon(R.drawable.ic_launcher);
        }

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
        // slidingTabLayout.setDistributeEvenly(true);
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
            addExpenseIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(addExpenseIntent);
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//        View unselectedTab = (View)tab.getCustomView();

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
                    openChart(code, distribution, colors);
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
                    openChart(code, distribution, colors);
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
                    openChart(code, distribution, colors);
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
}
