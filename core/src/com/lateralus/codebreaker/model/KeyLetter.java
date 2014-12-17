package com.lateralus.codebreaker.model;

import java.util.List;

import static com.lateralus.codebreaker.controller.helper.RandomUtils.getNextValue;

public class KeyLetter extends Letter {

    private LetterEnum mappedLetter;
    private boolean isSolved;

    public KeyLetter(int col, int row, List<LetterEnum> availableValues, List<LetterEnum> availableMappedLetters) {
        super(col, row, getNextValue(availableValues));
        this.mappedLetter = getNextValue(availableMappedLetters);
        isSolved = false;
    }

    public LetterEnum getMappedLetter() {
        return mappedLetter;
    }

    public void setMappedLetter(LetterEnum mappedLetter) {
        this.mappedLetter = mappedLetter;
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
