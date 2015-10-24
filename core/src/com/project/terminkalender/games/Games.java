package com.project.terminkalender.games;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.screens.TeacherGamesScreen;
import com.project.terminkalender.userdata.Task;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class Games {
	private Table gamesTable;
	private Array<TeacherGame> games, openGames;
	private boolean update;
	
	public Games() {
		Skin skin = TeacherMain.skin;
		
		gamesTable = new Table(skin);
		TeacherMain.teacherWebSockets.setGames(this);
		games = new Array<TeacherGame>();
		openGames = new Array<TeacherGame>();
		update = false;
	}
	
	public void updateGames(Array<String> games, Array<String> openGames) {
		this.games.clear();
		this.openGames.clear();
		this.games.addAll(gameStringToGameArray(games));
		this.openGames.addAll(gameStringToGameArray(openGames));
		update = true;
	}
	private Array<TeacherGame> gameStringToGameArray(Array<String> gamesString) {
		Array<TeacherGame> games = new Array<TeacherGame>();
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
					Array<String> usersArray = new Array<String>(gameData[3].split(TeacherWebSockets.TASKSPLIT));
					Array<String> users = stringUsersTousers(usersArray);
					games.add(new TeacherGame(gameData[0], gameData[1], new Array<Task>(), users));
				}
				else {
					Array<String> tasksArray = new Array<String>(gameData[2].split(TeacherWebSockets.TASKSPLIT));
					Array<String> usersArray = new Array<String>(gameData[3].split(TeacherWebSockets.TASKSPLIT));
					Array<String> users = stringUsersTousers(usersArray);
					Array<Task> tasks = stringTasksToTasks(tasksArray);
					games.add(new TeacherGame(gameData[0], gameData[1], tasks, users));
				}
			}
		}
		return games;
	}
	private Array<Task> stringTasksToTasks(Array<String> tasksArray) {
		Array<Task> tasks = new Array<Task>();
		for(String task : tasksArray) {
			String [] taskSplit = task.split(TeacherWebSockets.TASKLIMITSPLIT);
			Task newTask = new Task(taskSplit[0], taskSplit[1]);
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
	
	public void askGames() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.askGamesTeacher(teacherGamesScreen.getTeacher());
	}

	public Table getGamesTable() {
		return gamesTable;
	}
	
	public Array<TeacherGame> getGames() {
		return games;
	}
	public TeacherGame findGame(String gameName) {
		return findGame(gameName, this.games);
	}
	public TeacherGame findOpenGame(String gameName) {
		return findGame(gameName, this.openGames);
	}
	private TeacherGame findGame(String gameName, Array<TeacherGame> games) {
		for(TeacherGame game : games) {
			if(game.getName().equals(gameName)) {
				return game;
			}
		}
		return new TeacherGame("NoExiste", "NoExiste");
	}
	public boolean deleteGame(String gameName) {
		TeacherGame game = findGame(gameName);
		return games.removeValue(game, true);
	}
	public boolean deleteOpenGame(String gameName) {
		TeacherGame game = findOpenGame(gameName);
		return openGames.removeValue(game, true);
	}
	public Array<TeacherGame> getOpenGames() {
		return openGames;
	}

	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
