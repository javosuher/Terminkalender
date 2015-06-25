package com.project.terminkalender.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.Main;

public class AbstractScreen implements Screen {
	protected Viewport viewport;
	protected SpriteBatch batch;
	protected Stage stage;
	
	public AbstractScreen(Viewport viewport, SpriteBatch batch) {
		stage = new Stage(viewport, batch);
		this.viewport = viewport;
		this.batch = batch;
	}
	
	@Override
	public void render(float delta) {} 
	
	@Override
	public void resize(int width, int height) { 
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void show() {} 
	
	@Override
	public void hide() {} 
	
	@Override
	public void pause() {} 
	
	@Override
	public void resume() { 
		Main.assets.finishLoading(); 
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
	
	public Stage getStage() {
		return stage;
	}
}
