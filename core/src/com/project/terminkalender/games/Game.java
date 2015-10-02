package com.project.terminkalender.games;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.screens.TeacherGamesScreen;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class Game {
	private String name, password;
	private Array<String> tasks;

	public Game(String name, String password) {
		this.name = name;
		this.password = password;
		this.tasks = new Array<String>();
	}
	public Game(String name, String password, Array<String> tasks) {
		this.name = name;
		this.password = password;
		this.tasks = tasks;
	}
	
	public void update() {
		if(password.equals("")) {
			TeacherMain.warningDialog.show("You must fill the password", TeacherMain.teacherGamesScreen.getStage());
		}
		else if(password.contains(TeacherWebSockets.DATASPLIT) || password.contains(TeacherWebSockets.POINTSPLIT) || 
				password.contains(TeacherWebSockets.TASKSPLIT)) {
			TeacherMain.warningDialog.show("you musn't use ',', ';' or ':'", TeacherMain.teacherLoginRegisterScreen.getStage());
		}
		else {
			TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
			TeacherMain.teacherWebSockets.updateGame(name, teacherGamesScreen.getTeacher(), password, tasksString());
		}
	}
	
	public void openGamePetition() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.openGame(name, teacherGamesScreen.getTeacher(), password, tasksString());
	}
	public void closeGamePetition() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.closeGame(name, teacherGamesScreen.getTeacher());
	}
	public void removeGamePetition() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.removeGame(name, teacherGamesScreen.getTeacher());
	}

	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password.toLowerCase();
	}

	public void addTask(String task) {
		tasks.add(task);
	}
	public void eraseTask(String task) {
		tasks.removeValue(task, false);
	}
	public Array<String> getTasks() {
		return tasks;
	}
	public void setTask(Array<String> tasks) {
		this.tasks= tasks;
	}
	public String tasksString() {
		if(tasks.size > 0) {
			String tasksString = "";
			for(String task : tasks) {
				tasksString += task + TeacherWebSockets.TASKSPLIT;
			}
			return tasksString.substring(0, tasksString.length() - 1);
		}
		else return "";
	}
}
