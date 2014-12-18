package com.lateralus.codebreaker.controller;

import com.lateralus.codebreaker.model.KeyLetter;
import com.lateralus.codebreaker.model.PositionLetter;
import com.lateralus.codebreaker.model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lateralus.codebreaker.controller.helper.RandomUtils.getNextValue;
import static com.lateralus.codebreaker.controller.helper.RandomUtils.randomInt;

import com.lateralus.codebreaker.model.LetterEnum;
import static com.lateralus.codebreaker.model.World.KEY_LETTER_ROW;
import static com.lateralus.codebreaker.model.World.LETTER_COLUMN_COUNT;

public class LetterController implements CodeController {

    private static final float LETTER_FALL_SPEED = 0.2f;

    private float timeSinceLastLetterFall = 0f;

    @Override
    public void initialize(World world) {
        initializeKeyLetters(world);
        initializeActiveLetter(world);
        world.setCorrectLetters(new ArrayList<>());
        world.setIncorrectLetters(new ArrayList<>());
    }

    private void initializeKeyLetters(World world) {
        ArrayList<KeyLetter> keyLetters = new ArrayList<>();
        KeyLetter[] displayableKeyLetters = new KeyLetter[LETTER_COLUMN_COUNT];

        LetterEnum[] values = LetterEnum.values();
        List<LetterEnum> availableKeys = LetterEnum.allLetters();
        List<LetterEnum> availableValues = LetterEnum.allLetters();
        for (int i = 0; i < values.length; i++) {
            KeyLetter newLetter = new KeyLetter(availableKeys, availableValues);
            keyLetters.add(newLetter);
            if (i < LETTER_COLUMN_COUNT) {
                displayableKeyLetters[i] = newLetter;
            }
        }

        world.setKeyLetters(keyLetters);
        world.setDisplayableKeyLetters(displayableKeyLetters);
    }

    @Override
    public void update(World world, float delta) {
        timeSinceLastLetterFall += delta;
        if (timeSinceLastLetterFall >= LETTER_FALL_SPEED) {
            PositionLetter activeLetter = world.getActiveLetter();
            int currentRow = activeLetter.getRow();
            if (activeLetterWillHitBottom(currentRow) || activeLetterWillHitOtherLetter(world)) {
                onActiveLetterHit(world);
            } else {
                activeLetter.setRow(currentRow - 1);
            }
            timeSinceLastLetterFall = 0f;
        }
    }

    private boolean activeLetterWillHitBottom(int currentRow) {
        return currentRow <= KEY_LETTER_ROW + 1;
    }

    private boolean activeLetterWillHitOtherLetter(World world) {
        return activeLetterWillHitCorrectLetter(world) || activeLetterWillHitIncorrectLetter(world);
    }

    private boolean activeLetterWillHitCorrectLetter(World world) {
        return world.getCorrectLetters().stream()
                .anyMatch((letter) -> activeLetterWillHit(world, letter));
    }

    private boolean activeLetterWillHitIncorrectLetter(World world) {
        return world.getIncorrectLetters().stream()
                .anyMatch((letter) -> activeLetterWillHit(world, letter));
    }

    private boolean activeLetterWillHit(World world, PositionLetter letter) {
        return world.getActiveLetter().getCol() == letter.getCol() &&
               world.getActiveLetter().getRow() <= letter.getRow() + 1;
    }

    private void initializeActiveLetter(World world) {
        List<LetterEnum> availableLetters = world.getKeyLetters().stream()
                .filter(KeyLetter::isNotSolved)
                .map(KeyLetter::getValueLetter)
                .collect(Collectors.toList());
        world.setActiveLetter(new PositionLetter(randomInt(12), 19, getNextValue(availableLetters)));
    }

    private void onActiveLetterHit(World world) {
        world.getIncorrectLetters().add(world.getActiveLetter());
        initializeActiveLetter(world);
    }

}
