package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.project.terminkalender.Main;

public class SlotTarget extends Target {
	private Slot targetSlot;
	private SetTaskInCalendarDialog dialog;

	public SlotTarget(SlotActor actor) {
		super(actor);
		targetSlot = actor.getSlot();
		dialog = new SetTaskInCalendarDialog("Hola", Main.skin);
	}

	@Override
	public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
		//Slot payloadSlot = (Slot) payload.getObject();
		//getActor().setColor(Color.RED);
		return true;
	}

	@Override
	public void drop(Source source, Payload payload, float x, float y, int pointer) {
		if(targetSlot.hasPosition()) {
			dialog.show(Main.calendarScreen.getStage());
		}
	}

	@Override
	public void reset(Source source, Payload payload) {}

}
