package com.project.terminkalender.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;

public class DialogTextField extends Dialog {
	private static final TextField textField = new TextField("", Resources.skin);
	private static final ImageButton sendButton = new ImageButton(Resources.skin, "imageButtonArrowLeft");
	private static final ImageButton cancelButton = new ImageButton(Resources.skin, "imageButtonhideKeyboard");
	private TextFieldActor textFieldSource;
	private boolean keyboardActive;

	public DialogTextField(Skin skin) {
		super("", skin, "windowDialogTextField");
		keyboardActive = false;
		
		setMovable(false);
		setResizable(false);
		setModal(true);
		
		pad(2);
		add(cancelButton).pad(2).width(75).height(60);
		add(textField).pad(2).width(AppMain.WIDTH - 180);
		add(sendButton).pad(2).width(75).height(60);
		
		
		sendButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				keyboardActive = false;
				
				textFieldSource.setText(textField.getText());
				textField.setText("");
				hide();
				
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		cancelButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				keyboardActive = false;
				
				textField.setText("");
				hide();
				
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

	@Override
	public Dialog show(Stage stage) {
		super.show(stage);
		keyboardActive = true;
		setBounds(0, AppMain.HEIGHT - 100, AppMain.WIDTH, 100);
		textField.setText(textFieldSource.getText());
		if(textFieldSource.getText().length() > 0) {
			textField.setCursorPosition(textFieldSource.getText().length());
			textField.setSelection(0, textFieldSource.getText().length());
		}
		stage.setKeyboardFocus(textField);
		return this;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		Gdx.input.setOnscreenKeyboardVisible(keyboardActive);
	}

	public void setTextFieldSource(TextFieldActor textFieldSource) {
		this.textFieldSource = textFieldSource;
	}

	public static TextField getTextfield() {
		return textField;
	}
}
