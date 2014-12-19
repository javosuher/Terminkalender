package com.project.terminkalender;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
	private TextureRegion texture;
	private Rectangle rectangle;
	private float xMinima;
	private float yMinima;
	private float xMaxima;
	private float yMaxima;
	private Vector2 position;
	private boolean pressed;
	
	public Button(Texture texture, int x, int y) {
		this.texture = new TextureRegion(texture);
		rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
		int altoPantalla = Gdx.graphics.getHeight();
		xMinima = x;
		yMaxima = altoPantalla - y;
		xMaxima = x + rectangle.width;
		yMinima = altoPantalla - (y + rectangle.height);
		position = new Vector2();
		pressed = false;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}
	
	public void update() {
		if(buttonIsPressed()) {
			if(!pressed) {
				pressed = true;
				position.set(Gdx.input.getX(), Gdx.graphics.getHeight() - (Gdx.input.getY() + rectangle.height));
			}
			
		}
		else
			pressed = false;
	}
	
	private boolean buttonIsPressed() {
		return Gdx.input.isTouched() && Gdx.input.getX() >= xMinima && Gdx.input.getX() <= xMaxima
				&& Gdx.input.getY() >= yMinima && Gdx.input.getY() <= yMaxima;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}
}