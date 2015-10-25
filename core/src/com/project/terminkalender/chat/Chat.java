package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.tools.Pair;
import com.project.terminkalender.websockets.AppWebSockets;

public class Chat {
	public final static String YOU = "You";
	public final static String CHATSPACE = ": ";
	
	private String user;
	private Table messageTable;
	private Array<Pair<String>> messages;
	private boolean updateMessage, updateMessages;
	
	private TextButton textButton;
	private boolean inChat;
	
	public Chat(String user) {
		this.user = user;
		messageTable = new Table(Resources.skin);
		this.messages = new Array<Pair<String>>();
		updateMessage = false;
		messageTable.padBottom(20);
		
		textButton = new TextButton(user, Resources.skin);
		inChat = false;
	}
	public Chat(String user, Array<String> messages) {
		this(user);
		
		addMessages(messages);
	}
	
	public void addMessage(String message) {
		messages.add(new Pair<String>(YOU, message));
		AppMain.webSockets.sendMessageChat(message, user);
	}
	public void addMessages(Array<String> messages) {
		for(String message : messages) {
			String [] userPlusMessage = message.split(AppWebSockets.CHATSPLIT);
			if(userPlusMessage[0].equals(AppMain.user.getName())) {
				this.messages.add(new Pair<String>(YOU, userPlusMessage[1]));
			}
			else this.messages.add(new Pair<String>(userPlusMessage[0], userPlusMessage[1]));
		}
		updateMessages = true;
	}
	public void addMessageServer(String message) {
		messages.add(new Pair<String>(user, message));
		if(!isInChat()) {
			buttonNotification();
		}
		updateMessage = true;
	}
	
	public boolean isEmptyChat() {
		return user.equals("");
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
	public int getMessagesSize() {
		return messages.size;
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
	public void startUpdateMessage() {
		updateMessage = true;
	}
	public void startUpdateMessages() {
		updateMessages = true;
	}
	
	public TextButton getTextButton() {
		return textButton;
	}
	public void setTextButton(TextButton textButton) {
		this.textButton = textButton;
	}
	public void buttonNotification() {
		if(!isEmptyChat()) {
			textButton.setStyle(Resources.skin.get("orangeTextButton", TextButtonStyle.class));
		}
	}
	public void buttonNormal() {
		if(!isEmptyChat()) {
			textButton.setStyle(Resources.skin.get("default", TextButtonStyle.class));
		}
	}
	
	public boolean isInChat() {
		return inChat;
	}
	public void enterChat() {
		inChat = true;
	}
	public void exitChat() {
		inChat = false;
	}
}
