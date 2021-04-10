package com.example.mobdevspacexapp.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobdevspacexapp.data.model.Launch;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// Ref: https://developer.android.com/training/volley/requestqueue
public class VolleyController {

    private static VolleyController instance;
    private RequestQueue requestQueue;


    private VolleyController(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleyController getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyController(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    private JsonArrayRequest getJsonArrayRequest(Context context, String url, Response.Listener<JSONArray> onResponse, Response.ErrorListener onError){
        return new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                onResponse,
                onError
        );
    }
}
