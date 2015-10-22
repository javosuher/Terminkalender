package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.userdata.Game;
import com.project.terminkalender.websockets.WebSockets;

public class GamesOpen {
	private Table gamesTable;
	private Array<Game> games;
	private boolean update;
	
	public GamesOpen() {
		Skin skin = TeacherMain.skin;
		
		gamesTable = new Table(skin);
		games = new Array<Game>();
		update = false;
	}
	
	public void updateGames(Array<String> games) {
		this.games.clear();
		this.games.addAll(gameStringToGameArray(games));
		update = true;
	}
	private Array<Game> gameStringToGameArray(Array<String> gamesString) {
		Array<Game> games = new Array<Game>();
		for(String game : gamesString) {
			String [] gameData = game.split(WebSockets.DATASPLIT);
			if(gameData.length < 3) {
				games.add(new Game(gameData[0], gameData[1]));
			}
			else {
				Array<String> tasks = new Array<String>(gameData[2].split(WebSockets.TASKSPLIT));
				games.add(new Game(gameData[0], gameData[1], tasks));
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
	
	public Array<Game> getGames() {
		return games;
	}
	public Game findGame(String gameName) {
		return findGame(gameName, this.games);
	}
	private Game findGame(String gameName, Array<Game> games) {
		for(Game game : games) {
			if(game.getName().equals(gameName)) {
				return game;
			}
		}
		return new Game("NoExiste", "NoExiste");
	}
	public boolean deleteGame(String gameName) {
		Game game = findGame(gameName);
		return games.removeValue(game, true);
	}

	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
