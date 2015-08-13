package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.project.terminkalender.DialogActor;

public class GameDialog extends DialogActor {
	protected Game game;
	protected TextButton thisButton, actionButton;
	
	public GameDialog(Skin skin, final Game game, TextButton thisButton) {
		super("", skin);
		this.game = game;
		this.thisButton = thisButton;
	}
}

