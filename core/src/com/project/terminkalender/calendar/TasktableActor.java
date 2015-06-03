package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.project.terminkalender.Main;

public class TasktableActor extends Window {
	private final Tasktable tasktable = new Tasktable();
	
	public TasktableActor(DragAndDrop dragAndDrop, Skin skin) {
		super("Tasktable", skin);
		
		Table table = new Table();
		
		int i = 0;
		for (Slot slot : tasktable.getSlots()) {
			SlotActor slotActor = new SlotActor(skin, slot);
			dragAndDrop.addSource(new SlotSource(slotActor));
			dragAndDrop.addTarget(new SlotTarget(slotActor));
			table.add(slotActor);

			i++;
			if (i % 1 == 0) {
				table.row().padTop(2);
			}
		}
		
		final ScrollPane scroll = new ScrollPane(table, skin);

		scroll.setFlickScroll(false);
		scroll.setFadeScrollBars(false);
		add(scroll).fill().expand();
		
		setPosition(Main.WIDTH, Main.HEIGHT);
		setBounds(Main.WIDTH, Main.HEIGHT, 100, Main.HEIGHT);
		setMovable(false);
	}
}
