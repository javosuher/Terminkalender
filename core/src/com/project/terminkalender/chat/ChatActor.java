package com.project.terminkalender.chat;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.tools.Pair;
import com.project.terminkalender.tools.ScrollWindow;
import com.project.terminkalender.tools.TWindow;
import com.project.terminkalender.tools.TextFieldActor;
import com.project.terminkalender.websockets.AppWebSockets;

public class ChatActor extends Table {
	public final static String LEFT = "Left";
	public final static String RIGHT = "Right";
	public final static float MAXMESSAGEWIDTH = 450;
	public final static float STARTMESSAGEWIDTH = 60;
	public final static float INSCROLL = 2;
	
	private Chat chat;
	private final TextFieldActor textMessage;
	private ScrollWindow scrollMessagesTable;
	private float notInBottom;

	public ChatActor(final Skin skin) {
		super(skin);
		chat = new Chat("", new Room());
		
		final Table messageTable = new Table(Resources.skin);
		scrollMessagesTable = new ScrollWindow("", skin, messageTable);
		Label YouLabel = new Label("Du ", Resources.skin);
		scrollMessagesTable.getTitleTable().add(YouLabel).right();
		
		TWindow sendWindow = new TWindow("", skin);
		textMessage = new TextFieldActor("", skin, this);
		ImageButton sendButton = new ImageButton(skin, "imageButtonArrowLeft");
		
		sendWindow.setMovable(false);
		float widthMessages = 600;
		float heightMessages = 450;
		
		sendWindow.add(textMessage).width(400).padRight(8);
		sendWindow.add(sendButton).width(75).height(50);
		
		add(scrollMessagesTable).expand().width(widthMessages).height(heightMessages).top().pad(4).row();
		add(sendWindow).bottom().pad(4).height(130);
		
		setBounds(230, 2, widthMessages, AppMain.HEIGHT);
		
		Label label = new Label("Choose an user", Resources.skin);
		messageTable.add(label).expandX().center();
		
		notInBottom = 0;

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
			if(message.contains(AppWebSockets.POINTSPLIT) || message.contains(AppWebSockets.DATASPLIT) || 
					message.contains(AppWebSockets.CHATSPLIT)) {
				Resources.warningDialog.show("you musn't use '=', ';', or ':'", getStage());
			}
			else if(!chat.isEmptyChat()) {
				TextButton newMessage = new TextButton(message, Resources.skin, "fullTextButtonDescription");
				addMessageButton(messageTable, newMessage, RIGHT);
				chat.addMessage(message);
			}
		}
		textMessage.setText("");
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(chat.updateMessage() && !chat.updateMessages()) {
			Table messageTable = chat.getMessageTable();
			Array<Pair<String>> messages = chat.getMessages();
			
			TextButton newMessage = new TextButton(messages.peek().getSecond(), Resources.skin, "chatUserOrange");
			addMessageButton(messageTable, newMessage, LEFT);

			chat.finishUpdateMessage();
		}
		
		if(chat.updateMessages()) {
			Table messageTable = chat.getMessageTable();
			Array<Pair<String>> messages = chat.getMessages();
			
			messageTable.clear();
			
			for(Pair<String> message : messages) {
				if(message.getFirst().equals(Chat.YOU)) {
					TextButton newMessage = new TextButton(message.getSecond(), Resources.skin, "fullTextButtonDescription");
					addMessageButton(messageTable, newMessage, RIGHT);
					
					
				}
				else {
					TextButton newMessage = new TextButton(message.getSecond(), Resources.skin, "chatUserOrange");
					addMessageButton(messageTable, newMessage, LEFT);
				}
				
			}
			
			chat.finishUpdateMessage();
			chat.finishUpdateMessages();
		}
		
		scrollBottonAct();
	}
	private void addMessageButton(Table messageTable, TextButton textButton, String side) {
		float width = MAXMESSAGEWIDTH;
		float height = STARTMESSAGEWIDTH;
		
		float labelWidth = textButton.getLabel().getWidth() + 15;
		textButton.getLabel().setWrap(true);
		int widthDiference = (int) (labelWidth / (width));
		if(widthDiference > 0) {
			++widthDiference;
			height = widthDiference * height;
			if(side.equals(LEFT)) {
				messageTable.add(textButton).expandX().left().padLeft(20).padTop(4).width(width).height(height).row();
			}
			else if(side.equals(RIGHT)) {
				messageTable.add(textButton).expandX().right().padRight(20).padTop(4).width(width).height(height).row();
			}
		}
		else {
			if(side.equals(LEFT)) {
				messageTable.add(textButton).expandX().left().padLeft(20).padTop(4).width(labelWidth).height(height).row();
			}
			else if(side.equals(RIGHT)) {
				messageTable.add(textButton).expandX().right().padRight(20).padTop(4).width(labelWidth).height(height).row();
			}
		}
		updateScroll();	
	}
	
	private void scrollBottom() {
		ScrollPane scroll = scrollMessagesTable.getScrollTable();
		scroll.setScrollY(scroll.getMaxY());
	}
	private void scrollBottonAct() {
		if(notInBottom > 0) {
			scrollBottom();
			ScrollPane scroll = scrollMessagesTable.getScrollTable();
			if(scroll.getMaxY() == scroll.getVisualScrollY() && scroll.getMaxY() != 0) {
				--notInBottom;
			}
		}
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
		scrollMessagesTable.setTable(Resources.skin, chat.getMessageTable());
		chat.startUpdateMessages();
	}
	public String getUserName() {
		return chat.getUser();
	}
	
	public void updateScroll() {
		notInBottom = INSCROLL;
	}
}
