package fr.escape.game.entity.bonus;

import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.User;
import fr.escape.game.entity.CollisionBehavior;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.Entity;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.graphics.Texture;

abstract class AbstractBonus implements Bonus {
	
	private static int COEFFICIENT = 3;
	
	private final Texture drawable;
	private final EdgeNotifier eNotifier;
	private final KillNotifier kNotifier;
	private final CollisionBehavior collisionBehavior;
	
	private Body body;
	
	public AbstractBonus(Body body,Texture drawable, EdgeNotifier eNotifier, KillNotifier kNotifier, CollisionBehavior collisionBehavior) {
		
		this.body = Objects.requireNonNull(body);
		this.drawable = Objects.requireNonNull(drawable);
		this.eNotifier = Objects.requireNonNull(eNotifier);
		this.kNotifier = Objects.requireNonNull(kNotifier);
		this.collisionBehavior = Objects.requireNonNull(collisionBehavior);
		
	}

	@Override
	public void rotateBy(int angle) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void moveBy(float[] velocity) {
		if(body.isActive()) {
			body.setLinearVelocity(new Vec2(velocity[1], velocity[2]));
		}
	}
	
	@Override
	public void moveTo(float x, float y) {
		
		float distanceX = x - getX();
		float distanceY = y - getY();
		
		float max = Math.max(Math.abs(distanceX), Math.abs(distanceY));
		float coeff = COEFFICIENT / max;
		
		body.setLinearVelocity(new Vec2(distanceX * coeff, distanceY * coeff));
		
	}
	
	@Override
	public void setRotation(int angle) {
		throw new UnsupportedOperationException();
	}
	
	public Rectangle getEdge() {
		
		int x = CoordinateConverter.toPixelX(getX());
		int y = CoordinateConverter.toPixelY(getY());
		
		return new Rectangle(x - (drawable.getWidth() / 2), y - (drawable.getHeight() / 2), drawable.getWidth(), drawable.getHeight());
	}
	
	private float getX() {
		return body.getPosition().x;
	}

	private float getY() {
		return body.getPosition().y;
	}

	@Override
	public void draw(Graphics graphics) {
		
		int x = CoordinateConverter.toPixelX(body.getPosition().x) - (drawable.getWidth() / 2);
		int y = CoordinateConverter.toPixelY(body.getPosition().y) - (drawable.getHeight() / 2);
		
		graphics.draw(drawable, x, y);
	}
	
	@Override
	public void update(Graphics graphics, long delta) {
		draw(graphics);
		if(!eNotifier.isInside(getEdge())) {
			eNotifier.edgeReached(this);
		}
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public void setBody(Body body) {
		this.body = body;
	}

	@Override
	public void toDestroy() {
		kNotifier.destroy(this);
	}
	
	@Override
	public void collision(final User user, final Entity e, final int whois) {
		Foundation.ACTIVITY.post(new Runnable() {
			
			@Override
			public void run() {
				getCollisionBehavior().applyCollision(user, AbstractBonus.this, e, whois);
			}
			
		});	
	}
	
	CollisionBehavior getCollisionBehavior() {
		return collisionBehavior;
	}
}
