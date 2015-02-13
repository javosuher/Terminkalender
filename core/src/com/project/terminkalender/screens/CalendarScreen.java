package com.project.terminkalender.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.project.terminkalender.Main;
import com.project.terminkalender.calendar.Slot;
import com.project.terminkalender.calendar.SlotActor;
import com.project.terminkalender.calendar.SlotSource;
import com.project.terminkalender.calendar.SlotTarget;
import com.project.terminkalender.calendar.Tasktable;
import com.project.terminkalender.calendar.TasktableActor;
import com.project.terminkalender.calendar.Timetable;
import com.project.terminkalender.calendar.TimetableActor;

public class CalendarScreen extends AbstractScreen {
	private TimetableActor timetableActor;
	private TasktableActor tasktableActor;
	private Window w;

	public CalendarScreen(Main main) {
		super();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = Main.assets.get("skins/uiskin.json", Skin.class);
		DragAndDrop dragAndDrop = new DragAndDrop();
		timetableActor = new TimetableActor(new Timetable(), dragAndDrop, skin);
		tasktableActor = new TasktableActor(new Tasktable(), dragAndDrop, skin);
		stage.addActor(timetableActor);
		stage.addActor(tasktableActor);
	} 
	
	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
}
