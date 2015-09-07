package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	private final Chat chat;

	public ChatActor(final Skin skin, final Chat chat) {
		super(skin);
		this.chat = chat;
		
		final Table messageTable = chat.getMessageTable();
		ScrollWindow scrollTable = new ScrollWindow(chat.getUser(), skin, messageTable);
		TWindow sendWindow = new TWindow("Send", skin);
		
		final TextField textMessage = new TextField("", skin);
		TextButton sendButton = new TextButton("Send", skin);

		sendWindow.setMovable(false);
		setFillParent(true);
		float widthMessages = 800;
		float heightMessages = 450;

		sendWindow.add(textMessage).width(600).padRight(8);
		sendWindow.add(sendButton).width(100).height(50);
		
		add(scrollTable).expand().width(widthMessages).height(heightMessages).top().pad(8).row();
		add(sendWindow).expand().bottom().pad(8);

		sendButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y) {
				String message = textMessage.getText();
				if(!message.equals("")) {
					if(message.contains(WebSockets.POINTSPLIT) || message.contains(WebSockets.DATASPLIT) || 
							message.contains(WebSockets.CHATSPLIT)) {
						Main.warningDialog.show("you musn't use '=', ';' or ':'", getStage());
					}
					else {
						Label newMessage = new Label(Chat.YOU + Chat.CHATSPACE + message, skin);
						messageTable.add(newMessage).expandX().right().padRight(20).row();
						chat.addMessage(message);
					}
				}
			}
		});
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(chat.updateMessage()) {
			Table messageTable = chat.getMessageTable();
			Array<Pair<String>> messages = chat.getMessages();
			
			Label newMessage = new Label(messages.peek().toString(), Main.skin);
			messageTable.add(newMessage).expandX().left().padLeft(20).row();
			
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
			
			chat.finishUpdateMessages();
		}
	}
	
	public Chat getChat() {
		return chat;
	}
	public String getUserName() {
		return chat.getUser();
	}
}
