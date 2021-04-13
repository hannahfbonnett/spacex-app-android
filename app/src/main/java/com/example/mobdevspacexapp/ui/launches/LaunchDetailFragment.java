package com.example.mobdevspacexapp.ui.launches;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.model.Launch;
import com.example.mobdevspacexapp.data.model.Rocket;
import com.example.mobdevspacexapp.ui.HideShowIconInterface;
import com.example.mobdevspacexapp.ui.rockets.RocketDetailFragment;
import com.example.mobdevspacexapp.util.DateTimeConverter;
import com.squareup.picasso.Picasso;

public class LaunchDetailFragment extends Fragment {

    private AppCompatTextView launchNameText;
    private AppCompatTextView launchFlightNumberText;
    private AppCompatTextView launchDatetimeText;
    private AppCompatTextView launchRocketText;
    private AppCompatImageView launchIcon;
    private AppCompatTextView launchDetailsText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launch_detail, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.toolbar_title_launch));

        final Context context = getContext();
        if (context == null) return v;

        this.launchNameText = v.findViewById(R.id.launch_detail_name);
        this.launchFlightNumberText = v.findViewById(R.id.launch_detail_flight_no_value);
        this.launchDatetimeText = v.findViewById(R.id.launch_detail_datetime_value);
        this.launchIcon = v.findViewById(R.id.launch_detail_icon);
        this.launchDetailsText = v.findViewById(R.id.launch_detail_details);
        this.launchRocketText = v.findViewById(R.id.launch_detail_rocket_value);

        final Launch launch = getArguments().getParcelable("Launch");

        bind(launch);

        launchRocketText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRocketDetails(context, launch.getRocket());
            }
        });

        String value = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE).getString("settings_length_key", "default");
        System.out.println(value);

        ((HideShowIconInterface) getActivity()).showBackIcon();
        return v;
    }

    private void bind(Launch launch) {
        launchNameText.setText(launch.getName());
        launchFlightNumberText.setText(String.valueOf(launch.getFlightNumber()));
        launchDatetimeText.setText(DateTimeConverter.getFormattedUnixDateTime(launch.getDateTimeUnix()));
        if(!launch.getDetail().equals("null")) {
            launchDetailsText.setText(launch.getDetail());
        } else {
            launchDetailsText.setText("Awaiting more information from SpaceX.");
        }
        if(!launch.getPatchLinkSmall().equals("null")) {
            Picasso.get()
                    .load(launch.getPatchLinkSmall())
                    .into(launchIcon);
        }
        launchRocketText.setText(launch.getRocket().getName());
    }


    private void viewRocketDetails(Context context, Rocket rocket) {
        System.out.println("Clicked on rocket: " + rocket.getName());
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        RocketDetailFragment launchDetailFragment = new RocketDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Rocket", rocket);
        launchDetailFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, launchDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
