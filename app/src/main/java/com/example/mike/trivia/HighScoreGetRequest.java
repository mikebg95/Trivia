package com.example.mike.trivia;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class HighScoreGetRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private HighScoreGetRequest.Callback activity;

    public interface Callback {
        void gotHighScore(ArrayList<HighScore> highScores);
        void gotHighScoreError(String message);
    }

    public HighScoreGetRequest(Context context) {
        this.context = context;
    }

    void getHighScore(HighScoreGetRequest.Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://ide50-michaelgoldman.cs50.io:8080/list", this, this);
        queue.add(jsonArrayRequest);
        this.activity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighScoreError(error.getMessage());
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            ArrayList<HighScore> highScores = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject highScoreObject = response.getJSONObject(i);
                String name = highScoreObject.getString("name");
                int score = highScoreObject.getInt("score");
                HighScore highScore = new HighScore(name, score);
                highScores.add(highScore);
            }
            activity.gotHighScore(highScores);
        }
        catch(JSONException e) {
            activity.gotHighScoreError(e.getMessage());
        }

    }
}
