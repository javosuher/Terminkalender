package com.project.terminkalender;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DialogActor extends Dialog {

	public DialogActor(String title, Skin skin) {
		super("", skin);
		
		Button closeButton = new Button(TeacherMain.skin.get("closeRedButton", ButtonStyle.class));
		
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		pad(20);
		
		getTitleTable().add(closeButton);
		
		closeButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				hide();
			}
		});
	}
}
