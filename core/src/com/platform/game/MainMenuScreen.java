package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    private PlatformGame game;
    private OrthographicCamera camera;
    public MainMenuScreen(PlatformGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.getWidth(), game.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        Texture start = new Texture(Gdx.files.internal("background/start screen.png"));
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(start, 0, 0, 2400, 1150);
        game.font.draw(game.batch, "Welcome!!! ", (float) game.getWidth() / 2 - 100, (float) game.getHeight() / 2);
        game.font.draw(game.batch, "Tap anywhere to begin!", (float) game.getWidth() / 2 - 100, (float) game.getHeight() / 2 - 50);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
