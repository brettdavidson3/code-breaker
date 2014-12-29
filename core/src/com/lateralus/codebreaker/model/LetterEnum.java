package com.lateralus.codebreaker.model;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public enum LetterEnum {
    A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9),
    K(10), L(11), M(12), N(13), O(14), P(15), Q(16), R(17), S(18), T(19), U(20),
    V(21), W(22), X(23), Y(24), Z(25), BLANK(26);

    private int index;

    LetterEnum(int index) {
        this.index = index;
    }

    public static LetterEnum fromIndex(int index) {
        return LetterEnum.values()[index];
    }

    public static LetterEnum fromUppercaseChar(char c) {
        int index = c - 65;
        if (index < 0 || index > 25) {
            return null;
        }

        return fromIndex(index);
    }

    public static List<LetterEnum> allLetters() {
        return newArrayList(LetterEnum.values());
    }

    public int getIndex() {
        return index;
    }
}
