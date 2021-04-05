package com.example.mobdevspacexapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.mobdevspacexapp.R;

public class LaunchesActivity extends AppCompatActivity {

    private LaunchListViewAdapter launchListViewAdapter;
    private RecyclerView launchListView;
    private RequestQueue requestQueue;
    private static final int COLUMN_COUNT = 1;
    //private LaunchManager launchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launches);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.launchesFragmentContainer, new LaunchesTabsFragment())
                .commit();

        //launchManager = new LaunchManager(getAllLaunches());
        //launchListViewAdapter = new LaunchListViewAdapter(getApplicationContext(), launchManager);
        //launchListView = findViewById(R.id.launch_list_view);

    }

//    public List<Launch> getAllLaunches() {
//        String url = "https://api.spacexdata.com/v4/launches";
//        final List<Launch> allLaunches = new ArrayList<>();
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for(int i = 0; i < response.length(); i++) {
//                    try {
//                        //Log.d("RESPONSE", response.toString(2));
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        String launchName = jsonObject.getString("name");
//                        String launchNumber = jsonObject.getString("flight_number");
//                        String launchDateTime = jsonObject.getString("date_utc");
//                        String launchPatchLinkSmall = jsonObject.getJSONObject("links").getJSONObject("patch").getString("small");
//                        allLaunches.add(new Launch(Integer.getInteger(launchNumber), launchName, launchDateTime, launchPatchLinkSmall));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        if (error.getMessage() != null)
//                            Log.d("ERROR", error.getMessage()); //prints the error message to LogCat
//                    }
//                }
//        );
//        requestQueue.add(jsonArrayRequest);
//    }




}