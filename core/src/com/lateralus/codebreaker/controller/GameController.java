package com.lateralus.codebreaker.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lateralus.codebreaker.controller.input.InputListener;
import com.lateralus.codebreaker.model.GameModel;
import com.lateralus.codebreaker.model.letter.KeyLetter;
import com.lateralus.codebreaker.model.letter.LetterEnum;
import com.lateralus.codebreaker.model.letter.PositionLetter;
import com.lateralus.codebreaker.util.RandomUtils;
import com.lateralus.codebreaker.view.render.LetterRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.lateralus.codebreaker.model.GameModel.getTopRow;
import static com.lateralus.codebreaker.model.letter.LetterEnum.newWord;
import static com.lateralus.codebreaker.util.RandomUtils.getNextValue;
import static com.lateralus.codebreaker.util.RandomUtils.randomInt;

public class GameController implements CodeBreakerController {

    private static final float LETTER_FALL_INTERVAL = 0.4f;
    private static final float LETTER_FALL_SPEED_MULTIPLIER = 5f;

    private MainController mainController;
    private float timeSinceLastLetterFall = 0f;
    private GameModel model;
    private InputListener inputListener;

    public GameController(MainController mainController, GameModel model) {
        this.mainController = mainController;
        this.model = model;
        initializeKeyLetters();
        initializeActiveLetter();
        model.setIncorrectLetters(newArrayList());
        initializeInputListener();
    }

    private void initializeInputListener() {
        inputListener = new InputListener(model.getDifficulty().ordinal());
        inputListener.addHoldListener(Input.Keys.LEFT, this::moveActiveLetterLeft);
        inputListener.addHoldListener(Input.Keys.RIGHT, this::moveActiveLetterRight);
    }

    @Override
    public void update(float delta) {
        inputListener.checkInput(delta);
        updateActiveLetterRow(delta);
    }

    private void moveActiveLetterLeft() {
        int currentColumn = model.getActiveLetter().getCol();
        int currentRow = model.getActiveLetter().getRow();
        if (currentColumn > 0 && !letterAlreadyExists(currentColumn - 1, currentRow)) {
            model.getActiveLetter().setCol(currentColumn - 1);
        }
    }

    private void moveActiveLetterRight() {
        int currentColumn = model.getActiveLetter().getCol();
        int currentRow = model.getActiveLetter().getRow();
        if (currentColumn < LetterRenderer.LETTER_COLUMN_COUNT - 1 && !letterAlreadyExists(currentColumn + 1, currentRow)) {
            model.getActiveLetter().setCol(currentColumn + 1);
        }
    }

    private boolean letterAlreadyExists(int currentColumn, int currentRow) {
        Predicate<PositionLetter> positionMatches = l -> l.getCol() == currentColumn && l.getRow() == currentRow;

        if (model.getIncorrectLetters().stream().anyMatch(positionMatches)) {
            return true;
        }

        return false;
    }

    private void updateActiveLetterRow(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            timeSinceLastLetterFall += delta * LETTER_FALL_SPEED_MULTIPLIER;
        } else {
            timeSinceLastLetterFall += delta;
        }

        if (timeSinceLastLetterFall >= getFallInterval()) {
            PositionLetter activeLetter = model.getActiveLetter();
            int currentRow = activeLetter.getRow();
            if (activeLetterWillHitBottom(currentRow) || activeLetterWillHitIncorrectLetter()) {
                onActiveLetterHit();
            } else {
                activeLetter.setRow(currentRow - 1);
            }
            timeSinceLastLetterFall = 0f;
        }
    }

    private float getFallInterval() {
        return LETTER_FALL_INTERVAL - (model.getDifficulty().ordinal() * 0.15f);
    }

    private void initializeKeyLetters() {
        ArrayList<KeyLetter> keyLetters = newArrayList();

        List<LetterEnum> word = initializeWord();
        int startColumn = getRandomStartColumn(word);
        int columnAfterEnd = startColumn + word.size();

        List<LetterEnum> availableKeys = LetterEnum.allLetters();

        for (int i = 0; i < startColumn; i++) {
            keyLetters.add(new KeyLetter(availableKeys, LetterEnum.BLANK));
        }

        for (int i = startColumn; i < columnAfterEnd; i++) {
            keyLetters.add(new KeyLetter(availableKeys, word.get(i - startColumn)));
        }

        for (int i = columnAfterEnd; i < LetterRenderer.LETTER_COLUMN_COUNT; i++) {
            keyLetters.add(new KeyLetter(availableKeys, LetterEnum.BLANK));
        }

        model.setKeyLetters(keyLetters);
    }

    private int getRandomStartColumn(List<LetterEnum> word) {
        if (word.size() == LetterRenderer.LETTER_COLUMN_COUNT) {
            return 0;
        }
        return randomInt(LetterRenderer.LETTER_COLUMN_COUNT - word.size());
    }

    private List<LetterEnum> initializeWord() {
        return newWord(RandomUtils.getNextValue(model.getWordList()));
    }

    private boolean activeLetterWillHitBottom(int currentRow) {
        return currentRow <= 1;
    }

    private boolean activeLetterWillHitIncorrectLetter() {
        return model.getIncorrectLetters().stream()
                .anyMatch((letter) -> activeLetterWillHit(letter));
    }

    private boolean activeLetterWillHit(PositionLetter letter) {
        return model.getActiveLetter().getCol() == letter.getCol() &&
                model.getActiveLetter().getRow() <= letter.getRow() + 1;
    }

    private void initializeActiveLetter() {
        List<LetterEnum> availableLetters = getAvailableLetters();

        if (availableLetters.isEmpty()) {
            addPointsForCorrectWord();
            initializeKeyLetters();
            availableLetters = getAvailableLetters();
        }

        model.setActiveLetter(new PositionLetter(randomInt(12), getTopRow(), getNextValue(availableLetters)));
    }

    private List<LetterEnum> getAvailableLetters() {
        return model.getKeyLetters().stream()
                .filter(KeyLetter::isNotSolved)
                .map(KeyLetter::getValueLetter)
                .collect(Collectors.toList());
    }

    private void onActiveLetterHit() {
        PositionLetter activeLetter = model.getActiveLetter();
        KeyLetter keyLetter = model.getKeyLetters().get(activeLetter.getCol());

        if (activeLetter.getRow() == getTopRow()) {
            mainController.viewLoseScreen(model.getScore());
        } else if (activeLetter.getValue().equals(keyLetter.getValueLetter()) && keyLetter.isNotSolved()) {
            addPointsForCorrectLetter();
            solveAllRelatedKeyLetters(keyLetter.getValueLetter());
        } else {
            model.getIncorrectLetters().add(activeLetter);
        }
        initializeActiveLetter();
    }

    private void solveAllRelatedKeyLetters(LetterEnum value) {
        model.getKeyLetters().stream()
                .filter(letter -> letter.getValueLetter() == value)
                .forEach(letter -> letter.setSolved(true));
    }

    private void addPointsForCorrectLetter() {
        model.incrementScore(model.getDifficulty().ordinal() * 10 + 10);
    }

    private void addPointsForCorrectWord() {
        model.incrementScore(model.getDifficulty().ordinal() * 100 + 100);
    }

}
