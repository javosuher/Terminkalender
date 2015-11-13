package com.project.terminkalender.login;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;

public class Login {
	private Array<String> teachers;
	private boolean update;
	
	public Login() {
		teachers = new Array<String>();
		update = false;
		AppMain.webSockets.setLogin(this);
	}

	public void askTeacherServer() {
		AppMain.webSockets.askTeachers();
	}
	public void updateTeachersFromServer(Array<String> teachers) {
		setTeachers(teachers);
		update = true;
	}
	
	public void setTeachers(Array<String> teachers) {
		this.teachers = teachers;
	}
	public Array<String> getTeachers() {
		return teachers;
	}

	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		this.update = false;
	}
}
