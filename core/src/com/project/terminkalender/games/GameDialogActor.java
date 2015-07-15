package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.project.terminkalender.DialogActor;

public class GameDialogActor extends DialogActor {
	private Game game;
	
	public GameDialogActor(Skin skin, final Game game) {
		super("", skin);
		this.game = game;
		
		TextButton exitButton = new TextButton("Apply Changes", skin);
		Label nameLabel = new Label("Game name: ", skin);
		Label nameGameLabel = new Label(game.getName(), skin);
		Label passwordLabel = new Label("Password: ", skin);
		final Label passwordGameLabel = new Label(game.getPassword(), skin);
		Label newPasswordLabel = new Label("New Password: ", skin);
		final TextField newPasswordText = new TextField("", skin);
		TextButton newPasswordButton = new TextButton("Update", skin);
		Label tasksLabel = new Label("Tasks: ", skin);
		final TextField tasksText = new TextField("", skin);
		TextButton addTaskButton = new TextButton("Add", skin);
		TextButton deleteTaskButton = new TextButton("Delete", skin);
		final List<String> tasksBox = new List<String>(skin);
		ScrollPane taskBoxscroll = new ScrollPane(tasksBox, skin);
		tasksBox.setItems(game.getTasks());
		
		getButtonTable().defaults().width(150).height(50);
		taskBoxscroll.setFlickScroll(false);
		taskBoxscroll.setFadeScrollBars(false);
		
		getContentTable().padTop(40);
		getContentTable().add(nameLabel);
		getContentTable().add(nameGameLabel).row();
		getContentTable().add(passwordLabel);
		getContentTable().add(passwordGameLabel).row();
		getContentTable().add(newPasswordLabel);
		getContentTable().add(newPasswordText).row();
		getContentTable().add(newPasswordButton).width(75).height(30).colspan(2).padTop(10).padBottom(10).row();
		getContentTable().pad(40);
		getContentTable().add(tasksLabel);
		getContentTable().add(tasksText).row();
		getContentTable().add(addTaskButton).width(75).height(30).padTop(5).padBottom(5);
		getContentTable().add(deleteTaskButton).width(75).height(30).padTop(5).padBottom(5).row();
		getContentTable().add(taskBoxscroll).width(300).height(100).colspan(2).padTop(10);
		button(exitButton, "OK");
		
		newPasswordButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setPassword(newPasswordText.getText());
				passwordGameLabel.setText(game.getPassword());
			}
		});
		addTaskButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				String task = tasksText.getText();
				tasksText.setText("");
				game.addTask(task);
				tasksBox.setItems(game.getTasks());
			} 
		});
		deleteTaskButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				String task = tasksBox.getSelected();
				game.eraseTask(task);
				tasksBox.setItems(game.getTasks());
			}
		});
	}
	
	protected void result(Object object) {
		game.update();
	}
}
