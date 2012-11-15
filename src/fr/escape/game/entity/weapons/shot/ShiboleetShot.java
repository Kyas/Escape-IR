package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

//TODO Comment
public final class ShiboleetShot extends AbstractShot {
	
	static final float CHILD_RADIUS = 0.35f;
	
	private final Texture coreShiboleet;
	private final EntityContainer entityContainer;
	private final ShotFactory shotFactory;
	
	private boolean isVisible;
	private boolean isChild;

	public ShiboleetShot(Body body, boolean isChild, EntityContainer container, ShotCollisionBehavior collisionBehavior, ShotFactory factory) {
		super(body, container, container, collisionBehavior, 1);

		this.coreShiboleet = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);
		this.entityContainer = Objects.requireNonNull(container);
		this.shotFactory = Objects.requireNonNull(factory);
		this.isVisible = false;
		this.isChild = isChild;
	}
	
	@Override
	public void setPosition(float x, float y) {
		
		float size = CoordinateConverter.toMeterY(Math.max(getHeight(), getWidth()));
		getBody().setTransform(new Vec2(x, y + size), getBody().getAngle());
		
	}

	@Override
	public void receive(int message) {
		switch(message) {
			case Shot.MESSAGE_LOAD: {
				isVisible = true;
				System.err.println("m:"+this);
				break;
			}
			case Shot.MESSAGE_FIRE: {
				explode();
				receive(MESSAGE_DESTROY);
				break;
			}
			case Shot.MESSAGE_CRUISE: {
				isVisible = true;
				break;
			}
			case Shot.MESSAGE_HIT: {
				getBody().setType(BodyType.STATIC);
				getBody().setLinearVelocity(new Vec2(0.0f, 0.0f));
			}
			case Shot.MESSAGE_DESTROY: {
				
				isVisible = false;
				
				Foundation.ACTIVITY.post(new Runnable() {
					
					@Override
					public void run() {
						toDestroy();
					}
					
				});
	
				break;
			}
			default: {
				throw new IllegalArgumentException("Unknown Message: "+message);
			}
		}
	}

	private void explode() {
		
		ShiboleetShot s1 = createChild();
		ShiboleetShot s2 = createChild();
		ShiboleetShot s3 = createChild();
		ShiboleetShot s4 = createChild();
		
		s1.setShotConfiguration(new ShotContext(isPlayer(), getWidth(), getHeight()));
		s2.setShotConfiguration(new ShotContext(isPlayer(), getWidth(), getHeight()));
		s3.setShotConfiguration(new ShotContext(isPlayer(), getWidth(), getHeight()));
		s4.setShotConfiguration(new ShotContext(isPlayer(), getWidth(), getHeight()));
		
		s1.moveBy(new float[] {0.0f, 4.0f, (isPlayer())?-5.0f:5.0f});
		s2.moveBy(new float[] {0.0f, 1.25f, (isPlayer())?-5.0f:5.0f});
		s3.moveBy(new float[] {0.0f, -1.25f, (isPlayer())?-5.0f:5.0f});
		s4.moveBy(new float[] {0.0f, -4.0f, (isPlayer())?-5.0f:5.0f});
		
		s1.receive(MESSAGE_CRUISE);
		s2.receive(MESSAGE_CRUISE);
		s3.receive(MESSAGE_CRUISE);
		s4.receive(MESSAGE_CRUISE);
		
		entityContainer.push(s1);
		entityContainer.push(s2);
		entityContainer.push(s3);
		entityContainer.push(s4);
		
	}
	
	private ShiboleetShot createChild() {
		return (ShiboleetShot) shotFactory.createShiboleetShot(getX(), getY(), true);
	}

	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {
			
			Rectangle area = getEdge();
			
			graphics.draw(coreShiboleet, (int) area.getX(), (int) area.getY(), (int) area.getMaxX(), (int) area.getMaxY(), getAngle());
		}
	}
	
	@Override
	public void update(Graphics graphics, long delta) {
		
		draw(graphics);
		
		if(!getEdgeNotifier().isInside(getEdge())) {
			getEdgeNotifier().edgeReached(this);
		}
	}

	@Override
	protected Rectangle getEdge() {
		
		int cx = CoordinateConverter.toPixelX(getX());
		int cy = CoordinateConverter.toPixelY(getY());
		
		int x;
		int y;
		int width;
		int height;
		
		if(isChild) {
			
			width = (int) (coreShiboleet.getWidth() * CHILD_RADIUS);
			height = (int) (coreShiboleet.getHeight() * CHILD_RADIUS);
			x = CoordinateConverter.toPixelX(getBody().getPosition().x) - (width / 2);
			y = CoordinateConverter.toPixelY(getBody().getPosition().y) - (height / 2);
			
		} else {
			
			x = cx - (coreShiboleet.getWidth() / 2);
			y = cy - (coreShiboleet.getHeight() / 2);
			width = coreShiboleet.getWidth();
			height = coreShiboleet.getWidth();
		}
		
		return new Rectangle(x, y, width, height);
	}

}
