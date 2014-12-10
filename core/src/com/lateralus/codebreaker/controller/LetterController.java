package com.lateralus.codebreaker.controller;

import com.lateralus.codebreaker.model.Letter;
import com.lateralus.codebreaker.model.World;

import java.util.ArrayList;

import static com.lateralus.codebreaker.controller.helper.RandomUtils.randomInt;
import static com.lateralus.codebreaker.model.World.KEY_LETTER_ROW;
import static com.lateralus.codebreaker.model.World.LETTER_COLUMN_COUNT;

public class LetterController implements CodeController {

    private static final float LETTER_FALL_SPEED = 0.2f;

    private float timeSinceLastLetterFall = 0f;

    @Override
    public void initialize(World world) {
        initializeActiveLetter(world);

        ArrayList<Letter> keyLetters = new ArrayList<>();
        for (int i = 0; i < LETTER_COLUMN_COUNT; i++) {
            keyLetters.add(new Letter(i, KEY_LETTER_ROW));
        }
        world.setKeyLetters(keyLetters);

        world.setCorrectLetters(new ArrayList<>());
        world.setIncorrectLetters(new ArrayList<>());
    }

    @Override
    public void update(World world, float delta) {
        timeSinceLastLetterFall += delta;
        if (timeSinceLastLetterFall >= LETTER_FALL_SPEED) {
            Letter activeLetter = world.getActiveLetter();
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

    private boolean activeLetterWillHit(World world, Letter letter) {
        return world.getActiveLetter().getCol() == letter.getCol() &&
               world.getActiveLetter().getRow() <= letter.getRow() + 1;
    }

    private void initializeActiveLetter(World world) {
        world.setActiveLetter(new Letter(randomInt(12), 19));
    }

    private void onActiveLetterHit(World world) {
        world.getIncorrectLetters().add(world.getActiveLetter());
        initializeActiveLetter(world);
    }

}
