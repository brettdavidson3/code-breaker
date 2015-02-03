package com.lateralus.codebreaker.view;

import com.lateralus.codebreaker.model.LoseModel;
import com.lateralus.codebreaker.model.letter.LetterEnum;
import com.lateralus.codebreaker.view.render.LetterRenderer;

import java.util.List;

import static com.lateralus.codebreaker.model.letter.LetterEnum.newWord;

public class LoseView implements CodeBreakerView{

    public static final List<LetterEnum> WORD_GAME = newWord("game");
    public static final List<LetterEnum> WORD_OVER = newWord("over");
    public static final List<LetterEnum> WORD_NEW = newWord("new");
    public static final List<LetterEnum> WORD_HIGH = newWord("high");
    public static final List<LetterEnum> WORD_SCORE = newWord("score");
    public static final List<LetterEnum> WORD_YOUR = newWord("your");
    public static final List<LetterEnum> WORD_PRESS = newWord("press");
    public static final List<LetterEnum> WORD_ENTER = newWord("enter");
    private LoseModel model;

    public LoseView(LoseModel model) {
        this.model = model;
    }

    @Override
    public void reset(LetterRenderer letterRenderer, float delta) {
        letterRenderer.reset(delta, false);
    }

    @Override
    public void render(LetterRenderer letterRenderer) {
        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(15, WORD_GAME, WORD_OVER);

        if (model.getHighScore() == model.getScore()) {
            renderNewHighScoreText(letterRenderer);
        } else {
            renderNormalScoreText(letterRenderer);
        }

        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(1, WORD_PRESS, WORD_ENTER);
    }

    private void renderNewHighScoreText(LetterRenderer letterRenderer) {
        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(9, WORD_NEW);
        letterRenderer.drawCenteredWords(8, WORD_HIGH, WORD_SCORE);

        letterRenderer.useWhiteNumbers();
        letterRenderer.drawRightAlignedNumber(7, model.getScore());
    }

    private void renderNormalScoreText(LetterRenderer letterRenderer) {
        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(9, WORD_YOUR, WORD_SCORE);

        letterRenderer.useWhiteNumbers();
        letterRenderer.drawRightAlignedNumber(8, model.getScore());

        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(6, WORD_HIGH, WORD_SCORE);

        letterRenderer.useWhiteNumbers();
        letterRenderer.drawRightAlignedNumber(5, model.getHighScore());
    }
}
