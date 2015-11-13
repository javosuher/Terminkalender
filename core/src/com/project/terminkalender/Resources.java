package com.project.terminkalender;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.project.terminkalender.tools.WarningDialogActor;
import com.project.terminkalender.tools.KennySkin;
import com.project.terminkalender.tools.ReconnectButton;

final public class Resources {
	public static final AssetManager assets = new AssetManager();
	public static WarningDialogActor warningDialog;
	public static ReconnectButton reconnectButton;
	public static Skin skin;
	
	public static void load(String application) {
		assets.load("background.png", Texture.class);
		assets.load("logo.png", Texture.class);
		assets.load("cross.png", Texture.class);
		assets.load("redCircle.png", Texture.class);
		assets.load("folderIcon.png", Texture.class);
		assets.load("arrowLeft.png", Texture.class);
		assets.load("return.png", Texture.class);
		assets.load("reconnect.png", Texture.class);
		assets.finishLoading();
		skin = new KennySkin(application);
		warningDialog = new WarningDialogActor(skin);
		reconnectButton = new ReconnectButton(skin);
	}
}
