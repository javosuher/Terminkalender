package com.project.terminkalender.tools;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class ScrollWindow extends Window {
	
	public ScrollWindow(String title, Skin skin, Table table) {
		super(title, skin);
		
		ScrollPane scrollTable = new ScrollPane(table, skin, "window");
		
		getTitleTable().padTop(20);
		setMovable(false);
		
		add(scrollTable).padTop(50);
		scrollTable.setFadeScrollBars(false);
	}	
}
