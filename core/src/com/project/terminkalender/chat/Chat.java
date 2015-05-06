package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class Chat {
	private final static Table messageTable = new Table();
	private static Array<Label> messages;
	private WebSockets webSocket;
	
	public Chat() {
		messages = new Array<Label>();
		webSocket = new WebSockets();
	}
	
	public void addMessage(Label labelMessage, String message) {
		messages.add(labelMessage);
		webSocket.sendMessage(message);
	}
	public static void addMessageServer(Label labelMessage, String message) {
		messageTable.add(labelMessage).expandX().left().padLeft(20);
		messages.add(labelMessage);
	}
	
	public static Table getMessageTable() {
		return messageTable;
	}
	
	public Array<Label> getMessages() {
		return messages;
	}
}
