package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.project.terminkalender.TeacherMain;

public class Games {
	private Table gamesTable;
	
	public Games() {
		Skin skin = TeacherMain.assets.get("skins/uiskin.json", Skin.class);
		
		gamesTable = new Table(skin);
	}

	public Table getGamesTable() {
		return gamesTable;
	}
}
