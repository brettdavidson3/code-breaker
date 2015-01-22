package com.lateralus.codebreaker.controller;

import com.badlogic.gdx.Input;
import com.lateralus.codebreaker.controller.input.InputListener;
import com.lateralus.codebreaker.model.TitleModel;

public class TitleController implements CodeBreakerController {

    private MainController mainController;
    private TitleModel model;
    private InputListener inputListener;

    public TitleController(MainController mainController, TitleModel model) {
        this.mainController = mainController;
        this.model = model;
        initializeInputListener();
    }

    @Override
    public void update(float delta) {
        inputListener.checkInput(delta);
    }

    private void initializeInputListener() {
        this.inputListener = new InputListener();
        inputListener.addPressListener(Input.Keys.UP, this::onUpPressed);
        inputListener.addPressListener(Input.Keys.DOWN, this::onDownPressed);
        inputListener.addPressListener(Input.Keys.ENTER, this::onEnterPressed);
    }

    private void onUpPressed() {
        model.setDifficulty(model.getDifficulty().getPrevious());
    }

    private void onDownPressed() {
        model.setDifficulty(model.getDifficulty().getNext());
    }

    private void onEnterPressed() {
        mainController.viewGameScreen(model.getDifficulty());
    }
}
