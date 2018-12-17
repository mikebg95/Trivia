package com.example.mike.trivia;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class GamePlayActivity extends AppCompatActivity implements Serializable {

    // variable for build version
    int version = Build.VERSION.SDK_INT;

    // initialize variables for this activity
    private int questionCount;
    private int wins;
    private int fails;
    private int score;

    // create variables for layout items
    private TextView questionView;
    private RadioGroup choices;
    private RadioButton answer1;
    private RadioButton answer2;
    private RadioButton answer3;
    private RadioButton answer4;
    private Button submit;
    private TextView whichQuestion;
    private TextView whichScore;

    // create variable for arraylist questions
    private ArrayList<Question> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // set variables to
        questionView = findViewById(R.id.question);
        choices = findViewById(R.id.choices);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        submit = findViewById(R.id.submit);
        whichQuestion = findViewById(R.id.questionCount);
        whichScore = findViewById(R.id.scoreCount);

        // retrieve arraylist of questions from intent
        Intent intent = getIntent();
        questions = (ArrayList<Question>) intent.getSerializableExtra("questions");

        // keep track of question and score
        questionCount = 0;
        score = 0;

        // keep track of score and question number and show to user
        setInfo(questionCount, score);

        // get first question object and bind to layout views
        Question currentQuestion = questions.get(questionCount);
        bindViews(currentQuestion);


        // when answer is submitted
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // when none of the answer is chosen
                if (choices.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "please choose an answer",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // find out which answer was chosen
                    int id = choices.indexOfChild(choices.findViewById(choices.getCheckedRadioButtonId()));
                    RadioButton answerChosen = (RadioButton) choices.getChildAt(id);
                    String answerChosenString = answerChosen.getText().toString();

                    // check answer and add points
                    score += getPoints(questions.get(questionCount), answerChosenString);

                    // on last question, change "next" button to "submit"
                    if (questionCount == questions.size() - 2) {
                        String submitText = "Submit";
                        submit.setText(submitText);
                    }

                    // as long as it's not the last question
                    if (questionCount < questions.size() - 1) {

                        // clear item selected
                        choices.clearCheck();

                        // get next question object and bind to views
                        Question nextQuestion = questions.get(questionCount + 1);
                        bindViews(nextQuestion);

                        // keep track of question number
                        questionCount++;

                        // keep track of score and question number and show to user
                        setInfo(questionCount, score);


                    // when it's the last question
                    } else {

                        // go to score screen, send score variables as intent
                        Intent intent = new Intent(GamePlayActivity.this, HighScoresActivity.class);
                        intent.putExtra("score", score);
                        intent.putExtra("wins", wins);
                        intent.putExtra("fails", fails);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    // binds the text views to the question and answers
    // calls getQuestion and getShuffledAnswers,
    public void bindViews(Question question) {
        // set question view text
        String questionText = getQuestion(question);
        questionView.setText(questionText);

        // get array of shuffled answers
        ArrayList<String> answers = getShuffledAnswers(question);

        // set the first two answers
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));

        // if true/false
        if (answers.size() == 2) {
            answer3.setVisibility(View.INVISIBLE);
            answer4.setVisibility(View.INVISIBLE);
        }
        // if multiple choice
        else {
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.VISIBLE);
            answer3.setText(answers.get(2));
            answer4.setText(answers.get(3));
        }
    }

    // gets the current question (without html symbols)
    public String getQuestion(Question question) {
//        int version = Build.VERSION.SDK_INT;
        return  Html.fromHtml(question.getQuestion(), version).toString();
    }

    // gets array of shuffled answer choices for that question
    public ArrayList<String> getShuffledAnswers(Question question) {
        // make empty array for possible answers
        ArrayList<String> answers = new ArrayList<>();

        // add correct answer to array (without html symbols)
        answers.add(Html.fromHtml(question.getCorrectAnswer(), version).toString());

        // take out html symbols from incorrect answers and add to array
        for (int i = 0; i < question.getIncorrectAnswers().size(); i++) {
            answers.add(Html.fromHtml(question.getIncorrectAnswers().get(i), version).toString());
        }

        // shuffle and return array
        Collections.shuffle(answers);
        return answers;
    }

    // check if answer is correct, lets user know and calls getScore to calculate points
    public int getPoints(Question question, String answer) {
        Boolean correct = false;
        if (question.getCorrectAnswer().equals(answer)) {
            correct = true;
        }
        if (correct) {
            Toast.makeText(getApplicationContext(), "answer is correct!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "answer is incorrect! correct answer was " +
                            question.getCorrectAnswer(), Toast.LENGTH_SHORT).show();
        }

        return getScore(correct, question.getDifficulty());
    }

    // 15 points if difficult, 10 if medium and 5 if easy
    public int getScore(Boolean isCorrect, String difficulty) {
        int score = 0;
        if (isCorrect) {
            wins++;
            switch (difficulty) {
                case "hard":
                    score += 15;
                    break;
                case "medium":
                    score += 10;
                    break;
                case "easy":
                    score += 5;
                    break;
            }
        }
        else {
            fails++;
        }
        return score;
    }

    // keep track of score and question number and show to user
    public void setInfo(int questionCount, int score) {
        String questionText = "Question: " + (questionCount + 1) + "/" + questions.size();
        String scoreText = "Score: " + score;
        whichQuestion.setText(questionText);
        whichScore.setText(scoreText);
    }

    // when back button pressed, go back to home screen
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GamePlayActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}

