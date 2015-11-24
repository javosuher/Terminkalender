package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.chat.ChatActor;
import com.project.terminkalender.chat.RoomActor;
import com.project.terminkalender.tools.ReconnectButton;

public class ChatScreen extends AbstractScreen {
	private Background background;
	private RoomActor roomActor;
	private ChatActor chatActor;
	private TextButton changeToCalendarButton;
	private ReconnectButton reconnectButton;
	private boolean closeGame;
	
	public ChatScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(Resources.assets.get("background.png", Texture.class));
		
		background = new Background(backgroundTexture);
		chatActor = new ChatActor(Resources.skin);
		roomActor = new RoomActor(Resources.skin, chatActor);
		changeToCalendarButton = new TextButton("Kalender", Resources.skin);
		reconnectButton = Resources.reconnectButton;
		closeGame = false;
		
		stage.addActor(background);
		stage.addActor(roomActor);
		stage.addActor(chatActor);
		stage.addActor(changeToCalendarButton);
		
		changeToCalendarButton.setBounds(AppMain.WIDTH - 137, AppMain.HEIGHT - 68, 135, 66);
		
		changeToCalendarButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				AppMain.setNewScreen(AppMain.calendarScreen);
			}
		});
		
		stage.addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					Resources.exitDialogGame.show(stage);
				}
				return true;
			}
		});
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.addActor(reconnectButton);
		roomActor.setFirstChat();
	}
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		reconnectButton.remove();
		roomActor.setDefaultChat();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(closeGame) {
			AppMain.setNewScreen(AppMain.loginScreen);
			Resources.warningDialog.show("Game Closed", AppMain.loginGamesScreen.getStage());
			CalendarScreen calendarScreen = (CalendarScreen) AppMain.calendarScreen;
			calendarScreen.closeGameFalse();
			closeGame = false;
		}
		
		stage.act(delta);
		stage.draw();
	}
	
	public void closeGame() {
		closeGame = true;
	}
	public void closeGameFalse() {
		closeGame = false;
	}
}
