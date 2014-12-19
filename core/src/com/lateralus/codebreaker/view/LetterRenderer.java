package com.lateralus.codebreaker.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lateralus.codebreaker.model.PositionLetter;
import com.lateralus.codebreaker.model.KeyLetter;
import com.lateralus.codebreaker.model.World;

import static com.lateralus.codebreaker.model.World.*;

public class LetterRenderer implements CodeRenderer {

    private static final int LETTER_SIZE = 90;

    private Sprite[][] sprites;
    private PositionLetter[][] randomLetters;
    private TextureRegion[] greenTextures;
    private TextureRegion[] goldTextures;
    private TextureRegion[] blueCodeTextures;
    private TextureRegion[] grayCodeTextures;
    private TextureRegion[] specialTextures;
    private Texture letterTexture;
    private float timeSinceLastRandomLetters = 0f;
    private int startColumnForCurrentWord;

    @Override
    public void initialize(World world) {
        letterTexture = new Texture(Gdx.files.internal("letters-sprite.png"));
        initializeSprites();
        initalizeTextures();
        initializeRandomLetters();
        calculateStartColumnForCurrentWord(world);
    }

    @Override
    public void render(SpriteBatch spriteBatch, World world, float delta) {
        cycleRandomNumbers(delta);
        resetSprites();
        applySprites(world);
        drawSprites(spriteBatch);
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

    private void applySprites(World world) {
        updateActiveSprite(world);
        updateKeySprites(world);
        updateIncorrectSprites(world);
        updateCurrentWordSprites(world);
    }

    private void drawSprites(SpriteBatch spriteBatch) {
        for (Sprite[] spriteColumn : sprites) {
            for (Sprite sprite : spriteColumn) {
                sprite.draw(spriteBatch);
            }
        }
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

    private void calculateStartColumnForCurrentWord(World world) {
        int wordLength = world.getCurrentWord().size();
        if (wordLength % 2 == 1) {
            wordLength++;
        }
        startColumnForCurrentWord = (LETTER_COLUMN_COUNT / 2) - (wordLength / 2);
    }

    private TextureRegion getLetterTextureRegion(int row, int col) {
        return new TextureRegion(letterTexture, row * 90, col * 90, 90, 90);
    }

    private void updateBackgroundSprite(Sprite currentSprite, int col, int row) {
        currentSprite.setRegion(grayCodeTextures[randomLetters[col][row].getValue().getIndex()]);
    }

    private void updateActiveSprite(World world) {
        int col = world.getActiveLetter().getCol();
        int row = world.getActiveLetter().getRow();
        Sprite currentSprite = sprites[col][row];
        currentSprite.setRegion(greenTextures[world.getActiveLetter().getValue().getIndex()]);
    }

    private void updateKeySprites(World world) {
        int row = KEY_LETTER_ROW;
        for (int col = 0; col < LETTER_COLUMN_COUNT; col++) {
            Sprite currentSprite = sprites[col][row];
            KeyLetter currentKeyLetter = world.getDisplayableKeyLetters()[col];
            currentSprite.setRegion(blueCodeTextures[currentKeyLetter.getKeyLetter().getIndex()]);
        }
    }

    private void updateIncorrectSprites(World world) {
        for (PositionLetter incorrectLetter: world.getIncorrectLetters()) {
            sprites[incorrectLetter.getCol()][incorrectLetter.getRow()].setRegion(specialTextures[0]);
        }
    }

    private void updateCurrentWordSprites(World world) {
        int currentColumn = startColumnForCurrentWord;
        for (KeyLetter letter : world.getCurrentWord()) {
            TextureRegion texture;
            if (letter.isSolved()) {
                texture = goldTextures[letter.getValueLetter().getIndex()];
            } else {
                texture = blueCodeTextures[letter.getKeyLetter().getIndex()];
            }
            sprites[currentColumn][CURRENT_WORD_ROW].setRegion(texture);
            currentColumn++;
        }
    }

}
