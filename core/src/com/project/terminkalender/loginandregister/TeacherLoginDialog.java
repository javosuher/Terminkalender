package com.project.terminkalender.loginandregister;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.project.terminkalender.Main;

public class TeacherLoginDialog {
	private TextField userText, passwordText;
	

	public TeacherLoginDialog() {
		Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
		
		userText = new TextField("", skin);
		passwordText = new TextField("", skin);
	}
	
	public void loginTeacher() {
		if(userText.getText().equals("") || passwordText.getText().equals("")) {
			Main.warningDialog.show("You must fill the gaps", Main.teacherFirstScreen.getStage());
		}
		else {
			Main.webSockets.loginTeacher(userText.getText(), passwordText.getText());
		}
	}
	
	public void setEmpty() {
		userText.setText("");
		passwordText.setText("");
	}
	
	public TextField getUserText() {
		return userText;
	}
	public TextField getPasswordText() {
		return passwordText;
	}
}
