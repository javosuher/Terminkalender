package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.screens.TeacherGamesScreen;

public class CreateGameDialog {
	private TextField gameNameText, gamePasswordText, gamePasswordRepeatText;
	
	public CreateGameDialog() {
		Skin skin = TeacherMain.assets.get("skins/uiskin.json", Skin.class);
		
		gameNameText = new TextField("", skin);
		gamePasswordText = new TextField("", skin);
		gamePasswordRepeatText = new TextField("", skin);
	}
	
	public void createGame() {
		if(gameNameText.getText().equals("") || gamePasswordText.getText().equals("") || gamePasswordRepeatText.getText().equals("")) {
			TeacherMain.warningDialog.show("You must fill the gaps", TeacherMain.teacherGamesScreen.getStage());
		}
		else if(!gamePasswordText.getText().equals(gamePasswordRepeatText.getText())) {
			TeacherMain.warningDialog.show("Passwords must be the same", TeacherMain.teacherGamesScreen.getStage());
		}
		else {
			TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
			TeacherMain.teacherWebSockets.createGame(gameNameText.getText().toLowerCase(), teacherGamesScreen.getTeacher().toLowerCase(), gamePasswordText.getText().toLowerCase());
		}
	}
	
	public void setEmpty() {
		gameNameText.setText("");
		gamePasswordText.setText("");
		gamePasswordRepeatText.setText("");
	}
	
	public TextField getGameNameText() {
		return gameNameText;
	}
	public TextField getGamePasswordText() {
		return gamePasswordText;
	}
	public TextField getGamePasswordRepeatText() {
		return gamePasswordRepeatText;
	}
}
