package com.example.mobdevspacexapp.data.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mobdevspacexapp.data.model.Launch;
import com.example.mobdevspacexapp.data.model.Rocket;
import com.example.mobdevspacexapp.net.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpaceXDataService {

    Context context;

    public static final String BASE_API_URL = "https://api.spacexdata.com/v4";

    public SpaceXDataService(Context context) {
        this.context = context;
    }

    public String buildUrlString(String endpoint) {
        return BASE_API_URL + endpoint;
    }

    public interface LaunchesListener {
        void onError(String message);

        void onResponse(List<Launch> response);
    }

    public void getUpcomingLaunches(final LaunchesListener launchesListener) {
        final List<Launch> launches = new ArrayList<>();
        String url = buildUrlString("/launches/upcoming");

        JsonArrayRequest exampleRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int numberOfLaunches = response.length();
                        for (int i = 0; i < numberOfLaunches; i++) {
                            try {
                                launches.add(getLaunchFromJson(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        launchesListener.onResponse(launches);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        launchesListener.onError("Unable to get launch data.");
                    }
                }
        );
        VolleyController.getInstance(context).addToRequestQueue(exampleRequest);
    }

    public Launch getLaunchFromJson(JSONObject jsonObject) {
        Launch launch = new Launch();
        try {
            launch.setName(jsonObject.getString("name"));
            launch.setFlightNumber(jsonObject.getInt("flight_number"));
            launch.setDetail(jsonObject.getString("details"));
            launch.setDateUtc(jsonObject.getString("date_utc"));
            launch.setDateTimeUnix(jsonObject.getLong("date_unix"));
            launch.setPatchLinkSmall(jsonObject.getJSONObject("links").getJSONObject("patch").getString("small"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return launch;
    }
    

//    public Rocket getRocketById(String id, VolleyResponseListener volleyResponseListener) {
//        String url = ApiUtil.buildUrlString("/rockets/:id" + id);
//
//    }
}
