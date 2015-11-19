package com.project.terminkalender.loginandregister;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TeacherLoginRegisterActor extends Table {

	public TeacherLoginRegisterActor(Skin skin) {
		super(skin);
		
		TextButton loginButton = new TextButton("LOGIN", skin, "defaultBig");
		TextButton registerButton = new TextButton("REGISTER", skin, "defaultBig");
		final TeacherLoginDialogActor teacherLoginDialogActor = new TeacherLoginDialogActor(skin);
		final TeacherRegisterDialogActor teacherRegisterDialogActor = new TeacherRegisterDialogActor(skin);
		
		setFillParent(true);
		bottom();
		padBottom(100);
		
		float width = 400, height = 250;
		add(loginButton).width(width).height(height).pad(10);
		add(registerButton).width(width).height(height).pad(10);
		
		loginButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				teacherLoginDialogActor.show(getStage());
			}
		});
		
		registerButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				teacherRegisterDialogActor.show(getStage());
			}
		});
	}
}
