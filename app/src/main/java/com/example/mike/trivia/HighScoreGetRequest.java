package com.example.mike.trivia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HighScoreGetRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    // initialize variables for this class
    private Context context;
    private HighScoreGetRequest.Callback activity;

    // request performs callback to one of these
    public interface Callback {
        void gotHighScore(ArrayList<HighScore> highScores);
        void gotHighScoreError(String message);
    }

    // constructor for this class
    public HighScoreGetRequest(Context context) {
        this.context = context;
    }

    void getHighScore(HighScoreGetRequest.Callback activity) {

        // create queue, request json array and add to queue
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                "https://ide50-michaelgoldman.cs50.io:8080/list",
                this,
                this);
        queue.add(jsonArrayRequest);

        // save callback activity to local variable
        this.activity = activity;
    }

    // when api request returns error
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighScoreError(error.getMessage());
    }

    // when api request succeeded
    @Override
    public void onResponse(JSONArray response) {
        try {
            // create empty arraylist to store highscores
            ArrayList<HighScore> highScores = new ArrayList<>();

            // iterate through array list, create HighScore objects and add them to array
            for (int i = 0; i < response.length(); i++) {
                JSONObject highScoreObject = response.getJSONObject(i);
                String name = highScoreObject.getString("name");
                int score = highScoreObject.getInt("score");
                HighScore highScore = new HighScore(name, score);
                highScores.add(highScore);
            }

            // perform callback and send arraylist as argument
            activity.gotHighScore(highScores);
        }
        // when api request not succeeded
        catch(JSONException e) {
            activity.gotHighScoreError(e.getMessage());
        }

    }
}
