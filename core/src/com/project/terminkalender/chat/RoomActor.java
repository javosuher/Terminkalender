package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.screens.ChatScreen;
import com.project.terminkalender.screens.RoomScreen;

public class RoomActor extends Table {
	private final Room room = new Room();
	
	public RoomActor(Skin skin) {
		super(skin);
		
		Window usersWindow = new Window("Users", skin);
		TextButton refreshButton = new TextButton("Refresh", skin);
		
		Table usersTable = room.getUsersTable();
		ScrollPane scrolluserWindow = new ScrollPane(usersTable, skin);
		
		usersWindow.setMovable(false);
		setFillParent(true);
		
		usersWindow.add(scrolluserWindow).width(800).height(Main.HEIGHT - 16);
		add(usersWindow).width(800).height(Main.HEIGHT - 16).expand().left().pad(8);
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
			Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
			final RoomScreen roomScreen = ((RoomScreen) Main.roomScreen);
			
			usersTable.clear();
			roomScreen.chatScreensClear();
			
			int column = 0;
			for(Chat chat : chats) {
				ChatActor chatActor = new ChatActor(skin, chat);
				roomScreen.addChatScreen(chatActor);
				
				final TextButton userButton = new TextButton(chat.getUser(), skin);
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
						Main.setNewScreen(chatScreen);
					}
				});
			}
			
			room.finishUpdate();
		}
	}
}
