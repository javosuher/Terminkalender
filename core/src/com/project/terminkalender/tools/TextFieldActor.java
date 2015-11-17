package com.project.terminkalender.tools;


import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.project.terminkalender.Resources;
import com.project.terminkalender.chat.ChatActor;

public class TextFieldActor extends TextField {
	private static final DialogTextField dialog = new DialogTextField(Resources.skin);
	private TextFieldActor thisTextField;
	

	public TextFieldActor(String text, Skin skin) {
		super(text, skin);
		isInAndroid();
	}
	public TextFieldActor(String text, Skin skin, ChatActor chatActor) {
		super(text, skin);
		dialog.setChatActor(chatActor);
		isInAndroid();
	}
	
	private void isInAndroid() {
		//if(Gdx.app.getType() == ApplicationType.Android) {
			loadSoftKeyboard();
		//}
	}
	private void loadSoftKeyboard() {
		thisTextField  = this;
		setOnscreenKeyboard(new OnscreenKeyboard() {
			
			@Override
			public void show(boolean visible) {
				dialog.setTextFieldSource(thisTextField);
				dialog.show(getStage());
			}
	    });
	}
}