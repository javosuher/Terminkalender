package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OpenGameDialog extends GameDialog {
	
	public OpenGameDialog(Skin skin, final Game game, TextButton thisButton) {
		super(skin, game, thisButton);
		
		actionButton = new TextButton("Close Game", skin);
		Label nameLabel = new Label("Name: " + game.getName(), skin);
		Label passwordLabel = new Label("Password: " + game.getPassword(), skin);
		
		getButtonTable().defaults().width(150).height(50);
		
		getContentTable().padTop(40);
		getContentTable().add(nameLabel).padBottom(10).row();
		getContentTable().add(passwordLabel);
		getButtonTable().padTop(50);
		button(actionButton, "OK");
	}
	
	protected void result(Object object) {
		
	}
}
