package com.lateralus.codebreaker.controller;

import com.badlogic.gdx.Input;
import com.lateralus.codebreaker.controller.input.InputListener;

public class LoseController implements CodeBreakerController {

    private MainController mainController;
    private InputListener inputListener;

    public LoseController(MainController mainController) {
        this.mainController = mainController;
        initializeInputListener();
    }

    @Override
    public void update(float delta) {
        inputListener.checkInput(delta);
    }

    private void initializeInputListener() {
        inputListener = new InputListener();
        inputListener.addPressListener(Input.Keys.ENTER, () -> mainController.viewTitleScreen());
    }
}
