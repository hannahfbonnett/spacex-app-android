package com.example.mobdevspacexapp.ui.launches;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.mobdevspacexapp.R;

public class LaunchesActivity extends AppCompatActivity {

    private DrawerLayout navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.navDrawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                this.navDrawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        this.navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        changeInternalFragment(new LaunchesTabsFragment(), R.id.launchesFragmentContainer);
    }

    private void changeInternalFragment(Fragment fragment, int fragmentContainer){
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer, fragment)
                .commit();
    }






}