package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.Main;
import com.project.terminkalender.login.GamesOpen;
import com.project.terminkalender.login.GamesOpenActor;
import com.project.terminkalender.tools.ReconnectButton;

public class LoginGamesScreen extends AbstractScreen {
	private Background background;
	private ReconnectButton reconnectButton;
	private GamesOpenActor teacherGames;
	private boolean enterGame;

	public LoginGamesScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(Main.assets.get("background.png", Texture.class));
		
		background = new Background(backgroundTexture);
		reconnectButton = new ReconnectButton(Main.skin);
		teacherGames = new GamesOpenActor(Main.skin);
		enterGame = false;
		
		stage.addActor(background);
		stage.addActor(reconnectButton);
		stage.addActor(teacherGames);	
	}  
	public void updateGames(Array<String> games) {
		teacherGames.updateGames(games);
	}
	public GamesOpen getGames() {
		return teacherGames.getGames();
	}
	
	public void enterGame() {
		enterGame = true;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	} 
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(enterGame) {
			Main.createGameScreens();
			Main.setNewScreen(Main.calendarScreen);
			enterGame = false;
		}
		
		stage.act(delta);
		stage.draw();
	}
}
