package com.example.mobdevspacexapp.ui.launches;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
public class LaunchesPastFragment extends Fragment {

    private RecyclerView launchListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launches_innertabs, container, false);

        final Context context = getContext();
        if (context == null) return v;


        launchListView = v.findViewById(R.id.launch_list_view);
        LinearLayoutManager launchesLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        launchListView.setLayoutManager(launchesLayoutManager);

        String upcomingLaunchesUrl = ApiUtil.buildUrlString("/launches/past");

        fetchDataAndUpdateList(
                upcomingLaunchesUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Launch> pastLaunches = ApiUtil.getLaunchesFromJson(response);

                        LaunchListViewAdapter adapter = new LaunchListViewAdapter(context, pastLaunches);
                        launchListView.setAdapter(adapter);
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
