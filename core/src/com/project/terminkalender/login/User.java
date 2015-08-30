package com.project.terminkalender.login;

import com.project.terminkalender.Main;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class User {
	private String name, teacher;
	private GameOpen game;
	
	public User() {}
	public User(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
	}
	
	public void login(String teacher) {
		if(teacher.equals("")) {
			Main.warningDialog.show("You must fill the gaps", Main.loginScreen.getStage());
		}
		else if(teacher.contains(TeacherWebSockets.DATASPLIT) || 
				teacher.contains(TeacherWebSockets.POINTSPLIT) || teacher.contains(TeacherWebSockets.TASKSPLIT)) {
			Main.warningDialog.show("you musn't use ',', ';' or ':'", Main.loginScreen.getStage());
		}
		else {
			this.teacher = teacher.toLowerCase();
			Main.webSockets.login(teacher.toLowerCase());
		}
	}
	public void enterGame(String name, String gameName, String password) {
		if(name.equals("") || password.equals("")) {
			Main.warningDialog.show("You must fill the gaps", Main.loginScreen.getStage());
		}
		else if(name.contains(TeacherWebSockets.DATASPLIT) || name.contains(TeacherWebSockets.POINTSPLIT) || 
				name.contains(TeacherWebSockets.TASKSPLIT) || password.contains(TeacherWebSockets.DATASPLIT) || password.contains(TeacherWebSockets.POINTSPLIT) || 
				password.contains(TeacherWebSockets.TASKSPLIT)) {
			Main.warningDialog.show("you musn't use ',', ';' or ':'", Main.loginScreen.getStage());
		}
		else {
			this.name = name.toLowerCase();
			Main.webSockets.enterGame(name.toLowerCase(), teacher, gameName.toLowerCase(), password.toLowerCase());
		}
	}
	
	public void startGame(String gameName) {
		LoginGamesScreen loginGamesScreen = (LoginGamesScreen) Main.loginGamesScreen;
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
	public GameOpen getGame() {
		return game;
	}
}
