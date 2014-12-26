package com.project.terminkalender;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static AbstractScreen calendarScreen;
	
	private SpriteBatch batch;
	private Viewport viewport;
	private OrthographicCamera camera;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		viewport.update(WIDTH, HEIGHT, true);
		calendarScreen = new CalendarScreen(this);
		setScreen(calendarScreen);
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
	public void dispose() { // Método para eliminar recursos.
		calendarScreen.dispose();
		batch.dispose();
	}
}
