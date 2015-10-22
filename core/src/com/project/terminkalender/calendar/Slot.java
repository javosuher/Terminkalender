package com.project.terminkalender.calendar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Slot {
	private Array<SlotListener> slotListeners = new Array<SlotListener>();
	private Vector2 position;
	private boolean empty;
	private TaskCalendar task;

	public Slot(TaskCalendar task) {
		setTask(task);
		noPosition();
	}
	public Slot(TaskCalendar task, Vector2 position) {
		setTask(task);
		this.position = position;
	}
	public Slot() {
		setEmpty();
		noPosition();
	}
	public Slot(Vector2 position) {
		setEmpty();
		this.position = position;
	}

	public boolean isEmpty() {
		return empty;
	}
	
	public void noPosition() {
		position = new Vector2(-1, -1);
	}
	
	public void setEmpty() {
		task = new TaskCalendar();
		empty = true;
		notifyListeners();
	}
	public void setTask(TaskCalendar task) {
		this.task = task;
		empty = false;
		notifyListeners();
	}
	
	public void copy(Boolean empty, TaskCalendar task) {
		if(empty)
			setEmpty();
		else
			setTask(task);
	}
	
	public TaskCalendar getTask() {
		return task;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public boolean hasPosition() {
		return position.x != -1 && position.y != -1;
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
