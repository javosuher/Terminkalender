package com.project.terminkalender;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class ScrollWindow extends Window {
	
	public ScrollWindow(String title, Skin skin, Table table) {
		super(title, skin);
		
		ScrollPane scrollTable = new ScrollPane(table, skin, "window");
		
		add(scrollTable).padTop(50);
		
		getTitleTable().padTop(20);
		scrollTable.setFadeScrollBars(false);
		setMovable(false);
	}
}
