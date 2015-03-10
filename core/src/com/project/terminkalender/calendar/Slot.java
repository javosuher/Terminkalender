package com.project.terminkalender.calendar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.project.terminkalender.Main;

public class Slot {
	private Array<SlotListener> slotListeners = new Array<SlotListener>();
	private TextureRegion image;
	private boolean empty;
	private Task task;

	public Slot(Task task) {
		setTask(task);
	}	
	public Slot() {
		setEmpty();
	}

	public boolean isEmpty() {
		return empty;
	}
	
	public void setEmpty() {
		image = new TextureRegion(Main.assets.get("EmptySlot.png", Texture.class));
		task = null;
		empty = true;
		notifyListeners();
	}
	public void setTask(Task task) {
		image = new TextureRegion(Main.assets.get("Slot.png", Texture.class));
		this.task = task;
		empty = false;
		notifyListeners();
	}
	
	public void copy(Boolean empty, Task task) {
		if(empty)
			setEmpty();
		else
			setTask(task);
	}

	public TextureRegion getImage() {
		return image;
	}
	
	public Task getTask() {
		return task;
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
