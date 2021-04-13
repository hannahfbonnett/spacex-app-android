package com.example.mobdevspacexapp.ui.rockets;

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

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.model.Rocket;
import com.example.mobdevspacexapp.ui.HideShowIconInterface;
import com.squareup.picasso.Picasso;

public class RocketDetailFragment extends Fragment {

    private AppCompatTextView rocketNameText;
    private AppCompatTextView descriptionText;
    private AppCompatTextView activeText;
    private AppCompatTextView heightText;
    private AppCompatTextView diameterText;
    private AppCompatTextView massText;
    private AppCompatTextView firstFlightDateText;
    private AppCompatImageView rocketImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rocket_detail, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.toolbar_title_rocket));

        this.rocketNameText = v.findViewById(R.id.rocket_detail_name);
        this.descriptionText = v.findViewById(R.id.rocket_detail_details);
        this.activeText = v.findViewById(R.id.rocket_detail_active_value);
        this.heightText = v.findViewById(R.id.rocket_detail_height_value);
        this.diameterText = v.findViewById(R.id.rocket_detail_diameter_value);
        this.massText = v.findViewById(R.id.rocket_detail_mass_value);
        this.firstFlightDateText = v.findViewById(R.id.rocket_detail_first_flight_date_value);
        this.rocketImage = v.findViewById(R.id.rocket_detail_image);

        Rocket rocket = getArguments().getParcelable("Rocket");
        bind(rocket);

        ((HideShowIconInterface) getActivity()).showBackIcon();

        return v;
    }

    private void bind(Rocket rocket) {
        rocketNameText.setText(rocket.getName());
        descriptionText.setText(rocket.getDescription());
        if(rocket.isActive()) {
            activeText.setText(getString(R.string.rocket_active_true));
        } else {
            activeText.setText(getString(R.string.rocket_active_false));
        }
        if(getPreferredLengthUnit().equalsIgnoreCase("meters")) {
            heightText.setText(getString(R.string.length_amount_meters, rocket.getHeightMeters()));
            diameterText.setText(getString(R.string.length_amount_meters, rocket.getDiameterMeters()));
        } else if (getPreferredLengthUnit().equalsIgnoreCase("feet")) {
            heightText.setText(getString(R.string.length_amount_feet, rocket.getHeightFeet()));
            diameterText.setText(getString(R.string.length_amount_feet, rocket.getDiameterFeet()));
        }
        if(getPreferredWeightUnit().equalsIgnoreCase("pounds")) {
            massText.setText(getString(R.string.weight_amount_pounds, rocket.getMassLbs()));
        } else if(getPreferredWeightUnit().equalsIgnoreCase("kilograms")) {
            massText.setText(getString(R.string.weight_amount_kilograms, rocket.getMassKgs()));
        }
        firstFlightDateText.setText(rocket.getFirstFlightDate());
        if(!rocket.getImageLink().equals("null")) {
            Picasso.get()
                    .load(rocket.getImageLink())
                    .into(rocketImage);
        }
    }

    private String getPreferredLengthUnit() {
        return getActivity()
                .getPreferences(Context.MODE_PRIVATE)
                .getString("settings_length_key", "Meters");
    }

    private String getPreferredWeightUnit() {
        return getActivity()
                .getPreferences(Context.MODE_PRIVATE)
                .getString("settings_weight_key", "Pounds");
    }



}
