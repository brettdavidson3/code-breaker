package com.lateralus.codebreaker.controller;

import com.badlogic.gdx.Input;
import com.lateralus.codebreaker.controller.input.InputListener;
import com.lateralus.codebreaker.model.World;

public class TitleController implements CodeController {

    private World world;
    private InputListener inputListener;

    @Override
    public void initialize(World world) {
        this.world = world;
        initializeInputListener();
    }

    @Override
    public void update(float delta) {
        inputListener.checkInput(delta);
    }

    private void initializeInputListener() {
        this.inputListener = new InputListener();
        inputListener.addKeyListener(Input.Keys.UP, this::onUpPressed);
        inputListener.addKeyListener(Input.Keys.DOWN, this::onDownPressed);
        inputListener.addKeyListener(Input.Keys.ENTER, this::onEnterPressed);
    }

    private void onUpPressed() {
        switch (world.getDifficulty()) {
            case Easy:      world.setDifficulty(World.Difficulty.Hard);break;
            case Normal:    world.setDifficulty(World.Difficulty.Easy);break;
            case Hard:      world.setDifficulty(World.Difficulty.Normal);break;
        }
    }

    private void onDownPressed() {
        switch (world.getDifficulty()) {
            case Easy:      world.setDifficulty(World.Difficulty.Normal);break;
            case Normal:    world.setDifficulty(World.Difficulty.Hard);break;
            case Hard:      world.setDifficulty(World.Difficulty.Easy);break;
        }
    }

    private void onEnterPressed() {
        world.setScreen(World.Screen.Game);
    }
}
