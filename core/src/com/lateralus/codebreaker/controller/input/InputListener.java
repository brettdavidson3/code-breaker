package com.lateralus.codebreaker.controller.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Map;

public class InputListener {

    private Map<Integer, Runnable> keyListeners;
    private float timeSinceLastInput = 0f;
    private int currentKeyPressed;

    public void addKeyListener(int key, Runnable action) {
        keyListeners.put(key, action);
    }

    public void checkInput(float delta) {
        for (Map.Entry<Integer, Runnable> entry :  keyListeners.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                if (currentKeyPressed == entry.getKey()) {
                    timeSinceLastInput += delta;
                }
            }
        }
    }

}
