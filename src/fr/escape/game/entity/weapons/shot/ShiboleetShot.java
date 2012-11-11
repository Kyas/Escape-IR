package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public class ShiboleetShot extends AbstractShot {
	
	private final Texture coreShiboleet;
	private final EntityContainer entityContainer;
	
	private boolean isVisible;

	public ShiboleetShot(Body body, EntityContainer container) {
		super(body, container, container);

		this.coreShiboleet = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);
		this.entityContainer = container;
		this.isVisible = false;
	}

	@Override
	public void receive(int message) {
		switch(message) {
			case Shot.MESSAGE_LOAD: {
				isVisible = true;
				getBody().setActive(false);
				break;
			}
			case Shot.MESSAGE_FIRE: {
				explode();
				receive(MESSAGE_DESTROY);
				break;
			}
			case Shot.MESSAGE_CRUISE: {
				
				break;
			}
			case Shot.MESSAGE_HIT: {
	
				break;
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
		}
	}

	private void explode() {
		System.err.println("Explode Motherfucker");
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
