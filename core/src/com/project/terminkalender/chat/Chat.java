package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Chat {
	private final static Table messageTable = new Table();
	private static Array<Label> messages;
	
	public Chat() {
		messages = new Array<Label>();
	}
	
	public void addMessage(Label labelMessage, String message) {
		messages.add(labelMessage);
		Main.webSockets.sendMessageChat(message);
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
