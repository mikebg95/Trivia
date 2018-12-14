package com.example.mike.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {

    public int score;
    public String name;

    ListView highScoreList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

//        HighScoreGetRequest highScoreGetRequest = new HighScoreGetRequest(getApplicationContext());
//        highScoreGetRequest.getHighScore(this);

        Intent intent = getIntent();
        int wins = intent.getIntExtra("wins", 0);
        int fails = intent.getIntExtra("fails", 0);

        score = wins - fails;

        TextView result = findViewById(R.id.result);
        TextView result2 = findViewById(R.id.result2);
        result.setText("You finished the quiz! You answered " + wins + " questions correctly and " +
                        fails + " questions incorrectly!");

        result2.setText("Your score is " + score + "! Please fill in your name to submit your score!");


        Button submitScore = findViewById(R.id.submitScore);
        final EditText nameText = findViewById(R.id.name);
//        highScoreList = findViewById(R.id.highscores);

        submitScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameText.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "please insert name", Toast.LENGTH_SHORT).show();
                }
                else {
                    name = nameText.getText().toString().trim();
                    HighScore newHighScore = new HighScore(name, score);


                    HighScorePostRequest highScorePostRequest = new HighScorePostRequest(getApplicationContext());
                    highScorePostRequest.postHighScore(newHighScore);

                    Intent intent = new Intent(HighScoresActivity.this, HighScoreListActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighScoresActivity.this, MenuActivity.class);
        startActivity(intent);
    }

}
