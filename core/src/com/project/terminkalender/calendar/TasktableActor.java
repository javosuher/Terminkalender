package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.tools.ScrollWindow;

public class TasktableActor extends ScrollWindow {
	private final Tasktable tasktable = new Tasktable();
	
	public TasktableActor(DragAndDrop dragAndDrop, Skin skin) {
		super("Tasktable", skin);
		
		Table table = new Table();
		setTable(skin, table);
		
		for (Slot slot : tasktable.getSlots()) {
			SlotActor slotActor = new SlotActor(skin, slot);
			dragAndDrop.addSource(new SlotSource(slotActor));
			dragAndDrop.addTarget(new SlotTarget(slotActor));
			table.add(slotActor).width(80).height(80);

			table.row().padTop(2);
		}

		getScrollTable().setFlickScroll(false);
		getScrollTable().setFadeScrollBars(false);
		
		setPosition(AppMain.WIDTH, AppMain.HEIGHT);
		setBounds(AppMain.WIDTH, AppMain.HEIGHT, 100, AppMain.HEIGHT);
		setMovable(false);
		
		setWidth(125);
		setHeight(AppMain.HEIGHT);
		pad(8);
	}
}
