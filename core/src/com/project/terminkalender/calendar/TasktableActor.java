package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.screens.CalendarScreen;
import com.project.terminkalender.tools.ScrollWindow;

public class TasktableActor extends ScrollWindow {
	private final Tasktable tasktable = new Tasktable();
	
	public TasktableActor(DragAndDrop dragAndDrop, Skin skin) {
		super("Tasks", skin);
		
		Table table = new Table();
		setTable(skin, table, "scrollPaneWindowTasksCalendar");
		
		for (Slot slot : tasktable.getSlots()) {
			SlotActor slotActor = new SlotActor(skin, slot);
			dragAndDrop.addSource(new SlotSource(slotActor));
			dragAndDrop.addTarget(new SlotTarget(slotActor));
			table.add(slotActor).width(CalendarScreen.WIDTHTASK).height(CalendarScreen.HEIGHTTASK);

			table.row().padTop(5);
		}

		getScrollTable().setFlickScroll(false);
		getScrollTable().setFadeScrollBars(false);
		
		setBounds(AppMain.WIDTH - 212, 70, 210, AppMain.HEIGHT - 72);
		setMovable(false);
		pad(8);
	}

	public Tasktable getTasktable() {
		return tasktable;
	}
}
