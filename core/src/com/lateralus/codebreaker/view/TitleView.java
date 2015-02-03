package com.lateralus.codebreaker.view;

import com.lateralus.codebreaker.model.TitleModel;
import com.lateralus.codebreaker.model.constant.Difficulty;
import com.lateralus.codebreaker.model.letter.LetterEnum;
import com.lateralus.codebreaker.view.render.LetterRenderer;

import java.util.List;

import static com.lateralus.codebreaker.model.constant.Difficulty.*;
import static com.lateralus.codebreaker.model.letter.LetterEnum.newWord;

public class TitleView implements CodeBreakerView {

    private static final int DIFFICULTY_LIST_START_ROW = 12;
    public static final List<LetterEnum> WORD_CODE = newWord("code");
    public static final List<LetterEnum> WORD_BREAKER = newWord("breaker");
    public static final List<LetterEnum> WORD_EASY = newWord("easy");
    public static final List<LetterEnum> WORD_NORMAL = newWord("normal");
    public static final List<LetterEnum> WORD_HARD = newWord("hard");

    private final TitleModel model;

    public TitleView(TitleModel model) {
        this.model = model;
    }

    @Override
    public void reset(LetterRenderer letterRenderer, float delta) {
        letterRenderer.reset(delta, false);
    }

    @Override
    public void render(LetterRenderer letterRenderer) {
        letterRenderer.useGold();
        letterRenderer.drawCenteredWords(15, WORD_CODE, WORD_BREAKER);

        letterRenderer.useWhite();
        letterRenderer.drawCenteredWords(getRow(Easy), WORD_EASY);

        int rightIndicatorCol = letterRenderer.drawCenteredWords(getRow(Normal), WORD_NORMAL);
        int leftIndicatorCol = rightIndicatorCol - WORD_NORMAL.size() - 1;

        letterRenderer.drawCenteredWords(getRow(Hard), WORD_HARD);

        letterRenderer.useGold();
        int indicatorRow = getRow(model.getDifficulty());
        letterRenderer.drawLetter(rightIndicatorCol, indicatorRow, LetterEnum.BLANK);
        letterRenderer.drawLetter(leftIndicatorCol, indicatorRow, LetterEnum.BLANK);
    }

    private int getRow(Difficulty difficulty) {
        return DIFFICULTY_LIST_START_ROW - difficulty.ordinal();
    }
}
