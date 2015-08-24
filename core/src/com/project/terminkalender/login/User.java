package com.project.terminkalender.login;

import com.project.terminkalender.Main;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class User {
	private String name, teacher;
	
	public User() {}
	public User(String name, String teacher) {
		this.name = name;
		this.teacher = teacher;
	}
	
	public void login(String name, String teacher) {
		if(name.equals("") || teacher.equals("")) {
			Main.warningDialog.show("You must fill the gaps", Main.loginScreen.getStage());
		}
		else if(name.contains(TeacherWebSockets.DATASPLIT) || name.contains(TeacherWebSockets.POINTSPLIT) || 
				name.contains(TeacherWebSockets.TASKSPLIT) || teacher.contains(TeacherWebSockets.DATASPLIT) || 
				teacher.contains(TeacherWebSockets.POINTSPLIT) || teacher.contains(TeacherWebSockets.TASKSPLIT)) {
			Main.warningDialog.show("you musn't use ',', ';' or ':'", Main.loginScreen.getStage());
		}
		else {
			this.name = name;
			this.teacher = teacher;
			Main.webSockets.login(teacher);
		}
	}

	public String getName() {
		return name;
	}
	public String getTeacher() {
		return teacher;
	}	
}
