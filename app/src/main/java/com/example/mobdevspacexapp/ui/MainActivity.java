package com.example.mobdevspacexapp.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.ui.company.CompanyFragment;
import com.example.mobdevspacexapp.ui.launches.LaunchesTabsFragment;
import com.example.mobdevspacexapp.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout navDrawer;
    private NavigationView navView;

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

        this.navView = (NavigationView) findViewById(R.id.nav_view);
        this.navView.setNavigationItemSelectedListener(this);

        changeInternalFragment(new LaunchesTabsFragment(), R.id.fragmentContainer);
    }

    private void changeInternalFragment(Fragment fragment, int fragmentContainer){
        FragmentManager supportFragmentManager = getSupportFragmentManager();

        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer, fragment)
                .commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_drawer_settings:
                changeInternalFragment(new SettingsFragment(), R.id.fragmentContainer);
                break;
            case R.id.nav_drawer_company:
                changeInternalFragment(new CompanyFragment(), R.id.fragmentContainer);
                break;
        }

        this.navDrawer.closeDrawer(GravityCompat.START);

        return true;
    }






}