package com.example.mobdevspacexapp.data.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mobdevspacexapp.data.model.Company;
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

    //Ref: https://stackoverflow.com/questions/33535435/how-to-create-a-proper-volley-listener-for-cross-class-volley-method-calling
    public interface LaunchesListener {
        void onError(String message);

        void onResponse(List<Launch> response);
    }

    public void getUpcomingLaunches(final LaunchesListener launchesListener) {
        String url = buildUrlString("/launches/upcoming");
        getLaunches(url, launchesListener);
    }

    public void getPastLaunches(final LaunchesListener launchesListener) {
        String url = buildUrlString("/launches/past");
        getLaunches(url, launchesListener);
    }

    public void getAllLaunches(final LaunchesListener launchesListener) {
        String url = buildUrlString("/launches");
        getLaunches(url, launchesListener);
    }


    public void getLaunches(final String url, final LaunchesListener launchesListener) {
        final List<Launch> launches = new ArrayList<>();

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

    //Ref: https://stackoverflow.com/questions/33535435/how-to-create-a-proper-volley-listener-for-cross-class-volley-method-calling
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
        Rocket rocket = new Rocket();
        try {
            rocket.setName(response.getString("name"));
            rocket.setDescription(response.getString("description"));
            rocket.setImageLink(response.getJSONArray("flickr_images").get(0).toString());
            rocket.setActive(response.getBoolean("active"));
            rocket.setHeightMeters(response.getJSONObject("height").getLong("meters"));
            rocket.setHeightFeet(response.getJSONObject("height").getLong("feet"));
            rocket.setDiameterMeters(response.getJSONObject("diameter").getLong("meters"));
            rocket.setDiameterFeet(response.getJSONObject("diameter").getLong("feet"));
            rocket.setMassLbs(response.getJSONObject("mass").getLong("lb"));
            rocket.setMassKgs(response.getJSONObject("mass").getLong("kg"));
            rocket.setFirstFlightDate(response.getString("first_flight"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rocket;
    }

    //Ref: https://stackoverflow.com/questions/33535435/how-to-create-a-proper-volley-listener-for-cross-class-volley-method-calling
    public interface CompanyListener {
        void onError(String message);

        void onResponse(Company response);
    }

    public void getCompanyInfo(final CompanyListener companyListener) {
        String url = buildUrlString("/company");
        JsonObjectRequest arrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        companyListener.onResponse(getCompanyInfoFromJson(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        companyListener.onError("Unable to get company data.");
                    }
                }
        );
        VolleyController.getInstance(context).addToRequestQueue(arrayRequest);
    }

    public static Company getCompanyInfoFromJson(JSONObject response) {
        Company company = null;
        try {
            String name = response.getString("name");
            String description = response.getString("summary");
            String founder = response.getString("founder");
            int foundedYear = response.getInt("founded");
            int employees = response.getInt("employees");
            int vehicles = response.getInt("vehicles");
            int launchSites = response.getInt("launch_sites");
            int testSites = response.getInt("test_sites");
            String websiteLink = response.getJSONObject("links").getString("website");
            String flickrLink = response.getJSONObject("links").getString("flickr");
            String twitterLink = response.getJSONObject("links").getString("twitter");
            company = new Company(name, description, founder, foundedYear, employees, vehicles, launchSites, testSites, websiteLink, flickrLink, twitterLink);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return company;
    }
}
