package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.websockets.WebSockets;

public class GamesOpen {
	private Table gamesTable;
	private Array<GameOpen> games;
	private boolean update;
	
	public GamesOpen() {
		Skin skin = TeacherMain.skin;
		
		gamesTable = new Table(skin);
		games = new Array<GameOpen>();
		update = false;
	}
	
	public void updateGames(Array<String> games) {
		this.games.clear();
		this.games.addAll(gameStringToGameArray(games));
		update = true;
	}
	private Array<GameOpen> gameStringToGameArray(Array<String> gamesString) {
		Array<GameOpen> games = new Array<GameOpen>();
		for(String game : gamesString) {
			String [] gameData = game.split(WebSockets.DATASPLIT);
			if(gameData.length < 3) {
				games.add(new GameOpen(gameData[0], gameData[1]));
			}
			else {
				Array<String> tasks = new Array<String>(gameData[2].split(WebSockets.TASKSPLIT));
				games.add(new GameOpen(gameData[0], gameData[1], tasks));
			}
		}
		return games;
	}
	public void noGames() {
		gamesTable.clear();
	}

	public Table getGamesTable() {
		return gamesTable;
	}
	
	public Array<GameOpen> getGames() {
		return games;
	}
	public GameOpen findGame(String gameName) {
		return findGame(gameName, this.games);
	}
	private GameOpen findGame(String gameName, Array<GameOpen> games) {
		for(GameOpen game : games) {
			if(game.getName().equals(gameName)) {
				return game;
			}
		}
		return new GameOpen("NoExiste", "NoExiste");
	}
	public boolean deleteGame(String gameName) {
		GameOpen game = findGame(gameName);
		return games.removeValue(game, true);
	}

	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
