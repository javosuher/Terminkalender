package com.project.terminkalender.calendar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.project.terminkalender.Main;

public class SlotDescriptionListener extends InputListener {

	private boolean inside;

	private Actor tooltip;
	private boolean followCursor;

	private Vector2 position = new Vector2();
	private Vector2 tmp = new Vector2();
	private Vector2 offset = new Vector2(10, 10);

	public SlotDescriptionListener(Actor tooltip, boolean followCursor) {
		this.tooltip = tooltip;
		this.followCursor = followCursor;
	}

	@Override
	public boolean mouseMoved(InputEvent event, float x, float y) {
		if (inside && followCursor) {
			event.getListenerActor().localToStageCoordinates(tmp.set(x, y));
			//tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
			tooltip.setPosition(Main.WIDTH / 2, Main.WIDTH, 0);
		}
		return false;
	}

	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		inside = true;
		tooltip.setVisible(true);
		tmp.set(x, y);
		event.getListenerActor().localToStageCoordinates(tmp);
		//tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
		tooltip.setPosition(Main.WIDTH / 2, Main.WIDTH, 0);
		tooltip.toFront();
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		inside = false;
		tooltip.setVisible(false);
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		inside = true;
		tooltip.setVisible(true);
		tmp.set(x, y);
		event.getListenerActor().localToStageCoordinates(tmp);
		//tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
		tooltip.setPosition(Main.WIDTH / 2, Main.WIDTH, 0);
		tooltip.toFront();
		return true;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		inside = true;
		tooltip.setVisible(true);
		tmp.set(x, y);
		event.getListenerActor().localToStageCoordinates(tmp);
		//tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
		tooltip.setPosition(Main.WIDTH / 2, Main.WIDTH, 0);
		tooltip.toFront();
	}

	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		inside = true;
		tooltip.setVisible(true);
		tmp.set(x, y);
		event.getListenerActor().localToStageCoordinates(tmp);
		//tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
		tooltip.setPosition(Main.WIDTH / 2, Main.WIDTH, 0);
		tooltip.toFront();
	}

	public void setOffset(float offsetX, float offsetY) {
		offset.set(offsetX, offsetY);
	}
}
