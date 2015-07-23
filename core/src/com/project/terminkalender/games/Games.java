package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.TeacherWebSockets;

public class Games {
	private Table gamesTable;
	private Array<Game> games;
	private boolean update;
	
	public Games() {
		Skin skin = TeacherMain.assets.get("skins/uiskin.json", Skin.class);
		
		gamesTable = new Table(skin);
		TeacherMain.teacherWebSockets.setGames(this);
		games = new Array<Game>();
		update = false;
	}
	
	public void updateGames(Array<String> games) {
		this.games.clear();
		for(String game : games) {
			String [] gameData = game.split(TeacherWebSockets.DATASPLIT);
			if(gameData.length < 3) {
				this.games.add(new Game(gameData[0], gameData[1]));
			}
			else {
				Array<String> tasks = new Array<String>(gameData[2].split(TeacherWebSockets.TASKSPLIT));
				this.games.add(new Game(gameData[0], gameData[1], tasks));
			}
		}
		update = true;
	}
	public void noGames() {
		gamesTable.clear();
	}

	public Table getGamesTable() {
		return gamesTable;
	}
	
	public Array<Game> getGames() {
		return games;
	}

	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
