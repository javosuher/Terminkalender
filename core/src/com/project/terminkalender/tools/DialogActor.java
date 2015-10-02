package com.project.terminkalender.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.terminkalender.Main;
import com.project.terminkalender.TeacherMain;

public class DialogActor extends Dialog {

	public DialogActor(String title, Skin skin) {
		super("", skin, "windowDialog");
		
		Button closeButton;
		try { closeButton = new Button(Main.skin.get("closeRedButton", ButtonStyle.class)); }
		catch(Exception exception) { closeButton = new Button(TeacherMain.skin.get("closeRedButton", ButtonStyle.class)); }
		
		
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
