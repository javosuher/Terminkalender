package com.project.terminkalender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class CalendarScreen extends AbstractScreen {
	private Calendar calendar;
	private Texture background;

	public CalendarScreen(Main main) {
		super(main);
		background = new Texture("background.png");
		calendar = new Calendar();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		camera.update();
		calendar.update();
		
		batch.begin();
		batch.draw(background, 0, 0);
		calendar.draw(batch);
		batch.end();
	}
}
