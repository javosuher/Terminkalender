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
	public void create() { // Método que se llama cuando se ejecuta el juego
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(WIDTH, HEIGHT, camera); // Inicializamos todo
		calendarScreen = new CalendarScreen(this);
		setScreen(calendarScreen); // Establecemos la pantalla del juego como principal
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
