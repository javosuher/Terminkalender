package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.screens.TeacherGamesScreen;
import com.project.terminkalender.tools.DialogActor;

public class CreateGameDialogActor extends DialogActor {
	private CreateGameDialog createGameDialog;

	public CreateGameDialogActor(Skin skin) {
		super("", skin);
		createGameDialog = new CreateGameDialog();
		
		TextButton createGameButton = new TextButton("Create Game", skin);
		Label gameNameLabel = new Label("Game Name", skin);
		Label gamePasswordLabel = new Label("Password", skin);
		Label gamePasswordRepeatLabel = new Label("Repeat password", skin);
		TextField gameNameText = createGameDialog.getGameNameText();
		final TextField gamePasswordText = createGameDialog.getGamePasswordText();
		final TextField gamePasswordRepeatText = createGameDialog.getGamePasswordRepeatText();
		CheckBox showPasswordCheckBox = new CheckBox("  Show Password", skin);
		
		getButtonTable().defaults().width(150).height(75);
		
		getContentTable().padTop(40);
		getContentTable().add(gameNameLabel);
		getContentTable().add(gameNameText).row();
		getContentTable().add(gamePasswordLabel);
		getContentTable().add(gamePasswordText).row();
		getContentTable().add(gamePasswordRepeatLabel);
		getContentTable().add(gamePasswordRepeatText).row();
		getContentTable().add(showPasswordCheckBox).colspan(2).center();
		getButtonTable().padTop(50);
		button(createGameButton, "Register");
		
		gamePasswordText.setPasswordCharacter('*');
		gamePasswordText.setPasswordMode(true);
		gamePasswordRepeatText.setPasswordCharacter('*');
		gamePasswordRepeatText.setPasswordMode(true);
		
		showPasswordCheckBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				gamePasswordText.setPasswordMode(!gamePasswordText.isPasswordMode());
				gamePasswordRepeatText.setPasswordMode(!gamePasswordRepeatText.isPasswordMode());
			}
		});
	}
	
	protected void result(Object object) {
		createGameDialog.createGame();
		createGameDialog.setEmpty();
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.askGamesTeacher(teacherGamesScreen.getTeacher());
	}
}
