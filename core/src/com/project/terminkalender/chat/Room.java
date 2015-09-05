package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.websockets.WebSockets;

public class Room {
	private Table usersTable;
	private Array<Chat> chats;
	private boolean update;

	public Room() {
		usersTable = new Table(Main.skin);
		chats = new Array<Chat>();
		update = false;
		Main.webSockets.setRoom(this);
	}
	
	public void updateUsers(Array<String> users) {
		chats.clear();
		for(String user : users) {
			chats.add(new Chat(user));
		}
		update = true;
	}
	public void noUsers() {
		usersTable.clear();
	}
	
	public void updateChatFromUser(String user, String messages) {
		int index = indexUser(user);
		Chat chat = chats.get(index);
		Array<String> splitMessages = new Array<String>(messages.split(WebSockets.DATASPLIT));
		chat.addMessages(splitMessages);
	}
	
	public void updateMessageUser(String user, String message) {
		int indexUser = indexUser(user);
		if(indexUser == chats.size) {
			Array<String> messages = new Array<String>();
			messages.add(message);
			Chat chat = new Chat(user);
			chat.addMessageServer(message);
			chats.add(chat);
			update = true;
		}
		chats.get(indexUser).addMessageServer(message);
	}
	private int indexUser(String user) {
		boolean find = false;
		int index = 0;
		
		while(index < chats.size && !find) {
			if(user.equals(chats.get(index).getUser())) {
				find = true;
			}
			else ++index;
		}
		
		return index;
	}
	
	public void refreshUsers() {
		Main.webSockets.askUsers();
	}
	
	public Table getUsersTable() {
		return usersTable;
	}
	public Array<Chat> getChats() {
		return chats;
	}

	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
