package fr.escape.game.entity.weapons.shot;

import fr.escape.app.Graphics;
import fr.escape.graphics.Texture;

public abstract class AbstractShot implements Shot {
	
	private final Texture drawable;
	
	private int x;
	private int y;
	private int angle;
	
	public AbstractShot(Texture drawable) {
		this.drawable = drawable;
		this.x = 0;
		this.y = 0;
		angle = 0;
	}
	
	@Override
	public void moveBy(int x, int y) {
		this.setPosition(this.x + x, this.y + y);
	}

	@Override
	public void rotateBy(int angle) {
		this.setRotation(this.angle + angle);
	}
	
	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void setRotation(int angle) {
		angle = angle % 360;
		this.angle = angle;
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.draw(drawable, (getX() - (drawable.getWidth() / 2)), (getY() - (drawable.getHeight() / 2)), angle);
	}
	
	// TODO Remove ?
	int getX() {
		return this.x;
	}
	
	// TODO Remove ?
	int getY() {
		return this.y;
	}
	
	// TODO Remove ?
	int getAngle() {
		return this.angle;
	}
	
}
