package com.project.terminkalender;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Calendar {
	private Texture textureGap, textureButton;
	private TextureRegion gapTexture1, gapTexture2, gapTexture3;
	private Rectangle gapRectangle1, gapRectangle2, gapRectangle3;
	private Button button;
	
	public Calendar() {
		textureGap = new Texture("button.png");
		textureButton = new Texture("buttonR.png");
		gapTexture1 = new TextureRegion(textureGap);
		gapTexture2 = new TextureRegion(textureGap);
		gapTexture3 = new TextureRegion(textureGap);
		gapRectangle1 = new Rectangle(100, 100, textureGap.getWidth(), textureGap.getHeight());
		gapRectangle2 = new Rectangle(100, 250, textureGap.getWidth(), textureGap.getHeight());
		gapRectangle3 = new Rectangle(100, 400, textureGap.getWidth(), textureGap.getHeight());
		button = new Button(textureButton, 600, 250);
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(gapTexture1, gapRectangle1.getX(), gapRectangle1.getY());
		batch.draw(gapTexture2, gapRectangle2.getX(), gapRectangle2.getY());
		batch.draw(gapTexture3, gapRectangle3.getX(), gapRectangle3.getY());
		button.draw(batch);
	}
	
	public void update() {
		
	}
}
