package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.tools.Pair;
import com.project.terminkalender.websockets.WebSockets;

public class Chat {
	public final static String YOU = "You";
	
	private String user;
	private Table messageTable;
	private Array<Pair<String>> messages;
	private boolean updateMessage, updateMessages;
	
	public Chat(String user, Array<String> messages) {
		this.user = user;
		messageTable = new Table(Main.skin);
		this.messages = new Array<Pair<String>>();
		updateMessage = false;
		
		addMessages(messages);
	}
	
	public void addMessage(String message) {
		messages.add(new Pair<String>(YOU, message));
		Main.webSockets.sendMessageChat(message, user);
	}
	public void addMessages(Array<String> messages) {
		for(String message : messages) {
			String [] userPlusMessage = message.split(WebSockets.CHATSPLIT);
			if(userPlusMessage[0].equals(Main.user.getName())) {
				this.messages.add(new Pair<String>(YOU, userPlusMessage[1]));
			}
			else this.messages.add(new Pair<String>(userPlusMessage[0], userPlusMessage[1]));
		}
		updateMessages = true;
	}
	public void addMessageServer(String message) {
		messages.add(new Pair<String>(user, message));
		updateMessage = true;
	}
	
	public String getUser() {
		return user;
	}
	public Table getMessageTable() {
		return messageTable;
	}
	public Array<Pair<String>> getMessages() {
		return messages;
	}
	
	public boolean updateMessage() {
		return updateMessage;
	}
	public boolean updateMessages() {
		return updateMessages;
	}
	public void finishUpdateMessage() {
		updateMessage = false;
	}
	public void finishUpdateMessages() {
		updateMessages = false;
	}
}
