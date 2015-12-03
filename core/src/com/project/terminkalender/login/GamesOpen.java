package com.project.terminkalender.login;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Resources;
import com.project.terminkalender.games.GameDialog;
import com.project.terminkalender.games.TeacherGame;
import com.project.terminkalender.userdata.Game;
import com.project.terminkalender.userdata.Task;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class GamesOpen {
	private Table gamesTable;
	private Array<Game> games;
	private boolean update;
	
	public GamesOpen() {
		Skin skin = Resources.skin;
		
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
			String [] gameData = game.split(TeacherWebSockets.DATASPLIT);
			if(gameData.length < 3) {
				games.add(new TeacherGame(gameData[0], gameData[1]));
			}
			else if(gameData.length < 4) {
				Array<String> tasksArray = new Array<String>(gameData[2].split(TeacherWebSockets.TASKSPLIT));
				Array<Task> tasks = stringTasksToTasks(tasksArray);
				games.add(new TeacherGame(gameData[0], gameData[1], tasks));
			}
			else {
				if(gameData[2].equals("")) {
					Array<String> usersArray = new Array<String>(gameData[3].split(TeacherWebSockets.SPLIT));
					Array<String> users = stringUsersTousers(usersArray);
					games.add(new TeacherGame(gameData[0], gameData[1], new Array<Task>(), users));
				}
				else {
					Array<String> tasksArray = new Array<String>(gameData[2].split(TeacherWebSockets.TASKSPLIT));
					Array<String> usersArray = new Array<String>(gameData[3].split(TeacherWebSockets.SPLIT));
					Array<String> users = stringUsersTousers(usersArray);
					Array<Task> tasks = stringTasksToTasks(tasksArray);
					games.add(new Game(gameData[0], gameData[1], tasks, users));
				}
			}
		}
		return games;
	}
	private Array<Task> stringTasksToTasks(Array<String> tasksArray) {
		Array<Task> tasks = new Array<Task>();
		for(String task : tasksArray) {
			String [] taskSplit = task.split(TeacherWebSockets.TASKFIELDPLIT);
			Array<String> whatArray = new Array<String>();
			if(taskSplit.length > 2 && !taskSplit[2].equals("")) {
				whatArray = GameDialog.addSecondaryArray(taskSplit[2]);
			}
			Array<String> whereArray = new Array<String>();
			if(taskSplit.length > 3 && !taskSplit[3].equals("")) {
				whereArray = GameDialog.addSecondaryArray(taskSplit[3]);
			}
			
			Task newTask = new Task(taskSplit[0], taskSplit[1], whatArray, whereArray);
			tasks.add(newTask);
		}
		return tasks;
	}
	private Array<String> stringUsersTousers(Array<String> usersArray) {
		Array<String> users = new Array<String>();
		for(String user : usersArray) {
			users.add(user);
		}
		return users;
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
