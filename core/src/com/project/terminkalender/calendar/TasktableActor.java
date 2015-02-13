package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.project.terminkalender.Main;

public class TasktableActor extends Window {
	
	public TasktableActor(Tasktable tasktable, DragAndDrop dragAndDrop, Skin skin) {
		super("Tasktable", skin);

		//TextButton closeButton = new TextButton("X", skin);
		//closeButton.addListener(new HidingClickListener(this));
		//getButtonTable().add(closeButton).height(getPadTop());
		//ScrollPane scroll = new ScrollPane(getButtonTable());
		//scroll.setFillParent(false);
		
		Table table = new Table();
		
		int i = 0;
		for (Slot slot : tasktable.getSlots()) {
			SlotActor slotActor = new SlotActor(skin, slot);
			dragAndDrop.addSource(new SlotSource(slotActor));
			dragAndDrop.addTarget(new SlotTarget(slotActor));
			table.add(slotActor);

			i++;
			if (i % 3 == 0) {
				table.row();
			}
		}
		
		final ScrollPane scroll = new ScrollPane(table, skin);

		scroll.setFlickScroll(false);
		//setFillParent(true);
		add(scroll);
		
		setPosition(Main.WIDTH, Main.HEIGHT);
		setMovable(false);
		defaults().space(8);
		row().fill().expandX();
	}
}
