package com.project.terminkalender.calendar;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.project.terminkalender.Main;

public class SlotSource extends Source {
	private Slot sourceSlot;

	public SlotSource(SlotActor actor) {
		super(actor);
		this.sourceSlot = actor.getSlot();
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
			
			TextButton button = new TextButton(payloadSlot.getTask().getShortDescription(), Main.skin, "fullTextButtonDescription");
			button.setWidth(80);
			button.setHeight(80);

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
		} else {
			sourceSlot.copy(payloadSlot.isEmpty(), payloadSlot.getTask());
		}
	}
}
