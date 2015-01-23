package com.lateralus.codebreaker.model;

import com.lateralus.codebreaker.model.constant.Difficulty;
import com.lateralus.codebreaker.model.constant.WordConstants;
import com.lateralus.codebreaker.model.letter.KeyLetter;
import com.lateralus.codebreaker.model.letter.PositionLetter;
import com.lateralus.codebreaker.view.render.LetterRenderer;

import java.util.List;

public class GameModel {

    public static int getTopRow() { return LetterRenderer.GAME_AREA_ROW_COUNT - 1; }

    private PositionLetter activeLetter;
    private List<PositionLetter> correctLetters;
    private List<PositionLetter> incorrectLetters;
    private List<KeyLetter> keyLetters;
    private Difficulty difficulty;
    private List<String> wordList;
    private int score;

    public GameModel(Difficulty difficulty) {
        setDifficulty(difficulty);
        this.score = 1234;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
