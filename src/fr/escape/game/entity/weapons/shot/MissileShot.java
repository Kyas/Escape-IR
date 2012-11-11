package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public class MissileShot extends AbstractShot {
	
	private final Texture coreMissile;
	
	private boolean isVisible;

	public MissileShot(Body body, EntityContainer container) {
		super(body, container, container);
		
		this.coreMissile = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_MISSILE_SHOT);
		this.isVisible = false;
	}

	@Override
	public void receive(int message) {
		switch(message) {
			case Shot.MESSAGE_LOAD: {
				isVisible = true;
				break;
			}
			case Shot.MESSAGE_FIRE: {
				getBody().getFixtureList().m_filter.maskBits = 0x0002 | 0x0004 | 0x000F;
				break;
			}
			case Shot.MESSAGE_CRUISE: {
				
				break;
			}
			case Shot.MESSAGE_HIT:
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

	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {
			drawCoreMissile(graphics);
			//graphics.draw(getEdge(), Color.RED);
		}
	}

	private void drawCoreMissile(Graphics graphics) {
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x) - coreMissile.getWidth() / 2;
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y) - coreMissile.getHeight() / 2;
		
		graphics.draw(coreMissile, x, y, getAngle());
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
		
		return new Rectangle(x - (coreMissile.getWidth() / 2), y - (coreMissile.getHeight() / 2), coreMissile.getWidth(), coreMissile.getHeight());
	}

}
