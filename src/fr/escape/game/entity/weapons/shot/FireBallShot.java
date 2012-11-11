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

public final class FireBallShot extends AbstractShot {

	private static final int FIREBALL_SPEED = 4000;
	private static final float MINIMAL_RADIUS = 0.3f;
	
	private final Texture coreBall;
	private final Texture radiusEffect;
	
	private boolean isVisible;
	private boolean radiusGrown;
	private float radiusSize;
	private long timer;
	
	public FireBallShot(Body body,EdgeNotifier edgeNotifier, KillNotifier killNotifier) {
		super(body,edgeNotifier, killNotifier);
		
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
			//graphics.draw(getEdge(), Color.RED);
		}
	}

	@Override
	public void update(Graphics graphics, long delta) {
		
		timer += delta;
		draw(graphics);
		
		if(!getEdgeNotifier().isInside(getEdge())) {
			getEdgeNotifier().edgeReached(this);
		}
		
		if(radiusGrown) {
			radiusSize = getRadiusEffectSize();
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
		
		return new Rectangle(x - (coreBall.getWidth() / 2), y - (coreBall.getHeight() / 2), coreBall.getWidth(), coreBall.getHeight());
	}

	private void drawCoreBall(Graphics graphics) {
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x) - coreBall.getWidth() / 2;
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y) - coreBall.getHeight() / 2;
		
		graphics.draw(coreBall, x, y, getAngle());
	}
	
	private void drawRadiusEffect(Graphics graphics, int random) {
		int width = (int) (radiusEffect.getWidth() * radiusSize);
		int height = (int) (radiusEffect.getHeight() * radiusSize);
		
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x) - coreBall.getWidth() / 2;
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y) - coreBall.getHeight() / 2;
		
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
