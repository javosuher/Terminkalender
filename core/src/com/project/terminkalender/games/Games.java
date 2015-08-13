package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.TeacherWebSockets;
import com.project.terminkalender.screens.TeacherGamesScreen;

public class Games {
	private Table gamesTable;
	private Array<Game> games, openGames;
	private boolean update;
	
	public Games() {
		Skin skin = TeacherMain.skin;
		
		gamesTable = new Table(skin);
		TeacherMain.teacherWebSockets.setGames(this);
		games = new Array<Game>();
		openGames = new Array<Game>();
		update = false;
	}
	
	public void updateGames(Array<String> games, Array<String> openGames) {
		this.games.clear();
		this.openGames.clear();
		this.games.addAll(gameStringToGameArray(games));
		this.openGames.addAll(gameStringToGameArray(openGames));
		update = true;
	}
	private Array<Game> gameStringToGameArray(Array<String> gamesString) {
		Array<Game> games = new Array<Game>();
		for(String game : gamesString) {
			String [] gameData = game.split(TeacherWebSockets.DATASPLIT);
			if(gameData.length < 3) {
				games.add(new Game(gameData[0], gameData[1]));
			}
			else {
				Array<String> tasks = new Array<String>(gameData[2].split(TeacherWebSockets.TASKSPLIT));
				games.add(new Game(gameData[0], gameData[1], tasks));
			}
		}
		return games;
	}
	public void noGames() {
		gamesTable.clear();
	}
	
	public void askGames() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.askGamesTeacher(teacherGamesScreen.getTeacher());
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
	public Game findOpenGame(String gameName) {
		return findGame(gameName, this.openGames);
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
	public boolean deleteOpenGame(String gameName) {
		Game game = findOpenGame(gameName);
		return openGames.removeValue(game, true);
	}
	public Array<Game> getOpenGames() {
		return openGames;
	}

	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
