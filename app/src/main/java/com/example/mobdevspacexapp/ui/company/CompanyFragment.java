package com.example.mobdevspacexapp.ui.company;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.api.SpaceXDataService;
import com.example.mobdevspacexapp.data.model.Company;

public class CompanyFragment extends Fragment implements View.OnClickListener {

    private SpaceXDataService spaceXDataService;

    private AppCompatTextView nameText;
    private AppCompatTextView descriptionText;
    private AppCompatTextView founderNameText;
    private AppCompatTextView founderYearText;
    private AppCompatTextView employeesNumberText;
    private AppCompatTextView vehiclesNumberText;
    private AppCompatTextView launchSitesNumberText;
    private AppCompatTextView testSitesNumberText;
    private AppCompatTextView websiteLinkText;
    private AppCompatTextView flickrLinkText;
    private AppCompatTextView twitterLinkText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.toolbar_title_company));
        View v = inflater.inflate(R.layout.company_info, container, false);

        final Context context = getContext();
        if (context == null) return v;

        spaceXDataService = new SpaceXDataService(context);

        this.nameText = v.findViewById(R.id.company_name);
        this.descriptionText = v.findViewById(R.id.company_description);
        this.founderNameText = v.findViewById(R.id.company_founder_name);
        this.founderYearText = v.findViewById(R.id.company_founded_year);
        this.employeesNumberText = v.findViewById(R.id.company_employees_number);
        this.vehiclesNumberText = v.findViewById(R.id.company_vehicles_number);
        this.launchSitesNumberText = v.findViewById(R.id.company_launch_sites_number);
        this.testSitesNumberText = v.findViewById(R.id.company_test_sites_number);
        this.websiteLinkText = v.findViewById(R.id.company_website_link);
        this.flickrLinkText = v.findViewById(R.id.company_flickr_link);
        this.twitterLinkText = v.findViewById(R.id.company_twitter_link);

        fetchCompanyDataAndUpdateText();
        return v;
    }

    private void fetchCompanyDataAndUpdateText(){
        spaceXDataService.getCompanyInfo(new SpaceXDataService.CompanyListener() {
            @Override
            public void onError(String message) {
                Log.d("ERROR", message);
            }

            @Override
            public void onResponse(Company response) {
                nameText.setText(response.getName());
                descriptionText.setText(response.getDescription());
                founderNameText.setText(response.getFounder());
                founderYearText.setText(String.valueOf(response.getFoundedYear()));
                employeesNumberText.setText(String.valueOf(response.getNumberOfEmployees()));
                vehiclesNumberText.setText(String.valueOf(response.getNumberOfVehicles()));
                launchSitesNumberText.setText(String.valueOf(response.getNumberOfLaunchSites()));
                testSitesNumberText.setText(String.valueOf(response.getNumberOfTestSites()));
                websiteLinkText.setText(response.getWebsiteLink());
                flickrLinkText.setText(response.getFlickrLink());
                twitterLinkText.setText(response.getTwitterLink());

                setLinksOnClickListeners();
            }
        });
    }

    public void setLinksOnClickListeners() {
        websiteLinkText.setOnClickListener(this);
        flickrLinkText.setOnClickListener(this);
        twitterLinkText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String url = ((TextView)view).getText().toString();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
