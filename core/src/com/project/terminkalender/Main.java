package com.project.terminkalender;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.screens.AbstractScreen;

abstract class Main extends Game {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	
	public static SpriteBatch batch;
	public static Viewport viewport;
	public static OrthographicCamera camera;
	
	public static Main main;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		viewport.update(WIDTH, HEIGHT, true);
		Resources.load();
		Gdx.input.setCatchBackKey(true);
	}
	
	public static void setNewScreen(AbstractScreen newScreen) {
		main.setScreen(newScreen);
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		Resources.assets.dispose();
	}
}
