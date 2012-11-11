package fr.escape.game.entity.weapons.shot;

import java.awt.Rectangle;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public class ShiboleetShot extends AbstractShot {
	
	private final Texture coreShiboleet;
	
	private boolean isVisible;
	private long timer;

	public ShiboleetShot(Body body, EdgeNotifier edgeNotifier, KillNotifier killNotifier) {
		super(body, edgeNotifier, killNotifier);

		this.coreShiboleet = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);
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

	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {
			drawCoreShiboleet(graphics);
			//graphics.draw(getEdge(), Color.RED);
		}
	}

	private void drawCoreShiboleet(Graphics graphics) {
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x);
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y);
		
		graphics.draw(coreShiboleet, x, y, getAngle());
	}

	@Override
	public void update(Graphics graphics, long delta) {
		timer += delta;
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
