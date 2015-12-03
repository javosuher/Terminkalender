package com.project.terminkalender.userdata;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class Game {
	protected String name, password;
	protected Array<Task> tasks;
	protected Array<String> users;

	public Game(String name, String password) {
		this(name, password, new Array<Task>(), new Array<String>());
	}
	public Game(String name, String password, Array<Task> tasks) {
		this(name, password, tasks, new Array<String>());
	}
	public Game(String name, String password, Array<Task> tasks, Array<String> users) {
		this.name = name;
		this.password = password;
		this.tasks = tasks;
		this.users = users;
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

	public void addTask(Task task) {
		if(task.equals("")) {
			Resources.warningDialog.show("You must fill the task", AppMain.loginGamesScreen.getStage());
		}
		else tasks.add(task);
	}
	public void eraseTask(Task task) {
		tasks.removeValue(task, true);
	}
	public void eraseTask(String taskName) {
		int index = 0;
		for(Task task : tasks) {
			if(task.getName().equals(taskName)) {
				tasks.removeIndex(index);
			}
			++index;
		}
	}
	public Array<Task> getTasks() {
		return tasks;
	}
	public void setTasks(Array<Task> tasks) {
		this.tasks = tasks;
	}
	public String tasksToString() {
		if(tasks.size > 0) {
			String tasksString = "";
			for(Task task : tasks) {
				tasksString += task + TeacherWebSockets.TASKSPLIT;
			}
			return tasksString.substring(0, tasksString.length() - 1);
		}
		else return "";
	}
	
	public void addUserUser(String user) {
		if(user.equals("")) {
			Resources.warningDialog.show("You must fill the user", AppMain.loginGamesScreen.getStage());
		}
		else users.add(user);
	}
	public void eraseUser(String user) {
		users.removeValue(user, false);
	}
	public Array<String> getUsers() {
		return users;
	}
	public void setUsers(Array<String> users) {
		this.users = users;
	}
	public String usersToString() {
		if(users.size > 0) {
			String userString = "";
			for(String user : users) {
				userString += user + TeacherWebSockets.SPLIT;
			}
			return userString.substring(0, userString.length() - 1);
		}
		else return "";
	}
}
