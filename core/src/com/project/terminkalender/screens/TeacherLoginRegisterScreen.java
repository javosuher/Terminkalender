package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.Background;
import com.project.terminkalender.ReconnectButton;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.loginandregister.TeacherLoginRegisterActor;

public class TeacherLoginRegisterScreen extends AbstractScreen {
	private Background background;
	private TeacherLoginRegisterActor teacherLoginRegisterActor;
	private ReconnectButton reconnectButton;

	public TeacherLoginRegisterScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(TeacherMain.assets.get("background.png", Texture.class));
		Skin skin = TeacherMain.skin;
		
		background = new Background(backgroundTexture);
		teacherLoginRegisterActor = new TeacherLoginRegisterActor(skin);
		reconnectButton = new ReconnectButton(skin);
		
		stage.addActor(background);
		stage.addActor(teacherLoginRegisterActor);
		stage.addActor(reconnectButton);
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
