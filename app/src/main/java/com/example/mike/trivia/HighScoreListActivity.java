package com.example.mike.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class HighScoreListActivity extends AppCompatActivity implements HighScoreGetRequest.Callback {

    // initialize list view variable
    ListView highScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);

        // link list view to variable and create homescreen button variable
        highScoreList = findViewById(R.id.highScoreList);
        Button homeScreen = findViewById(R.id.restart);

        // perform api request for high scores
        HighScoreGetRequest highScoreGetRequest = new HighScoreGetRequest(getApplicationContext());
        highScoreGetRequest.getHighScore(this);

        // when clicked on home screen button
        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to menuActivity via intent
                Intent intent = new Intent(HighScoreListActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    // when callback performed succesfully
    @Override
    public void gotHighScore(ArrayList<HighScore> highScores) {

        // sort the high scores
        Collections.sort(highScores, new SortByScore());

        // create adapter for high scores and set adapter to list view
        HighScoreAdapter adapter = new HighScoreAdapter(this, R.layout.high_score_row, highScores);
        highScoreList.setAdapter(adapter);
    }

    // when callback not succeeded
    @Override
    public void gotHighScoreError(String message) {
        // print message
    }

    // when back button pressed, go back to home screen
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighScoreListActivity.this, MenuActivity.class);
        startActivity(intent);
    }

}
