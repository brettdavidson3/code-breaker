package com.lateralus.codebreaker.controller.input;

import com.badlogic.gdx.Gdx;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class InputListener {

    private static final float KEY_PRESS_INTERVAL = 0.125f;

    private Map<Integer, Runnable> keyListeners;
    private float timeSinceLastInput = 0f;
    private int currentKeyPressed;

    public InputListener() {
        this.keyListeners = newHashMap();
    }

    public void addKeyListener(int key, Runnable action) {
        keyListeners.put(key, action);
    }

    public void checkInput(float delta) {
        boolean anyKeyPressed = false;

        for (Map.Entry<Integer, Runnable> entry : keyListeners.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                anyKeyPressed = true;
                if (currentKeyPressed == entry.getKey()) {
                    timeSinceLastInput += delta;
                    if (timeSinceLastInput >= KEY_PRESS_INTERVAL) {
                        entry.getValue().run();
                        timeSinceLastInput = 0f;
                    }
                } else {
                    entry.getValue().run();
                    timeSinceLastInput = 0f;
                    currentKeyPressed = entry.getKey();
                }
            }
        }

        if (!anyKeyPressed) {
            currentKeyPressed = -1;
        }
    }

}
