package com.project.terminkalender;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.chat.Room;

public class WebSockets {
	public final static String POINTSPLIT = ":";
	public final static String MESSAGE = "Message";
	public final static String USERSROOM = "UsersRoom";
	public final static String LOGINTEACHER = "LoginTeacher";
	public final static String REGISTERTEACHER = "RegisterTeacher";
	
	private WebSocketClient wsc;
	private boolean connected;
	
	private Room room;
	
	public WebSockets(String serverDirection) {
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
				
				if(action.equals(MESSAGE))
					addMessageToChat(trueMessage);
				else if(action.equals(USERSROOM))
					refreshUsers(trueMessage);
				else if(action.equals(LOGINTEACHER))
					loginTeacherCheck(trueMessage);
				else if(action.equals(REGISTERTEACHER))
					registerTeacherCheck(trueMessage);
					
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
	public void loginTeacherCheck(String message) {
		String userTeacher = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("NoExist")) {
			Main.warningDialog.show(userTeacher + " not exists", Main.teacherFirstScreen.getStage());
		}
		else if(check.equals("WrongPassword")) {
			Main.warningDialog.show(userTeacher + " has another password", Main.teacherFirstScreen.getStage());
		}
		else {
			Main.warningDialog.show(userTeacher + " connected", Main.teacherFirstScreen.getStage());
		}
	}
	public void registerTeacherCheck(String message) {
		String userTeacher = message.split(POINTSPLIT)[0];
		String check = message.split(POINTSPLIT)[1];
		
		if(check.equals("Failure")) {
			Main.warningDialog.show(userTeacher + " already exists", Main.teacherFirstScreen.getStage());
		}
		else {
			Main.warningDialog.show(userTeacher + " registered", Main.teacherFirstScreen.getStage());
		}
	}
	
	public boolean sendMessageChat(String message, String user) {
		if(message.length() != 0)
			return sendMessage(MESSAGE + POINTSPLIT + user + POINTSPLIT + message);
		else return false;
	}
	public boolean askUsers() {
		return sendMessage(USERSROOM + POINTSPLIT);
	}
	public boolean loginTeacher(String teacherUser, String password) {
		return sendMessage(LOGINTEACHER + POINTSPLIT + teacherUser + POINTSPLIT + password);
	}
	public boolean registerTeacher(String teacherUser, String password) {
		return sendMessage(REGISTERTEACHER + POINTSPLIT + teacherUser + POINTSPLIT + password);
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
