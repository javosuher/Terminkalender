package com.project.terminkalender.loginandregister;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.project.terminkalender.TeacherMain;

public class TeacherLoginDialog {
	private TextField userText, passwordText;
	private boolean update;
	

	public TeacherLoginDialog() {
		Skin skin = TeacherMain.assets.get("skins/uiskin.json", Skin.class);
		
		userText = new TextField("", skin);
		passwordText = new TextField("", skin);
		TeacherMain.teacherWebSockets.setTeacherLoginDialog(this);
		update = false;
	}
	
	public void loginTeacher() {
		if(userText.getText().equals("") || passwordText.getText().equals("")) {
			TeacherMain.warningDialog.show("You must fill the gaps", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else {
			TeacherMain.teacherWebSockets.loginTeacher(userText.getText().toLowerCase(), passwordText.getText().toLowerCase());
		}
	}
	
	public void loginSuccess() {
		update = true;
	}
	
	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
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
