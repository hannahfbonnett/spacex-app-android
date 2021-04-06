package com.example.mobdevspacexapp.data.api;

import com.example.mobdevspacexapp.data.model.Launch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiUtil {

    public static final String BASE_API_URL = "https://api.spacexdata.com/v4";

    public static String buildUrlString(String endpoint) {
        return BASE_API_URL + endpoint;
    }

    public static ArrayList<Launch> getLaunchesFromJson(JSONArray response) {
        ArrayList<Launch> launches = new ArrayList<>();
        int numberOfLaunches = response.length();
        for(int i = 0; i < numberOfLaunches; i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String launchName = jsonObject.getString("name");
                int launchNumber = jsonObject.getInt("flight_number");
                String launchDetail = jsonObject.getString("details");
                String launchDateTime = jsonObject.getString("date_utc");
                Long dateTimeUnix = jsonObject.getLong("date_unix");
                String launchPatchLinkSmall = jsonObject.getJSONObject("links").getJSONObject("patch").getString("small");
                launches.add(new Launch(launchNumber, launchName, launchDetail, launchDateTime, launchPatchLinkSmall, dateTimeUnix));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return launches;
    }
}
