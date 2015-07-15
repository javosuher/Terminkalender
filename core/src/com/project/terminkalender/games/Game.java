package com.project.terminkalender.games;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.screens.TeacherGamesScreen;

public class Game {
	private String name, password;
	private Array<String> tasks;

	public Game(String name, String password) {
		this.name = name;
		this.password = password;
		this.tasks = new Array<String>();
	}
	
	public void update() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.updateGame(name, teacherGamesScreen.getTeacher(), password, tasksString());
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
		String tasksString = "";
		for(String task : tasks) {
			tasksString += task + ",";
		}
		return tasksString.substring(0, tasksString.length() - 1);
	}
}
