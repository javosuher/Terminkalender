package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.project.terminkalender.Main;
import com.project.terminkalender.tools.TWindow;

public class LoginActor extends Table {

	public LoginActor(Skin skin) {
		super(skin);
		
		Window loginWindow = new TWindow("", skin, "window2");
		Label teacherName = new Label("Teacher", skin);
		final TextField teacherNameText = new TextField("", skin);
		TextButton loginButton = new TextButton("Enter", skin);
		
		setFillParent(true);
		
		loginWindow.add(teacherName).row();
		loginWindow.add(teacherNameText).row();
		loginWindow.add(loginButton).width(100).height(50).colspan(2).padTop(40);
		add(loginWindow).center();
		
		loginButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				Main.user.login(teacherNameText.getText());
			}
		});
	}
}