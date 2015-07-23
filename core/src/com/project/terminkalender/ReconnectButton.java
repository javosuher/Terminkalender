package com.project.terminkalender;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ReconnectButton extends TextButton {
	
	public ReconnectButton(Skin skin) {
		super("Reconnect", skin);
		
		setBounds(Main.WIDTH - 108, 8, 100, 50);
		
		this.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				TeacherMain.reconnect();
			}
		});
	}
}
