package com.project.terminkalender.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.TeacherMain;

public class ReconnectButton extends TextButton {
	
	public ReconnectButton(Skin skin) {
		super("Reconnect", skin);
		
		setBounds(AppMain.WIDTH - 118, 8, 110, 50);
		
		this.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				try { AppMain.reconnect(); } 
				catch(Exception exception) { TeacherMain.reconnect(); }
			}
		});
	}
}
