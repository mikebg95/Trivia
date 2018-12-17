package com.example.mike.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoreAdapter extends ArrayAdapter<HighScore> {

    // create empty array list to store high scores
    private ArrayList<HighScore> highScores;

    public HighScoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HighScore> highScores) {
        super(context, resource, highScores);
        this.highScores = highScores;
    }

    // bind views to row in list view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.high_score_row, parent, false);
        }

        // variables linked to text views
        TextView name = convertView.findViewById(R.id.name);
        TextView score = convertView.findViewById(R.id.score);

        // set text views to name and score
        name.setText(highScores.get(position).getName());
        String scoreText = Integer.toString(highScores.get(position).getScore());
        score.setText(scoreText);

        return convertView;
    }

}
