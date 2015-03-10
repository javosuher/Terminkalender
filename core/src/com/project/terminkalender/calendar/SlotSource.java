package com.project.terminkalender.calendar;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;

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
		
		Payload payload = new Payload();
		Slot payloadSlot = new Slot();
		payloadSlot.copy(sourceSlot.isEmpty(), sourceSlot.getTask());
		sourceSlot.setEmpty();
		payload.setObject(payloadSlot);

		TextureRegion icon = payloadSlot.getImage();

		Actor dragActor = new Image(icon);
		payload.setDragActor(dragActor);

		Actor validDragActor = new Image(icon);
		payload.setValidDragActor(validDragActor);

		Actor invalidDragActor = new Image(icon);
		payload.setInvalidDragActor(invalidDragActor);

		return payload;
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
