package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ChatActor extends Table {

	public ChatActor(final Chat chat, final Skin skin) {
		super(skin);
		
		Window messagesWindow = new Window("Chat", skin);
		Window sendWindow = new Window("Send", skin);
		
		final Table messageTable = Chat.getMessageTable();
		messageTable.setSkin(skin);
		ScrollPane scrollTable = new ScrollPane(messageTable, skin);
		final TextArea textMessage = new TextArea("", skin);
		TextButton sendButton = new TextButton("Send", skin);

		messagesWindow.setMovable(false);
		sendWindow.setMovable(false);
		setFillParent(true);
		float widthMessages = 800;
		float heightMessages = 500;

		messagesWindow.add(scrollTable).width(widthMessages - 5).height(heightMessages - 5);
		sendWindow.add(textMessage).width(600).padRight(8);
		sendWindow.add(sendButton).width(100).height(50);

		add(messagesWindow).expand().width(widthMessages).height(heightMessages).top().pad(8).row();
		add(sendWindow).expand().bottom().pad(8);

		sendButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				String message = textMessage.getText();
				if(message.length() != 0) {
					Label newMessage = new Label(message, skin);
					messageTable.add(newMessage).expandX().right().padRight(20);
					chat.addMessage(newMessage, message);
				}
			}
		});
	}
}
