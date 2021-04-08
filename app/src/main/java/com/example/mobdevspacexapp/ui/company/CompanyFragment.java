package com.example.mobdevspacexapp.ui.company;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.api.ApiUtil;
import com.example.mobdevspacexapp.data.model.Company;
import com.example.mobdevspacexapp.net.VolleyController;

import org.json.JSONObject;

public class CompanyFragment extends Fragment {

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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
        View v = inflater.inflate(R.layout.company_info, container, false);

        this.nameText = v.findViewById(R.id.company_name);
        this.descriptionText = v.findViewById(R.id.company_description);
        this.founderNameText = v.findViewById(R.id.company_founder_name);
        this.founderYearText = v.findViewById(R.id.company_founded_year);

        if(founderYearText == null) {
            System.out.println("Founder year text is null");
        }
        this.employeesNumberText = v.findViewById(R.id.company_employees_number);
        this.vehiclesNumberText = v.findViewById(R.id.company_vehicles_number);
        this.launchSitesNumberText = v.findViewById(R.id.company_launch_sites_number);
        this.testSitesNumberText = v.findViewById(R.id.company_test_sites_number);
        this.websiteLinkText = v.findViewById(R.id.company_website_link);
        this.flickrLinkText = v.findViewById(R.id.company_flickr_link);
        this.twitterLinkText = v.findViewById(R.id.company_twitter_link);

        String upcomingLaunchesUrl = ApiUtil.buildUrlString("/company");

        fetchDataAndUpdateText(
                upcomingLaunchesUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Company company = ApiUtil.getCompanyInfoFromJson(response);
                        
                        nameText.setText(company.getName());
                        descriptionText.setText(company.getDescription());
                        founderNameText.setText(company.getFounder());
                        founderYearText.setText(String.valueOf(company.getFoundedYear()));
                        employeesNumberText.setText(String.valueOf(company.getNumberOfEmployees()));
                        vehiclesNumberText.setText(String.valueOf(company.getNumberOfVehicles()));
                        launchSitesNumberText.setText(String.valueOf(company.getNumberOfLaunchSites()));
                        testSitesNumberText.setText(String.valueOf(company.getNumberOfTestSites()));
                        websiteLinkText.setText(company.getWebsiteLink());
                        flickrLinkText.setText(company.getFlickrLink());
                        twitterLinkText.setText(company.getTwitterLink());
                    }
                });

        return v;
    }


    private void fetchDataAndUpdateText(String url, Response.Listener<JSONObject> onResponse){
        JsonObjectRequest exampleRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                onResponse,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.getMessage());
                    }
                }
        );
        VolleyController.getInstance(getActivity().getApplicationContext()).addToRequestQueue(exampleRequest);
    }
}
