package com.project.terminkalender.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.project.terminkalender.AppMain;

public class ExitDialogGame  extends ExitDialog {

	public ExitDialogGame(Skin skin) {
		super(skin, "Do you want to exit the Game?");	
	}

	@Override
	public void exit() {
		AppMain.setNewScreen(AppMain.loginGamesScreen);
		AppMain.setFalseLoadGameScreens();
	}
}
