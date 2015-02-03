package com.lateralus.codebreaker.view;

import com.lateralus.codebreaker.view.render.LetterRenderer;

public interface CodeBreakerView {

    public void reset(LetterRenderer letterRenderer, float delta);
    public void render(LetterRenderer letterRenderer);
}
