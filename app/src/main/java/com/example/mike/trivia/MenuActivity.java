package com.example.mike.trivia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements QuestionsRequest.Callback {

    // initialize variables for dropdown menus
    private Spinner difficultyDropdown;
    private Spinner typeDropdown;

    // initialize arrays containing items for dropdown menus
    private static final String[] categories = {"Any Category", "General Knowledge", "Entertainment:"
            + " Books", "Entertainment: Film", "Entertainment: Music", "Entertainment: Musicals &" +
            "Theatres", "Entertainment: Television", "Entertainment: Video Games", "Entertainment:" +
            "Board Games", "Science & Nature", "Science: Computers", "Science: Mathematics",
            "Mythology", "Sports", "Geography", "History", "Politics", "Art", "Celebrities",
            "Animals", "Vehicles", "Entertainment: Comics", "Science: Gadgets", "Entertainment:" +
            "Japanese Anime & Manga", "Entertainment: Cartoon & Animations"};
    private static final String[] difficulties = {"Any Difficulty", "Easy", "Medium", "Hard"};
    private static final String[] types = {"Any Type", "Multiple Choice", "True / False"};

    // initialize variables for this activity
    private int numberOfQuestions;
    private int category;
    private String difficulty;
    private String type;
    private String APIUrl = "https://opentdb.com/api.php?amount=";
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // save application context in a variable
        context = getApplicationContext();

        // create variable for user to submit number of questions
        final EditText numQuestions = findViewById(R.id.numQuestions);

        // create variable for submit button
        Button submitButton = findViewById(R.id.start);
        Button seeHighScores = findViewById(R.id.highscores);

        // create variables for dropdown menus
        Spinner categoriesDropdown = findViewById(R.id.category);
        difficultyDropdown = findViewById(R.id.difficulty);
        typeDropdown = findViewById(R.id.type);

        // create adapters for dropdown menus
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(MenuActivity.this,
                android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(MenuActivity.this,
                android.R.layout.simple_spinner_item, difficulties);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(MenuActivity.this,
                android.R.layout.simple_spinner_item, types);

        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // set adapters to dropdown menus
        categoriesDropdown.setAdapter(categoriesAdapter);
        difficultyDropdown.setAdapter(difficultyAdapter);
        typeDropdown.setAdapter(typeAdapter);

        // when category selected
        categoriesDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // save category (except when "any type" chosen)
                if (position != 0) {
                    category = position + 8;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // not an option as "Any Type" is the default
            }
        });

        // when submit button is clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                APIUrl = "https://opentdb.com/api.php?amount=";

                // get information typed in by user
                difficulty = difficultyDropdown.getSelectedItem().toString();
                type = typeDropdown.getSelectedItem().toString();

                // when user doesn't fill in number of questions
                if (TextUtils.isEmpty(numQuestions.getText())) {
                    Toast.makeText(context, "please fill in the amount of questions",
                            Toast.LENGTH_SHORT).show();
                }
                // when user does fill in number of questions
                else {
                    // save number as string variable
                    numberOfQuestions = Integer.parseInt(numQuestions.getText().toString());

                    // number of questions must be between 1 and 50
                    if (numberOfQuestions > 0 && numberOfQuestions <= 50) {

                        // add number of questions to api url
                        APIUrl += numberOfQuestions;

                        // if user chooses specific category, add to url
                        if (category != 0) {
                            APIUrl += "&category=" + Integer.toString(category);
                        }

                        // if user chooses specific difficulty, add to url
                        if (!difficulty.equals("Any Difficulty")) {
                            APIUrl += "&difficulty=" + difficulty.toLowerCase();
                        }

                        // if user chooses specific type, add to url
                        if (!type.equals("Any Type")) {
                            APIUrl += "&type=";
                            if (type.equals("Multiple Choice")) {
                                APIUrl += "multiple";
                            } else if (type.equals("True / False")) {
                                APIUrl += "boolean";
                            }
                        }

                        // send request for categories
                        QuestionsRequest questionsRequest = new QuestionsRequest(context);
                        questionsRequest.getQuestions(MenuActivity.this, APIUrl);

                    // when number of questions inserted by user is not between 1 and 50
                    } else {
                        Toast.makeText(context, "number of questions must be between 1 and 50",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        // when "see highscores" button is clicked
        seeHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to HighScoreListActivity via intent
                Intent intent = new Intent(MenuActivity.this, HighScoreListActivity.class);
                startActivity(intent);
            }
        });

    }

    // when api request succeeded
    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        Intent intent = new Intent(MenuActivity.this, GamePlayActivity.class);
        intent.putExtra("questions", questions);
        startActivity(intent);
    }

    // when api request failed
    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}



