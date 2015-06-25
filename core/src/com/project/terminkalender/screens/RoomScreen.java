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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.Background;
import com.project.terminkalender.Main;
import com.project.terminkalender.chat.Chat;
import com.project.terminkalender.chat.ChatActor;
import com.project.terminkalender.chat.RoomActor;

public class RoomScreen extends AbstractScreen {
	private Background background;
	private RoomActor roomActor;
	private TextButton changeToCalendarButton;
	
	private Array<ChatScreen> chatScreens;
	
	public RoomScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(Main.assets.get("background.png", Texture.class));
		Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
		
		background = new Background(backgroundTexture);
		roomActor = new RoomActor(skin);
		changeToCalendarButton = new TextButton("Calendar", skin);
		chatScreens = new Array<ChatScreen>();
		
		stage.addActor(background);
		stage.addActor(roomActor);
		stage.addActor(changeToCalendarButton);
		
		changeToCalendarButton.setBounds(Main.WIDTH - 108, Main.HEIGHT - 58, 100, 50);
		
		changeToCalendarButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				Main.setNewScreen(Main.calendarScreen);
			}
		});
	}
	
	public void addChatScreen(ChatActor chat) {
		chatScreens.add(new ChatScreen(viewport, batch, chat));
	}
	
	public void chatScreensClear() {
		chatScreens.clear();
	}
	
	public Array<ChatScreen> getChatScreens() {
		return chatScreens;
	}
	public ChatScreen getChatScreen(String chatUser) {
		for(ChatScreen chatScreen : chatScreens) {
			if(chatUser.equals(chatScreen.getChatUser()))
				return chatScreen;
		}
		return new ChatScreen(viewport, batch, new ChatActor(new Skin(), new Chat("Usuario no encontrado")));
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
