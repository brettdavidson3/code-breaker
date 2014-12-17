package com.lateralus.codebreaker.model;

import java.util.List;

public class World {

    public static final int LETTER_COLUMN_COUNT = 12;
    public static final int LETTER_ROW_COUNT = 17;
    public static final int KEY_LETTER_ROW = 2;

    private Letter activeLetter;
    private List<Letter> correctLetters;
    private List<Letter> incorrectLetters;
    private List<KeyLetter> keyLetters;

    public Letter getActiveLetter() {
        return activeLetter;
    }

    public void setActiveLetter(Letter activeLetter) {
        this.activeLetter = activeLetter;
    }

    public List<KeyLetter> getKeyLetters() {
        return keyLetters;
    }

    public void setKeyLetters(List<KeyLetter> keyLetters) {
        this.keyLetters = keyLetters;
    }

    public List<Letter> getCorrectLetters() {
        return correctLetters;
    }

    public void setCorrectLetters(List<Letter> correctLetters) {
        this.correctLetters = correctLetters;
    }

    public List<Letter> getIncorrectLetters() {
        return incorrectLetters;
    }

    public void setIncorrectLetters(List<Letter> incorrectLetters) {
        this.incorrectLetters = incorrectLetters;
    }
}
