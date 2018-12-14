package com.example.mike.trivia;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class HighScorePostRequest  {

    private Context context;
//    private Callback activity;

//    private interface Callback {
//        void postedHighScore(ArrayList<HighScore> highScores);
//        void postedHighScoreError(String message);
//    }

    public HighScorePostRequest(Context context) {
        this.context = context;
    }

    void postHighScore(final HighScore newHighScore) {
        RequestQueue queue = Volley.newRequestQueue(context);
//        this.activity = activity;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://ide50-michaelgoldman.cs50.io:8080/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(context, response, Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
//                params.put("name", newHighScore.getName());
                params.put("score", Integer.toString(newHighScore.getScore()));
                params.put("name", newHighScore.getName());
                return params;

            }
        };

        queue.add(stringRequest);

    }
}