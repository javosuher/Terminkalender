package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.calendar.GuideDialog;
import com.project.terminkalender.calendar.InteractionsDialog;
import com.project.terminkalender.calendar.Slot;
import com.project.terminkalender.calendar.SlotActor;
import com.project.terminkalender.calendar.TaskCalendar;
import com.project.terminkalender.calendar.TasktableActor;
import com.project.terminkalender.calendar.Timetable;
import com.project.terminkalender.calendar.TimetableActor;
import com.project.terminkalender.tools.DialogActor;
import com.project.terminkalender.tools.ReconnectButton;
import com.project.terminkalender.websockets.WebSockets;

public class CalendarScreen extends AbstractScreen {
	public static final float WIDTHTASK = 120;
	public static final float HEIGHTTASK = 75;
	
	private Background background;
	private TimetableActor timetableActor;
	private TasktableActor tasktableActor;
	private Array<TaskCalendar> tasks;
	private TextButton changeToChatButton, validateButton;
	private ImageButton guideButton, interactionButton;
	private DialogActor interactionDialog, guideDialog;
	private ReconnectButton reconnectButton;
	private boolean closeGame, inCalendar;

	public CalendarScreen(Viewport viewport, SpriteBatch batch) {
		super(viewport, batch);
		
		TextureRegion backgroundTexture = new TextureRegion(Resources.assets.get("background.png", Texture.class));
		DragAndDrop dragAndDrop = new DragAndDrop();
		
		background = new Background(backgroundTexture);
		timetableActor = new TimetableActor(dragAndDrop, Resources.skin);
		tasktableActor = new TasktableActor(dragAndDrop, Resources.skin);
		reconnectButton = Resources.reconnectButton;
		tasks = new Array<TaskCalendar>();
		Array<Slot> slots = tasktableActor.getTasktable().getSlots();
		for(Slot slot : slots) {
			tasks.add(slot.getTask());
		}
		changeToChatButton = new TextButton("Chat", Resources.skin);
		validateButton = new TextButton("Aktivität beendet!", Resources.skin, "greenTextButton");
		interactionButton = new ImageButton(Resources.skin, "interactionButton");
		guideButton = new ImageButton(Resources.skin, "guideButton");
		interactionDialog = new InteractionsDialog("", Resources.skin);
		guideDialog = new GuideDialog("", Resources.skin);
		closeGame = false;
		inCalendar = false;
		
		stage.addActor(background);
		stage.addActor(timetableActor);
		stage.addActor(tasktableActor);
		stage.addActor(changeToChatButton);
		stage.addActor(validateButton);
		stage.addActor(interactionButton);
		stage.addActor(guideButton);
		
		changeToChatButton.setBounds(15, 2, 135, 66);
		validateButton.setBounds(510, 2, 260, 66);
		interactionButton.setBounds(340, 2, 100, 66);
		guideButton.setBounds(220, 2, 100, 66);
		
		changeToChatButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				ChatNormal();
				AppMain.setNewScreen(AppMain.chatScreen);
			}
		});
		
		validateButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				AppMain.webSockets.validateCalendarData();
			}
		});
		
		interactionButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				interactionDialog.show(stage);
			}
		});
		
		guideButton.addListener(new ClickListener() {

			@Override 
			public void clicked(InputEvent event, float x, float y){
				guideDialog.show(stage);
			}
		});
		
		stage.addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					Resources.exitDialogGame.show(stage);
				}
				return true;
			}
		});
	}
	
	public void updateTasks(Array<String> tasksArray) {
		Array<Slot> tasksTableSlots = tasktableActor.getTasktable().getSlots();
		Array<Slot> timeTableSlot = timetableActor.getTimetable().getSlots();
		Array<Slot> updateTasks = new Array<Slot>();
		for(String task : tasksArray) {
			String [] taskSplit = task.split(WebSockets.DATASPLIT);
			for(int index = 0; index < tasksTableSlots.size; ++index) {
				TaskCalendar originalTask = tasksTableSlots.get(index).getTask();
				if(taskSplit[0].equals(originalTask.getDescription())) {
					originalTask.setLocation(taskSplit[1]);
					String [] taskPosition = taskSplit[2].split(WebSockets.SPLIT);
					originalTask.setPositionCalendar(taskPosition[0], taskPosition[1]);
					if(taskSplit.length > 3) {
						Array<String> taskPartners = new Array<String>(taskSplit[3].split(WebSockets.SPLIT));
						originalTask.setPartners(taskPartners);
					}
					if(taskSplit.length > 4) {
						originalTask.setWhat(taskSplit[4]);
					}
					if(taskSplit.length > 5) {
						originalTask.setWhere(taskSplit[5]);
					}
					updateTasks.add(tasksTableSlots.get(index));
					index = tasksTableSlots.size;
				}
			}
		}
		for(Slot slot : updateTasks) {
			Slot slotNewPosition = timeTableSlot.get((int) slot.getTask().getPosition().x * Timetable.COLUMN + (int) slot.getTask().getPosition().y);
			slotNewPosition.copy(slot.isEmpty(), slot.getTask());
			slot.setEmpty();
		}
	}
	public void validateTasks(Array<String> wrongTasks) {
		Array <SlotActor> slotActors = timetableActor.getSlotActors();
		if(!wrongTasks.get(0).equals("")) {
			for(String wrongTaskDescription : wrongTasks) {
				for(int index = 0; index < slotActors.size; ++index) {
					SlotActor slotActor = slotActors.get(index);
					if(wrongTaskDescription.equals(slotActor.getSlot().getTask().getDescription())) {
						slotActor.setStyle(Resources.skin.get("textButtonTaskRed", TextButtonStyle.class));
						index = slotActors.size;
					}
				}
			}
		}
	}	
	public boolean setTaskInSlotEmpty(TaskCalendar task) {
		Array<Slot> tasksTableSlots = tasktableActor.getTasktable().getSlots();
		for(int taskTableIndex = 0; taskTableIndex < tasksTableSlots.size; ++taskTableIndex) {
			Slot slot = tasksTableSlots.get(taskTableIndex);
			if(slot.isEmpty()) {
				Slot emptySlot = task.getSlot();
				slot.copy(task.getSlot().isEmpty(), task);
				emptySlot.setEmpty();
				return true;
			}
		}
		return false;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.addActor(reconnectButton);
		inCalendar = true;
	} 
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		reconnectButton.remove();
		inCalendar = false;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(closeGame) {
			AppMain.setNewScreen(AppMain.loginScreen);
			Resources.warningDialog.show("Spiel momentan nicht verfügbar", AppMain.loginGamesScreen.getStage());
			ChatScreen chatScreen = (ChatScreen) AppMain.chatScreen;
			chatScreen.closeGameFalse();
			closeGame = false;
		}
		
		stage.act(delta);
		stage.draw();
	}
	
	public void closeGame() {
		closeGame = true;
	}
	public void closeGameFalse() {
		closeGame = false;
	}
	
	public boolean inCalendar() {
		return inCalendar;
	}
	
	public void ChatNotification() {
		changeToChatButton.setStyle(Resources.skin.get("orangeTextButton", TextButtonStyle.class));
	}
	public void ChatNormal() {
		changeToChatButton.setStyle(Resources.skin.get("default", TextButtonStyle.class));
	}
	
	public Array<Slot> getCalendarSlots() {
		return timetableActor.getTimetable().getSlots();
	}
}
