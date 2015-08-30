package com.project.terminkalender.login;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class GameOpen {
	private String name, password;
	private Array<String> tasks;

	public GameOpen(String name, String password) {
		this.name = name;
		this.password = password;
		this.tasks = new Array<String>();
	}
	public GameOpen(String name, String password, Array<String> tasks) {
		this.name = name;
		this.password = password;
		this.tasks = tasks;
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
		if(task.equals("")) {
			TeacherMain.warningDialog.show("You must fill the task", Main.loginGamesScreen.getStage());
		}
		else tasks.add(task);
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
