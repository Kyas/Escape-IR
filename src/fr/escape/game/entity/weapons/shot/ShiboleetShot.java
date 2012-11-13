package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public class ShiboleetShot extends AbstractShot {
	
	private final Texture coreShiboleet;
	private final EntityContainer entityContainer;
	private final ShotFactory shotFactory;
	
	private boolean isVisible;

	public ShiboleetShot(Body body, EntityContainer container, ShotFactory factory) {
		super(body, container, container,1);

		this.coreShiboleet = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);
		this.entityContainer = Objects.requireNonNull(container);
		this.shotFactory = Objects.requireNonNull(factory);
		this.isVisible = false;
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
	
				break;
			}
			case Shot.MESSAGE_DESTROY: {
				
				isVisible = true;
				
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

	private void explode() {
		
		System.err.println("Explode Motherfucker");
		int mask = getBody().getFixtureList().m_filter.maskBits;
		boolean isPlayer = (mask != PLAYER_TYPE);
		System.err.println(mask);
		
		Shot s1 = createChild();
		System.err.println("s1:"+s1);
		Shot s2 = createChild();
		Shot s3 = createChild();
		Shot s4 = createChild();
	
		// TODO Compute Angle
		s1.setFireMask(isPlayer);
		s1.getBody().setLinearVelocity(new Vec2(0.0f, -1.0f));
		s2.setFireMask(isPlayer);
		s2.getBody().setLinearVelocity(new Vec2(0.0f, -2.0f));
		s3.setFireMask(isPlayer);
		s3.getBody().setLinearVelocity(new Vec2(0.0f, -3.0f));
		s4.setFireMask(isPlayer);
		s4.getBody().setLinearVelocity(new Vec2(0.0f, -4.0f));
		/*s2.moveTo(4.0f, 0.0f);
		s3.moveTo(6.0f, 0.0f);
		s4.moveTo(8.0f, 0.0f);*/
		
		s1.receive(MESSAGE_CRUISE);
		s2.receive(MESSAGE_CRUISE);
		s3.receive(MESSAGE_CRUISE);
		s4.receive(MESSAGE_CRUISE);
		
		entityContainer.push(s1);
		entityContainer.push(s2);
		entityContainer.push(s3);
		entityContainer.push(s4);
		
	}
	
	private Shot createChild() {
		return shotFactory.createShiboleetShot(getX(), getY());
	}

	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {
			drawCoreShiboleet(graphics);
			graphics.draw(getEdge(), Color.RED);
		}
	}

	private void drawCoreShiboleet(Graphics graphics) {
		
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x) - (coreShiboleet.getWidth() / 2);
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y) - (coreShiboleet.getWidth() / 2);
		
		graphics.draw(coreShiboleet, x, y, getAngle());
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
		int x = CoordinateConverter.toPixelX(getX());
		int y = CoordinateConverter.toPixelY(getY());
		
		return new Rectangle(x - (coreShiboleet.getWidth() / 2), y - (coreShiboleet.getHeight() / 2), coreShiboleet.getWidth(), coreShiboleet.getHeight());
	}

}
