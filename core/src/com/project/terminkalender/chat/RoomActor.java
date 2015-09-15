package com.project.terminkalender.chat;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.tools.ScrollWindow;

public class RoomActor extends Table {
	private final Room room = new Room();
	private ChatActor chatActor;
	
	public RoomActor(Skin skin, ChatActor chatActor) {
		super(skin);
		this.chatActor = chatActor;
		
		ScrollWindow usersWindow = new ScrollWindow("Users", skin, room.getUsersTable());
		TextButton updateUsersButton = new TextButton("Update Users", skin);
		
		usersWindow.setMovable(false);
		
		add(usersWindow).width(150).height(Main.HEIGHT - 80).expand().pad(8).row();
		add(updateUsersButton).width(135).height(65).padBottom(10);
		
		room.refreshUsers();
		
		updateUsersButton.addListener(new ClickListener() {

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
			
			usersTable.clear();
			
			for(final Chat chat : chats) {
				float width = 115;
				float height = 100;
				
				final TextButton userButton = chat.getTextButton();
				float labelHeight = (userButton.getLabel().getWidth() / userButton.getWidth() * userButton.getLabel().getHeight() + height);
				userButton.getLabel().setWrap(true);
				if(userButton.getLabel().getWidth() > width) {
					height = labelHeight;
				}
				
				usersTable.add(userButton).width(width).height(height).pad(5);
				usersTable.row();
				
				userButton.addListener(new ClickListener() {

					@Override 
					public void clicked(InputEvent event, float x, float y) {
						chatActor.setChat(chat);
						chat.setTextButton(userButton);
						Main.webSockets.askChatFromUser(chat.getUser(), chat.getMessagesSize());
					}
				});
			}
			
			room.finishUpdate();
		}
	}
}
