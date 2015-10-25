package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.chat.ChatActor;
import com.project.terminkalender.chat.RoomActor;

public class ChatScreen extends AbstractScreen {
	private Background background;
	private RoomActor roomActor;
	private ChatActor chatActor;
	private TextButton changeToCalendarButton;
	
	public ChatScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(Resources.assets.get("background.png", Texture.class));
		
		background = new Background(backgroundTexture);
		Table table = new Table(Resources.skin);
		chatActor = new ChatActor(Resources.skin);
		roomActor = new RoomActor(Resources.skin, chatActor);
		changeToCalendarButton = new TextButton("Calendar", Resources.skin);
		
		table.add(roomActor);
		table.add(chatActor);
		table.add(changeToCalendarButton).top().width(100).height(50).pad(8);
		
		table.setFillParent(true);
		
		stage.addActor(background);
		stage.addActor(table);
		
		changeToCalendarButton.setBounds(AppMain.WIDTH - 108, 8, 100, 50);
		
		changeToCalendarButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				AppMain.setNewScreen(AppMain.calendarScreen);
			}
		});
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
