package com.platform.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PlatformGame extends Game {
	private int width, height;
	public BitmapFont font;
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		this.font.getData().setScale(3f);
		this.shapeRenderer = new ShapeRenderer();
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		font.dispose();
		batch.dispose();
	}
}
