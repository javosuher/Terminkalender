package com.project.terminkalender.calendar;

import com.badlogic.gdx.utils.Array;

public class Tasktable {
	private Array<Slot> slots;

	public Tasktable() {
		int nSlots = 20;
		slots = new Array<Slot>(nSlots);
		for (int i = 0; i < nSlots; i++) {
			slots.add(new Slot(false));
		}
	}

	public Array<Slot> getSlots() {
		return slots;
	}
}
