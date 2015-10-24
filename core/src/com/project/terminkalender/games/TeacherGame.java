package com.project.terminkalender.games;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.TeacherMain;
import com.project.terminkalender.screens.TeacherGamesScreen;
import com.project.terminkalender.userdata.Game;
import com.project.terminkalender.userdata.Task;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class TeacherGame extends Game {

	public TeacherGame(String name, String password) {
		super(name, password);
	}
	public TeacherGame(String name, String password, Array<Task> tasks, Array<String> users) {
		super(name, password, tasks, users);
	}
	public TeacherGame(String name, String password, Array<Task> tasks) {
		super(name, password, tasks);
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
			TeacherMain.teacherWebSockets.updateGame(name, teacherGamesScreen.getTeacher(), password, tasksToString(), usersToString());
		}
	}
	
	public void openGamePetition() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.openGame(name, teacherGamesScreen.getTeacher(), password, tasksToString(), usersToString());
	}
	public void closeGamePetition() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.closeGame(name, teacherGamesScreen.getTeacher());
	}
	public void removeGamePetition() {
		TeacherGamesScreen teacherGamesScreen = (TeacherGamesScreen) TeacherMain.teacherGamesScreen;
		TeacherMain.teacherWebSockets.removeGame(name, teacherGamesScreen.getTeacher());
	}
}
