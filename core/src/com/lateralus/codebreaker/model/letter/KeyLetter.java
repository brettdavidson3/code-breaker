package com.lateralus.codebreaker.model.letter;

import java.util.List;

import static com.lateralus.codebreaker.util.RandomUtils.getNextValue;

public class KeyLetter {

    private LetterEnum keyLetter;
    private LetterEnum valueLetter;
    private boolean isSolved;

    public KeyLetter(List<LetterEnum> availableKeys, LetterEnum valueLetter) {
        this.keyLetter = getNextValue(availableKeys);
        this.valueLetter = valueLetter;
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
