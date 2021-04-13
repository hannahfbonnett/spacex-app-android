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

import com.example.mobdevspacexapp.R;
import com.example.mobdevspacexapp.data.api.LaunchesResponseListener;
import com.example.mobdevspacexapp.data.api.SpaceXDataService;
import com.example.mobdevspacexapp.data.model.Launch;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LaunchesPastFragment extends Fragment {

    private SpaceXDataService spaceXDataService;
    private RecyclerView launchListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.launches_innertabs, container, false);

        final Context context = getContext();
        if (context == null) return v;

        spaceXDataService = new SpaceXDataService(context);

        launchListView = v.findViewById(R.id.launch_list_view);
        LinearLayoutManager launchesLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        launchListView.setLayoutManager(launchesLayoutManager);

        fetchLaunchesAndUpdateList(context);

        return v;
    }

    /*
        Get past launches using the SpaceX api.
        Use the response to send to the view adapter.
     */
    private void fetchLaunchesAndUpdateList(final Context context){
        spaceXDataService.getPastLaunches(new LaunchesResponseListener() {
            @Override
            public void onError(String message) {
                Log.d("ERROR", message);
            }

            @Override
            public void onResponse(List<Launch> response) {
                LaunchListViewAdapter adapter = new LaunchListViewAdapter(context, response);
                launchListView.setAdapter(adapter);
            }
        });
    }
}
