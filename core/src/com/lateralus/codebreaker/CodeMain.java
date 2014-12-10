package com.lateralus.codebreaker;

import com.badlogic.gdx.Game;
import com.lateralus.codebreaker.controller.screen.GameScreen;

public class CodeMain extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }

}
