package com.project.terminkalender.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.chat.ChatActor;

public class DialogTextField extends Dialog {
	private static final TextField textField = new TextField("", Resources.skin);
	private static final ImageButton sendButton = new ImageButton(Resources.skin, "imageButtonArrowLeft");
	private static final ImageButton cancelButton = new ImageButton(Resources.skin, "imageButtonhideKeyboard");
	private TextField textFieldSource;
	private boolean keyboardActive;
	
	private ChatActor chatActor;
	private boolean isInChat;

	public DialogTextField(Skin skin) {
		super("", skin, "windowDialogTextField");
		keyboardActive = false;
		isInChat = false;
		
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
				sendText();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		cancelButton.addListener(new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				cancelText();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.ENTER) {
					sendText();
				}
				return true;
			}
		});
	}
	
	private void sendText() {
		textFieldSource.setText(textField.getText());
		if(isInChat && AppMain.chatScreen.getStage() == getStage()) {
			chatActor.sendMessageActor();
			getStage().setKeyboardFocus(textField);
		}
		else {
			keyboardActive = false;
			hide();
		}
		textField.setText("");
	}
	private void cancelText() {
		keyboardActive = false;
		textField.setText("");
		hide();
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

	public void setTextFieldSource(TextField textFieldSource) {
		this.textFieldSource = textFieldSource;
	}

	public static TextField getTextfield() {
		return textField;
	}
	
	public void setChatActor(ChatActor chatActor) {
		this.chatActor = chatActor;
		isInChat = true;
	}
}