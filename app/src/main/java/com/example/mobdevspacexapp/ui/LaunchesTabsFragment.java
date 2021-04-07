package com.example.mobdevspacexapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mobdevspacexapp.R;
import com.google.android.material.tabs.TabLayout;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LaunchesTabsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launches_tabs_fragment, container, false);

        String[] tabTitles = this.getResources().getStringArray(R.array.launchesTabTitles);

        LaunchesTabsFragmentPagerAdapter adapter = new LaunchesTabsFragmentPagerAdapter(this.getChildFragmentManager(),tabTitles);

        ViewPager vp = (ViewPager) v.findViewById(R.id.launches_pager);
        vp.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.launches_tab_layout);
        tabLayout.setupWithViewPager(vp);

        return v;
    }
}
