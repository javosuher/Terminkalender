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
import com.project.terminkalender.screens.RoomScreen;
import com.project.terminkalender.screens.TeacherLoginRegisterScreen;

public class Main extends Game {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final AssetManager assets = new AssetManager();
	
	public static final int PORT = 8080;
	public static final String IP = "192.168.1.133";
	public static WebSockets webSockets;
	
	public static AbstractScreen calendarScreen, roomScreen, teacherFirstScreen;
	public static WarningDialogActor warningDialog;
	
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
		webSockets = new WebSockets("ws://"+ Main.IP +":"+ Main.PORT);
		main = this;
		
		loadAssets();
		
		warningDialog = new WarningDialogActor(assets.get("skins/uiskin.json", Skin.class));
		calendarScreen = new CalendarScreen();
		roomScreen = new RoomScreen();
		teacherFirstScreen = new TeacherLoginRegisterScreen();
		setScreen(teacherFirstScreen);
	}
	
	private void loadAssets() {
		assets.load("skins/uiskin.json", Skin.class);
		assets.load("Slot.png", Texture.class);
		assets.load("EmptySlot.png", Texture.class);
		assets.load("background.png", Texture.class);
		assets.finishLoading();
	}
	
	public static void setNewScreen(AbstractScreen newScreen) {
		main.setScreen(newScreen);
	}
	
	@Override
	public void dispose() {
		calendarScreen.dispose();
		batch.dispose();
		assets.dispose();
	}
}
