package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Objects;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

//TODO Comment
public class ShiboleetShot extends AbstractShot {
	
	static final float CHILD_RADIUS = 0.70f;
	
	private final Texture coreShiboleet;
	private final EntityContainer entityContainer;
	private final ShotFactory shotFactory;
	
	private boolean isVisible;
	private boolean isChild;

	public ShiboleetShot(Body body, boolean isChild, EntityContainer container, ShotFactory factory) {
		super(body, container, container,1);

		this.coreShiboleet = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);
		this.entityContainer = Objects.requireNonNull(container);
		this.shotFactory = Objects.requireNonNull(factory);
		this.isVisible = false;
		this.isChild = isChild;
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
		
		ShiboleetShot s1 = createChild();
		ShiboleetShot s2 = createChild();
		ShiboleetShot s3 = createChild();
		ShiboleetShot s4 = createChild();
	
		// TODO Compute Angle
		s1.setFireMask(isPlayer);
		s2.setFireMask(isPlayer);
		s3.setFireMask(isPlayer);
		s4.setFireMask(isPlayer);
				
		s1.moveTo(2.0f, (isPlayer)?-5.0f:5.0f);
		s2.moveTo(0.75f, (isPlayer)?-5.0f:5.0f);
		s3.moveTo(-0.75f, (isPlayer)?-5.0f:5.0f);
		s4.moveTo(-2.0f, (isPlayer)?-5.0f:5.0f);
		
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
			graphics.draw(getEdge(), Color.RED);
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
