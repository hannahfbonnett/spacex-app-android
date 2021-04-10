package com.example.mobdevspacexapp.ui.rockets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.model.Rocket;

public class RocketDetailFragment extends Fragment {

    private AppCompatTextView rocketNameText;
    private AppCompatTextView descriptionText;
    private AppCompatTextView activeText;
    private AppCompatTextView heightText;
    private AppCompatTextView diameterText;
    private AppCompatTextView massText;
    private AppCompatTextView firstFlightDateText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rocket_detail, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Rocket");

        this.rocketNameText = v.findViewById(R.id.rocket_detail_name);
        this.descriptionText = v.findViewById(R.id.rocket_detail_details);
        this.activeText = v.findViewById(R.id.rocket_detail_active_value);
        this.heightText = v.findViewById(R.id.rocket_detail_height_value);
        this.diameterText = v.findViewById(R.id.rocket_detail_diameter_value);
        this.massText = v.findViewById(R.id.rocket_detail_mass_value);
        this.firstFlightDateText = v.findViewById(R.id.rocket_detail_first_flight_date_value);

        Rocket rocket = getArguments().getParcelable("Rocket");
        bind(rocket);

        return v;
    }

    private void bind(Rocket rocket) {
        rocketNameText.setText(rocket.getName());
        descriptionText.setText(rocket.getDescription());
        activeText.setText(String.valueOf(rocket.isActive()));
        heightText.setText(String.valueOf(rocket.getHeightMeters()));
        diameterText.setText(String.valueOf(rocket.getDiameterMeters()));
        massText.setText(String.valueOf(rocket.getMassLbs()));
        firstFlightDateText.setText(rocket.getFirstFlightDate());
    }



}
