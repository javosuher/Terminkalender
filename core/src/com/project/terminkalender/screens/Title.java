package com.project.terminkalender.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.project.terminkalender.AppMain;
import com.project.terminkalender.Resources;

public class Title extends Actor {
	private TextureRegion image;

	public Title() {
		image = new TextureRegion(Resources.assets.get("logo.png", Texture.class));	
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(image, AppMain.WIDTH / 2 - image.getTexture().getWidth() / 2 , 
				AppMain.HEIGHT - 150, image.getTexture().getWidth(), image.getTexture().getHeight());
	}
}
