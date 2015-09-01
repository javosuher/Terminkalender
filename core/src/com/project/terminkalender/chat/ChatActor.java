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
import com.project.terminkalender.tools.ScrollWindow;
import com.project.terminkalender.tools.TWindow;

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
				if(message.length() != 0) {
					Label newMessage = new Label(message, skin);
					messageTable.add(newMessage).expandX().right().padRight(20);
					chat.addMessage(message);
				}
			}
		});
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(chat.update()) {
			Table messageTable = chat.getMessageTable();
			Array<String> messages = chat.getMessages();
			
			Label newMessage = new Label(messages.peek(), Main.skin);
			messageTable.add(newMessage).expandX().left().padRight(20);
			
			chat.finishUpdate();
		}
	}
	
	public Chat getChat() {
		return chat;
	}
}
