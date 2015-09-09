package com.project.terminkalender.calendar;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.project.terminkalender.Main;

public class SlotActor extends ImageButton implements SlotListener {
	private Slot slot;
	private SlotDescription tooltip;
	private boolean setTSescription;

	public SlotActor(Skin skin, Slot slot) {
		super(createStyle(skin, slot));
		this.slot = slot;
		setTSescription = true;

		slot.addListener(this);
	}

	private static ImageButtonStyle createStyle(Skin skin, Slot slot) {
		TextureRegion image = slot.getImage();
		ImageButtonStyle style = new ImageButtonStyle(skin.get("calender", ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(image);
		style.imageDown = new TextureRegionDrawable(image);

		return style;
	}

	public Slot getSlot() {
		return slot;
	}

	@Override
	public void hasChanged(Slot slot) {
		setStyle(createStyle(Main.skin, slot));
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(setTSescription) {
			tooltip = new SlotDescription(slot, Main.skin);
			getStage().addActor(tooltip);
			addListener(new SlotDescriptionListener(tooltip, true));
			setTSescription = false;
		}
	}
}
