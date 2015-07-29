package com.project.terminkalender.games;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.TeacherWebSockets;
import com.project.terminkalender.screens.TeacherGamesScreen;

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
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.updateGame(name, teacherGamesScreen.getTeacher(), password, tasksString());
	}
	
	public void openGame() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.openGame(name, teacherGamesScreen.getTeacher(), password, tasksString());
	}

	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
