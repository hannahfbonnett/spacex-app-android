package com.example.mobdevspacexapp.data.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int numberOfLaunches = response.length();
                        for (int i = 0; i < numberOfLaunches; i++) {
                            try {
                                final Launch launch = getLaunchFromJson(response.getJSONObject(i));
                                getRocketById(response.getJSONObject(i).getString("rocket"), new RocketListener() {
                                    @Override
                                    public void onError(String message) {
                                        Log.d("ERROR", message);
                                    }

                                    @Override
                                    public void onResponse(Rocket response) {
                                        launch.setRocket(response);
                                    }
                                });
                                launches.add(launch);
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
        VolleyController.getInstance(context).addToRequestQueue(arrayRequest);
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

    public interface RocketListener {
        void onError(String message);

        void onResponse(Rocket response);
    }

    public void getRocketById(final String id, final RocketListener rocketListener) {
        String url = buildUrlString("/rockets/" + id);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        rocketListener.onResponse(getRocketFromJson(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        rocketListener.onError("Unable to get rocket data with id " + id);
                    }
                }
        );
        VolleyController.getInstance(context).addToRequestQueue(objectRequest);

    }

    public Rocket getRocketFromJson(JSONObject response) {
        Rocket rocket = null;
        try {
            String name = response.getString("name");
            String description = response.getString("description");
            List<String> imageLinks = new ArrayList<>();
            JSONArray imageArray = response.getJSONArray("flickr_images");
            int numberOfImages = imageArray.length();
            for(int i = 0; i < numberOfImages; i++) {
                String imageLink = imageArray.get(i).toString();
                imageLinks.add(imageLink);
            }
            boolean isActive = response.getBoolean("active");
            float heightMeters = response.getJSONObject("height").getLong("meters");
            float diameterMeters = response.getJSONObject("diameter").getLong("meters");
            long massLbs = response.getJSONObject("mass").getLong("lb");
            String firstFlightDate = response.getString("first_flight");
            rocket = new Rocket(name, imageLinks, description, isActive, heightMeters, diameterMeters, massLbs, firstFlightDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rocket;
    }
}
