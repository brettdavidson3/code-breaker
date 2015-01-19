package com.lateralus.codebreaker.model;

public class LoseModel {

    private int score;
    private int highScore;

    public LoseModel(int score, int highScore) {
        this.score = score;
        this.highScore = highScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
