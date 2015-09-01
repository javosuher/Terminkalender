package com.project.terminkalender.tools;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class ScrollWindow extends Window {
	private ScrollPane scrollTable;
	
	public ScrollWindow(String title, Skin skin) {
		super(title, skin);
	}
	public ScrollWindow(String title, Skin skin, Table table) {
		super(title, skin);
		
		setTable(skin, table);
	}
	public ScrollWindow(Skin skin, Table table) {
		super("", skin);
		
		setTable(skin, table);
	}
	
	public void setTable(Skin skin, Table table) {
		scrollTable = new ScrollPane(table, skin, "window");
		mainValues(scrollTable);
	}
	private void mainValues(ScrollPane scrollTable) {
		getTitleTable().padTop(20);
		setMovable(false);
		
		add(scrollTable).expand().fill().padTop(50);
		scrollTable.setFadeScrollBars(false);
	}
	
	public ScrollPane getScrollTable() {
		return scrollTable;
	}
}
