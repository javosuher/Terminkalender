package com.project.terminkalender.loginandregister;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.project.terminkalender.tools.DialogActor;

public class TeacherRegisterDialogActor extends DialogActor {
	private TeacherRegisterDialog teacherRegisterDialog;

	public TeacherRegisterDialogActor(Skin skin) {
		super("", skin);
		teacherRegisterDialog = new TeacherRegisterDialog(); 

		TextButton registerButton = new TextButton("Register", skin);
		Label userLabel = new Label("Username", skin);
		Label passwordLabel = new Label("Password", skin);
		Label passwordRepeatLabel = new Label("Repeat password", skin);
		TextField userText = teacherRegisterDialog.getUserText();
		final TextField passwordText = teacherRegisterDialog.getPasswordText();
		final TextField passwordRepeatText = teacherRegisterDialog.getPasswordRepeatText();
		CheckBox showPasswordCheckBox = new CheckBox("  Show Password", skin);
		
		getButtonTable().defaults().width(100).height(50);
		
		getContentTable().padTop(40);
		getContentTable().add(userLabel);
		getContentTable().add(userText).row();
		getContentTable().add(passwordLabel);
		getContentTable().add(passwordText).row();
		getContentTable().add(passwordRepeatLabel);
		getContentTable().add(passwordRepeatText).row();
		getContentTable().add(showPasswordCheckBox).colspan(2).center();
		getButtonTable().padTop(50);
		button(registerButton, "Register");
		
		passwordText.setPasswordCharacter('*');
		passwordText.setPasswordMode(true);
		passwordRepeatText.setPasswordCharacter('*');
		passwordRepeatText.setPasswordMode(true);
		
		showPasswordCheckBox.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				passwordText.setPasswordMode(!passwordText.isPasswordMode());
				passwordRepeatText.setPasswordMode(!passwordRepeatText.isPasswordMode());
			}
		});
	}
	
	protected void result(Object object) {
		teacherRegisterDialog.registerTeacher();
		teacherRegisterDialog.setEmpty();
	}
}
