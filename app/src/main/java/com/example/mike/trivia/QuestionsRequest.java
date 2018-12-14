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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;



public class QuestionsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback activity;

    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionsError(String message);
    }

    public QuestionsRequest(Context context) {
        this.context = context;
    }

    void getQuestions(Callback activity, String url) {
        Log.d("url2", url);
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
        this.activity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Log.d("JSONObject received!", "JSONObject received!");
            // create arraylist to stash question objects
            ArrayList<Question> questions = new ArrayList<>();

            // get json objects and convert to question objects
            JSONArray questionsArray = response.getJSONArray("results");
            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionObject = questionsArray.getJSONObject(i);
                String category = questionObject.getString("category");
                Log.d("category", category);

                String type = questionObject.getString("type");
                Log.d("type", type);

                String difficulty = questionObject.getString("difficulty");
                Log.d("difficulty", difficulty);

                String question = questionObject.getString("question");
                Log.d("question", question);

                String correctAnswer = questionObject.getString("correct_answer");
                Log.d("correct answer", correctAnswer);

                JSONArray incorrectAnswers = questionObject.getJSONArray("incorrect_answers");
                ArrayList<String> incorrectAnswersArray = new ArrayList<>();
                for (int j = 0; j < incorrectAnswers.length(); j++) {
                    Log.d("incorrect answer " + j, incorrectAnswers.get(j).toString());
                    incorrectAnswersArray.add(incorrectAnswers.get(j).toString());

                }


                // add question objects to arraylist of questions
                Question objectQuestion =  new Question(category, type, difficulty, question, correctAnswer, incorrectAnswersArray);
                questions.add(objectQuestion);

            }
            activity.gotQuestions(questions);
        }
        catch (JSONException e) {
            activity.gotQuestionsError(e.getMessage());
        }

    }
}
