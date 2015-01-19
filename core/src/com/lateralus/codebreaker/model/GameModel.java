package com.lateralus.codebreaker.model;

import com.lateralus.codebreaker.model.constant.Difficulty;
import com.lateralus.codebreaker.model.constant.WordConstants;
import com.lateralus.codebreaker.model.letter.KeyLetter;
import com.lateralus.codebreaker.model.letter.PositionLetter;

import java.util.List;

public class GameModel {

    public static final int LETTER_COLUMN_COUNT = 12;
    public static final int LETTER_ROW_COUNT = 17;
    public static final int KEY_LETTER_ROW = 0;

    private PositionLetter activeLetter;
    private List<PositionLetter> correctLetters;
    private List<PositionLetter> incorrectLetters;
    private List<KeyLetter> keyLetters;
    private Difficulty difficulty;
    private List<String> wordList;

    public GameModel(Difficulty difficulty) {
        setDifficulty(difficulty);
    }

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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.wordList = WordConstants.getWordList(difficulty);
    }

    public List<String> getWordList() {
        return wordList;
    }
}
