package com.example.mobdevspacexapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.mobdevspacexapp.R;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LaunchesAllFragment extends Fragment {
    public static final String allText = "All launches here";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launches_innertabs, container, false);

        AppCompatTextView textView = v.findViewById(R.id.launches_innertab_text);
        textView.setText(allText);

        return v;
    }
}
