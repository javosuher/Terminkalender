package com.project.terminkalender.loginandregister;

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

public class TeacherLoginDialogActor extends DialogActor {
	private TeacherLoginDialog teacherLoginDialog;

	public TeacherLoginDialogActor(Skin skin) {
		super("", skin);
		teacherLoginDialog = new TeacherLoginDialog(); 

		TextButton registerButton = new TextButton("Login", skin);
		Label userLabel = new Label("Username", skin);
		Label passwordLabel = new Label("Password", skin);
		TextField userText = teacherLoginDialog.getUserText();
		final TextField passwordText = teacherLoginDialog.getPasswordText();
		CheckBox showPasswordCheckBox = new CheckBox("  Show Password", skin);
		showPasswordCheckBox.right();
		
		getButtonTable().defaults().width(100).height(50);
		
		getContentTable().padTop(40);
		getContentTable().add(userLabel);
		getContentTable().add(userText).row();
		getContentTable().add(passwordLabel);
		getContentTable().add(passwordText).row();
		getContentTable().add(showPasswordCheckBox).colspan(2).center();
		getButtonTable().padTop(50);
		button(registerButton, "Login");
		
		passwordText.setPasswordCharacter('*');
		passwordText.setPasswordMode(true);
		showPasswordCheckBox.setChecked(false);
		
		showPasswordCheckBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				passwordText.setPasswordMode(!passwordText.isPasswordMode());
			}
		});
	}
	
	protected void result(Object object) {
		teacherLoginDialog.loginTeacher();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(teacherLoginDialog.update()) {
			TeacherMain.setNewScreen(TeacherMain.teacherGamesScreen);
			TeacherMain.teacherWebSockets.askGamesTeacher(teacherLoginDialog.getUserText().getText());
			TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
			teacherGamesScreen.setTeacher(teacherLoginDialog.getUserText().getText());
			teacherLoginDialog.setEmpty();
			teacherLoginDialog.finishUpdate();
		}
	}
}
