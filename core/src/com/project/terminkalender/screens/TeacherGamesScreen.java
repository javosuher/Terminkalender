package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.Background;
import com.project.terminkalender.Main;
import com.project.terminkalender.ReconnectButton;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.games.GamesActor;

public class TeacherGamesScreen extends AbstractScreen {
	private Background background;
	private String teacher;
	private GamesActor gamesActor;
	private TextButton changeToLoginRegisterButton;
	private ReconnectButton reconnectButton;

	public TeacherGamesScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(TeacherMain.assets.get("background.png", Texture.class));
		Skin skin = TeacherMain.skin;
		
		background = new Background(backgroundTexture);
		gamesActor = new GamesActor(skin);
		changeToLoginRegisterButton = new TextButton("Log out", skin);
		reconnectButton = new ReconnectButton(skin);
		
		stage.addActor(background);
		stage.addActor(gamesActor);
		stage.addActor(changeToLoginRegisterButton);
		stage.addActor(reconnectButton);
		
		changeToLoginRegisterButton.setBounds(Main.WIDTH - 108, Main.HEIGHT - 58, 100, 50);
		
		changeToLoginRegisterButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				TeacherMain.setNewScreen(TeacherMain.teacherLoginRegisterScreen);
			}
		});
	}
	
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
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
		
		stage.act(delta);
		stage.draw();
	}
}
