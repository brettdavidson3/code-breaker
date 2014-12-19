package com.lateralus.codebreaker.controller;

import com.lateralus.codebreaker.controller.helper.NumberUtils;
import com.lateralus.codebreaker.controller.helper.RandomUtils;
import com.lateralus.codebreaker.model.KeyLetter;
import com.lateralus.codebreaker.model.PositionLetter;
import com.lateralus.codebreaker.model.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lateralus.codebreaker.controller.helper.CollectionUtils.newArrayList;
import static com.lateralus.codebreaker.controller.helper.RandomUtils.getNextValue;
import static com.lateralus.codebreaker.controller.helper.RandomUtils.randomInt;

import com.lateralus.codebreaker.model.LetterEnum;
import static com.lateralus.codebreaker.model.World.KEY_LETTER_ROW;
import static com.lateralus.codebreaker.model.World.LETTER_COLUMN_COUNT;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class LetterController implements CodeController {

    private static final float LETTER_FALL_SPEED = 0.2f;

    private float timeSinceLastLetterFall = 0f;

    @Override
    public void initialize(World world) {
        initializeKeyLetters(world);
        initializeCurrentWord(world);
        initializeDisplayableKeyLetters(world);
        initializeActiveLetter(world);
        world.setCorrectLetters(new ArrayList<>());
        world.setIncorrectLetters(new ArrayList<>());
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

    private void initializeKeyLetters(World world) {
        ArrayList<KeyLetter> keyLetters = new ArrayList<>();

        List<LetterEnum> availableKeys = LetterEnum.allLetters();
        List<LetterEnum> availableValues = LetterEnum.allLetters();
        for (int i = 0; i < LetterEnum.values().length; i++) {
            KeyLetter newLetter = new KeyLetter(availableKeys, availableValues);
            keyLetters.add(newLetter);
        }

        world.setKeyLetters(keyLetters);
    }

    private void initializeDisplayableKeyLetters(World world) {
        KeyLetter[] displayableKeyLetters = new KeyLetter[LETTER_COLUMN_COUNT];

        HashSet<KeyLetter> keyLetterSet = new HashSet<>(world.getCurrentWord());
        List<Integer> possibleKeyLetterCols = NumberUtils.numberRange(12);
        for (KeyLetter keyLetter : keyLetterSet) {
            Integer col = getNextValue(possibleKeyLetterCols);
            displayableKeyLetters[col] = keyLetter;
        }

        List<KeyLetter> remainingKeyLetters = world.getKeyLetters().stream()
                .filter(kl -> !keyLetterSet.contains(kl))
                .collect(toList());
        while (!possibleKeyLetterCols.isEmpty()) {
            Integer col = getNextValue(possibleKeyLetterCols);
            displayableKeyLetters[col] = getNextValue(remainingKeyLetters);
        }

        world.setDisplayableKeyLetters(displayableKeyLetters);
    }

    private void initializeCurrentWord(World world) {
        Map<LetterEnum, List<KeyLetter>> keyLettersByValue = world.getKeyLetters().stream()
                .collect(groupingBy(KeyLetter::getValueLetter));

        String word = "hello"; // TODO
        List<KeyLetter> currentWord = new ArrayList<>();

        for (char c : word.toUpperCase().toCharArray()) {
            LetterEnum currentLetter = LetterEnum.fromUppercaseChar(c);
            if (currentLetter == null) {
                // TODO - prune dictionary so this won't ever happen
                break;
            }
            List<KeyLetter> keyLetters = keyLettersByValue.get(currentLetter);
            currentWord.add(keyLetters.get(0));
        }

        world.setCurrentWord(currentWord);
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
        ArrayList<KeyLetter> displayableKeyLetters = newArrayList(world.getDisplayableKeyLetters());

        List<LetterEnum> availableLetters = world.getKeyLetters().stream()
                .filter(l -> displayableKeyLetters.contains(l))
                .filter(KeyLetter::isNotSolved)
                .map(KeyLetter::getValueLetter)
                .collect(Collectors.toList());
        world.setActiveLetter(new PositionLetter(randomInt(12), 19, getNextValue(availableLetters)));
    }

    private void onActiveLetterHit(World world) {
        KeyLetter keyLetter = world.getDisplayableKeyLetters()[world.getActiveLetter().getCol()];

        if (world.getActiveLetter().getValue().equals(keyLetter.getValueLetter())) {
            keyLetter.setSolved(true);
        } else {
            world.getIncorrectLetters().add(world.getActiveLetter());
        }
        initializeActiveLetter(world);
    }

}
