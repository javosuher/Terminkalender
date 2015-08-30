package com.project.terminkalender;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.login.User;
import com.project.terminkalender.screens.AbstractScreen;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.screens.LoginScreen;
import com.project.terminkalender.screens.RoomScreen;
import com.project.terminkalender.tools.WarningDialogActor;
import com.project.terminkalender.tools.kennySkin;
import com.project.terminkalender.websockets.ServerDirection;
import com.project.terminkalender.websockets.WebSockets;

public class Main extends Game {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final AssetManager assets = new AssetManager();
	public static Skin skin;
	
	public static WebSockets webSockets;
	public static AbstractScreen loginScreen, loginGamesScreen, calendarScreen, roomScreen;
	public static WarningDialogActor warningDialog;
	public static User user;
	
	public static SpriteBatch batch;
	public static Viewport viewport;
	public static OrthographicCamera camera;
	
	public static Main main;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		viewport.update(WIDTH, HEIGHT, true);
		webSockets = new WebSockets(ServerDirection.serverDirection);
		main = this;
		user = new User();
		
		loadAssets();
		
		loginScreen = new LoginScreen(viewport, batch);
		loginGamesScreen = new LoginGamesScreen(viewport, batch);
		warningDialog = new WarningDialogActor(skin);
		setScreen(loginScreen);
	}
	
	public static void createGameScreens() {
		calendarScreen = new CalendarScreen(viewport, batch);
		roomScreen = new RoomScreen(viewport, batch);
	}
	
	private void loadAssets() {
		skin = new kennySkin();
		assets.load("Slot.png", Texture.class);
		assets.load("EmptySlot.png", Texture.class);
		assets.load("background.png", Texture.class);
		assets.finishLoading();
	}
	
	public static void setNewScreen(AbstractScreen newScreen) {
		main.setScreen(newScreen);
	}
	
	public static void reconnect() {
		webSockets.connect(ServerDirection.serverDirection);
	}
	
	@Override
	public void dispose() {
		//calendarScreen.dispose();
		batch.dispose();
		assets.dispose();
	}
}
