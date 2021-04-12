package com.example.mobdevspacexapp.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.mobdevspacexapp.R;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
        setPreferencesFromResource(R.xml.settings_preference, rootKey);

        this.sharedPreferences = this.getActivity().getSharedPreferences("settings", MODE_PRIVATE);

        ListPreference listPreference = findPreference("settings_length_key");
        listPreference.setValue(sharedPreferences.getString(listPreference.getKey(), ""));
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                System.out.println("List preference change");
                System.out.println(preference.getKey() + " preference changed to " + newValue);
                sharedPreferences.edit().putString(preference.getKey(), newValue.toString()).apply();
                return true;
            }
        });

    }

}
