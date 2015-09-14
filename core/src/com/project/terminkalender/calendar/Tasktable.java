package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;
import com.project.terminkalender.login.GameOpen;

public class Tasktable {
	private Array<Slot> slots;
	private Table table;

	public Tasktable() {
		GameOpen game = Main.user.getGame();
		table = new Table();
		int nSlots = game.getTasks().size;
		slots = new Array<Slot>(nSlots);
		for (int i = 0; i < nSlots; i++) {
			slots.add(new Slot(new Task("Soy una MARIPOSA")));
		}
	}

	public Array<Slot> getSlots() {
		return slots;
	}
	public Table getTable() {
		return table;
	}
}
