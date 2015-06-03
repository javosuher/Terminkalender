package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Room {
	private Table usersTable;
	private Array<Chat> chats;
	private boolean update;

	public Room() {
		Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
		
		usersTable = new Table(skin);
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
