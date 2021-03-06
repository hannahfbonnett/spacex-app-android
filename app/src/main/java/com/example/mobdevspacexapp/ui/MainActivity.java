package com.example.mobdevspacexapp.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.ui.company.CompanyFragment;
import com.example.mobdevspacexapp.ui.launches.LaunchesTabsFragment;
import com.example.mobdevspacexapp.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HideShowIconInterface {

    private static final int DEFAULT_DRAWER_ITEM = R.id.nav_drawer_launches;
    private DrawerLayout navDrawer;
    private NavigationView navView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Toolbar toolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.navDrawer = this.findViewById(R.id.drawer_layout);
        this.toggle = new ActionBarDrawerToggle(
                this,
                this.navDrawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        this.navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        this.navView = findViewById(R.id.nav_view);
        this.navView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            this.navView.setCheckedItem(DEFAULT_DRAWER_ITEM);
            this.navView.getMenu().performIdentifierAction(DEFAULT_DRAWER_ITEM, 0);
        }
        initSharedPreferences();
    }

    /*
        Set the shared preferences to the default values if
        the user has not yet set their preferences.
     */
    private void initSharedPreferences() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if (!prefs.contains(getString(R.string.settings_length_key))) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getString(R.string.settings_length_key), getString(R.string.length_meters));
            editor.apply();
        }
        if (!prefs.contains(getString(R.string.settings_mass_key))) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getString(R.string.settings_mass_key), getString(R.string.mass_pounds));
            editor.apply();
        }
    }

    /*
        Change the displayed fragment to the provided fragment.
     */
    private void changeInternalFragment(Fragment fragment, int fragmentContainer){
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer, fragment)
                .commit();
    }


    /*
        When an item has been selected in the navigation drawer,
        update the displayed fragment.
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_drawer_launches:
                changeInternalFragment(new LaunchesTabsFragment(), R.id.fragmentContainer);
                break;
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

    /*
        When the back button is pressed, close the nav drawer if it is open
        and go back to the previous fragment.
     */
    @Override
    public void onBackPressed() {
        if (navDrawer != null && navDrawer.isDrawerOpen(GravityCompat.START))
            navDrawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    /*
        Show the hamburger icon that shows the nav drawer when clicked.
        This is shown when a sub fragment is not open.
        Ref: https://stackoverflow.com/questions/41547122/android-hamburger-menu-and-back-arrow
     */
    @Override
    public void showHamburgerIcon() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.setToolbarNavigationClickListener(null);
    }

    /*
        Show the back icon that when clicked displays the last fragment.
        This is shown when a sub fragment is open.
        Ref: https://stackoverflow.com/questions/41547122/android-hamburger-menu-and-back-arrow
     */
    @Override
    public void showBackIcon() {
        toggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    
}