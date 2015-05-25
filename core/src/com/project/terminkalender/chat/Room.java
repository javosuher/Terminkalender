package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Room {
	private Table usersTable;
	private Array<String> users;
	private boolean update;

	public Room() {
		Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
		usersTable = new Table(skin);
		users = new Array<String>();
		update = false;
		Main.webSockets.setRoom(this);
	}
	
	public void updateUsers(Array<String> newUsers) {
		users = newUsers;
		update = true;
	}
	
	public void refreshUsers() {
		Main.webSockets.askUsers();
	}
	
	public Table getUsersTable() {
		return usersTable;
	}
	public Array<String> getUsers() {
		return users;
	}
	
	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
