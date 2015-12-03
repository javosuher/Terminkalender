package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.tools.TWindow;

public class TimetableActor extends TWindow {
	private final Timetable timetable = new Timetable();
	private Array<SlotActor> slotActors; 

	public TimetableActor(DragAndDrop dragAndDrop, Skin skin) {
		super("Kalender", skin);
		slotActors = new Array<SlotActor>();
		
		setPosition(2, 70);
		setMovable(false);
		defaults().space(8);
		row().fill().expandX();
		
		Array<String> dayTime = new Array<String>(4);
		dayTime.add("Mittwoch");
		dayTime.add("Donnerstag");
		dayTime.add("Freitag");
		dayTime.add("Samstag");
		dayTime.add("Sonntag");
		int dayTimeIndex = 0;
		
		add(new Label("", skin));
		add(new Label("Morgens", skin));
		add(new Label(" Mittags", skin));
		add(new Label("Nachmittags", skin));
		add(new Label(" Abends", skin));
		row();
		add(new Label(dayTime.get(dayTimeIndex), skin));
		++dayTimeIndex;
		
		int index = 0;
		for (Slot slot : timetable.getSlots()) {
			SlotActor slotActor = new SlotActor(skin, slot);
			slotActors.add(slotActor);
			dragAndDrop.addSource(new SlotSource(slotActor));
			dragAndDrop.addTarget(new SlotTarget(slotActor));
			dragAndDrop.setDragActorPosition(-CalendarScreen.WIDTHTASK / 2, CalendarScreen.HEIGHTTASK / 2);
			if(index % Timetable.COLUMN == 0) {
				add(slotActor).width(CalendarScreen.WIDTHTASK).height(CalendarScreen.HEIGHTTASK).padRight(30);
			}
			else add(slotActor).width(CalendarScreen.WIDTHTASK).height(CalendarScreen.HEIGHTTASK).padRight(8);

			++index;
			if(index % Timetable.COLUMN == 0) {
				row();
				if(dayTimeIndex < dayTime.size) {
					add(new Label(dayTime.get(dayTimeIndex), skin));
					++dayTimeIndex;
				}
			}
		}
		pack();
	}

	public Timetable getTimetable() {
		return timetable;
	}

	public Array<SlotActor> getSlotActors() {
		return slotActors;
	}
}
