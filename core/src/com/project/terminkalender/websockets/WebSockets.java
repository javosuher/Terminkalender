package com.project.terminkalender.websockets;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.chat.Room;
import com.project.terminkalender.screens.LoginGamesScreen;
import com.project.terminkalender.screens.LoginScreen;

public class WebSockets {
	public final static String POINTSPLIT = ":";
	public final static String DATASPLIT = ";";
	public final static String TASKSPLIT = ",";
	public final static String LOGIN = "Login";
	public final static String ENTERGAME = "EnterGame";
	public final static String MESSAGE = "Message";
	public final static String USERSROOM = "UsersRoom";
	
	private WebSocketClient wsc;
	private boolean connected;
	
	private Room room;
	
	public WebSockets(String serverDirection) {
		connect(serverDirection);
	}
	
	public void connect(String serverDirection) {
		URI url = null;
		try {
			url = new URI(serverDirection);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} 
		
		Draft standard = new Draft_17();
		wsc = new WebSocketClient(url, standard) {

			@Override
			public void onOpen(ServerHandshake handshake) {
				Gdx.app.log("WebSocket", "WSClient Conected.");
				connected = true;
			}

			@Override
			public void onMessage(String message) {
				Gdx.app.log("WebSocket", "WSClient receive message -> " + message);
				
				String action = message.split(POINTSPLIT)[0];
				String trueMessage = message.substring(action.length() + 1);
				
				if(action.equals(LOGIN))
					teacherGamesActive(trueMessage);
				else if(action.equals(ENTERGAME))
					enterGame(trueMessage);
				else if(action.equals(MESSAGE))
					addMessageToChat(trueMessage);
				else if(action.equals(USERSROOM))
					refreshUsers(trueMessage);
					
			}

			@Override
			public void onError(Exception ex) {
				Gdx.app.log("WebSocket", "WSClient Error -> " + ex);
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				Gdx.app.log("WebSocket", "WSClient closed.");
				connected = false;
			}
		};
		
		wsc.connect();
	}
	
	public void teacherGamesActive(String message) {
		Array<String> games = constructArrayGames(message);
		LoginScreen loginScreen = (LoginScreen) Main.loginScreen;
		LoginGamesScreen loginGamesScreen = (LoginGamesScreen) Main.loginGamesScreen;
		loginScreen.login();
		loginGamesScreen.updateGames(games);
	}
	public void enterGame(String message) {
		if(message.equals("WrongPassword")) {
			Main.warningDialog.show("Wrong Password", Main.loginGamesScreen.getStage());
		}
		else {
			Main.user.startGame(message);
		}
	}
	public void addMessageToChat(String message) {
		String user = message.split(POINTSPLIT)[0];
		String trueMessage = message.substring(user.length() + 1);
		
		room.updateMessageUser(user, trueMessage);
	}
	public void refreshUsers(String message) {
		String [] users = message.split(POINTSPLIT);
		if(!users[0].equals("")) {
			Array<String> newUsers = new Array<String>(users);
			room.updateUsers(newUsers);
		}
		else room.noUsers();
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
	public boolean sendMessageChat(String message, String user) {
		if(message.length() != 0)
			return sendMessage(MESSAGE + POINTSPLIT + user + POINTSPLIT + message);
		else return false;
	}
	public boolean askUsers() {
		return sendMessage(USERSROOM + POINTSPLIT);
	}
	private boolean sendMessage(String message) {
		if (connected) {
			Gdx.app.log("WebSocket", "WSClient send message: " + message);
			wsc.send(message);
			return true;
		}
		else return false;
	}

	public WebSocketClient getWsc() {
		return wsc;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
}
