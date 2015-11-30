package com.project.terminkalender.calendar;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.tools.TextFieldActor;
import com.project.terminkalender.userdata.Game;
import com.project.terminkalender.websockets.AppWebSockets;

public class SetTaskInCalendarDialog extends Dialog {
	public static final String OK = "Ok";
	public static final String CANCEL = "CANCEL";
	
	private TaskCalendar task;
	private Label partnerLabel, locationLabel;
	private Array<SelectBox<String>> partnertsboxes;
	private TextFieldActor locationText;
	private Array<String> users;

	public SetTaskInCalendarDialog(String title, Skin skin) {
		super("", skin, "windowDialog");
		
		center();
		setMovable(false);
		setResizable(false);
		setModal(true);
		pad(20);
		padTop(35);
		
		task = new TaskCalendar();
		partnerLabel = new Label("Mit? ", skin);
		locationLabel = new Label("Wo? ", skin);
		
		
		
		partnertsboxes = new Array<SelectBox<String>>();
		locationText = new TextFieldActor("", skin);
		TextButton acceptButton = new TextButton("OK", skin);
		TextButton cancelButton = new TextButton("Cancel", skin, "redTextButton");
		
		Game game = AppMain.user.getGame();
		users = new Array<String>(game.getUsers());
		users.removeValue(AppMain.user.getUserName(), false);
		
		getButtonTable().defaults().width(175).height(100);
		getContentTable().padTop(40);
		getContentTable().padBottom(40);
		button(acceptButton, OK);
		button(cancelButton, CANCEL);
		
		addListener(new InputListener() {

			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					cancelTask();
					hide();
				}
				return true;
			}
		});
	}
	
	@Override
	public Dialog show(Stage stage) {
		construct();
		return super.show(stage);
	}
	
	private void construct() {
		getContentTable().clear();
		partnertsboxes.clear();
		
		getTitleLabel().setText(task.getDescription());
		int numberPartners = task.getNumberPartners();
		Array<String> partnerts = task.getPartners();
		if(numberPartners > 0) {
			getContentTable().add(partnerLabel);
			for(int index = 0; index < numberPartners; ++index) {
				SelectBox<String> partnertsBox = new SelectBox<String>(Resources.skin);
				partnertsBox.setItems(users);
				partnertsBox.setSelectedIndex(index);
				if(partnerts.size > index) {
					String partnerString = partnerts.get(index);
					partnertsBox.setSelected(partnerString);
				}
				if(index > 0) {
					Label otherPartnerLabel = new Label("und", Resources.skin);
					getContentTable().add(otherPartnerLabel);
				}
				partnertsboxes.add(partnertsBox);
				getContentTable().add(partnertsBox).width(226).right();
			}
		}
		getContentTable().row();
		locationText.setText(task.getLocation());
		getContentTable().add(locationLabel);
		getContentTable().add(locationText).width(226);
	}
	
	protected void result(Object object) {
		if(object.equals(OK)) {
			sendTaskData();
		}
		else if(object.equals(CANCEL)) {
			cancelTask();
		}
	}
	private void sendTaskData() {
		String location = locationText.getText();
		if(location.contains(AppWebSockets.POINTSPLIT) || location.contains(AppWebSockets.DATASPLIT) || 
		   location.contains(AppWebSockets.TASKSPLIT)) {
			Resources.warningDialog.show("you musn't use ',', ';', or ':'", getStage());
			cancelTask();
		}
		else {
			task.setLocation(locationText.getText());
			int numberPartners = partnertsboxes.size;
			task.clearPartners();
			for(int index = 0; index < numberPartners; ++index) {
				SelectBox<String> partnertsBox = partnertsboxes.get(index);
				task.addPartner(partnertsBox.getSelected());
			}
			task.addDataServer();
		}
	}
	private void cancelTask() {
		CalendarScreen calendarScreen = (CalendarScreen) AppMain.calendarScreen;
		calendarScreen.setTaskInSlotEmpty(task);
	}

	public void setTask(TaskCalendar task) {
		this.task = task;
	}
}
