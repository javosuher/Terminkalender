package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.tools.DialogActor;

public class SetTaskInCalendarDialog extends DialogActor {

	public SetTaskInCalendarDialog(String title, Skin skin) {
		super(title, skin);
		
		Label partnerLabel = new Label("Partner", skin);
		SelectBox<String> partnertsBox = new SelectBox<String>(skin);
		TextButton acceptButton = new TextButton("Accept: ", skin);
		
		getButtonTable().defaults().width(150).height(50);
		Array<String> array = new Array<String>();
		array.add("Pepe");
		array.add("Juan");
		array.add("Maria");
		partnertsBox.setItems(array);
		
		getContentTable().padTop(40);
		getContentTable().add(partnerLabel);
		getContentTable().add(partnertsBox);
		getContentTable().pad(40);
		
		button(acceptButton, "accept");
	}
	
	protected void result(Object object) {
		
	}
}
