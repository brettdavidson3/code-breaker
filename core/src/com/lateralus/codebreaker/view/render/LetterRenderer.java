package com.lateralus.codebreaker.view.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lateralus.codebreaker.model.letter.LetterEnum;
import com.lateralus.codebreaker.model.letter.PositionLetter;

import java.util.List;

import static com.lateralus.codebreaker.model.GameModel.LETTER_COLUMN_COUNT;
import static com.lateralus.codebreaker.model.GameModel.LETTER_ROW_COUNT;

public class LetterRenderer {

    private static final int LETTER_SIZE = 90;

    private Sprite[][] sprites;
    private PositionLetter[][] randomLetters;
    private TextureRegion[] greenTextures;
    private TextureRegion[] whiteTextures;
    private TextureRegion[] goldTextures;
    private TextureRegion[] blueCodeTextures;
    private TextureRegion[] grayCodeTextures;
    private TextureRegion[] specialTextures;
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

    public void useSpecial() {
        currentTextures = specialTextures;
    }

    private void cycleRandomNumbers(float delta) {
        timeSinceLastRandomLetters += delta;
        if (timeSinceLastRandomLetters >= 0.1f) { // 100 ms
            randomizeBackgroundLetters();
            timeSinceLastRandomLetters = 0f;
        }
    }

    private void resetSprites() {
        for (int col = 0; col < getSpriteGridColumnCount(); col++) {
            for (int row = 0; row < getSpriteGridRowCount(); row++) {
                Sprite currentSprite = sprites[col][row];
                updateBackgroundSprite(currentSprite, col, row);
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

    public void drawLetter(int col, int row, LetterEnum letter) {
        sprites[col][row].setRegion(currentTextures[letter.getIndex()]);
    }

    private void initializeSprites() {
        sprites = new Sprite[getSpriteGridColumnCount()][getSpriteGridRowCount()];

        for (int col = 0; col < getSpriteGridColumnCount(); col++) {
            for (int row = 0; row < getSpriteGridRowCount(); row++) {
                Sprite currentSprite = new Sprite(letterTexture, 0, 0, LETTER_SIZE, LETTER_SIZE);
                currentSprite.setPosition(col * LETTER_SIZE, row * LETTER_SIZE);
                sprites[col][row] = currentSprite;
            }
        }
    }

    private int getSpriteGridRowCount() {
        return LETTER_ROW_COUNT + 3;
    }

    private int getSpriteGridColumnCount() {
        return LETTER_COLUMN_COUNT;
    }

    private void initalizeTextures() {
        greenTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            greenTextures[i] = getLetterTextureRegion(i, 0);
        }

        whiteTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            whiteTextures[i] = getLetterTextureRegion(i, 1);
        }

        goldTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            goldTextures[i] = getLetterTextureRegion(i, 2);
        }

        grayCodeTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            grayCodeTextures[i] = getLetterTextureRegion(i, 3);
        }

        blueCodeTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            blueCodeTextures[i] = getLetterTextureRegion(i, 4);
        }

        specialTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            specialTextures[i] = getLetterTextureRegion(i, 6);
        }

    }

    private void initializeRandomLetters() {
        randomLetters = new PositionLetter[getSpriteGridColumnCount()][getSpriteGridRowCount()];
        randomizeBackgroundLetters();
    }

    private void randomizeBackgroundLetters() {
        for (int col = 0; col < getSpriteGridColumnCount(); col++) {
            for (int row = 0; row < getSpriteGridRowCount(); row++) {
                randomLetters[col][row] = new PositionLetter(col, row);
            }
        }
    }

    private TextureRegion getLetterTextureRegion(int row, int col) {
        return new TextureRegion(letterTexture, row * 90, col * 90, 90, 90);
    }

    private void updateBackgroundSprite(Sprite currentSprite, int col, int row) {
        currentSprite.setRegion(grayCodeTextures[randomLetters[col][row].getValue().getIndex()]);
    }

}
