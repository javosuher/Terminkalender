package com.project.terminkalender.calendar;

import com.badlogic.gdx.utils.Array;

public class Timetable {
	private Array<Slot> slots;

	public Timetable() {
		int nSlots = 42;
		slots = new Array<Slot>(nSlots);
		for (int i = 0; i < nSlots; i++) {
			slots.add(new Slot());
		}
	}

	public Array<Slot> getSlots() {
		return slots;
	}
}
