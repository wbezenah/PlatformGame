package com.platform.game;

import static com.platform.game.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

import objects.Player;
import objects.RightGauser;
import objects.Token;
import objects.Crab;
import objects.RightGauser;

public class GameScreen implements Screen {
    private PlatformGame game;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private LevelManager levelManager;
    private PlayerContactListener playerContactListener;
    TouchControl touchControl;
    private Player player;
    private Token token;
    private ArrayList<Crab> crab;
    private ArrayList<RightGauser> rightGauser;
    boolean gameOver = false;
    private int setLevel = -1;

    public GameScreen(PlatformGame game, int initialLevel) {
        init(game, initialLevel);
    }
    public GameScreen(PlatformGame game) {
        init(game, 0);
    }

    private void init(PlatformGame game, int initialLevel) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, game.getWidth(), game.getHeight());
        this.touchControl = new TouchControl(game.getWidth(), game.getHeight());

        crab = new ArrayList<Crab>();
        rightGauser = new ArrayList<RightGauser>();

        this.world = new World(new Vector2(0, -9.8f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.levelManager = new LevelManager(this, initialLevel);
        this.playerContactListener = new PlayerContactListener(this);

        this.world.setContactListener(playerContactListener);

    }

    public World getWorld() {
        return world;
    }
    public LevelManager getLevelManager() { return levelManager; }
    public void setPlayer(Player player) { this.player = player; }
    public void setToken(Token token) { this.token = token; }
    public void addCrab(Crab crab) { this.crab.add(crab); }
    public void addRightGauser(RightGauser rightGauser) { this.rightGauser.add(rightGauser); }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.update();
        game.batch.setProjectionMatrix(camera.combined);


        levelManager.orthogonalTiledMapRenderer.setView(camera);
        levelManager.orthogonalTiledMapRenderer.render();

        game.batch.begin();
        player.render(game.batch);
        for(int i = 0; i < crab.size(); i++){
            crab.get(i).render(game.batch);
        }
        for(int i = 0; i < rightGauser.size(); i++){
            rightGauser.get(i).render(game.batch);
        }
        game.batch.end();

        touchControl.render(game.shapeRenderer);

//        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    private void destroyBodies() {
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++) {
            if(!world.isLocked()) {
                world.destroyBody(bodies.get(i));
            }
        }
    }
    public void queueSetLevel(int levelNum) {
        setLevel = levelNum;
    }
    private void update() {
        if(setLevel >= 0) {
            destroyBodies();
            crab.clear();
            rightGauser.clear();
            levelManager.setLevel(setLevel);
            setLevel = -1;
        }
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        game.batch.setProjectionMatrix(camera.combined);
        levelManager.orthogonalTiledMapRenderer.setView(camera);

        if(!gameOver) {
            player.update();
            for(int i = 0; i < crab.size(); i++){
                crab.get(i).update();
            }
            for(int i = 0; i < rightGauser.size(); i++){
                rightGauser.get(i).update();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        Vector3 cameraPos = camera.position;
        cameraPos.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
        cameraPos.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
        camera.position.set(cameraPos);
        camera.update();
    }

    @Override
    public void dispose() {
        this.levelManager.dispose();
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
}
