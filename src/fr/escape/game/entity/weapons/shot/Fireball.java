package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class Fireball extends AbstractShot {

	private static final int FIREBALL_SPEED = 4000;
	
	private final Texture coreBall;
	private final Texture radiusEffect;
	
	private boolean isVisible;
	private boolean radiusGrown;
	private float radiusSize;
	private long timer;
	
	public Fireball(EdgeNotifier edgeNotifier, KillNotifier killNotifier) {
		super(null,edgeNotifier, killNotifier);
		
		this.coreBall = Foundation.resources.getTexture(TextureLoader.WEAPON_FIREBALL_CORE_SHOT);
		this.radiusEffect = Foundation.resources.getTexture(TextureLoader.WEAPON_FIREBALL_RADIUS_SHOT);
		
		this.isVisible = false;
		this.radiusGrown = false;
		this.radiusSize = 0.5f;
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
		}
	}

	@Override
	public void receive(int message) {
		switch(message) {
		case MESSAGE_LOAD: {
			isVisible = true;
			radiusGrown = true;
			break;
		}
		case MESSAGE_FIRE: {
			radiusGrown = false;
			break;
		}
		case MESSAGE_CRUISE: {
			
			break;
		}
		case MESSAGE_HIT: {

			break;
		}
		case MESSAGE_DESTROY: {
			
			isVisible = false;
			
			Foundation.activity.post(new Runnable() {
				
				@Override
				public void run() {
					destroy();
				}
				
			});

			break;
		}
	}
	}

	@Override
	protected Rectangle getEdge() {
		return new Rectangle(getX() - (coreBall.getWidth() / 2), getY() - (coreBall.getHeight() / 2), coreBall.getWidth(), coreBall.getHeight());
	}

	private void drawCoreBall(Graphics graphics) {
		
		int x = getX() - (coreBall.getWidth() / 2);
		int y = getY() - (coreBall.getHeight() / 2);
		
		graphics.draw(coreBall, x, y, getAngle());
	}
	
	private void drawRadiusEffect(Graphics graphics, int random) {

		int width = (int) (radiusEffect.getWidth() * radiusSize);
		int height = (int) (radiusEffect.getHeight() * radiusSize);
		int x = getX() - (width / 2);
		int y = getY() - (height / 2);
		
		graphics.draw(radiusEffect, x, y, x + width, y + height, random);
	}
	
	private float getRadiusEffectSize() {
		
		long time = timer;
		
		if(time > FIREBALL_SPEED) {
			time = FIREBALL_SPEED;
		}
		
		return (0.5f*(((float) time / FIREBALL_SPEED)))+0.5f;
		
	}
}
