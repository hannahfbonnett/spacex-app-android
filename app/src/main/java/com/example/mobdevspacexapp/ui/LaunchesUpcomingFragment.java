package com.example.mobdevspacexapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.api.ApiUtil;
import com.example.mobdevspacexapp.data.model.Launch;
import com.example.mobdevspacexapp.net.VolleyController;

import org.json.JSONArray;


import java.util.ArrayList;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LaunchesUpcomingFragment extends Fragment {

    public static final String upcomingText = "Upcoming launches here";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launches_innertabs, container, false);

        Context context = getContext();
        if (context == null) return v;

        final AppCompatTextView textView = v.findViewById(R.id.launches_innertab_text);
        textView.setText(upcomingText);

        String upcomingLaunchesUrl = ApiUtil.buildUrlString("/launches/upcoming");

        fetchDataAndUpdateList(
                upcomingLaunchesUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Launch> upcomingLaunches = ApiUtil.getLaunchesFromJson(response);
                        String resultString = "";
                        for (Launch launch : upcomingLaunches) {
                            resultString = resultString + launch.getFlightName() + "\n" + launch.getDateUtc() + "\n\n";
                        }
                        textView.setText(resultString);
                    }
                });

        return v;
    }

    private void fetchDataAndUpdateList(String url, Response.Listener<JSONArray> onResponse){
        JsonArrayRequest exampleRequest = new JsonArrayRequest(
                Request.Method.GET, // .POST, .DELETE, .PUT...
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
