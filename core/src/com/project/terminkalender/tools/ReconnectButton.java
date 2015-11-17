package com.project.terminkalender.tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.TeacherMain;

public class ReconnectButton extends ImageButton {
	
	public ReconnectButton(Skin skin) {
		super(skin, "imageButtonReconnect");
		
		setBounds(AppMain.WIDTH - 102, 2, 100, 66);
		
		this.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				try { AppMain.reconnect(); } 
				catch(Exception exception) { TeacherMain.reconnect(); }
			}
		});
	}
	
	public void setOrangeStyle() {
		setStyle(Resources.skin.get("orangeImageButtonReconnect", ImageButtonStyle.class));
	}
	public void setDefaultStyle() {
		setStyle(Resources.skin.get("imageButtonReconnect", ImageButtonStyle.class));
	}
}
