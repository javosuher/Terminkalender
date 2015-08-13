package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OpenGameDialog extends GameDialog {
	
	public OpenGameDialog(Skin skin, final Game game, TextButton thisButton) {
		super(skin, game, thisButton);
		
		actionButton = new TextButton("Close Game", skin);
		button(actionButton, "OK");
		
		getButtonTable().padTop(50);
	}
}
