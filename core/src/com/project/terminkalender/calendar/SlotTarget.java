package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;

public class SlotTarget extends Target {
	private Slot targetSlot;
	
	public SlotTarget(SlotActor actor) {
		super(actor);
		targetSlot = actor.getSlot();
	}

	@Override
	public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
		return true;
	}

	@Override
	public void drop(Source source, Payload payload, float x, float y, int pointer) {}

	@Override
	public void reset(Source source, Payload payload) {}

}
