package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Chat {
	private String user;
	private Table messageTable;
	private Array<Label> messages;
	
	public Chat(String user) {
		Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
		
		this.user = user;
		messageTable = new Table(skin);
		messages = new Array<Label>();
	}
	
	public void addMessage(Label labelMessage, String message) {
		messages.add(labelMessage);
		Main.webSockets.sendMessageChat(message);
	}
	public void addMessageServer(Label labelMessage, String message) {
		messageTable.add(labelMessage).expandX().left().padLeft(20);
		messages.add(labelMessage);
	}
	
	public String getUser() {
		return user;
	}
	public Table getMessageTable() {
		return messageTable;
	}
	public Array<Label> getMessages() {
		return messages;
	}
}
