package com.project.terminkalender.websockets;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.chat.Room;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.screens.LoginScreen;
import com.project.terminkalender.userdata.User;

public class AppWebSockets extends WebSockets {
	private Room room;
	
	public AppWebSockets(String serverDirection) {
		super(serverDirection);
	}
	
	@Override
	protected void options(String action, String message) {
		if(action.equals(LOGIN))
			teacherGamesActive(message);
		else if(action.equals(ENTERGAME))
			enterGame(message);
		else if(action.equals(MESSAGE))
			addMessageToChat(message);
		else if(action.equals(USERSROOM))
			updateUsers(message);
		else if(action.equals(CHAT))
			updateChat(message);
	}
	
	public void teacherGamesActive(String message) {
		Array<String> games = constructArrayGames(message);
		LoginScreen loginScreen = (LoginScreen) AppMain.loginScreen;
		LoginGamesScreen loginGamesScreen = (LoginGamesScreen) AppMain.loginGamesScreen;
		loginScreen.login();
		loginGamesScreen.updateGames(games);
	}
	public void enterGame(String message) {
		if(message.equals("WrongPassword")) {
			AppMain.warningDialog.show("Wrong Password", AppMain.loginGamesScreen.getStage());
		}
		else {
			AppMain.user.startGame(message);
		}
	}
	public void addMessageToChat(String message) {
		String user = message.split(POINTSPLIT)[0];
		String trueMessage = message.substring(user.length() + 1);
		
		room.updateMessageUser(user, trueMessage);
	}
	public void updateUsers(String message) {
		String [] users = message.split(POINTSPLIT);
		if(!users[0].equals("")) {
			Array<String> newUsers = new Array<String>(users);
			room.updateUsers(newUsers);
		}
		else room.noUsers();
	}
	public void updateChat(String message) {
		String [] userWithMessages = message.split(POINTSPLIT);
		if(userWithMessages.length == 2) {
			room.updateChatFromUser(userWithMessages[0], userWithMessages[1]);
		}
	}
	private Array<String> constructArrayGames(String gamesData) {
		String [] games = gamesData.split(POINTSPLIT);
		if(!games[0].equals("")) {
			return new Array<String>(games);
		}
		else return new Array<String>();
	}
	
	public boolean login(String teacher) {
		return sendMessage(LOGIN + POINTSPLIT + teacher);
	}
	public boolean enterGame(String name, String teacher, String gameName, String password) {
		return sendMessage(ENTERGAME + POINTSPLIT + name + POINTSPLIT + teacher + POINTSPLIT + gameName + POINTSPLIT + password);
	}
	public boolean sendMessageChat(String message, String userDestination) {
		if(message.length() != 0) {
			User user = AppMain.user;
			return sendMessage(MESSAGE + POINTSPLIT + user.getName() + POINTSPLIT + user.getGame().getName() + POINTSPLIT + user.getTeacher() + POINTSPLIT + userDestination + POINTSPLIT + message);
		}
		else return false;
	}
	public boolean askUsers() {
		User user = AppMain.user;
		return sendMessage(USERSROOM + POINTSPLIT + user.getName() + POINTSPLIT + user.getGame().getName() + POINTSPLIT + user.getTeacher());
	}
	public boolean askChatFromUser(String userDestination, int messagesSize) {
		User user = AppMain.user;
		return sendMessage(CHAT + POINTSPLIT + user.getName() + POINTSPLIT + user.getGame().getName() + POINTSPLIT + user.getTeacher() + POINTSPLIT + userDestination + POINTSPLIT + messagesSize);
	}
	public boolean sendCalendarInformation() {
		User user = AppMain.user;
		return sendMessage(TASKVALIDATE + POINTSPLIT);
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
}
