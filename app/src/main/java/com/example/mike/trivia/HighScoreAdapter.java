package com.example.mike.trivia;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HighScoreAdapter extends ArrayAdapter<HighScore> {
    // create empty arraylist to store high scores
    private ArrayList<HighScore> highScores = new ArrayList<>();

    public HighScoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HighScore> highScores) {
        super(context, resource, highScores);
        this.highScores = highScores;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.high_score_row, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        TextView score = convertView.findViewById(R.id.score);

        name.setText(highScores.get(position).getName());
        score.setText(Integer.toString(highScores.get(position).getScore()));

        return convertView;
    }

}
