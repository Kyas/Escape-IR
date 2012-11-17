package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;

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
public class JupiterShot extends AbstractShot {
	private final Texture coreJupiterShot;
	
	private boolean isVisible;

	public JupiterShot(Body body, EntityContainer container, ShotCollisionBehavior collisionBehavior) {
		super(body, container, container, collisionBehavior, 2);
		
		this.coreJupiterShot = Foundation.RESOURCES.getTexture(TextureLoader.JUPITER_SPECIAL);
	}

	@Override
	public void receive(int message) {
		switch(message) {
			case Shot.MESSAGE_LOAD: {
				isVisible = true;
				break;
			}
			case Shot.MESSAGE_FIRE: {
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

	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {
			Rectangle area = getEdge();
			graphics.draw(coreJupiterShot, (int) area.getX(), (int) area.getY(), (int) area.getMaxX(), (int) area.getMaxY(), getAngle());
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
		int x = CoordinateConverter.toPixelX(getX());
		int y = CoordinateConverter.toPixelY(getY());
		
		return new Rectangle(x - (coreJupiterShot.getWidth() / 2), y - (coreJupiterShot.getHeight() / 2), coreJupiterShot.getWidth(), coreJupiterShot.getHeight());
	}

}
