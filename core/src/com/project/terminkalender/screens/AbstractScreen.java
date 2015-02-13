package com.project.terminkalender.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.project.terminkalender.Main;

public class AbstractScreen implements Screen {
	protected SpriteBatch batch;
	protected Stage stage;
	
	public AbstractScreen() {
		stage = new Stage(Main.viewport, Main.batch);
		this.batch = Main.batch;
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
