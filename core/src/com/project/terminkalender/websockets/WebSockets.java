package com.project.terminkalender.websockets;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.badlogic.gdx.Gdx;

public abstract class WebSockets {
	public final static String POINTSPLIT = ":";
	public final static String DATASPLIT = ";";
	public final static String TASKSPLIT = ",";
	public final static String TASKLIMITSPLIT = "-";
	public final static String CHATSPLIT = "=";
	
	public final static String LOGIN = "Login";
	public final static String ENTERGAME = "EnterGame";
	public final static String MESSAGE = "Message";
	public final static String USERSROOM = "UsersRoom";
	public final static String CHAT = "Chat";
	public final static String TASK = "Task";
	public final static String TASKVALIDATE = "TaskValidate";
	
	public final static String LOGINTEACHER = "LoginTeacher";
	public final static String REGISTERTEACHER = "RegisterTeacher";
	public final static String CREATEGAME = "CreateGame";
	public final static String UPDATEGAME = "UpdateGame";
	public final static String GAMES = "Games";
	public final static String OPENGAMES = "OpenGames";
	public final static String REMOVEGAMES = "RemoveGames";
	public final static String CLOSEGAMES = "CloseGames";
	
	protected WebSocketClient wsc;
	protected boolean connected;
	
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
				
				options(action, trueMessage);	
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
	
	protected abstract void options(String action, String message);
	
	protected boolean sendMessage(String message) {
		if(connected) {
			Gdx.app.log("WebSocket", "WSClient send message: " + message);
			wsc.send(message);
			return true;
		}
		else return false;
	}

	public WebSocketClient getWsc() {
		return wsc;
	}
}
