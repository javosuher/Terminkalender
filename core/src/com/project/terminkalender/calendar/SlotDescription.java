package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.project.terminkalender.Resources;

public class SlotDescription extends Window implements SlotListener {
	private Slot slot;

	public SlotDescription(Slot slot, Skin skin) {
		super("", skin, "windowDescriptionGreen");
		this.slot = slot;
		hasChanged(slot);
		slot.addListener(this);
		setVisible(false);
	}

	@Override
	public void hasChanged(Slot slot) {
		if (slot.isEmpty()) {
			setVisible(false);
			return;
		}
		
		//setTitle("Task description");
		clear();
		Label label = new Label(slot.getTask().getDescription(), Resources.skin);
		add(label);
		pack();
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (slot.isEmpty()) {
			super.setVisible(false);
		}
	}

}
