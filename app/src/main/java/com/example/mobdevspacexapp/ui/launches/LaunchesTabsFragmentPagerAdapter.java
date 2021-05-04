package com.example.mobdevspacexapp.ui.launches;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LaunchesTabsFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles;

    LaunchesTabsFragmentPagerAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabTitles = tabTitles;
    }

    /*
        Return the Fragment associated with a specified position.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            default:
                return new LaunchesUpcomingFragment();
            case 1:
                return new LaunchesPastFragment();
            case 2:
                return new LaunchesAllFragment();
        }
    }

    /*
        Return the number of views available.
     */
    @Override
    public int getCount() {
        return this.tabTitles.length;
    }

    /*
        Return the page title associated with a specified position.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitles[position];
    }
}

