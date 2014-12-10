package com.lateralus.codebreaker.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lateralus.codebreaker.model.Letter;
import com.lateralus.codebreaker.model.World;

import java.util.function.Predicate;

import static com.lateralus.codebreaker.model.World.KEY_LETTER_ROW;
import static com.lateralus.codebreaker.model.World.LETTER_COLUMN_COUNT;
import static com.lateralus.codebreaker.model.World.LETTER_ROW_COUNT;

public class LetterRenderer implements CodeRenderer {

    private static final int LETTER_SIZE = 90;

    private Sprite[][] sprites;
    private Letter[][] randomLetters;
    private TextureRegion[] blueTextures;
    private TextureRegion[] greenTextures;
    private TextureRegion[] grayTextures;
    private TextureRegion[] specialTextures;
    private Texture letterTexture;
    private float timeSinceLastRandomLetters = 0f;

    @Override
    public void initialize(World world) {
        letterTexture = new Texture(Gdx.files.internal("letters-sprite.png"));
        initializeSprites();
        initalizeTextures();
        initializeRandomLetters();
    }

    @Override
    public void render(SpriteBatch spriteBatch, World world, float delta) {
        timeSinceLastRandomLetters += delta;
        if (timeSinceLastRandomLetters >= 0.1f) { // 100 ms
            randomizeBackgroundLetters();
            timeSinceLastRandomLetters = 0f;
        }

        for (int col = 0; col < getSpriteGridColumnCount(); col++) {
            for (int row = 0; row < getSpriteGridRowCount(); row++) {
                Sprite currentSprite = sprites[col][row];
                if (isActiveSprite(world, col, row)) {
                    updateActiveSprite(world, currentSprite);
                } else if (isKeySprite(row)) {
                    updateKeySprite(world, currentSprite, col);
                } else if (isIncorrectSprite(world, col, row)) {
                    updateIncorrectSprite(currentSprite);
                } else if (isCorrectSprite(world, col, row)) {

                } else {
                    updateBackgroundSprite(currentSprite, col, row);
                }
                currentSprite.draw(spriteBatch);
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
        grayTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            grayTextures[i] = getLetterTextureRegion(i, 3);
        }

        blueTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            blueTextures[i] = getLetterTextureRegion(i, 4);
        }

        greenTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            greenTextures[i] = getLetterTextureRegion(i, 0);
        }

        specialTextures = new TextureRegion[26];
        for (int i = 0; i < 26; i++) {
            specialTextures[i] = getLetterTextureRegion(i, 6);
        }

    }

    private void initializeRandomLetters() {
        randomLetters = new Letter[getSpriteGridColumnCount()][getSpriteGridRowCount()];
        randomizeBackgroundLetters();
    }

    private void randomizeBackgroundLetters() {
        for (int col = 0; col < getSpriteGridColumnCount(); col++) {
            for (int row = 0; row < getSpriteGridRowCount(); row++) {
                randomLetters[col][row] = new Letter(col, row);
            }
        }
    }

    private TextureRegion getLetterTextureRegion(int row, int col) {
        return new TextureRegion(letterTexture, row * 90, col * 90, 90, 90);
    }

    private boolean isActiveSprite(World world, int col, int row) {
        return world.getActiveLetter().getRow() == row && world.getActiveLetter().getCol() == col;
    }

    private void updateActiveSprite(World world, Sprite currentSprite) {
        currentSprite.setRegion(greenTextures[world.getActiveLetter().getValue().getIndex()]);
    }

    private boolean isKeySprite(int row) {
        return row == KEY_LETTER_ROW;
    }

    private void updateKeySprite(World world, Sprite currentSprite, int col) {
        currentSprite.setRegion(blueTextures[world.getKeyLetters().get(col).getValue().getIndex()]);
    }

    private void updateBackgroundSprite(Sprite currentSprite, int col, int row) {
        currentSprite.setRegion(grayTextures[randomLetters[col][row].getValue().getIndex()]);
    }

    private boolean isIncorrectSprite(World world, int col, int row) {
        return world.getIncorrectLetters().stream()
                .anyMatch(positionMatchesPredicate(col, row));
    }

    private void updateIncorrectSprite(Sprite currentSprite) {
        currentSprite.setRegion(specialTextures[0]);
    }

    private boolean isCorrectSprite(World world, int col, int row) {
        return world.getCorrectLetters().stream()
                .anyMatch(positionMatchesPredicate(col, row));
    }

    private static Predicate<Letter> positionMatchesPredicate(int col, int row) {
        return (letter) -> letter.getCol() == col && letter.getRow() == row;
    }

}
