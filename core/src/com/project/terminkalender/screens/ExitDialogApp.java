package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ExitDialogApp extends ExitDialog {

	public ExitDialogApp(Skin skin) {
		super(skin, "Willst du das Spiel beenden?");
	}

	@Override
	public void exit() {
		Gdx.app.exit();
	}
}
