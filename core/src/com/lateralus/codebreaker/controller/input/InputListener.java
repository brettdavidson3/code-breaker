package com.lateralus.codebreaker.controller.input;

import com.badlogic.gdx.Gdx;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class InputListener {

    private static final float KEY_HOLD_INTERVAL = 0.125f;

    private Map<Integer, Runnable> pressListeners;
    private Map<Integer, Runnable> holdListeners;
    private float timeSinceLastHoldInput = 0f;
    private int currentKeyPressed; // TODO: keep track of more than one key being pressed
    private int currentKeyHeld; // TODO: keep track of more than one key being held

    public InputListener() {
        this.pressListeners = newHashMap();
        this.holdListeners = newHashMap();
    }

    public void addPressListener(int key, Runnable action) {
        pressListeners.put(key, action);
    }

    public void addHoldListener(int key, Runnable action) {
        holdListeners.put(key, action);
    }

    public void checkInput(float delta) {
        checkPressInput();
        checkHoldInput(delta);
    }

    private void checkPressInput() {
        boolean anyKeyPressed = false;

        for (Map.Entry<Integer, Runnable> entry : pressListeners.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                anyKeyPressed = true;
                if (currentKeyPressed != entry.getKey()) {
                    currentKeyPressed = entry.getKey();
                    entry.getValue().run();
                }
            }
        }

        if (!anyKeyPressed) {
            currentKeyPressed = -1;
        }
    }

    private void checkHoldInput(float delta) {
        boolean anyKeyPressed = false;

        for (Map.Entry<Integer, Runnable> entry : holdListeners.entrySet()) {
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                anyKeyPressed = true;
                if (currentKeyHeld == entry.getKey()) {
                    timeSinceLastHoldInput += delta;
                    if (timeSinceLastHoldInput >= KEY_HOLD_INTERVAL) {
                        entry.getValue().run();
                        timeSinceLastHoldInput = 0f;
                    }
                } else {
                    entry.getValue().run();
                    timeSinceLastHoldInput = 0f;
                    currentKeyHeld = entry.getKey();
                }
            }
        }

        if (!anyKeyPressed) {
            currentKeyHeld = -1;
        }
    }

}
