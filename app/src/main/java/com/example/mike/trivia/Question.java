package com.example.mike.trivia;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Question implements Serializable {

    // variables for question item
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;

    // constructor for this class
    public Question(String category, String type, String difficulty, String question,
                    String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    // getters for this class
    public String getCategory() {
        return category;
    }
    public String getType() {
        return type;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public String getQuestion() {
        return question;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
