package com.example.mike.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class GamePlayActivity extends AppCompatActivity implements Serializable {

    public int questionCount;
    public int wins;
    public int fails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // create variables to show question and answer
        final TextView question = findViewById(R.id.question);
        final RadioButton answer1 = findViewById(R.id.answer1);
        final RadioButton answer2 = findViewById(R.id.answer2);
        final RadioButton answer3 = findViewById(R.id.answer3);
        final RadioButton answer4 = findViewById(R.id.answer4);
        final RadioGroup choices = findViewById(R.id.choices);

        // create variable for submit button
        final Button submit = findViewById(R.id.submit);

        final TextView whichQuestion = findViewById(R.id.questionCount);
        final TextView whichScore = findViewById(R.id.scoreCount);




        // retrieve arraylist of questions
        Intent intent = getIntent();
        final ArrayList<Question> questions = (ArrayList<Question>) intent.getSerializableExtra("questions");

        // keep track of question
        questionCount = 0;

        whichQuestion.setText("Question: " + questionCount + "/" + questions.size());
        whichScore.setText("Score: " + (wins - fails));

//        question.setText(questions.get(questionCount).getQuestion());
//
//        Html.fromHtml(String).toString();

        String firstQuestion = questions.get(questionCount).getQuestion();
        firstQuestion = Html.fromHtml(firstQuestion).toString();

        question.setText(firstQuestion);

//        Log.d("question with html", questions.get(questionCount).getQuestion());
//        Log.d("question without html", Html.fromHtml(questions.get(questionCount).getQuestion()).toString());


//        Log.d("first incorrect answer", questions.get(questionCount).getIncorrectAnswers().get(0));



        // get list of possible answers and shuffle them
        ArrayList<String> possibleAnswers = possibleAnswers(questionCount, questions);
        Collections.shuffle(possibleAnswers);

        // show first question and answer choices
//        question.setText(questions.get(questionCount).getQuestion());
        answer1.setText(possibleAnswers.get(0));
        answer2.setText(possibleAnswers.get(1));

        // if multiple choice
        if (possibleAnswers.size() > 2) {
            answer3.setText(possibleAnswers.get(2));
            answer4.setText(possibleAnswers.get(3));
//            answer3.setVisibility(View.VISIBLE);
//            answer4.setVisibility(View.VISIBLE);
        }
        else {
            answer3.setVisibility(View.INVISIBLE);
            answer4.setVisibility(View.INVISIBLE);
        }


        //
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                answer3.setVisibility(View.VISIBLE);
                answer4.setVisibility(View.VISIBLE);

                if (choices.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "please choose an answer", Toast.LENGTH_SHORT).show();
                }
                else {

                    // find out which answer was chosen
                    int id = choices.indexOfChild(choices.findViewById(choices.getCheckedRadioButtonId()));

                    RadioButton answerChosen = (RadioButton) choices.getChildAt(id);
                    String answerChosenString = answerChosen.getText().toString();

                    if (questionCount == questions.size() - 2) {
                        submit.setText("Submit");
                    }

                    if (questionCount < questions.size() - 1) {

                        // check if chosen answer is correct or false
                        if (answerChosenString.equals(questions.get(questionCount).getCorrectAnswer())) {
                            Toast.makeText(getApplicationContext(), "answer is correct!", Toast.LENGTH_SHORT).show();
                            wins++;
                        } else {
                            Toast.makeText(getApplicationContext(), "answer is incorrect! correct answer was " + questions.get(questionCount).getCorrectAnswer(), Toast.LENGTH_SHORT).show();
                            fails++;
                        }

                        whichScore.setText("Score: " + (wins - fails));

                        // clear question and answers
                        question.setText("");
                        answer1.setText("");
                        answer2.setText("");
                        answer3.setText("");
                        answer4.setText("");


                        // get shuffled array of next answers
                        ArrayList<String> possibleAnswers = possibleAnswers(questionCount + 1, questions);
                        Collections.shuffle(possibleAnswers);

                        // get next question
                        String currentQuestion = getQuestion(questionCount + 1, questions);
                        currentQuestion = Html.fromHtml(currentQuestion).toString();

                        // bind the question and answers to the views
                        question.setText(currentQuestion);

                        Log.d("question with html", questions.get(questionCount + 1).getQuestion());
                        Log.d("question without html", Html.fromHtml(questions.get(questionCount + 1).getQuestion()).toString());

                        answer1.setText(possibleAnswers.get(0));
                        answer2.setText(possibleAnswers.get(1));

                        // if multiple choice question
                        if (possibleAnswers.size() > 2) {
                            answer3.setText(possibleAnswers.get(2));
                            answer4.setText(possibleAnswers.get(3));
                        }

                        // if true/false question
                        else {
                            answer3.setVisibility(View.INVISIBLE);
                            answer4.setVisibility(View.INVISIBLE);
                        }

                        choices.clearCheck();

                        // keep track of question number
                        questionCount++;

                        whichQuestion.setText("Question: " + questionCount + "/" + questions.size());



                        // set visibility of 3rd and 4th answer back to visible
//                    answer3.setVisibility(View.VISIBLE);
//                    answer4.setVisibility(View.VISIBLE);


                    } else {
                        // check if chosen answer is correct or false
                        if (answerChosenString.equals(questions.get(questionCount).getCorrectAnswer())) {
                            Toast.makeText(getApplicationContext(), "answer is correct!", Toast.LENGTH_SHORT).show();
                            wins++;
                        } else {
                            fails++;
                            Toast.makeText(getApplicationContext(), "answer is incorrect! correct answer was " + questions.get(questionCount).getCorrectAnswer(), Toast.LENGTH_SHORT).show();
                        }

                        // go to score screen
                        Intent intent = new Intent(GamePlayActivity.this, HighScoresActivity.class);
                        intent.putExtra("wins", wins);
                        intent.putExtra("fails", fails);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    public String getQuestion(int questionCount, ArrayList<Question> questions) {
        return questions.get(questionCount).getQuestion();
    }

    public ArrayList<String> possibleAnswers(int questionCount, ArrayList<Question> questions) {
        // make empty array for possible answers
        ArrayList<String> answers = new ArrayList<>();

        // get to current question
        Question currentQuestion = questions.get(questionCount);

        // add correct answer to array
        answers.add(Html.fromHtml(currentQuestion.getCorrectAnswer()).toString());


        // add incorrect answers to array
//        answers.addAll(currentQuestion.getIncorrectAnswers());

        // take out html symbols fr
        for (int i = 0; i < currentQuestion.getIncorrectAnswers().size(); i++) {
            answers.add(Html.fromHtml(currentQuestion.getIncorrectAnswers().get(i)).toString());
        }

        return answers;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GamePlayActivity.this, MenuActivity.class);
        startActivity(intent);
    }


}

