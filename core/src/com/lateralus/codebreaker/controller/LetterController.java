package com.lateralus.codebreaker.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lateralus.codebreaker.controller.input.InputListener;
import com.lateralus.codebreaker.model.KeyLetter;
import com.lateralus.codebreaker.model.LetterEnum;
import com.lateralus.codebreaker.model.PositionLetter;
import com.lateralus.codebreaker.model.World;
import com.lateralus.codebreaker.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.lateralus.codebreaker.model.LetterEnum.newWord;
import static com.lateralus.codebreaker.model.World.*;
import static com.lateralus.codebreaker.util.RandomUtils.getNextValue;
import static com.lateralus.codebreaker.util.RandomUtils.randomInt;

public class LetterController implements CodeController {

    private static final float LETTER_FALL_SPEED = 0.4f;
    private static final float LETTER_FALL_SPEED_MULTIPLIER = 5f;

    private float timeSinceLastLetterFall = 0f;
    private World world;
    private InputListener inputListener;

    @Override
    public void initialize(World world) {
        this.world = world;
        initializeKeyLetters();
        initializeActiveLetter();
        world.setCorrectLetters(newArrayList());
        world.setIncorrectLetters(newArrayList());
        initializeInputListener();
    }

    private void initializeInputListener() {
        inputListener = new InputListener();
        inputListener.addKeyListener(Input.Keys.LEFT, this::moveActiveLetterLeft);
        inputListener.addKeyListener(Input.Keys.RIGHT, this::moveActiveLetterRight);
    }

    @Override
    public void update(float delta) {
        inputListener.checkInput(delta);
        updateActiveLetterRow(delta);
    }

    private void moveActiveLetterLeft() {
        int currentColumn = world.getActiveLetter().getCol();
        int currentRow = world.getActiveLetter().getRow();
        if (currentColumn > 0 && !letterAlreadyExists(currentColumn - 1, currentRow)) {
            world.getActiveLetter().setCol(currentColumn - 1);
        }
    }

    private void moveActiveLetterRight() {
        int currentColumn = world.getActiveLetter().getCol();
        int currentRow = world.getActiveLetter().getRow();
        if (currentColumn < LETTER_COLUMN_COUNT - 1 && !letterAlreadyExists(currentColumn + 1, currentRow)) {
            world.getActiveLetter().setCol(currentColumn + 1);
        }
    }

    private boolean letterAlreadyExists(int currentColumn, int currentRow) {
        Predicate<PositionLetter> positionMatches = l -> l.getCol() == currentColumn && l.getRow() == currentRow;

        if (world.getIncorrectLetters().stream().anyMatch(positionMatches)) {
            return true;
        }
        if (world.getCorrectLetters().stream().anyMatch(positionMatches)) {
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

        if (timeSinceLastLetterFall >= LETTER_FALL_SPEED) {
            PositionLetter activeLetter = world.getActiveLetter();
            int currentRow = activeLetter.getRow();
            if (activeLetterWillHitBottom(currentRow) || activeLetterWillHitOtherLetter()) {
                onActiveLetterHit();
            } else {
                activeLetter.setRow(currentRow - 1);
            }
            timeSinceLastLetterFall = 0f;
        }
    }

    private void initializeKeyLetters() {
        ArrayList<KeyLetter> keyLetters = newArrayList();

        List<LetterEnum> word = initializeWord();
        int startColumn = randomInt(LETTER_COLUMN_COUNT - word.size());
        int columnAfterEnd = startColumn + word.size();

        List<LetterEnum> availableKeys = LetterEnum.allLetters();

        for (int i = 0; i < startColumn; i++) {
            keyLetters.add(new KeyLetter(availableKeys, LetterEnum.BLANK));
        }

        for (int i = startColumn; i < columnAfterEnd; i++) {
            keyLetters.add(new KeyLetter(availableKeys, word.get(i - startColumn)));
        }

        for (int i = columnAfterEnd; i < LETTER_COLUMN_COUNT; i++) {
            keyLetters.add(new KeyLetter(availableKeys, LetterEnum.BLANK));
        }

        world.setKeyLetters(keyLetters);
    }

    private List<LetterEnum> initializeWord() {
        return newWord(RandomUtils.getNextValue(world.getWordList()));
    }

    private boolean activeLetterWillHitBottom(int currentRow) {
        return currentRow <= KEY_LETTER_ROW + 1;
    }

    private boolean activeLetterWillHitOtherLetter() {
        return activeLetterWillHitCorrectLetter() || activeLetterWillHitIncorrectLetter();
    }

    private boolean activeLetterWillHitCorrectLetter() {
        return world.getCorrectLetters().stream()
                .anyMatch((letter) -> activeLetterWillHit(letter));
    }

    private boolean activeLetterWillHitIncorrectLetter() {
        return world.getIncorrectLetters().stream()
                .anyMatch((letter) -> activeLetterWillHit(letter));
    }

    private boolean activeLetterWillHit(PositionLetter letter) {
        return world.getActiveLetter().getCol() == letter.getCol() &&
                world.getActiveLetter().getRow() <= letter.getRow() + 1;
    }

    private void initializeActiveLetter() {
        List<LetterEnum> availableLetters = getAvailableLetters();

        if (availableLetters.isEmpty()) {
            initializeKeyLetters();
            availableLetters = getAvailableLetters();
        }

        world.setActiveLetter(new PositionLetter(randomInt(12), LETTER_ROW_COUNT + 1, getNextValue(availableLetters)));
    }

    private List<LetterEnum> getAvailableLetters() {
        return world.getKeyLetters().stream()
                .filter(KeyLetter::isNotSolved)
                .map(KeyLetter::getValueLetter)
                .collect(Collectors.toList());
    }

    private void onActiveLetterHit() {
        PositionLetter activeLetter = world.getActiveLetter();
        KeyLetter keyLetter = world.getKeyLetters().get(activeLetter.getCol());

        if (activeLetter.getValue().equals(keyLetter.getValueLetter())) {
            keyLetter.setSolved(true);
        } else {
            world.getIncorrectLetters().add(activeLetter);
        }
        initializeActiveLetter();
    }

}
