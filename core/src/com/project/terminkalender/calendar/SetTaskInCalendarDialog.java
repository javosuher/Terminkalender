package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.tools.DialogActor;
import com.project.terminkalender.userdata.Game;
import com.project.terminkalender.websockets.AppWebSockets;

public class SetTaskInCalendarDialog extends DialogActor {
	private TaskCalendar task;
	private Label partnerLabel, locationLabel;
	private Array<SelectBox<String>> partnertsboxes;
	private TextField locationText;
	private Array<String> users;

	public SetTaskInCalendarDialog(String title, Skin skin) {
		super(title, skin);
		
		task = new TaskCalendar();
		partnerLabel = new Label("Partner: ", skin);
		locationLabel = new Label("Place: ", skin);
		partnertsboxes = new Array<SelectBox<String>>();
		locationText = new TextField("", skin);
		TextButton acceptButton = new TextButton("Accept", skin);
		
		Game game = AppMain.user.getGame();
		users = new Array<String>(game.getUsers());
		users.removeValue(AppMain.user.getUserName(), false);
		
		getButtonTable().defaults().width(150).height(50);
		getContentTable().padTop(40);
		getContentTable().padBottom(40);
		button(acceptButton, "accept");
	}
	
	@Override
	public Dialog show(Stage stage) {
		construct();
		return super.show(stage);
	}
	
	private void construct() {
		getContentTable().clear();
		partnertsboxes.clear();
		
		int numberPartners = task.getNumberPartners();
		Array<String> partnerts = task.getPartners();
		for(int index = 0; index < numberPartners; ++index) {
			SelectBox<String> partnertsBox = new SelectBox<String>(Resources.skin);
			partnertsBox.setItems(users);
			if(partnerts.size > index) {
				String partnerString = partnerts.get(index);
				partnertsBox.setSelected(partnerString);
			}
			partnertsboxes.add(partnertsBox);
		}
		locationText.setText(task.getLocation());

		if(numberPartners > 0) {
			getContentTable().add(partnerLabel);
			for(SelectBox<String> partnertsBox : partnertsboxes) {
				getContentTable().add(partnertsBox).right();
			}
			getContentTable().row();
		}
		getContentTable().add(locationLabel);
		getContentTable().add(locationText);
	}
	
	protected void result(Object object) {
		String location = locationText.getText();
		if(location.contains(AppWebSockets.POINTSPLIT) || location.contains(AppWebSockets.DATASPLIT) || 
		   location.contains(AppWebSockets.TASKSPLIT)) {
			Resources.warningDialog.show("you musn't use ',', ';', or ':'", getStage());
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

	public void setTask(TaskCalendar task) {
		this.task = task;
	}
}
