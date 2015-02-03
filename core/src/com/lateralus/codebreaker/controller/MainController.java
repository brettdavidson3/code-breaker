package com.lateralus.codebreaker.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lateralus.codebreaker.model.GameModel;
import com.lateralus.codebreaker.model.LoseModel;
import com.lateralus.codebreaker.model.MainModel;
import com.lateralus.codebreaker.model.TitleModel;
import com.lateralus.codebreaker.model.constant.Difficulty;
import com.lateralus.codebreaker.model.screen.CodeBreakerScreen;
import com.lateralus.codebreaker.view.GameView;
import com.lateralus.codebreaker.view.LoseView;
import com.lateralus.codebreaker.view.TitleView;
import com.lateralus.codebreaker.view.render.LetterRenderer;

public class MainController implements Screen {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private CodeBreakerScreen currentScreen;
    private LetterRenderer letterRenderer;
    private MainModel mainModel;

    public MainController() {
        spriteBatch = new SpriteBatch();
        letterRenderer = new LetterRenderer();
        mainModel = new MainModel(10); // TODO: save this to disk
        initViewport();
        viewTitleScreen();
    }

    @Override
    public void render(float delta) {
        updateController(delta);
        applyView(delta);
        resetScreen();
        drawSprites();
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

    private void updateController(float delta) {
        currentScreen.getController().update(delta);
    }

    private void applyView(float delta) {
        currentScreen.getView().reset(letterRenderer, delta);
        currentScreen.getView().render(letterRenderer);
    }

    private void resetScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawSprites() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        letterRenderer.render(spriteBatch);

        spriteBatch.end();
    }

    public void viewTitleScreen() {
        TitleModel model = new TitleModel();
        TitleView view = new TitleView(model);
        TitleController controller = new TitleController(this, model);

        currentScreen = new CodeBreakerScreen(model, view, controller);
    }

    public void viewGameScreen(Difficulty difficulty) {
        GameModel model = new GameModel(difficulty);
        GameView view = new GameView(model);
        GameController controller = new GameController(this, model);

        currentScreen = new CodeBreakerScreen(model, view, controller);
    }

    public void viewLoseScreen(int score) {
        if (score > mainModel.getHighScore()) {
            mainModel.setHighScore(score);
        }

        LoseModel model = new LoseModel(score, mainModel.getHighScore());
        LoseView view = new LoseView(model);
        LoseController controller = new LoseController(this);

        currentScreen = new CodeBreakerScreen(model, view, controller);
    }

}
