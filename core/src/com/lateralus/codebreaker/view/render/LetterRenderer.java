package com.lateralus.codebreaker.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lateralus.codebreaker.model.letter.LetterEnum;
import com.lateralus.codebreaker.model.letter.PositionLetter;

import java.util.List;

public class LetterRenderer {

    public static final int LETTER_COLUMN_COUNT = 12;
    public static final int LETTER_ROW_COUNT = 21;
    public static final int GAME_AREA_ROW_COUNT = 19;
    private static final int LETTER_SIZE = 90;

    private Sprite[][] sprites;
    private PositionLetter[][] randomLetters;
    private TextureRegion[] greenTextures;
    private TextureRegion[] whiteTextures;
    private TextureRegion[] goldTextures;
    private TextureRegion[] grayCodeTextures;
    private TextureRegion[] blueCodeTextures;
    private TextureRegion[] whiteNumberTextures;
    private TextureRegion[] currentTextures;
    private Texture letterTexture;
    private float timeSinceLastRandomLetters = 0f;

    public LetterRenderer() {
        letterTexture = new Texture(Gdx.files.internal("letters-sprite.png"));
        initializeSprites();
        initalizeTextures();
        initializeRandomLetters();
    }

    public void reset(float delta) {
        cycleRandomNumbers(delta);
        resetSprites();
    }

    public void render(SpriteBatch spriteBatch) {
        for (Sprite[] spriteColumn : sprites) {
            for (Sprite sprite : spriteColumn) {
                sprite.draw(spriteBatch);
            }
        }
    }

    public void useGreen() {
        currentTextures = greenTextures;
    }

    public void useWhite() {
        currentTextures = whiteTextures;
    }

    public void useGold() {
        currentTextures = goldTextures;
    }

    public void useBlueCode() {
        currentTextures = blueCodeTextures;
    }

    public void useGrayCode() {
        currentTextures = grayCodeTextures;
    }

    public void useWhiteNumbers() {
        currentTextures = whiteNumberTextures;
    }

    private void cycleRandomNumbers(float delta) {
        timeSinceLastRandomLetters += delta;
        if (timeSinceLastRandomLetters >= 0.1f) { // 100 ms
            randomizeBackgroundLetters();
            timeSinceLastRandomLetters = 0f;
        }
    }

    private void resetSprites() {
        for (int col = 0; col < LETTER_COLUMN_COUNT; col++) {
            useGrayCode();
            for (int row = 0; row < GAME_AREA_ROW_COUNT; row++) {
                drawLetter(randomLetters[col][row]);
            }

            useWhiteNumbers();
            for (int row = GAME_AREA_ROW_COUNT; row < LETTER_ROW_COUNT; row++) {
                drawLetter(col, row, LetterEnum.BLANK);  // TODO - this location may move
            }
        }
    }

    public int drawCenteredWords(int row, List<LetterEnum>... words) {
        int totalSize = words.length - 1; // start with number of spaces between words
        for (List<LetterEnum> word : words) {
            totalSize += word.size();
        }

        int col = getFirstColumnForCenteredWord(totalSize);
        for (List<LetterEnum> word : words) {
            col = drawWord(col, row, word) + 1;
        }
        return col - 1;
    }

    public void drawLeftAlignedWord(int row, List<LetterEnum> word) {
        drawWord(0, row, word);
    }

    private int getFirstColumnForCenteredWord(int wordLength) {
        return (int) Math.floor((LETTER_COLUMN_COUNT - wordLength) / 2);
    }

    public int drawWord(int col, int row, List<LetterEnum> word) {
        for (LetterEnum letter : word) {
            drawLetter(col, row, letter);
            col++;
        }
        return col;
    }

    public void drawLetter(PositionLetter letter) {
        drawLetter(letter.getCol(), letter.getRow(), letter.getValue());
    }

    public void drawLetter(int col, int row, LetterEnum letter) {
        sprites[col][row].setRegion(currentTextures[letter.ordinal()]);
    }

    public void drawRightAlignedNumber(int row, int number) {
        int col = LETTER_COLUMN_COUNT - 1;
        int powerOfTen = 1;
        while (number >= powerOfTen - 1) {
            drawDigit(col--, row, (number % (powerOfTen * 10)) / powerOfTen);
            powerOfTen *= 10;
        }
    }

    public void drawDigit(int col, int row, int digit) {
        sprites[col][row].setRegion(currentTextures[digit]);
    }

    private void initializeSprites() {
        sprites = new Sprite[LETTER_COLUMN_COUNT][LETTER_ROW_COUNT];

        for (int col = 0; col < LETTER_COLUMN_COUNT; col++) {
            for (int row = 0; row < LETTER_ROW_COUNT; row++) {
                Sprite currentSprite = new Sprite(letterTexture, 0, 0, LETTER_SIZE, LETTER_SIZE);
                currentSprite.setPosition(col * LETTER_SIZE, row * LETTER_SIZE);
                sprites[col][row] = currentSprite;
            }
        }
    }

    private void initalizeTextures() {
        int size = LetterEnum.values().length;
        greenTextures = new TextureRegion[size];
        whiteTextures = new TextureRegion[size];
        goldTextures = new TextureRegion[size];
        grayCodeTextures = new TextureRegion[size];
        blueCodeTextures = new TextureRegion[size];
        whiteNumberTextures = new TextureRegion[size];

        initializeTexturesAtRow(0, greenTextures);
        initializeTexturesAtRow(1, whiteTextures);
        initializeTexturesAtRow(2, goldTextures);
        initializeTexturesAtRow(3, grayCodeTextures);
        initializeTexturesAtRow(4, blueCodeTextures);
        // 5 - TODO: clean up sprite
        initializeTexturesAtRow(6, whiteNumberTextures);
    }

    private void initializeTexturesAtRow(int row, TextureRegion[] textures) {
        for (int i = 0; i < LetterEnum.values().length; i++) {
            textures[i] = getLetterTextureRegion(i, row);
        }
    }

    private void initializeRandomLetters() {
        randomLetters = new PositionLetter[LETTER_COLUMN_COUNT][GAME_AREA_ROW_COUNT];
        randomizeBackgroundLetters();
    }

    private void randomizeBackgroundLetters() {
        for (int col = 0; col < LETTER_COLUMN_COUNT; col++) {
            for (int row = 0; row < GAME_AREA_ROW_COUNT; row++) {
                randomLetters[col][row] = new PositionLetter(col, row);
            }
        }
    }

    private TextureRegion getLetterTextureRegion(int col, int row) {
        return new TextureRegion(letterTexture, col * 90, row * 90, 90, 90);
    }

}
