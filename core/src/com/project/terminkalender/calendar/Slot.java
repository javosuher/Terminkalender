package com.project.terminkalender.calendar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Slot {
	private Array<SlotListener> slotListeners = new Array<SlotListener>();
	private TextureRegion image;
	private boolean empty;

	public Slot(boolean empty) {
		setEmpty(empty);
	}

	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty(boolean empty) {
		if (empty) {
			image = new TextureRegion(Main.assets.get("EmptySlot.png", Texture.class));
		} else {
			image = new TextureRegion(Main.assets.get("Slot.png", Texture.class));
		}
		this.empty = empty;
		notifyListeners();
	}

	public TextureRegion getImage() {
		return image;
	}
	public void addListener(SlotListener slotListener) {
		slotListeners.add(slotListener);
	}

	public void removeListener(SlotListener slotListener) {
		slotListeners.removeValue(slotListener, true);
	}

	private void notifyListeners() {
		for (SlotListener slotListener : slotListeners) {
			slotListener.hasChanged(this);
		}
	}
}
