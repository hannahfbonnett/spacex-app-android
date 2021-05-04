package com.example.mobdevspacexapp.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.mobdevspacexapp.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    private SharedPreferences sharedPreferences;

    /*
        Inflate the hierarchy using the provided XML attribute.
        Change the action bar title.
        Get the stored preferences and update the UI.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.toolbar_title_settings));
        setPreferencesFromResource(R.xml.settings_preference, rootKey);

        this.sharedPreferences = this.getActivity().getPreferences(MODE_PRIVATE);

        ListPreference lengthListPreference = findPreference(getString(R.string.settings_length_key));
        if(lengthListPreference != null) {
            lengthListPreference.setValue(sharedPreferences.getString(lengthListPreference.getKey(), getString(R.string.length_default_unit)));
            lengthListPreference.setOnPreferenceChangeListener(this);
        }
        ListPreference weightListPreference = findPreference(getString(R.string.settings_mass_key));
        if(weightListPreference != null) {
            weightListPreference.setValue(sharedPreferences.getString(weightListPreference.getKey(), getString(R.string.mass_default_unit)));
            weightListPreference.setOnPreferenceChangeListener(this);
        }
    }

    /*
        Update the shared preferences when a preference has been changed.
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        sharedPreferences.edit().putString(preference.getKey(), newValue.toString()).apply();
        return true;
    }
}
