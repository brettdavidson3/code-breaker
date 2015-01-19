package com.lateralus.codebreaker.controller;

public class LoseController implements CodeBreakerController {

    private final MainController mainController;

    public LoseController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void update(float delta) {

    }
}
