package com.project.terminkalender;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DialogActor extends Dialog {

	public DialogActor(String title, Skin skin) {
		super("", skin, "dialog");
		
		TextButton closeButton = new TextButton("X", skin, "default");
		
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		pad(20);
		
		closeButton.setColor(Color.RED);
		getTitleTable().add(closeButton).width(30);
		
		closeButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				hide();
			}
		});
	}
}
