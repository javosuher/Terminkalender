package com.project.terminkalender.chat;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.tools.Pair;
import com.project.terminkalender.tools.ScrollWindow;
import com.project.terminkalender.tools.TWindow;
import com.project.terminkalender.websockets.WebSockets;

public class ChatActor extends Table {
	private Chat chat;
	private final TextField textMessage;
	private ScrollWindow scrollMessagesTable;

	public ChatActor(final Skin skin) {
		super(skin);
		chat = new Chat("");
		
		final Table messageTable = new Table(Main.skin);
		scrollMessagesTable = new ScrollWindow("", skin, messageTable);
		TWindow sendWindow = new TWindow(Main.user.getName() + " Send:", skin);
		
		textMessage = new TextField("", skin);
		TextButton sendButton = new TextButton("Send", skin);

		sendWindow.setMovable(false);
		float widthMessages = 700;
		float heightMessages = 450;

		sendWindow.add(textMessage).width(550).padRight(8);
		sendWindow.add(sendButton).width(100).height(50);
		
		add(scrollMessagesTable).expand().width(widthMessages).height(heightMessages).top().pad(8).row();
		add(sendWindow).expand().bottom().pad(8);
		
		Label label = new Label("Choose an user", Main.skin);
		messageTable.add(label).expandX().center();

		sendButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y) {
				sendMessageActor();
			}
		});
		
		addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.ENTER) {
					sendMessageActor();
				}
				return true;
			}
		});
	}
	
	public void sendMessageActor() {
		Table messageTable = chat.getMessageTable();
		String message = textMessage.getText();
		if(!message.equals("")) {
			if(message.contains(WebSockets.POINTSPLIT) || message.contains(WebSockets.DATASPLIT) || 
					message.contains(WebSockets.CHATSPLIT)) {
				Main.warningDialog.show("you musn't use '=', ';' or ':'", getStage());
			}
			else if(!chat.isEmptyChat()) {
				Label newMessage = new Label(Chat.YOU + Chat.CHATSPACE + message, Main.skin);
				messageTable.add(newMessage).expandX().right().padRight(20).row();
				chat.addMessage(message);
			}
		}
		srollBottom();
		textMessage.setText("");
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(chat.updateMessage() && !chat.updateMessages()) {
			Table messageTable = chat.getMessageTable();
			Array<Pair<String>> messages = chat.getMessages();
			
			Label newMessage = new Label(messages.peek().toString(), Main.skin);
			messageTable.add(newMessage).expandX().left().padLeft(20).row();
			
			srollBottom();
			chat.finishUpdateMessage();
		}
		
		if(chat.updateMessages()) {
			Table messageTable = chat.getMessageTable();
			Array<Pair<String>> messages = chat.getMessages();
			
			messageTable.clear();
			
			for(Pair<String> message : messages) {
				Label newMessage = new Label(message.toString(), Main.skin);
				if(message.getFirst().equals(Chat.YOU)) {
					messageTable.add(newMessage).expandX().right().padRight(20).row();
				}
				else messageTable.add(newMessage).expandX().left().padLeft(20).row();
				
			}
			
			srollBottom();
			chat.finishUpdateMessage();
			chat.finishUpdateMessages();
		}
	}
	
	private void srollBottom() {
		ScrollPane scroll = scrollMessagesTable.getScrollTable();
		scroll.setScrollY(scroll.getMaxY());
	}
	
	public Chat getChat() {
		return chat;
	}
	public void setChat(Chat chat) {
		this.chat.exitChat();
		this.chat = chat;
		this.chat.enterChat();
		this.chat.buttonNormal();
		
		scrollMessagesTable.clear();
		scrollMessagesTable.getTitleLabel().setText(chat.getUser());
		scrollMessagesTable.setTable(Main.skin, chat.getMessageTable());
		chat.startUpdateMessages();
	}
	public String getUserName() {
		return chat.getUser();
	}
}
