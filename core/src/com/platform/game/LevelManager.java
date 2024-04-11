package com.platform.game;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

public class LevelManager {
    private GameScreen gameScreen;
    public Array<String> levelPaths = new Array<>();
    private int currentLevel = 0;
    public TileMapManager tileMapManager;
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public LevelManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        this.levelPaths.add("map/map_0.tmx");
        this.levelPaths.add("map/map_1.tmx");

        this.tileMapManager = new TileMapManager(this.gameScreen);
        this.tileMapManager.init(levelPaths.get(currentLevel));
        this.orthogonalTiledMapRenderer = tileMapManager.getOrthogonalTiledMapRenderer();
    }

    public boolean nextLevel() {
        if(levelPaths.size <= ++currentLevel) { return false; }
        tileMapManager.setMap(levelPaths.get(currentLevel));

        return true;
    }

    public void dispose() {
        orthogonalTiledMapRenderer.dispose();
        tileMapManager.dispose();
    }
}
