package com.lateralus.codebreaker.view;

import com.lateralus.codebreaker.model.GameModel;
import com.lateralus.codebreaker.model.letter.KeyLetter;
import com.lateralus.codebreaker.model.letter.LetterEnum;
import com.lateralus.codebreaker.model.letter.PositionLetter;
import com.lateralus.codebreaker.view.render.LetterRenderer;

import static com.lateralus.codebreaker.model.GameModel.KEY_LETTER_ROW;
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
        int col = model.getActiveLetter().getCol();
        int row = model.getActiveLetter().getRow();

        // TODO - ughh need fireworks
        if (model.getActiveLetter().getValue().getIndex() == 26) {
            letterRenderer.useSpecial();
            letterRenderer.drawLetter(col, row, LetterEnum.A);
        } else {
            letterRenderer.useGreen();
            letterRenderer.drawLetter(col, row, model.getActiveLetter().getValue());
        }
    }

    private void updateKeySprites(LetterRenderer letterRenderer) {
        int row = KEY_LETTER_ROW;
        for (int col = 0; col < LETTER_COLUMN_COUNT; col++) {
            KeyLetter currentKeyLetter = model.getKeyLetters().get(col);

            if (currentKeyLetter.isSolved()) {
                // TODO - ughh need fireworks
                if (currentKeyLetter.getValueLetter().getIndex() == 26) {
                    letterRenderer.useSpecial();
                    letterRenderer.drawLetter(col, row, LetterEnum.A);
                } else {
                    letterRenderer.useGold();
                    letterRenderer.drawLetter(col, row, currentKeyLetter.getValueLetter());
                }
            } else {
                // TODO - ughh need fireworks
                if (currentKeyLetter.getKeyLetter().getIndex() == 26) {
                    letterRenderer.useSpecial();
                    letterRenderer.drawLetter(col, row, LetterEnum.A);
                } else {
                    letterRenderer.useBlueCode();
                    letterRenderer.drawLetter(col, row, currentKeyLetter.getKeyLetter());
                }

            }
        }
    }

    private void updateIncorrectSprites(LetterRenderer letterRenderer) {
        for (PositionLetter incorrectLetter : model.getIncorrectLetters()) {

            // TODO - ughh need fireworks
            if (incorrectLetter.getValue().getIndex() == 26) {
                letterRenderer.useSpecial();
                letterRenderer.drawLetter(incorrectLetter.getCol(), incorrectLetter.getRow(), LetterEnum.A);
            } else {
                letterRenderer.useWhite();
                letterRenderer.drawLetter(incorrectLetter.getCol(), incorrectLetter.getRow(), incorrectLetter.getValue());
            }
        }
    }
}
