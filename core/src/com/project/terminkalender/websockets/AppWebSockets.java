package com.project.terminkalender.websockets;

import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.chat.Room;
import com.project.terminkalender.login.Login;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.screens.ChatScreen;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.screens.LoginScreen;
import com.project.terminkalender.userdata.User;

public class AppWebSockets extends WebSockets {
	private Room room;
	private Login login;
	
	public AppWebSockets(String serverDirection) {
		super(serverDirection);
	}
	
	@Override
	protected void options(String action, String message) {
		if(action.equals(TEACHERS))
			teachers(message);
		else if(action.equals(LOGIN))
			teacherGamesActive(message);
		else if(action.equals(ENTERGAME))
			enterGame(message);
		else if(action.equals(MESSAGE))
			addMessageToChat(message);
		else if(action.equals(USERSROOM))
			updateUsers(message);
		else if(action.equals(CHAT))
			updateChat(message);
		else if(action.equals(TASKS))
			updateCalendar(message);
		else if(action.equals(TASKVALIDATE))
			validatedTask(message);
		else if(action.equals(CLOSEGAMES))
			closeGame(message);
	}
	
	public void teachers(String message) {
		Array<String> teachers = new Array<String>(message.split(POINTSPLIT));
		login.updateTeachersFromServer(teachers);
	}
	public void teacherGamesActive(String message) {
		Array<String> games = constructArrayData(message);
		LoginScreen loginScreen = (LoginScreen) AppMain.loginScreen;
		LoginGamesScreen loginGamesScreen = (LoginGamesScreen) AppMain.loginGamesScreen;
		loginScreen.login();
		loginGamesScreen.updateGames(games);
	}
	public void enterGame(String message) {
		if(message.equals("WrongPassword")) {
			Resources.warningDialog.show("Wrong Password", AppMain.loginGamesScreen.getStage());
		}
		else if(message.equals("UserNoExist")) {
			Resources.warningDialog.show("User no Exist", AppMain.loginGamesScreen.getStage());
		}
		else {
			String gameName = message.split(POINTSPLIT)[0];
			String userUserName = message.split(POINTSPLIT)[1];
			AppMain.user.startGame(gameName, userUserName);
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
	public void updateCalendar(String message) {
		Array<String> tasks = constructArrayData(message);
		if(tasks.size > 0) {
			AppMain.finishLoadingGameScreens();
			CalendarScreen calendarScreen = (CalendarScreen) AppMain.calendarScreen;
			calendarScreen.updateTasks(tasks);
		}
	}
	public void validatedTask(String message) {
		String [] wrongTasks = message.split(POINTSPLIT);
		CalendarScreen calendarScreen = (CalendarScreen) AppMain.calendarScreen;
		calendarScreen.validateTasks(new Array<String>(wrongTasks));
	}
	public void closeGame(String message) {
		CalendarScreen calendarScreen = (CalendarScreen) AppMain.calendarScreen;
		ChatScreen chatScreen = (ChatScreen) AppMain.chatScreen;
		calendarScreen.closeGame();
		chatScreen.closeGame();
		login(AppMain.user.getTeacher());
	}
	private Array<String> constructArrayData(String stringData) {
		String [] array = stringData.split(POINTSPLIT);
		if(!array[0].equals("")) {
			return new Array<String>(array);
		}
		else return new Array<String>();
	}
	
	public boolean askTeachers() {
		if(connected) {
			return sendMessage(TEACHERS + POINTSPLIT);
		}
		else return false;
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
			return sendMessage(MESSAGE + POINTSPLIT + user.getUserName() + POINTSPLIT + user.getGame().getName() + 
					POINTSPLIT + user.getTeacher() + POINTSPLIT + userDestination + POINTSPLIT + message);
		}
		else return false;
	}
	public boolean askUsers() {
		User user = AppMain.user;
		return sendMessage(USERSROOM + POINTSPLIT + user.getName() + POINTSPLIT + user.getGame().getName() + POINTSPLIT + user.getTeacher());
	}
	public boolean askChatFromUser(String userDestination, int messagesSize) {
		User user = AppMain.user;
		return sendMessage(CHAT + POINTSPLIT + user.getUserName() + POINTSPLIT + user.getGame().getName() + 
				POINTSPLIT + user.getTeacher() + POINTSPLIT + userDestination + POINTSPLIT + messagesSize);
	}
	public boolean sendTaskFill(String description, String location, String position, String partners) {
		User user = AppMain.user;
		return sendMessage(TASK + POINTSPLIT + user.getUserName() + POINTSPLIT + user.getGame().getName() + 
				POINTSPLIT + user.getTeacher() + POINTSPLIT + description + POINTSPLIT + location + POINTSPLIT + position + POINTSPLIT + partners);
	}
	public boolean validateCalendarData() {
		User user = AppMain.user;
		return sendMessage(TASKVALIDATE + POINTSPLIT + user.getUserName() + POINTSPLIT + user.getGame().getName() + 
				POINTSPLIT + user.getTeacher());
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
}
