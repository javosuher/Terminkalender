package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.project.terminkalender.tools.DialogActor;

public class ExitDialog extends DialogActor {
	public static final String EXIT = "Exit";
	public static final String CANCEL = "Cancel";
	
	private boolean isShow;

	public ExitDialog(Skin skin) {
		super("", skin);
		isShow = false;
		
		Label messageLabel = new Label("Do you want to exit?", skin);
		TextButton exitButton = new TextButton("Exit", skin, "textButtonLarge");
		TextButton cancelButton = new TextButton("Cancel", skin, "textButtonLarge");
				
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		getButtonTable().defaults().width(175).height(100);
		pad(20);
				
		getContentTable().padTop(40);
		getContentTable().add(messageLabel);
		getButtonTable().padTop(50);
		button(exitButton, EXIT);
		button(cancelButton, CANCEL);
	}

	@Override
	public Dialog show(Stage stage) {
		if(!isShow) {
			super.show(stage);
			isShow = true;
		}
		return this;
	}

	@Override
	public void hide() {
		super.hide();
		isShow = false;
	}

	@Override
	protected void result(Object object) {
		if(object.equals(EXIT)) {
			Gdx.app.exit();
		}
		else if(object.equals(CANCEL)) {
			hide();
		}
	}
}
