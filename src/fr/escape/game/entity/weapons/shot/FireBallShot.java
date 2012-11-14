package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

//TODO Comment
public final class FireBallShot extends AbstractShot {

	private static final int FIREBALL_SPEED = 4000;
	private static final float MINIMAL_RADIUS = 0.4f;
	
	private final Texture coreBall;
	private final Texture radiusEffect;
	
	private boolean isVisible;
	private boolean radiusGrown;
	private float radiusSize;
	private long timer;
	
	public FireBallShot(Body body, EntityContainer container, ShotCollisionBehavior collisionBehavior) {
		super(body, container, container, collisionBehavior, 3);
		
		this.coreBall = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT);
		this.radiusEffect = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL_RADIUS_SHOT);
		
		this.isVisible = false;
		this.radiusGrown = false;
		this.radiusSize = MINIMAL_RADIUS;
	}

	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {
			drawCoreBall(graphics);
			drawRadiusEffect(graphics, (int) (timer % 100)*10);
			graphics.draw(getEdge(), Color.RED);
		}
	}

	@Override
	public void update(Graphics graphics, long delta) {
		
		timer += delta;
		draw(graphics);
		
		if(radiusGrown) {
			radiusSize = getRadiusEffectSize();
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(radiusSize, radiusSize);

			getBody().getFixtureList().m_shape = shape;
		}
		
		if(!getEdgeNotifier().isInside(getEdge())) {
			getEdgeNotifier().edgeReached(this);
		}
		
	}

	@Override
	public void receive(int message) {
		switch(message) {
			case Shot.MESSAGE_LOAD: {
				isVisible = true;
				radiusGrown = true;
				break;
			}
			case Shot.MESSAGE_FIRE: {
				radiusGrown = false;
				break;
			}
			case Shot.MESSAGE_CRUISE: {
				
				break;
			}
			case Shot.MESSAGE_HIT:
			case Shot.MESSAGE_DESTROY: {
				
				isVisible = false;
				
				// TODO Remove ?
				Foundation.ACTIVITY.post(new Runnable() {
					
					@Override
					public void run() {
						toDestroy();
					}
					
				});
	
				break;
			}
		}
	}

	@Override
	// TODO Finish
	protected Rectangle getEdge() {
		
		int x = CoordinateConverter.toPixelX(getX());
		int y = CoordinateConverter.toPixelY(getY());
		
		int width = (int) (radiusEffect.getWidth() * radiusSize);
		int height = (int) (radiusEffect.getHeight() * radiusSize);
		
		// In case of Fire Radius is lower than CoreBall
		width = Math.max(width, coreBall.getWidth());
		height = Math.max(height, coreBall.getHeight());
		
		return new Rectangle(x - (width / 2), y - (height / 2), width, height);
	}

	private void drawCoreBall(Graphics graphics) {
		
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x) - coreBall.getWidth() / 2;
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y) - coreBall.getHeight() / 2;
		
		graphics.draw(coreBall, x, y, getAngle());
	}
	
	private void drawRadiusEffect(Graphics graphics, int random) {
		
		int width = (int) (radiusEffect.getWidth() * radiusSize);
		int height = (int) (radiusEffect.getHeight() * radiusSize);
		
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x) - (width / 2);
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y) - (height / 2);
		
		graphics.draw(radiusEffect, x, y, x + width, y + height, random);
		
	}
	
	private float getRadiusEffectSize() {
		
		long time = timer;
		
		if(time > FIREBALL_SPEED) {
			time = FIREBALL_SPEED;
		}
		
		return ((1.0f - MINIMAL_RADIUS) * (((float) time / FIREBALL_SPEED))) + MINIMAL_RADIUS;
		
	}
}
