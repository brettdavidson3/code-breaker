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
    private LoseModel model;

    public LoseView(LoseModel model) {
        this.model = model;
    }

    @Override
    public void render(LetterRenderer letterRenderer) {
        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(11, WORD_GAME, WORD_OVER);

        if (model.getHighScore() == model.getScore()) {
            renderNewHighScoreText(letterRenderer);
        } else {
            renderNormalScoreText(letterRenderer);
        }
    }

    private void renderNewHighScoreText(LetterRenderer letterRenderer) {
        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(6, WORD_NEW);
        letterRenderer.drawCenteredWords(5, WORD_HIGH, WORD_SCORE);

        letterRenderer.useWhite();
        letterRenderer.drawCenteredWords(4, newWord("abcdef"));
    }

    private void renderNormalScoreText(LetterRenderer letterRenderer) {
        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(6, WORD_YOUR, WORD_SCORE);

        letterRenderer.useWhite();
        letterRenderer.drawCenteredWords(5, newWord("ghijkl"));

        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(3, WORD_HIGH, WORD_SCORE);

        letterRenderer.useWhite();
        letterRenderer.drawCenteredWords(2, newWord("mnopqr"));
    }
}
