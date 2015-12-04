package com.project.terminkalender.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.project.terminkalender.AppMain;

public class ExitDialogGame  extends ExitDialog {

	public ExitDialogGame(Skin skin) {
		super(skin, "Willst du das Spiel beenden?");	
	}

	@Override
	public void exit() {
		AppMain.setNewScreen(AppMain.loginGamesScreen);
		AppMain.setFalseLoadGameScreens();
	}
}
