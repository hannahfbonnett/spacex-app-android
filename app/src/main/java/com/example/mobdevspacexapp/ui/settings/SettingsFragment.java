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

    public static final String DEFAULT_LENGTH_UNIT = "Meters";
    public static final String DEFAULT_MASS_UNIT = "Pounds";

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.toolbar_title_settings));
        setPreferencesFromResource(R.xml.settings_preference, rootKey);

        this.sharedPreferences = this.getActivity().getPreferences(MODE_PRIVATE);

        ListPreference lengthListPreference = findPreference(getString(R.string.settings_length_key));
        if(lengthListPreference != null) {
            lengthListPreference.setValue(sharedPreferences.getString(lengthListPreference.getKey(), DEFAULT_LENGTH_UNIT));
            lengthListPreference.setOnPreferenceChangeListener(this);
        }
        ListPreference weightListPreference = findPreference(getString(R.string.settings_weight_key));
        if(weightListPreference != null) {
            weightListPreference.setValue(sharedPreferences.getString(weightListPreference.getKey(), DEFAULT_MASS_UNIT));
            weightListPreference.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        sharedPreferences.edit().putString(preference.getKey(), newValue.toString()).apply();
        return true;
    }
}
