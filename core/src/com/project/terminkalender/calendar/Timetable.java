package com.project.terminkalender.calendar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Timetable {
	public static final int COLUMN = 5;
	public static final int NPOSITION = 20;
		
	private Array<Slot> slots;

	public Timetable() {
		slots = new Array<Slot>(NPOSITION);
		
		int i = -1;
		for (int index = 0; index < NPOSITION; index++) {
			int j = index % COLUMN;
			if(j == 0) {
				++i;
			}
			slots.add(new Slot(new Vector2(i, j)));
		}
	}
	
	public Array<Slot> getSlots() {
		return slots;
	}
}
