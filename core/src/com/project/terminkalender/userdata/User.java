package com.project.terminkalender.userdata;

import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.login.GamesOpen;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class User {
	private String name, userName, teacher;
	private Game game;
	
	public User() {}
	public User(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
	}
	
	public void login(String teacher) {
		if(teacher == null) {
			Resources.warningDialog.show("Select a Teacher", AppMain.loginScreen.getStage());
		}
		else {
			this.teacher = teacher.toLowerCase();
			AppMain.webSockets.login(teacher.toLowerCase());
		}
	}
	public void enterGame(String name, String gameName, String password) {
		if(name.equals("") || password.equals("")) {
			Resources.warningDialog.show("You must fill the gaps", AppMain.loginGamesScreen.getStage());
		}
		else if(name.contains(TeacherWebSockets.DATASPLIT) || name.contains(TeacherWebSockets.POINTSPLIT) || 
				name.contains(TeacherWebSockets.TASKSPLIT) || password.contains(TeacherWebSockets.DATASPLIT) || password.contains(TeacherWebSockets.POINTSPLIT) || 
				password.contains(TeacherWebSockets.TASKSPLIT)) {
			Resources.warningDialog.show("you musn't use ',', ';' or ':'", AppMain.loginGamesScreen.getStage());
		}
		else {
			this.name = name.toLowerCase();
			AppMain.webSockets.enterGame(name, teacher, gameName.toLowerCase(), password.toLowerCase());
		}
	}
	
	public void startGame(String gameName, String userUserName) {
		LoginGamesScreen loginGamesScreen = (LoginGamesScreen) AppMain.loginGamesScreen;
		GamesOpen games = loginGamesScreen.getGames();
		game = games.findGame(gameName);
		userName = userUserName;
		loginGamesScreen.enterGame();
	}

	public String getName() {
		return name;
	}
	public String getUserName() {
		return userName;
	}
	public String getTeacher() {
		return teacher;
	}	
	public Game getGame() {
		return game;
	}
}
