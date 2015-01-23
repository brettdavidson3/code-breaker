package com.lateralus.codebreaker.model.letter;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public enum LetterEnum {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, BLANK;

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

    public static List<LetterEnum> newWord(String word) {
        List<LetterEnum> newWord = newArrayList();

        for (char c : word.toUpperCase().toCharArray()) {
            LetterEnum currentLetter = LetterEnum.fromUppercaseChar(c);
            if (currentLetter == null) {
                throw new RuntimeException("Invalid word detected! Word was: " + word);
            }
            newWord.add(currentLetter);
        }

        return newWord;
    }

}
