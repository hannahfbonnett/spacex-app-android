package com.example.mobdevspacexapp.ui.launches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.ui.HideShowIconInterface;
import com.google.android.material.tabs.TabLayout;

import lombok.NoArgsConstructor;

//Ref: altered from learning resource TabsExercise.
@NoArgsConstructor
public class LaunchesTabsFragment extends Fragment {

    /*
        Inflate the launches tabs layout.
        Change the action bar title.
        Setup the tab titles and give them to the custom adapter.
        Assign the adapter to the view pager.
        Assign the view pager to the tab layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launches_tabs_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.toolbar_title_launches));

        String[] tabTitles = this.getResources().getStringArray(R.array.launchesTabTitles);

        LaunchesTabsFragmentPagerAdapter adapter = new LaunchesTabsFragmentPagerAdapter(this.getChildFragmentManager(), tabTitles);

        ViewPager vp = v.findViewById(R.id.launches_pager);
        vp.setAdapter(adapter);

        TabLayout tabLayout = v.findViewById(R.id.launches_tab_layout);
        tabLayout.setupWithViewPager(vp);

        ((HideShowIconInterface) getActivity()).showHamburgerIcon();

        return v;
    }
}
