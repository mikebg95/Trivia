package com.example.mike.trivia;

import java.util.Comparator;

public class SortByScore implements Comparator<HighScore> {

    // used for sorting in ascending order of score

    public int compare(HighScore a, HighScore b) {
        return b.getScore() - a.getScore();
    }
}

