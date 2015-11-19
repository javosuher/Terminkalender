package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.Resources;
import com.project.terminkalender.loginandregister.TeacherLoginRegisterActor;
import com.project.terminkalender.tools.ReconnectButton;

public class TeacherLoginRegisterScreen extends AbstractScreen {
	private Background background;
	private Title logo; 
	private TeacherLoginRegisterActor teacherLoginRegisterActor;
	private ExitDialog exitDialog;
	private ReconnectButton reconnectButton;

	public TeacherLoginRegisterScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(Resources.assets.get("background.png", Texture.class));
		
		background = new Background(backgroundTexture);
		logo = new Title();
		teacherLoginRegisterActor = new TeacherLoginRegisterActor(Resources.skin);
		exitDialog = new ExitDialog(Resources.skin);
		reconnectButton = Resources.reconnectButton;
		
		stage.addActor(background);
		stage.addActor(logo);
		stage.addActor(teacherLoginRegisterActor);
		
		stage.addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					exitDialog.show(stage);
				}
				return true;
			}
		});
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.addActor(reconnectButton);
	} 
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		reconnectButton.remove();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
}
