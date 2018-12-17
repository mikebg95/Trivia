package com.example.mike.trivia;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class HighScorePostRequest  {

    // initialize context variable
    private Context context;

    // constructor for this class
    public HighScorePostRequest(Context context) {
        this.context = context;
    }

    // request api post method
    void postHighScore(final HighScore newHighScore) {

        // initialize queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // perform string request
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://ide50-michaelgoldman.cs50.io:8080/list",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // do nothing
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                // add values to api
                Map<String, String> params = new HashMap<>();
                params.put("score", Integer.toString(newHighScore.getScore()));
                params.put("name", newHighScore.getName());
                return params;
            }
        };

        // add string request to queue
        queue.add(stringRequest);

    }
}