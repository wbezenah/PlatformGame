package com.platform.game;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LevelManager {
    public static class MapInfo {
        private String mapPath;
        private String mapName;
        public MapInfo(String mapPath, String mapName) {
            this.mapPath = mapPath;
            this.mapName = mapName;
        }
        public String getMapPath() { return mapPath; }
        public String getMapName() { return mapName; }
    }

    public static final List<MapInfo> LEVELS = Collections.unmodifiableList(Arrays.asList(
            new MapInfo("map/map_0.tmx", "Level1"),
            new MapInfo("map/map_1.tmx", "Level2"),
            new MapInfo("map/map_2.tmx", "Level3"),
            new MapInfo("map/map_3.tmx", "Level4"),
            new MapInfo("map/map_4.tmx", "Level5"),
            new MapInfo("map/map_5.tmx", "Level6"),
            new MapInfo("map/map_6.tmx", "Level7"),
            new MapInfo("map/map_7.tmx", "Level8")
    ));
    
    private GameScreen gameScreen;
    public int currentLevel;
    public TileMapManager tileMapManager;
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public LevelManager(GameScreen gameScreen, int initialLevel) {
        this.gameScreen = gameScreen;
        this.currentLevel = initialLevel;
        this.tileMapManager = new TileMapManager(this.gameScreen);
        this.tileMapManager.init(LEVELS.get(currentLevel).getMapPath());
        this.orthogonalTiledMapRenderer = tileMapManager.getOrthogonalTiledMapRenderer();
    }

    public boolean nextLevel() {
        if(LEVELS.size() <= ++currentLevel) { return false; }
        tileMapManager.setMap(LEVELS.get(currentLevel).getMapPath());
        return true;
    }

    public boolean prevLevel() {
        if(currentLevel == 0) { return false; }
        tileMapManager.setMap(LEVELS.get(--currentLevel).getMapPath());
        return true;
    }

    public boolean setLevel(int levelNum) {
        if(LEVELS.size() <= levelNum) { return false; }
        currentLevel = levelNum;
        tileMapManager.setMap(LEVELS.get(levelNum).getMapPath());
        return true;
    }

    public boolean setLevel(String levelName) {
        for(MapInfo map : LEVELS) {
            if(map.getMapName().equals(levelName)) {
                tileMapManager.setMap(map.getMapPath());
                return true;
            }
        }
        return false;
    }

    public void dispose() {
        orthogonalTiledMapRenderer.dispose();
        tileMapManager.dispose();
    }
}
