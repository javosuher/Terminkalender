package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.project.terminkalender.Main;
import com.project.terminkalender.tools.TWindow;

public class TimetableActor extends TWindow {
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
			add(slotActor).width(80).height(80);

			i++;
			if (i % Timetable.COLUMN == 0) {
				row();
			}
		}
		pack();
	}
}
