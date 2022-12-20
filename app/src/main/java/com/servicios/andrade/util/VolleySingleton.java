package com.servicios.andrade.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton intanciaVolley;
    private RequestQueue request;
    private static Context contexto;

    private VolleySingleton(Context context) {
        contexto = context;
        request = getRequestQueue();
    }


    public static synchronized VolleySingleton getIntanciaVolley(Context context) {
        if (intanciaVolley == null) {
            intanciaVolley = new VolleySingleton(context);
        }

        return intanciaVolley;
    }

    public RequestQueue getRequestQueue() {
        if (request == null) {
            request = Volley.newRequestQueue(contexto.getApplicationContext());
        }

        return request;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
