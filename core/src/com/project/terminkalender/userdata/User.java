package com.project.terminkalender.userdata;

import com.project.terminkalender.AppMain;
import com.project.terminkalender.login.GamesOpen;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class User {
	private String name, teacher;
	private Game game;
	
	public User() {}
	public User(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
	}
	
	public void login(String teacher) {
		if(teacher.equals("")) {
			AppMain.warningDialog.show("You must fill the gaps", AppMain.loginScreen.getStage());
		}
		else if(teacher.contains(TeacherWebSockets.DATASPLIT) || 
				teacher.contains(TeacherWebSockets.POINTSPLIT) || teacher.contains(TeacherWebSockets.TASKSPLIT)) {
			AppMain.warningDialog.show("you musn't use ',', ';' or ':'", AppMain.loginScreen.getStage());
		}
		else {
			this.teacher = teacher.toLowerCase();
			AppMain.webSockets.login(teacher.toLowerCase());
		}
	}
	public void enterGame(String name, String gameName, String password) {
		if(name.equals("") || password.equals("")) {
			AppMain.warningDialog.show("You must fill the gaps", AppMain.loginScreen.getStage());
		}
		else if(name.contains(TeacherWebSockets.DATASPLIT) || name.contains(TeacherWebSockets.POINTSPLIT) || 
				name.contains(TeacherWebSockets.TASKSPLIT) || password.contains(TeacherWebSockets.DATASPLIT) || password.contains(TeacherWebSockets.POINTSPLIT) || 
				password.contains(TeacherWebSockets.TASKSPLIT)) {
			AppMain.warningDialog.show("you musn't use ',', ';' or ':'", AppMain.loginScreen.getStage());
		}
		else {
			this.name = name.toLowerCase();
			AppMain.webSockets.enterGame(name.toLowerCase(), teacher, gameName.toLowerCase(), password.toLowerCase());
		}
	}
	
	public void startGame(String gameName) {
		LoginGamesScreen loginGamesScreen = (LoginGamesScreen) AppMain.loginGamesScreen;
		GamesOpen games = loginGamesScreen.getGames();
		game = games.findGame(gameName);
		loginGamesScreen.enterGame();
	}

	public String getName() {
		return name;
	}
	public String getTeacher() {
		return teacher;
	}	
	public Game getGame() {
		return game;
	}
}
