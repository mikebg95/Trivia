package com.example.mike.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class HighScoresActivity extends AppCompatActivity {

    // initialize variables for this class
    private int score;
    private String name;
    ListView highScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        // get game results via intent and calculate score
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        int wins = intent.getIntExtra("wins", 0);
        int fails = intent.getIntExtra("fails", 0);

        // link button and views to variables
        TextView result = findViewById(R.id.result);
        TextView result2 = findViewById(R.id.result2);
        Button submitScore = findViewById(R.id.submitScore);
        final EditText nameText = findViewById(R.id.name);

        // set text views to relevant results and score
        String resultText1 = "You finished the quiz! You answered " + wins +
                " questions correctly and " + fails + " questions incorrectly!";
        String resultText2 = "Your score is " + score + "! Please fill in your name to submit" +
                "your score!";
        result.setText(resultText1);
        result2.setText(resultText2);

        // when clicked on submit button
        submitScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when no name is submitted
                if (nameText.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "please insert name",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // create new high score object
                    name = nameText.getText().toString().trim();
                    HighScore newHighScore = new HighScore(name, score);

                    // make api request to add name and score
                    HighScorePostRequest highScorePostRequest = new HighScorePostRequest(getApplicationContext());
                    highScorePostRequest.postHighScore(newHighScore);

                    // go to highScoreListActivity via intent
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
