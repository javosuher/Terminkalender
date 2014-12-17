package com.project.terminkalender;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AbstractScreen implements Screen {
	protected Main main;
	protected SpriteBatch batch;
	protected OrthographicCamera camera; 
	protected Viewport viewport; 
	
	public AbstractScreen(Main main) {
		this.main = main;
		this.batch = main.getBatch();
		this.camera = main.getCamera();
		this.viewport = main.getViewport();
	}
	
	@Override
	public void render(float delta) {} 
	
	@Override
	public void resize(int width, int height) { 
		viewport.update(width, height);
	}
	
	@Override
	public void show() {} 
	
	@Override
	public void hide() {} 
	
	@Override
	public void pause() {} 
	
	@Override
	public void resume() {}
	
	@Override
	public void dispose() {}
}
