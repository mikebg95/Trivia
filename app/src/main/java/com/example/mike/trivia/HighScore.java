package com.example.mike.trivia;

import java.io.Serializable;

public class HighScore implements Serializable {

    // variables for this class
    private String name;
    private int score;

    // constructor for this class
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // getters for this class
    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }

    // setters for this class
    public void setName(String name) {
        this.name = name;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
