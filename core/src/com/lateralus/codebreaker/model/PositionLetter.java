package com.lateralus.codebreaker.model;

import static com.lateralus.codebreaker.controller.helper.RandomUtils.randomInt;

public class PositionLetter {

    private int row;
    private int col;
    private LetterEnum value;

    public PositionLetter(int col, int row) {
        this.value = LetterEnum.fromIndex(randomInt(26));
        this.row = row;
        this.col = col;
    }

    public PositionLetter(int col, int row, LetterEnum value) {
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public LetterEnum getValue() {
        return value;
    }

    public void setValue(LetterEnum value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
