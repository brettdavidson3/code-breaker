package com.lateralus.codebreaker.controller.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lateralus.codebreaker.controller.CodeController;
import com.lateralus.codebreaker.controller.LetterController;
import com.lateralus.codebreaker.model.World;
import com.lateralus.codebreaker.view.CodeRenderer;
import com.lateralus.codebreaker.view.LetterRenderer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GameScreen implements Screen {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private World world;
    private LetterController letterController;
    private LetterRenderer letterRenderer;

    public GameScreen(Game game) {
        spriteBatch = new SpriteBatch();
        world = new World();
        initViewport();
        initControllers();
        initRenderers();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateControllers(delta);
        renderSprites(delta);
    }

    private void updateControllers(float delta) {
        switch (world.getScreen()) {
            case Title: break;
            case Game: letterController.update(world, delta); break;
            case Lose: break;
        }
    }

    private void renderSprites(float delta) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        letterRenderer.render(spriteBatch, world, delta);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void initViewport() {
        float windowWidth = Gdx.graphics.getWidth();
        float windowHeight = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(windowWidth, windowHeight);
        camera.position.set(camera.viewportWidth, camera.viewportHeight, 0);
        camera.zoom = 2; // TODO: make dynamic
        camera.update();
    }

    private void initControllers() {
        letterController = new LetterController();
        letterController.initialize(world);
    }

    private void initRenderers() {
        letterRenderer = new LetterRenderer();
        letterRenderer.initialize(world);
    }

}
