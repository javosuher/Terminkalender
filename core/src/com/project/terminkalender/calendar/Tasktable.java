package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Tasktable {
	private Array<Slot> slots;
	private Table table;

	public Tasktable() {
		table = new Table();
		Array<String> tasks = Main.user.getGame().getTasks();
		slots = new Array<Slot>(tasks.size);
		
		for (String task : tasks) {
			slots.add(new Slot(new TaskCalendar(task)));
		}
	}
	
	public Array<Slot> getSlots() {
		return slots;
	}
	public Table getTable() {
		return table;
	}
}
