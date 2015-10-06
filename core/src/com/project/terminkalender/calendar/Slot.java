package com.project.terminkalender.calendar;

import com.badlogic.gdx.utils.Array;

public class Slot {
	private Array<SlotListener> slotListeners = new Array<SlotListener>();
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
		task = new Task();
		empty = true;
		notifyListeners();
	}
	public void setTask(Task task) {
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
