package com.lateralus.codebreaker.model;

public class MainModel {

    private int highScore;

    public MainModel(int highScore) {
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
