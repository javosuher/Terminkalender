package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.project.terminkalender.Resources;
import com.project.terminkalender.tools.ScrollWindow;

public class OpenGameDialogActor extends GameDialog {
	
	public OpenGameDialogActor(Skin skin, final TeacherGame game, TextButton thisButton) {
		super(skin, game, thisButton);
		
		actionButton = new TextButton("Close Game", skin, "textButtonLargeRed");
		Label nameLabel = new Label("Name: " + game.getName(), skin);
		Label passwordLabel = new Label("Password: " + game.getPassword(), skin);
		
		Table tasksBoxTable = new Table(skin);
		ScrollWindow tasksBoxWindow = new ScrollWindow("TASKS LIST", skin, tasksBoxTable);
		List<String> tasksBox = new List<String>(Resources.skin);
		tasksBox.setItems(tasksToTaskBox(game.getTasks()));
		Table usersBoxTable = new Table(skin);
		ScrollWindow usersBoxWindow = new ScrollWindow("USERS LIST", skin, usersBoxTable);
		List<String> usersBox = new List<String>(Resources.skin);
		usersBox.setItems(game.getUsers());
		
		getButtonTable().defaults().width(175).height(100);
		
		tasksBoxTable.add(tasksBox).expand().fill();
		usersBoxTable.add(usersBox).expand().fill();
		
		getContentTable().padTop(40);
		getContentTable().add(nameLabel).colspan(2).row();
		getContentTable().add(passwordLabel).colspan(2).padBottom(10).row();
		getContentTable().add(tasksBoxWindow).padTop(8).width(300).height(250).padRight(15);
		getContentTable().add(usersBoxWindow).padTop(8).width(300).height(250);
		getButtonTable().padTop(30);
		button(actionButton, "OK");
	}
	
	protected void result(Object object) {
		game.closeGamePetition();
	}
}
