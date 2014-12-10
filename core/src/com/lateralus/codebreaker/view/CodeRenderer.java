package com.lateralus.codebreaker.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lateralus.codebreaker.model.World;

public interface CodeRenderer {

    public void initialize(World world);
    public void render(SpriteBatch spriteBatch, World world, float delta);
}
