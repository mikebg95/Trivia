package com.example.mike.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class HighScoreListActivity extends AppCompatActivity implements HighScoreGetRequest.Callback {

    ListView highScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_list);



        highScoreList = findViewById(R.id.highScoreList);

        HighScoreGetRequest highScoreGetRequest = new HighScoreGetRequest(getApplicationContext());
        highScoreGetRequest.getHighScore(this);

        Button homeScreen = findViewById(R.id.restart);
        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighScoreListActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void gotHighScore(ArrayList<HighScore> highScores) {

        Log.d("got high scores", "gotHighScore: ");

        Collections.sort(highScores, new SortByScore());

        // create adapter for high scores
        HighScoreAdapter adapter = new HighScoreAdapter(this, R.layout.high_score_row, highScores);

        // set adapter to listview
        highScoreList.setAdapter(adapter);
    }

    @Override
    public void gotHighScoreError(String message) {
        // print message
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighScoreListActivity.this, MenuActivity.class);
        startActivity(intent);
    }

}
