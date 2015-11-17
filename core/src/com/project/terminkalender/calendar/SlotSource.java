package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;
import com.project.terminkalender.screens.CalendarScreen;

public class SlotSource extends Source {
	private Slot sourceSlot;
	private SetTaskInCalendarDialog dialog;

	public SlotSource(SlotActor actor) {
		super(actor);
		this.sourceSlot = actor.getSlot();
		this.dialog = new SetTaskInCalendarDialog("", Resources.skin);
	}

	@Override
	public Payload dragStart(InputEvent event, float x, float y, int pointer) {
		if (sourceSlot.isEmpty()) {
			return null;
		}
		else {
			Payload payload = new Payload();
			Slot payloadSlot = new Slot();
			payloadSlot.copy(sourceSlot.isEmpty(), sourceSlot.getTask());
			sourceSlot.setEmpty();
			payload.setObject(payloadSlot);
			
			TextButton button = new TextButton(payloadSlot.getTask().getShortDescription(), Resources.skin, "fullTextButtonDescription");
			button.setWidth(CalendarScreen.WIDTHTASK);
			button.setHeight(CalendarScreen.HEIGHTTASK);

			Actor dragActor = button;
			payload.setDragActor(dragActor);

			Actor validDragActor = button;
			payload.setValidDragActor(validDragActor);

			Actor invalidDragActor = button;
			payload.setInvalidDragActor(invalidDragActor);

			return payload;
		}
	}

	@Override
	public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
		Slot payloadSlot = (Slot) payload.getObject();
		if (target != null) {
			Slot targetSlot = ((SlotActor) target.getActor()).getSlot();
			sourceSlot.copy(targetSlot.isEmpty(), targetSlot.getTask());
			targetSlot.copy(payloadSlot.isEmpty(), payloadSlot.getTask());
			
			if(targetSlot.hasPosition()) {
				dialog.setTask(targetSlot.getTask());
				dialog.show(AppMain.calendarScreen.getStage());
			}
		} else {
			sourceSlot.copy(payloadSlot.isEmpty(), payloadSlot.getTask());
		}
	}
}
