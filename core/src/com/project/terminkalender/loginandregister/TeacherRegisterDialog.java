package com.project.terminkalender.loginandregister;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.project.terminkalender.TeacherMain;

public class TeacherRegisterDialog {
	private TextField userText, passwordText, passwordRepeatText;
	

	public TeacherRegisterDialog() {
		Skin skin = TeacherMain.skin;
		
		userText = new TextField("", skin);
		passwordText = new TextField("", skin);
		passwordRepeatText = new TextField("", skin);
	}
	
	public void registerTeacher() {
		if(userText.getText().equals("") || passwordText.getText().equals("") || passwordRepeatText.getText().equals("")) {
			TeacherMain.warningDialog.show("You must fill the gaps", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else if(!passwordText.getText().equals(passwordRepeatText.getText())) {
			TeacherMain.warningDialog.show("Passwords must be the same", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else {
			TeacherMain.teacherWebSockets.registerTeacher(userText.getText().toLowerCase(), passwordText.getText().toLowerCase());
		}
	}
	
	public void setEmpty() {
		userText.setText("");
		passwordText.setText("");
		passwordRepeatText.setText("");
	}
	
	public TextField getUserText() {
		return userText;
	}
	public TextField getPasswordText() {
		return passwordText;
	}
	public TextField getPasswordRepeatText() {
		return passwordRepeatText;
	}
}
