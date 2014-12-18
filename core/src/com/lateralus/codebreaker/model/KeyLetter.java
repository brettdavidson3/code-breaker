package com.lateralus.codebreaker.model;

import java.util.List;

import static com.lateralus.codebreaker.controller.helper.RandomUtils.getNextValue;

public class KeyLetter {

    private LetterEnum keyLetter;
    private LetterEnum valueLetter;
    private boolean isSolved;

    public KeyLetter(List<LetterEnum> availableKeys, List<LetterEnum> availableValues) {
        this.keyLetter = getNextValue(availableKeys);
        this.valueLetter = getNextValue(availableValues);
        isSolved = false;
    }

    public LetterEnum getKeyLetter() {
        return keyLetter;
    }

    public void setKeyLetter(LetterEnum keyLetter) {
        this.keyLetter = keyLetter;
    }

    public LetterEnum getValueLetter() {
        return valueLetter;
    }

    public void setValueLetter(LetterEnum valueLetter) {
        this.valueLetter = valueLetter;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public boolean isNotSolved() {
        return !isSolved;
    }

    public void setSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

}
