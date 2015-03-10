package com.project.terminkalender;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Background extends Actor {
	private final static float SPEED = 0.5f;
	private final static float OUTOFRANGE = Main.HEIGHT;
	private TextureRegion backgroundTop, backgroundBot;
	private float xTop, yTop, xBot, yBot;
	
	public Background(TextureRegion texture) {
		backgroundTop = texture;
		backgroundBot = texture;
		setTopPosition(0, 0);
		setBotPosition(0, -Main.HEIGHT);
		setSize(Main.WIDTH, Main.HEIGHT);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(backgroundTop, xTop, yTop, getWidth(), getHeight());
		batch.draw(backgroundBot, xBot, yBot, getWidth(), getHeight());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(yTop >= OUTOFRANGE)
			setTopPosition(0, -Main.HEIGHT);
		if(yBot >= OUTOFRANGE)
			setBotPosition(0, -Main.HEIGHT);
		
		yTop += SPEED;
		yBot += SPEED;
	}
	
	public void setTopPosition(float x, float y) {
		xTop = x;
		yTop = y;
	}
	
	public void setBotPosition(float x, float y) {
		xBot = x;
		yBot = y;
	}
}
