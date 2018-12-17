package com.example.mike.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuestionsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    // initialize variables for context and callback activity
    private Context context;
    private Callback activity;

    // callback will be performed on one of these
    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionsError(String message);
    }

    // constructor for this class
    public QuestionsRequest(Context context) {
        this.context = context;
    }

    void getQuestions(Callback activity, String url) {

        // create volley queue, request json object and add to queue
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

        // save callback activity in local variable
        this.activity = activity;
    }

    // when api request failed
    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionsError(error.getMessage());
    }

    // when api request succeeded
    @Override
    public void onResponse(JSONObject response) {
        try {

            // create arraylist to stash question objects
            ArrayList<Question> questions = new ArrayList<>();

            // get json objects and convert to question objects
            JSONArray questionsArray = response.getJSONArray("results");
            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionObject = questionsArray.getJSONObject(i);

                String category = questionObject.getString("category");
                String type = questionObject.getString("type");
                String difficulty = questionObject.getString("difficulty");
                String question = questionObject.getString("question");
                String correctAnswer = questionObject.getString("correct_answer");

                // convert json array of incorrect answers to array list
                JSONArray incorrectAnswers = questionObject.getJSONArray("incorrect_answers");
                ArrayList<String> incorrectAnswersArray = new ArrayList<>();
                for (int j = 0; j < incorrectAnswers.length(); j++) {
                    Log.d("incorrect answer " + j, incorrectAnswers.get(j).toString());
                    incorrectAnswersArray.add(incorrectAnswers.get(j).toString());

                }

                // add question objects to array list of questions
                Question objectQuestion =  new Question(category, type, difficulty, question,
                        correctAnswer, incorrectAnswersArray);
                questions.add(objectQuestion);

            }

            // perform callback and add array list of question objects
            activity.gotQuestions(questions);
        }
        catch (JSONException e) {
            activity.gotQuestionsError(e.getMessage());
        }

    }
}
