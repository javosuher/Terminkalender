package com.project.terminkalender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class CalendarScreen extends AbstractScreen {
	private Texture texture;

	public CalendarScreen(Main main) {
		super(main);
		texture = new Texture("badlogic.jpg");
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.begin();
		batch.draw(texture, 0, 0, texture.getWidth(), texture.getHeight());
		batch.end();
	}
}
