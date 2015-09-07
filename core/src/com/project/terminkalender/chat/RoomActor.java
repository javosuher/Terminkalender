package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.screens.ChatScreen;
import com.project.terminkalender.screens.RoomScreen;
import com.project.terminkalender.tools.ScrollWindow;

public class RoomActor extends Table {
	private final Room room = new Room();
	
	public RoomActor(Skin skin) {
		super(skin);
		
		ScrollWindow usersWindow = new ScrollWindow("Users", skin, room.getUsersTable());
		TextButton refreshButton = new TextButton("Refresh", skin);
		
		usersWindow.setMovable(false);
		setFillParent(true);
		
		add(usersWindow).width(200).height(Main.HEIGHT - 91).expand().left().pad(8).row();
		add(refreshButton).width(150).height(75).expand().right().pad(8);
		
		room.refreshUsers();
		
		refreshButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				room.refreshUsers();
			}
		});
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(room.update()) {
			Table usersTable = room.getUsersTable();
			Array<Chat> chats = room.getChats();
			final RoomScreen roomScreen = ((RoomScreen) Main.roomScreen);
			
			usersTable.clear();
			roomScreen.chatScreensClear();
			
			int column = 0;
			for(final Chat chat : chats) {
				ChatActor chatActor = new ChatActor(Main.skin, chat);
				roomScreen.addChatScreen(chatActor);
				
				final TextButton userButton = new TextButton(chat.getUser(), Main.skin);
				usersTable.add(userButton).width(200).height(100).pad(30);
				
				++column;
				if(column % 3 == 0) {
					usersTable.row();
				}
				
				userButton.addListener(new ClickListener() {

					@Override 
					public void clicked(InputEvent event, float x, float y) {
						String user = userButton.getText().toString();
						ChatScreen chatScreen = roomScreen.getChatScreen(user);
						Main.webSockets.askChatFromUser(chat.getUser(), chat.getMessagesSize());
						Main.setNewScreen(chatScreen);
					}
				});
			}
			
			room.finishUpdate();
		}
	}
}
