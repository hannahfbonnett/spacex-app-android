package com.example.mobdevspacexapp.data.api;

import java.net.URL;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiUtil {

    public static final String BASE_API_URL = "https://api.spacexdata.com/v4";

    public static String buildUrlString(String endpoint) {
        return BASE_API_URL + endpoint;
    }
}
