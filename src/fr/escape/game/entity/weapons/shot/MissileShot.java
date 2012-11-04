package fr.escape.game.entity.weapons.shot;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.graphics.Texture;

public final class MissileShot implements Shot {
	
	private static final String DRAWABLE_ID = "wpmissile";
	
	private final Texture drawable;
	
	private int x;
	private int y;
	
	private int drawX;
	private int drawY;
	private int angle;
	
	public MissileShot() {
		drawable = Foundation.resources.getDrawable(DRAWABLE_ID);
		x = 0;
		y = 0;
		drawX = x - (drawable.getWidth() / 2);
		drawY = y - (drawable.getHeight() / 2);
		angle = 0;
	}

	public void draw(Graphics graphics) {
		graphics.draw(drawable, drawX, drawY, angle);
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
		
		// Update Position
		this.x = x;
		this.y = y;
		
		// Update Draw Position
		this.drawX = this.x - (drawable.getWidth() / 2);
		this.drawY = this.y - (drawable.getHeight() / 2);
	}

	@Override
	public void setRotation(int angle) {
		angle = angle % 360;
		this.angle = angle;
	}
	
}
