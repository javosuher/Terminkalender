package com.project.terminkalender.calendar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.project.terminkalender.AppMain;

public class SlotDescriptionListener extends DragListener {
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
			tooltip.setPosition(AppMain.WIDTH / 2 - tooltip.getWidth() / 2, AppMain.HEIGHT - 80);
		}
		return true;
	}

	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		setDescription(event, x, y, pointer);
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		setDescriptionHide();
	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		setDescription(event, x, y, pointer);;
		return true;
	}
	
	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		setDescription(event, x, y, pointer);
	}

	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		setDescription(event, x, y, pointer);
	}

	public void setOffset(float offsetX, float offsetY) {
		offset.set(offsetX, offsetY);
	}
	
	private void setDescription(InputEvent event, float x, float y, int pointer) {
		inside = true;
		tooltip.setVisible(true);
		tmp.set(x, y);
		event.getListenerActor().localToStageCoordinates(tmp);
		//tooltip.setPosition(tmp.x + position.x + offset.x, tmp.y + position.y + offset.y);
		tooltip.setPosition(AppMain.WIDTH / 2 - tooltip.getWidth() / 2, AppMain.HEIGHT - 80);
		tooltip.toFront();
	}
	public void setDescriptionVisible() {
		inside = true;
		tooltip.setVisible(true);
		tooltip.setPosition(AppMain.WIDTH / 2 - tooltip.getWidth() / 2, AppMain.HEIGHT - 80);
		tooltip.toFront();
	}
	public void setDescriptionHide() {
		inside = false;
		tooltip.setVisible(false);
	}
}
