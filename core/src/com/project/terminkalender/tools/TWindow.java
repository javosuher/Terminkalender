package com.project.terminkalender.tools;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class TWindow extends Window {

	public TWindow(String title, Skin skin) {
		super(title, skin);
		
		defaultConfiguration();
	}	
	public TWindow(String title, Skin skin, String styleName) {
		super(title, skin, styleName);
		
		defaultConfiguration();
	}
	private void defaultConfiguration() {
		padTop(50);
		setMovable(false);
	}
}
