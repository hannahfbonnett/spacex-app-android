package com.example.mobdevspacexapp.ui.launches;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.model.Launch;
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

        this.launchNameText = v.findViewById(R.id.launch_detail_name);
        this.launchFlightNumberText = v.findViewById(R.id.launch_detail_flight_no);
        this.launchDatetimeText = v.findViewById(R.id.launch_detail_datetime);
        this.launchIcon = v.findViewById(R.id.launch_detail_icon);
        this.launchDetailsText = v.findViewById(R.id.launch_detail_details);
        this.launchRocketText = v.findViewById(R.id.launch_detail_rocket);

        Launch launch = getArguments().getParcelable("Launch");

        bind(launch);

        return v;
    }

    public void bind(Launch launch) {
        launchNameText.setText(launch.getName());
        launchFlightNumberText.setText("Flight no: " + launch.getFlightNumber());
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
}
