package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;
import java.util.Objects;

import fr.escape.app.Graphics;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.graphics.Texture;

public abstract class AbstractShot implements Shot {
	
	private final Texture drawable;
	private final EdgeNotifier eNotifier;
	
	private int x;
	private int y;
	private int angle;
	
	public AbstractShot(Texture drawable, EdgeNotifier edgeNotifier) {
		this.drawable = Objects.requireNonNull(drawable);
		this.eNotifier = Objects.requireNonNull(edgeNotifier);
		this.x = 0;
		this.y = 0;
		this.angle = 0;
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
		
		if(!eNotifier.getEdge().intersects(getEdge())) {
			eNotifier.edgeReached(this);
		}
	}
	
	@Override
	public void setRotation(int angle) {
		angle = angle % 360;
		this.angle = angle;
	}
	
	@Override
	public void draw(Graphics graphics) {
		graphics.draw(drawable, (getX() - (drawable.getWidth() / 2)), (getY() - (drawable.getHeight() / 2)), getAngle());
	}
	
	// TODO Remove ?
	protected int getX() {
		return this.x;
	}
	
	// TODO Remove ?
	protected int getY() {
		return this.y;
	}
	
	// TODO Remove ?
	protected int getAngle() {
		return this.angle;
	}
	
	protected Rectangle getEdge() {
		
		int x = this.x - (drawable.getWidth() / 2);
		int y = this.y - (drawable.getHeight() / 2);
		
		return new Rectangle(x, y, x + drawable.getWidth(), y + drawable.getHeight());
	}
	
}
