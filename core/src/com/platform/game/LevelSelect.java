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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.platform.game.LevelManager;

import java.util.ArrayList;

public class LevelSelect implements Screen{
    private PlatformGame game;
    private OrthographicCamera camera;
    private Stage stage;
    ArrayList<Button> levels;
    int BUTTONHEIGHT = 100, BUTTONWIDTH = 200;
    public LevelSelect(PlatformGame game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.getWidth(), game.getHeight());
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin neon = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));
        levels = new ArrayList<Button>();
        for(int i = 0; i < LevelManager.LEVELS.size; i++){
            System.out.println(LevelManager.LEVELS.get(i).getMapName());
            levels.add(new TextButton(LevelManager.LEVELS.get(i).getMapName(), neon));
            levels.get(i).setSize(BUTTONWIDTH, BUTTONHEIGHT);
            levels.get(i).setTransform(true);
            levels.get(i).scaleBy(2f);
            System.out.println(i%4 + "   "+ i);
            levels.get(i).setPosition(0 + (BUTTONWIDTH * (i%4) * 3f), game.getHeight()/1.25f - (BUTTONHEIGHT * ((int)(i+4)/4) * 3f));
            final int levNum = i;
            levels.get(i).addListener(new InputListener(){
                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

                }
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(new GameScreen(game, levNum));
                    dispose();
                    return true;
                }
            });
            stage.addActor(levels.get(i));
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        Texture start = new Texture(Gdx.files.internal("background/Level Select.png"));
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(start, 0, 0, 2400, 1140);
        game.batch.end();

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
