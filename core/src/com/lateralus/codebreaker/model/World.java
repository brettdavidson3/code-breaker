package com.lateralus.codebreaker.model;

import java.util.List;

public class World {

    public static final int LETTER_COLUMN_COUNT = 12;
    public static final int LETTER_ROW_COUNT = 17;
    public static final int KEY_LETTER_ROW = 2;

    private PositionLetter activeLetter;
    private List<PositionLetter> correctLetters;
    private List<PositionLetter> incorrectLetters;
    private List<KeyLetter> keyLetters;
    private KeyLetter[] displayableKeyLetters;

    public PositionLetter getActiveLetter() {
        return activeLetter;
    }

    public void setActiveLetter(PositionLetter activeLetter) {
        this.activeLetter = activeLetter;
    }

    public List<KeyLetter> getKeyLetters() {
        return keyLetters;
    }

    public void setKeyLetters(List<KeyLetter> keyLetters) {
        this.keyLetters = keyLetters;
    }

    public List<PositionLetter> getCorrectLetters() {
        return correctLetters;
    }

    public void setCorrectLetters(List<PositionLetter> correctLetters) {
        this.correctLetters = correctLetters;
    }

    public List<PositionLetter> getIncorrectLetters() {
        return incorrectLetters;
    }

    public void setIncorrectLetters(List<PositionLetter> incorrectLetters) {
        this.incorrectLetters = incorrectLetters;
    }

    public KeyLetter[] getDisplayableKeyLetters() {
        return displayableKeyLetters;
    }

    public void setDisplayableKeyLetters(KeyLetter[] displayableKeyLetters) {
        this.displayableKeyLetters = displayableKeyLetters;
    }
}
