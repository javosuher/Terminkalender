package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

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
	
	public void updateMessageUser(String user, String message) {
		int indexUser = indexUser(user);
		if(indexUser == chats.size) {
			chats.add(new Chat(user));
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
