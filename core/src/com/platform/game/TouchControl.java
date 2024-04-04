package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;

public class TouchControl {
    public enum ButtonType {
        JUMP,
        RIGHT,
        LEFT
    }
    private float width;
    private float height;
    private static final float RADIUS = 70f;
    private static final float SCREEN_MARGIN = 150f;
    private Vector2 jumpButtonPos;
    private Vector2 leftButtonPos;
    private Vector2 rightButtonPos;

    public TouchControl(float windowWidth, float windowHeight) {
        this.width = windowWidth;
        this.height = windowHeight;

        this.jumpButtonPos = new Vector2(0 + SCREEN_MARGIN, 0 + SCREEN_MARGIN);

        this.rightButtonPos = new Vector2(width - SCREEN_MARGIN, 0 + SCREEN_MARGIN);
        this.leftButtonPos = new Vector2(width - (2 * SCREEN_MARGIN) - RADIUS, 0 + SCREEN_MARGIN);

    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(jumpButtonPos.x, jumpButtonPos.y, RADIUS);
        shapeRenderer.circle(rightButtonPos.x, rightButtonPos.y, RADIUS);
        shapeRenderer.circle(leftButtonPos.x, leftButtonPos.y, RADIUS);
        shapeRenderer.end();
    }

    public boolean isTouched(ButtonType buttonType) {
        if(!Gdx.input.isTouched()) { return false; }

        Array<Vector2> touches = new Array<>();
        for(int i = 0; i < 20; i++) {
            if(Gdx.input.isTouched(i)) {
                touches.add(new Vector2(Gdx.input.getX(), height - Gdx.input.getY()));
            }
        }

        Vector2 buttonPos = new Vector2();
        switch(buttonType) {
            case JUMP:
                buttonPos.set(jumpButtonPos);
                break;

            case LEFT:
                buttonPos.set(leftButtonPos);
                break;

            case RIGHT:
                buttonPos.set(rightButtonPos);
                break;
        }

        for(Vector2 touchPos : touches) {
            float dx = touchPos.x - buttonPos.x;
            float dy = touchPos.y - buttonPos.y;
            float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

            if(distance < RADIUS) { return true; }

        }

        return false;
    }


}
