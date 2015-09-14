package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.project.terminkalender.Main;

public class SlotActor extends TextButton implements SlotListener {
	private Slot slot;
	private SlotDescription slotDescription;
	private boolean setTSescription;

	public SlotActor(Skin skin, Slot slot) {
		super(slot.getTask().getDescription(), skin);
		setAppearance(slot);
		setTSescription = true;

		slot.addListener(this);
	}
	
	private void setAppearance(Slot slot) {
		this.slot = slot;
		if(slot.isEmpty()) {
			setText("");
			setStyle(Main.skin.get("emptyTextButtonDescription", TextButtonStyle.class));
		}
		else {
			setText(slot.getTask().getDescription());
			setStyle(Main.skin.get("fullTextButtonDescription", TextButtonStyle.class));
		}
	}

	public Slot getSlot() {
		return slot;
	}

	@Override
	public void hasChanged(Slot slot) {
		setAppearance(slot);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(setTSescription) {
			slotDescription = new SlotDescription(slot, Main.skin);
			getStage().addActor(slotDescription);
			addListener(new SlotDescriptionListener(slotDescription, true));
			setTSescription = false;
		}
	}
}
