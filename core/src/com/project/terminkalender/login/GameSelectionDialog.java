package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.tools.DialogActor;
import com.project.terminkalender.userdata.Game;

public class GameSelectionDialog extends DialogActor {
	private Game game;
	private TextField nameText, passwordText;

	public GameSelectionDialog(String title, Game game, Skin skin) {
		super(title, skin);
		
		this.game = game;
		
		TextButton enterGameButton = new TextButton("Enter", skin);
		Label nameLabel = new Label("Your name", skin);
		Label passwordLabel = new Label("Password", skin);
		nameText = new TextField("", skin);
		passwordText = new TextField("", skin);
		CheckBox showPasswordCheckBox = new CheckBox("  Show Password", skin);
		showPasswordCheckBox.right();
		
		getButtonTable().defaults().width(100).height(50);
		
		getContentTable().padTop(40);
		getContentTable().add(nameLabel).center().row();
		getContentTable().add(nameText).center().row();
		getContentTable().add(passwordLabel).center().row();
		getContentTable().add(passwordText).center().row();
		getContentTable().add(showPasswordCheckBox).center().colspan(2).center();
		getButtonTable().padTop(30);
		button(enterGameButton, "Enter");
		
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
		AppMain.user.enterGame(nameText.getText(), game.getName(), passwordText.getText());
	}
}
