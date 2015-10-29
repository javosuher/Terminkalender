package com.project.terminkalender;

import com.project.terminkalender.screens.AbstractScreen;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.screens.ChatScreen;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.screens.LoginScreen;
import com.project.terminkalender.tools.WarningDialogActor;
import com.project.terminkalender.userdata.User;
import com.project.terminkalender.websockets.ServerDirection;
import com.project.terminkalender.websockets.AppWebSockets;

public class AppMain extends Main {
	public static AppWebSockets webSockets;
	public static AbstractScreen loginScreen, loginGamesScreen, calendarScreen, chatScreen;
	public static User user;
	
	@Override
	public void create() {
		super.create();
		webSockets = new AppWebSockets(ServerDirection.serverDirection);
		main = this;
		user = new User();
		
		loginScreen = new LoginScreen(viewport, batch);
		loginGamesScreen = new LoginGamesScreen(viewport, batch);
		setScreen(loginScreen);
	}
	
	public static void createGameScreens() {
		calendarScreen = new CalendarScreen(viewport, batch);
		chatScreen = new ChatScreen(viewport, batch);
	}

	public static void reconnect() {
		webSockets.connect(ServerDirection.serverDirection);
	}
}
