package com.lateralus.codebreaker.model;

import com.lateralus.codebreaker.controller.helper.RandomUtils;

import static com.lateralus.codebreaker.controller.helper.RandomUtils.randomInt;

public class Letter {

    public Letter(int col, int row) {
        this.value = LetterEnum.fromIndex(randomInt(26));
        this.row = row;
        this.col = col;
    }

    public enum LetterEnum {
        A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9),
        K(10), L(11), M(12), N(13), O(14), P(15), Q(16), R(17), S(18), T(19), U(20),
        V(21), W(22), X(23), Y(24), Z(25);

        private int index;

        LetterEnum(int index) {
            this.index = index;
        }

        public static LetterEnum fromIndex(int index) {
            return LetterEnum.values()[index];
        }

        public int getIndex() {
            return index;
        }
    };

    private int row;
    private int col;
    private LetterEnum value;

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
