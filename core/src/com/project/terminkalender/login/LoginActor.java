package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.tools.TWindow;

public class LoginActor extends Table {
	private Login login;
	private SelectBox<String> teachersSelectBox;

	public LoginActor(Skin skin) {
		super(skin);
		login = new Login();
		
		Window loginWindow = new TWindow("", skin, "window2");
		Label teacherName = new Label("Teacher", skin);
		teachersSelectBox = new SelectBox<String>(skin);
		TextButton loginButton = new TextButton("Enter", skin, "defaultBig");

		setPosition(AppMain.WIDTH / 2, 225);
		
		loginWindow.add(teacherName).row();
		loginWindow.add(teachersSelectBox).width(175).row();
		loginWindow.add(loginButton).width(175).height(100).colspan(2).padTop(40);
		add(loginWindow);
		
		loginButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				AppMain.user.login(teachersSelectBox.getSelected());
			}
		});
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(teachersSelectBox.getItems() != null && teachersSelectBox.getItems().size == 0) {
			login.askTeacherServer();
		}
		
		if(login.update()) {
			teachersSelectBox.setItems(login.getTeachers());
			login.finishUpdate();
		}
	}
}