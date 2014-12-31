package com.lateralus.codebreaker.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.lateralus.codebreaker.util.RandomUtils;
import com.lateralus.codebreaker.model.KeyLetter;
import com.lateralus.codebreaker.model.LetterEnum;
import com.lateralus.codebreaker.model.PositionLetter;
import com.lateralus.codebreaker.model.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.lateralus.codebreaker.util.RandomUtils.getNextValue;
import static com.lateralus.codebreaker.util.RandomUtils.randomInt;
import static com.lateralus.codebreaker.model.World.KEY_LETTER_ROW;
import static com.lateralus.codebreaker.model.World.LETTER_COLUMN_COUNT;
import static com.lateralus.codebreaker.model.World.LETTER_ROW_COUNT;
import static java.util.stream.Collectors.groupingBy;

public class LetterController implements CodeController {

    private static final float LETTER_FALL_SPEED = 0.4f;
    private static final float LETTER_FALL_SPEED_MULTIPLIER = 5f;
    private static final float COLUMN_CHANGE_SPEED = 0.125f;

    private float timeSinceLastLetterFall = 0f;
    private float timeSinceLastColumnChange = 0f;
    private int currentKeyPressed;

    @Override
    public void initialize(World world) {
        initializeKeyLetters(world);
        initializeActiveLetter(world);
        world.setCorrectLetters(newArrayList());
        world.setIncorrectLetters(newArrayList());
    }

    @Override
    public void update(World world, float delta) {
        updateActiveLetterCol(world, delta);
        updateActiveLetterRow(world, delta);
    }

    private void updateActiveLetterCol(World world, float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (currentKeyPressed == Input.Keys.LEFT) {
                timeSinceLastColumnChange += delta;
                if (timeSinceLastColumnChange >= COLUMN_CHANGE_SPEED) {
                    moveActiveLetterLeft(world);
                    timeSinceLastColumnChange = 0f;
                }
            } else {
                moveActiveLetterLeft(world);
                timeSinceLastColumnChange = 0f;
                currentKeyPressed = Input.Keys.LEFT;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (currentKeyPressed == Input.Keys.RIGHT) {
                timeSinceLastColumnChange += delta;
                if (timeSinceLastColumnChange >= COLUMN_CHANGE_SPEED) {
                    moveActiveLetterRight(world);
                    timeSinceLastColumnChange = 0f;
                }
            } else {
                moveActiveLetterRight(world);
                timeSinceLastColumnChange = 0f;
                currentKeyPressed = Input.Keys.RIGHT;
            }
        } else {
            currentKeyPressed = -1;
        }
    }

    private void moveActiveLetterLeft(World world) {
        int currentColumn = world.getActiveLetter().getCol();
        int currentRow = world.getActiveLetter().getRow();
        if (currentColumn > 0 && !letterAlreadyExists(currentColumn - 1, currentRow, world)) {
            world.getActiveLetter().setCol(currentColumn - 1);
        }
    }

    private void moveActiveLetterRight(World world) {
        int currentColumn = world.getActiveLetter().getCol();
        int currentRow = world.getActiveLetter().getRow();
        if (currentColumn < LETTER_COLUMN_COUNT - 1 && !letterAlreadyExists(currentColumn + 1, currentRow, world)) {
            world.getActiveLetter().setCol(currentColumn + 1);
        }
    }

    private boolean letterAlreadyExists(int currentColumn, int currentRow, World world) {
        Predicate<PositionLetter> positionMatches = l -> l.getCol() == currentColumn && l.getRow() == currentRow;

        if (world.getIncorrectLetters().stream().anyMatch(positionMatches)) {
            return true;
        }
        if (world.getCorrectLetters().stream().anyMatch(positionMatches)) {
            return true;
        }

        return false;
    }

    private void updateActiveLetterRow(World world, float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            timeSinceLastLetterFall += delta * LETTER_FALL_SPEED_MULTIPLIER;
        } else {
            timeSinceLastLetterFall += delta;
        }

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

    private void initializeKeyLetters(World world) {
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
        ArrayList<String> words = newArrayList("Our", "personal", "business", "area", "includes", "personal", "organizers", "that", "can",
                "help", "you", "manage", "your", "finances,", "loans", "and", "budgets.", "This", "section", "also", "includes",
                "resume", "and", "letter", "templates,", "personal", "organizers,", "planners", "and", "checklists.",
                "We", "have", "a", "wide", "variety", "of", "calendars", "including", "annual", "and", "monthly", "with", "a",
                "variety", "of", "formats", "including", "day", "planners.", "Our", "Parenting", "section", "includes", "educational",
                "tools,", "math", "worksheets", "and", "parenting", "forms", "for", "download.", "Also", "check", "out", "other",
                "forms", "for", "managing", "and", "organizing", "sporting", "events,", "hobbies,", "recreation", "and", "tournaments."
        );
        String word = RandomUtils.getNextValue(words);
        List<LetterEnum> newWord = newArrayList();

        for (char c : word.toUpperCase().toCharArray()) {
            LetterEnum currentLetter = LetterEnum.fromUppercaseChar(c);
            // TODO - prune dictionary so we don't get non A-Z characters
            if (currentLetter != null) {
                newWord.add(currentLetter);
            }
        }

        return newWord;
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
        List<LetterEnum> availableLetters = getAvailableLetters(world);

        if (availableLetters.isEmpty()) {
            initializeKeyLetters(world);
            availableLetters = getAvailableLetters(world);
        }

        world.setActiveLetter(new PositionLetter(randomInt(12), LETTER_ROW_COUNT + 1, getNextValue(availableLetters)));
    }

    private List<LetterEnum> getAvailableLetters(World world) {
        return world.getKeyLetters().stream()
                .filter(KeyLetter::isNotSolved)
                .map(KeyLetter::getValueLetter)
                .collect(Collectors.toList());
    }

    private void onActiveLetterHit(World world) {
        PositionLetter activeLetter = world.getActiveLetter();
        KeyLetter keyLetter = world.getKeyLetters().get(activeLetter.getCol());

        if (activeLetter.getValue().equals(keyLetter.getValueLetter())) {
            keyLetter.setSolved(true);
        } else {
            world.getIncorrectLetters().add(activeLetter);
        }
        initializeActiveLetter(world);
    }

}
