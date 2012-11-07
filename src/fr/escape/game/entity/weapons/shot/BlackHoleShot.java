package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class BlackHoleShot extends AbstractShot {

	private static final int ROTATION_SPEED = 600;
	private static final int EVENT_HORIZON_TIME = 5000;
	private static final int EVENT_HORIZON_SPEED = 1000;
	
	private final Texture coreHelix;
	private final Texture leftHelix;
	private final Texture rightHelix;
	private final Texture eventHorizon;
	
	private boolean isVisible;
	private boolean drawCoreHelix;
	private boolean drawLeftAndRightHelix;
	private boolean drawEventHorizon;
	
	private long timer;
	
	public BlackHoleShot(Body body,EdgeNotifier edgeNotifier, KillNotifier killNotifier) {
		super(body,edgeNotifier, killNotifier);
		
		this.isVisible = false;
		this.drawCoreHelix = false;
		this.drawLeftAndRightHelix = false;
		this.drawEventHorizon = false;
		
		this.coreHelix = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT);
		this.leftHelix = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_LEFT_SHOT);
		this.rightHelix = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_RIGHT_SHOT);
		this.eventHorizon = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_EVENT_HORIZON_SHOT);
		
	}

	@Override
	public void receive(int message) {
		switch(message) {
			case MESSAGE_LOAD: {
				isVisible = true;
				drawCoreHelix = true;
				break;
			}
			case MESSAGE_FIRE: {
				drawLeftAndRightHelix = true;
				break;
			}
			case MESSAGE_CRUISE: {
				
				break;
			}
			case MESSAGE_HIT: {
				drawLeftAndRightHelix = false;
				drawCoreHelix = false;
				drawEventHorizon = true;
				timer = 0;
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
	public void update(Graphics graphics, long delta) {
		
		timer += delta;
		draw(graphics);
		
		if(drawEventHorizon && timer > ((EVENT_HORIZON_SPEED * 2) + EVENT_HORIZON_TIME)) {
			receive(AbstractShot.MESSAGE_DESTROY);
		}
		
	}
	
	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {

			if(drawLeftAndRightHelix) {
				drawLeftAndRightHelix(graphics);
			}
			if(drawEventHorizon) {
				drawEventHorizon(graphics);
			}
			if(drawCoreHelix) {
				drawCoreHelix(graphics);
			}
			
			graphics.draw(getEdge(), Color.RED);
		}
	}

	private void drawCoreHelix(Graphics graphics) {
		
		int x = getX() - (coreHelix.getWidth() / 2);
		int y = getY() - (coreHelix.getHeight() / 2);
		
		graphics.draw(coreHelix, x, y, getAngle());
	}
	
	private void drawLeftAndRightHelix(Graphics graphics) {
		
		int x = getX() - (leftHelix.getWidth() / 2);
		int y = getY() - (leftHelix.getHeight() / 2);
		int angle = (int) (((float) timer / ROTATION_SPEED) * 360);
		
		graphics.draw(leftHelix, x, y, angle);
		
		x = getX() - (rightHelix.getWidth() / 2);
		y = getY() - (rightHelix.getHeight() / 2);
		angle = 360 - angle;
		
		graphics.draw(rightHelix, x, y, angle);
	}
	
	private void drawEventHorizon(Graphics graphics) {
		
		
		float size = getEventHorizonSize();
		
		System.out.println(size);
		
		int width = (int) (eventHorizon.getWidth() * size);
		int height = (int) (eventHorizon.getHeight() * size);
		int x = getX() - (width / 2);
		int y = getY() - (height / 2);
		
		graphics.draw(eventHorizon, x, y, x + width, y + height);
	}
	
	private float getEventHorizonSize() {
		
		long time = timer;
		
		if(timer > EVENT_HORIZON_SPEED + EVENT_HORIZON_TIME) {

			time = EVENT_HORIZON_SPEED - (timer - (EVENT_HORIZON_SPEED + EVENT_HORIZON_TIME));
			time = (time < 0)?0:time;

		}

		if(time <= EVENT_HORIZON_SPEED) {
			return ((float) time / EVENT_HORIZON_SPEED);
		} 
		
		return 1.0f;
	}

	@Override
	protected Rectangle getEdge() {
		
		if(drawLeftAndRightHelix) {
			
			int offset = leftHelix.getWidth();
			offset = Math.max(leftHelix.getHeight(), offset);
			offset = Math.max(rightHelix.getWidth(), offset);
			offset = Math.max(rightHelix.getHeight(), offset);
			
			return new Rectangle(getX() - (offset / 2), getY() - (offset / 2), offset, offset);
			
		} else if(drawEventHorizon) {
			return new Rectangle(getX() - (eventHorizon.getWidth() / 2), getY() - (eventHorizon.getHeight() / 2), eventHorizon.getWidth(), eventHorizon.getHeight());
		}
		
		return new Rectangle(getX() - (coreHelix.getWidth() / 2), getY() - (coreHelix.getHeight() / 2), coreHelix.getWidth(), coreHelix.getHeight());
	}

	
}
