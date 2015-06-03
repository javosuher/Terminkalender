package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.project.terminkalender.Main;

public class TimetableActor extends Window {
	private final Timetable timetable = new Timetable();

	public TimetableActor(DragAndDrop dragAndDrop, Skin skin) {
		super("Timetable", skin);
		
		setPosition(0, Main.HEIGHT);
		setMovable(false);
		defaults().space(8);
		row().fill().expandX();

		int i = 0;
		for (Slot slot : timetable.getSlots()) {
			SlotActor slotActor = new SlotActor(skin, slot);
			dragAndDrop.addSource(new SlotSource(slotActor));
			dragAndDrop.addTarget(new SlotTarget(slotActor));
			add(slotActor);

			i++;
			if (i % 7 == 0) {
				row();
			}
		}
		pack();
	}
}
