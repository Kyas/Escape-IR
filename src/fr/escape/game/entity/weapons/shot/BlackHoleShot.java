package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;

import org.jbox2d.dynamics.Body;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.CoordinateConverter;
import fr.escape.game.entity.notifier.EdgeNotifier;
import fr.escape.game.entity.notifier.KillNotifier;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

// TODO Finish
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
		
		this.coreHelix = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT);
		this.leftHelix = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_LEFT_SHOT);
		this.rightHelix = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_RIGHT_SHOT);
		this.eventHorizon = Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE_EVENT_HORIZON_SHOT);
		
	}

	@Override
	public void receive(int message) {
		switch(message) { 
			case MESSAGE_LOAD: {
				getBody().getFixtureList().getShape().m_radius = CoordinateConverter.toMeterX(coreHelix.getHeight() / 2);
				isVisible = true;
				drawCoreHelix = true;
				break;
			}
			case MESSAGE_FIRE: {
				getBody().getFixtureList().getShape().m_radius = CoordinateConverter.toMeterX(leftHelix.getHeight() / 2);
				drawLeftAndRightHelix = true;
				break;
			}
			case MESSAGE_CRUISE: {
				getBody().getFixtureList().getShape().m_radius = CoordinateConverter.toMeterX(rightHelix.getHeight() / 2);
				break;
			}
			case MESSAGE_HIT: {
				getBody().getFixtureList().getShape().m_radius = CoordinateConverter.toMeterX(eventHorizon.getHeight() / 2);
				drawEventHorizon = true;
				timer = 0;
				break;
			}
			case MESSAGE_DESTROY: {
				
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
		setState(message);
	}

	@Override
	public void update(Graphics graphics, long delta) {
		
		timer += delta;
		draw(graphics);
		
		if(!getEdgeNotifier().isInside(getEdge())) {
			getEdgeNotifier().edgeReached(this);
		}
		
		if(drawEventHorizon && timer > ((EVENT_HORIZON_SPEED * 2) + EVENT_HORIZON_TIME)) {
			receive(Shot.MESSAGE_DESTROY);
		}
		
	}
	
	@Override
	public void draw(Graphics graphics) {
		if(isVisible) {

			if(drawLeftAndRightHelix) {
				drawLeftAndRightHelix(graphics);
			}
			if(drawCoreHelix) {
				drawCoreHelix(graphics);
			}
			if(drawEventHorizon) {
				drawEventHorizon(graphics);
			}
			
			graphics.draw(getEdge(), Color.RED);
		}
	}

	private void drawCoreHelix(Graphics graphics) {
		
		int x = CoordinateConverter.toPixelX(getBody().getPosition().x) - coreHelix.getWidth() / 2;
		int y = CoordinateConverter.toPixelY(getBody().getPosition().y) - coreHelix.getHeight() / 2;
		
		graphics.draw(coreHelix, x, y, getAngle());
	}
	
	private void drawLeftAndRightHelix(Graphics graphics) {
		
		int centerX = CoordinateConverter.toPixelX(getBody().getPosition().x);
		int centerY = CoordinateConverter.toPixelY(getBody().getPosition().y);
		
		int x = centerX - (leftHelix.getWidth() / 2);
		int y = centerY - (leftHelix.getHeight() / 2);
		int angle = (int) (((float) timer / ROTATION_SPEED) * 360);
		
		graphics.draw(leftHelix, x, y, angle);
		
		x = centerX - (rightHelix.getWidth() / 2);
		y = centerY - (rightHelix.getHeight() / 2);
		angle = 360 - angle;
		
		graphics.draw(rightHelix, x, y, angle);
	}
	
	private void drawEventHorizon(Graphics graphics) {

		float size = getEventHorizonSize();
		
		int centerX = CoordinateConverter.toPixelX(getBody().getPosition().x);
		int centerY = CoordinateConverter.toPixelY(getBody().getPosition().y);

		int width = (int) (eventHorizon.getWidth() * size);
		int height = (int) (eventHorizon.getHeight() * size);
		int x = centerX - (width / 2);
		int y = centerY - (height / 2);
		
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
		
		drawLeftAndRightHelix = false;
		drawCoreHelix = false;
		
		return 1.0f;
	}

	@Override
	protected Rectangle getEdge() {
		int x = CoordinateConverter.toPixelX(getX());
		int y = CoordinateConverter.toPixelY(getY());
		
		if(drawEventHorizon) {
			return new Rectangle(x - (eventHorizon.getWidth() / 2), y - (eventHorizon.getHeight() / 2), eventHorizon.getWidth(), eventHorizon.getHeight());
		} else if(drawLeftAndRightHelix) {
			
			int offset = leftHelix.getWidth();
			offset = Math.max(leftHelix.getHeight(), offset);
			offset = Math.max(rightHelix.getWidth(), offset);
			offset = Math.max(rightHelix.getHeight(), offset);
			
			return new Rectangle(x - (offset / 2), y - (offset / 2), offset, offset);
			
		}
		
		return new Rectangle(x - (coreHelix.getWidth() / 2), y - (coreHelix.getHeight() / 2), coreHelix.getWidth(), coreHelix.getHeight());
	}

	
}
