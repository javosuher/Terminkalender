package com.project.terminkalender;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.project.terminkalender.tools.WarningDialogActor;
import com.project.terminkalender.tools.kennySkin;

final public class Resources {
	public static final AssetManager assets = new AssetManager();
	public static WarningDialogActor warningDialog;
	public static Skin skin;
	
	public static void load() {
		assets.load("background.png", Texture.class);
		assets.load("folderIcon.png", Texture.class);
		assets.finishLoading();
		skin = new kennySkin();
		warningDialog = new WarningDialogActor(skin);
	}
}
