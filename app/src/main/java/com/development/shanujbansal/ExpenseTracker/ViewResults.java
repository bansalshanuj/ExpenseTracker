package com.development.shanujbansal.ExpenseTracker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.development.shanujbansal.ExpenseTracker.Fragments.AllResultFragment;
import com.development.shanujbansal.ExpenseTracker.Fragments.CategoryResultFragment;
import com.development.shanujbansal.ExpenseTracker.Fragments.CustomFilterResultFragment;
import com.development.shanujbansal.ExpenseTracker.Fragments.MonthResultFragment;
import com.development.shanujbansal.ExpenseTracker.Fragments.YearResultFragment;
import com.development.shanujbansal.ExpenseTracker.Views.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Locale;


public class ViewResults extends ActionBarActivity implements ActionBar.TabListener {
    private SlidingTabLayout slidingTabLayout;
    private ArrayList<Fragment> fragments;
    SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setTheme(R.style.BlackTheme);
        setContentView(R.layout.activity_view_results);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.drawable.ic_launcher);
        }
        if (getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setIcon(R.drawable.ic_launcher);
        }

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);

        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add((new AllResultFragment()).newInstance(0));
        fragments.add((new CategoryResultFragment()).newInstance(1));
        fragments.add((new MonthResultFragment()).newInstance(2));
        fragments.add((new CustomFilterResultFragment()).newInstance(3));
        fragments.add((new YearResultFragment()).newInstance(4));

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
                    "https://play.google.com/store/apps/details?id=com.development.shanujbansal.ExpenseTracker&hl=en";
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
}









