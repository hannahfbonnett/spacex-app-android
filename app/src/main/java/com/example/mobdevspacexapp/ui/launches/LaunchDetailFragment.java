package com.example.mobdevspacexapp.ui.launches;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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

    /*
        Inflate the launch detail layout.
        Change the action bar title.
        Find the views using their ID's.
        Bind the launch data to the views and add an on click listener.
     */
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

        if(launch != null) {
            bind(launch);
            launchRocketText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewRocketDetails(context, launch.getRocket());
                }
            });
        }
        ((HideShowIconInterface) getActivity()).showBackIcon();
        return v;
    }

    /*
        Set the text and image using the launch attributes.
     */
    private void bind(Launch launch) {
        launchNameText.setText(launch.getName());
        launchFlightNumberText.setText(String.valueOf(launch.getFlightNumber()));
        launchDatetimeText.setText(DateTimeConverter.getFormattedUnixDateTime(launch.getDateTimeUnix()));
        if(!launch.getDetail().equals("null")) {
            launchDetailsText.setText(launch.getDetail());
        } else {
            launchDetailsText.setText(getString(R.string.launch_details_null));
        }
        if(!launch.getPatchLinkSmall().equals("null")) {
            Picasso.get()
                    .load(launch.getPatchLinkSmall())
                    .into(launchIcon);
        }
        launchRocketText.setText(launch.getRocket().getName());
    }

    /*
        View rocket details when rocket name is clicked on.
     */
    private void viewRocketDetails(Context context, Rocket rocket) {
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
