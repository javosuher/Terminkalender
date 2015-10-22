package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.Main;
import com.project.terminkalender.login.LoginActor;
import com.project.terminkalender.tools.ReconnectButton;

public class LoginScreen extends AbstractScreen {
	private Background background;
	private ReconnectButton reconnectButton;
	private LoginActor loginActor;
	private boolean changeScreen;
	
	public LoginScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(Main.assets.get("background.png", Texture.class));
		
		changeScreen = false;
		background = new Background(backgroundTexture);
		reconnectButton = new ReconnectButton(Main.skin);
		loginActor = new LoginActor(Main.skin);
		
		stage.addActor(background);
		stage.addActor(reconnectButton);
		stage.addActor(loginActor);
	}
	
	public void login() {
		changeScreen = true;
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
		
		if(changeScreen) {
			Main.setNewScreen(Main.loginGamesScreen);
			changeScreen = false;
		}
		
		stage.act(delta);
		stage.draw();
	}
}
