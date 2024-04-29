package com.platform.game;

import static com.platform.game.Constants.PLAYER_MASS;
import static com.platform.game.Constants.PPM;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import java.util.Objects;

import objects.Player;
import objects.Token;
import objects.Crab;
import objects.RightGauser;

public class TileMapManager {
    private TiledMap tiledMap;
    private GameScreen gameScreen;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public TileMapManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer getOrthogonalTiledMapRenderer() {
        return this.orthogonalTiledMapRenderer;
    }

    public Vector2 getTiledMapDimension() {
        MapProperties prop = tiledMap.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        return new Vector2(mapPixelWidth, mapPixelHeight);
    }

    public void init(String mapPath) {
        if(Objects.nonNull(tiledMap)) { tiledMap.dispose(); }
        tiledMap = new TmxMapLoader().load(mapPath);
        parseObjects(tiledMap.getLayers().get("objects").getObjects());
        this.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    void setMap(String mapPath) {
        orthogonalTiledMapRenderer.getMap().dispose();
        tiledMap = new TmxMapLoader().load(mapPath);
        parseObjects(tiledMap.getLayers().get("objects").getObjects());
        orthogonalTiledMapRenderer.setMap(tiledMap);
    }

    private void parseObjects(MapObjects objects) {
        for(MapObject object : objects) {
            if(object instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) object);
            }

            if(object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                String name = object.getName();

                Vector2 pos;
                Body body;
                switch(name) {
                    case "player":
                        pos = new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                        body = BodyManager.createBody(pos, rectangle.getWidth(), rectangle.getHeight(), false, gameScreen.getWorld());
                        MassData playerMassData = body.getMassData();
                        playerMassData.mass = PLAYER_MASS;
                        body.setMassData(playerMassData);
                        gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body, gameScreen.touchControl));
                        break;


                    case "token":
                        pos = new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                        body = BodyManager.createBody(pos, rectangle.getWidth(), rectangle.getHeight(), true, gameScreen.getWorld());
                        gameScreen.setToken(new Token(rectangle.getWidth(), rectangle.getHeight(), body));
                        break;

                    case "crab":
                        pos = new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                        body = BodyManager.createBody(pos, rectangle.getWidth(), rectangle.getHeight(), false, gameScreen.getWorld());
                        gameScreen.addCrab(new Crab(rectangle.getWidth(), rectangle.getHeight(), body));
                        break;

                    case "right_geyser":
                        pos = new Vector2(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
                        body = BodyManager.createBody(pos, rectangle.getWidth(), rectangle.getHeight(), true, gameScreen.getWorld());
                        gameScreen.addRightGauser(new RightGauser(rectangle.getWidth(), rectangle.getHeight(), body));
                        break;
                }
            }
        }
    }

    private void createStaticBody(PolygonMapObject object) {
        BodyDef staticBodyDef = new BodyDef();
        staticBodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(staticBodyDef);
        body.setUserData(object.getName());

        Shape shape = createPolygonShape(object);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject object) {
        float[] vertices = object.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2(vertices[i*2] / PPM, vertices[i * 2 + 1] / PPM);
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    public void dispose() {
        this.tiledMap.dispose();
    }
}
