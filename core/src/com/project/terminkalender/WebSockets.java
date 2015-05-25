package com.project.terminkalender;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.chat.Chat;
import com.project.terminkalender.chat.Room;

public class WebSockets {
	public final static String POINTSPLIT = ":";
	public final static String MESSAGE = "Message";
	public final static String USERSROOM = "UsersRoom";
	
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
		Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
		Label newMessage = new Label(message, skin);
		Chat.addMessageServer(newMessage, message);
	}
	public void refreshUsers(String message) {
		String [] users = message.split(POINTSPLIT);
		if(!users[0].equals("")) {
			Array<String> newUsers = new Array<String>(users);
			room.updateUsers(newUsers);
		}
	}
	
	public boolean sendMessageChat(String message) {
		return sendMessage(MESSAGE + POINTSPLIT + message);
	}
	public boolean askUsers() {
		return sendMessage(USERSROOM + POINTSPLIT);
	}
	private boolean sendMessage(String message) {
		if (connected && message.length() != 0) {
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
