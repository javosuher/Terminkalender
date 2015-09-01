package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Chat {
	public final static String POINTSPLIT = ":";
	public final static String ME = "Me";
	
	private String user;
	private Table messageTable;
	private Array<String> messages;
	private boolean update;
	
	public Chat(String user) {
		this.user = user;
		messageTable = new Table(Main.skin);
		messages = new Array<String>();
		update = false;
	}
	
	public void addMessage(String message) {
		messages.add(ME + POINTSPLIT + message);
		Main.webSockets.sendMessageChat(message, user);
	}
	public void addMessageServer(String message) {
		messages.add(message);
		update = true;
	}
	
	public String getUser() {
		return user;
	}
	public Table getMessageTable() {
		return messageTable;
	}
	public Array<String> getMessages() {
		return messages;
	}
	
	public boolean update() {
		return update;
	}
	public void finishUpdate() {
		update = false;
	}
}
