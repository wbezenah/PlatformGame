package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private PlatformGame game;
    private OrthographicCamera camera;
    private Stage stage;
    Button campaign, levelSelect;
    int BUTTONHEIGHT = 100, BUTTONWIDTH = 200;

    public MainMenuScreen(PlatformGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.getWidth(), game.getHeight());
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin neon = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        campaign = new TextButton("Campaign", neon);
        campaign.setSize(BUTTONWIDTH, BUTTONHEIGHT);
        campaign.setTransform(true);
        campaign.scaleBy(3f);
        campaign.setPosition((float) game.getWidth() / 1.75f - 100, (float) game.getHeight() / 2 - 50);
        campaign.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });
        stage.addActor(campaign);
        levelSelect = new TextButton("Level Select", neon);
        levelSelect.setSize(BUTTONWIDTH, BUTTONHEIGHT);
        levelSelect.setTransform(true);
        levelSelect.scaleBy(3f);
        levelSelect.setPosition((float) game.getWidth() / 1.75f - 100, (float) game.getHeight() / 2 - 150 - BUTTONHEIGHT * 3);
        levelSelect.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(levelSelect);
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
        //game.font.draw(game.batch, "Welcome!!! ", (float) game.getWidth() / 2 - 100, (float) game.getHeight() / 2);
        //game.font.draw(game.batch, "Tap anywhere to begin!", (float) game.getWidth() / 2 - 100, (float) game.getHeight() / 2 - 50);
        game.batch.end();

       /* if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }*/
        stage.draw();
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
