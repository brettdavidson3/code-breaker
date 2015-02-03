package com.lateralus.codebreaker.view;

import com.lateralus.codebreaker.model.GameModel;
import com.lateralus.codebreaker.model.letter.KeyLetter;
import com.lateralus.codebreaker.model.letter.LetterEnum;
import com.lateralus.codebreaker.view.render.LetterRenderer;

import java.util.List;

import static com.lateralus.codebreaker.model.letter.LetterEnum.newWord;
import static com.lateralus.codebreaker.view.render.LetterRenderer.LETTER_COLUMN_COUNT;
import static com.lateralus.codebreaker.view.render.LetterRenderer.LETTER_ROW_COUNT;

public class GameView implements CodeBreakerView {

    public static final List<LetterEnum> WORD_SCORE = newWord("score");
    private GameModel model;

    public GameView(GameModel model) {
        this.model = model;
    }

    @Override
    public void reset(LetterRenderer letterRenderer, float delta) {
        letterRenderer.reset(delta, true);
    }

    @Override
    public void render(LetterRenderer letterRenderer) {
        renderScore(letterRenderer);
        renderActiveSprite(letterRenderer);
        renderKeySprites(letterRenderer);
        renderIncorrectSprites(letterRenderer);
    }

    private void renderScore(LetterRenderer letterRenderer) {
        letterRenderer.useWhite();
        letterRenderer.drawLeftAlignedWord(LETTER_ROW_COUNT - 1, WORD_SCORE);

        letterRenderer.useWhiteNumbers();
        letterRenderer.drawRightAlignedNumber(LETTER_ROW_COUNT - 1, model.getScore());
    }

    private void renderActiveSprite(LetterRenderer letterRenderer) {
        letterRenderer.useGreen();
        letterRenderer.drawLetter(model.getActiveLetter());
    }

    private void renderKeySprites(LetterRenderer letterRenderer) {
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

    private void renderIncorrectSprites(LetterRenderer letterRenderer) {
        letterRenderer.useWhite();
        model.getIncorrectLetters().forEach(letterRenderer::drawLetter);
    }
}
