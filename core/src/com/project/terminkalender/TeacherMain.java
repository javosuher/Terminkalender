package com.project.terminkalender;

import com.project.terminkalender.screens.AbstractScreen;
import com.project.terminkalender.screens.TeacherGamesScreen;
import com.project.terminkalender.screens.TeacherLoginRegisterScreen;
import com.project.terminkalender.tools.KennySkin;
import com.project.terminkalender.websockets.ServerDirection;
import com.project.terminkalender.websockets.TeacherWebSockets;

public class TeacherMain extends Main {
	public static TeacherWebSockets teacherWebSockets;
	public static AbstractScreen teacherLoginRegisterScreen, teacherGamesScreen;
	
	
	@Override
	public void create() {
		super.create();
		Resources.load(KennySkin.TEACHER);
		teacherWebSockets = new TeacherWebSockets(ServerDirection.serverDirection);
		main = this;
		
		teacherLoginRegisterScreen = new TeacherLoginRegisterScreen(viewport, batch);
		teacherGamesScreen = new TeacherGamesScreen(viewport, batch);
		setScreen(teacherLoginRegisterScreen);
	}
	
	public static void reconnect() {
		teacherWebSockets.connect(ServerDirection.serverDirection);
	}
}
