package com.lateralus.codebreaker.view;

import com.lateralus.codebreaker.model.GameModel;
import com.lateralus.codebreaker.model.letter.KeyLetter;
import com.lateralus.codebreaker.model.letter.PositionLetter;
import com.lateralus.codebreaker.view.render.LetterRenderer;

import static com.lateralus.codebreaker.model.GameModel.LETTER_COLUMN_COUNT;

public class GameView implements CodeBreakerView {

    private GameModel model;

    public GameView(GameModel model) {
        this.model = model;
    }

    @Override
    public void render(LetterRenderer letterRenderer) {
        updateActiveSprite(letterRenderer);
        updateKeySprites(letterRenderer);
        updateIncorrectSprites(letterRenderer);
    }

    private void updateActiveSprite(LetterRenderer letterRenderer) {
        letterRenderer.useGreen();
        letterRenderer.drawLetter(model.getActiveLetter());
    }

    private void updateKeySprites(LetterRenderer letterRenderer) {
        for (int col = 0; col < LETTER_COLUMN_COUNT; col++) {
            KeyLetter currentKeyLetter = model.getKeyLetters().get(col);

            if (currentKeyLetter.isSolved()) {
                letterRenderer.useGold();
                letterRenderer.drawLetter(col, 0, currentKeyLetter.getValueLetter());
            } else {
                letterRenderer.useBlueCode();
                letterRenderer.drawLetter(col, 0, currentKeyLetter.getKeyLetter());
            }
        }
    }

    private void updateIncorrectSprites(LetterRenderer letterRenderer) {
        letterRenderer.useWhite();
        model.getIncorrectLetters().forEach(letterRenderer::drawLetter);
    }
}
