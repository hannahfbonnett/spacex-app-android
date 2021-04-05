package com.example.mobdevspacexapp.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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
}
