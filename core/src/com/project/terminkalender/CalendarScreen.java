package com.project.terminkalender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class CalendarScreen extends AbstractScreen {
	private Calendar calendar;

	public CalendarScreen(Main main) {
		super(main);
		calendar = new Calendar();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		calendar.update();
		
		batch.begin();
		calendar.draw(batch);
		batch.end();
	}
}
