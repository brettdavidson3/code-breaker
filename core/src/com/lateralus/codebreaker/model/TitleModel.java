package com.lateralus.codebreaker.model;

import com.lateralus.codebreaker.model.constant.Difficulty;

public class TitleModel {

    private Difficulty difficulty;

    public TitleModel() {
        this.difficulty = Difficulty.Normal;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
