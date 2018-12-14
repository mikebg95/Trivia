package com.example.mike.trivia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements QuestionsRequest.Callback {

    private Spinner categoriesDropdown;
    private static final String[] categories = {"Any Category", "General Knowledge", "Entertainment: Books", "Entertainment: Film", "Entertainment: Music", "Entertainment: Musicals & Theatres", "Entertainment: Television", "Entertainment: Video Games", "Entertainment: Board Games", "Science & Nature", "Science: Computers", "Science: Mathematics", "Mythology", "Sports", "Geography", "History", "Politics", "Art", "Celebrities", "Animals", "Vehicles", "Entertainment: Comics", "Science: Gadgets", "Entertainment: Japanese Anime & Manga", "Entertainment: Cartoon & Animations"};

    private Spinner difficultyDropdown;
    private static final String[] difficulties = {"Any Difficulty", "Easy", "Medium", "Hard"};

    private Spinner typeDropdown;
    private static final String[] types = {"Any Type", "Multiple Choice", "True / False"};


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

        context = getApplicationContext();

        // create variable for number of questions
        final EditText numQuestions = findViewById(R.id.numQuestions);

        // create variable for submit button
        Button submitButton = findViewById(R.id.start);
        Button seeHighScores = findViewById(R.id.highscores);

        // create variables for dropdown menus
        categoriesDropdown = findViewById(R.id.category);
        difficultyDropdown = findViewById(R.id.difficulty);
        typeDropdown = findViewById(R.id.type);


        // create adapters for dropdown menus
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_spinner_item, categories);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_spinner_item, difficulties);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(MenuActivity.this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // set adapters to dropdown menus
        categoriesDropdown.setAdapter(categoriesAdapter);
        difficultyDropdown.setAdapter(difficultyAdapter);
        typeDropdown.setAdapter(typeAdapter);

        // set listener for categories dropdown menu
        categoriesDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("category position", Integer.toString(position));
                if (position != 0) {
                    category = position + 8;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APIUrl = "https://opentdb.com/api.php?amount=";

                // get information typed in by user
                difficulty = difficultyDropdown.getSelectedItem().toString();
                type = typeDropdown.getSelectedItem().toString();

                if (TextUtils.isEmpty(numQuestions.getText())) {
                    Toast.makeText(context, "please fill in the amount of questions", Toast.LENGTH_SHORT).show();
                }
                else {
                    numberOfQuestions = Integer.parseInt(numQuestions.getText().toString());


                    if (numberOfQuestions > 0 && numberOfQuestions <= 50) {
                        APIUrl += numberOfQuestions;

                        if (category != 0) {
                            APIUrl += "&category=" + Integer.toString(category);
                        }

                        if (!difficulty.equals("Any Difficulty")) {
                            APIUrl += "&difficulty=" + difficulty.toLowerCase();
                        }

                        if (!type.equals("Any Type")) {
                            APIUrl += "&type=";
                            if (type.equals("Multiple Choice")) {
                                APIUrl += "multiple";
                            } else if (type.equals("True / False")) {
                                APIUrl += "boolean";
                            }
                        }


                        Log.v("url", APIUrl);

                        // send request for categories
                        QuestionsRequest questionsRequest = new QuestionsRequest(context);
                        questionsRequest.getQuestions(MenuActivity.this, APIUrl);
                    } else {
                        Toast.makeText(context, "number of questions must be between 1 and 50", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        seeHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, HighScoreListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        Log.v("received questions!", "received questions!");
        Intent intent = new Intent(MenuActivity.this, GamePlayActivity.class);
        intent.putExtra("questions", questions);
        startActivity(intent);
    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}



