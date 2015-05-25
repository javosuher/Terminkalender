package com.project.terminkalender;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.screens.AbstractScreen;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.screens.ChatScreen;

public class Main extends Game {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final AssetManager assets = new AssetManager();
	
	public static final int PORT = 8080;
	public static final String IP = "192.168.1.131";
	public static WebSockets webSockets;
	
	public static AbstractScreen calendarScreen, chatScreen;
	
	public static SpriteBatch batch;
	public static Viewport viewport;
	public static OrthographicCamera camera;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		viewport.update(WIDTH, HEIGHT, true);
		webSockets = new WebSockets("ws://"+ Main.IP +":"+ Main.PORT);
		
		loadAssets();
		
		calendarScreen = new CalendarScreen(this);
		chatScreen = new ChatScreen(this);
		setScreen(chatScreen);
	}
	
	private void loadAssets() {
		assets.load("skins/uiskin.json", Skin.class);
		assets.load("Slot.png", Texture.class);
		assets.load("EmptySlot.png", Texture.class);
		assets.load("background.png", Texture.class);
		assets.finishLoading();
	}
	
	@Override
	public void dispose() {
		calendarScreen.dispose();
		batch.dispose();
		assets.dispose();
	}
}
