package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class ShotFactory {
	
	private static final Texture MISSILE_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_MISSILE_SHOT);
	private static final Texture SHIBOLEET_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);

	public static Shot createBlackholeShot(EntityContainer ec) {
		return new AbstractShot(Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT), ec) {
			
			private final Texture coreHelix = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_CORE_SHOT);
			private final Texture leftHelix = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_LEFT_SHOT);
			private final Texture rightHelix = Foundation.resources.getTexture(TextureLoader.WEAPON_BLACKHOLE_RIGHT_SHOT);
			
			private long time = 0;
			private Rectangle edge = new Rectangle(0, 0);
			
			@Override
			public void receive(int message) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void draw(Graphics graphics) {
				
				int x;
				int y;
				int angle;
				
				int offset;
				
				offset = Math.max(coreHelix.getWidth(), 0);
				offset = Math.max(coreHelix.getHeight(), offset);
				offset = Math.max(leftHelix.getWidth(), offset);
				offset = Math.max(leftHelix.getHeight(), offset);
				offset = Math.max(rightHelix.getWidth(), offset);
				offset = Math.max(rightHelix.getHeight(), offset);
				
				x = getX() - (coreHelix.getWidth() / 2);
				y = (getY() - (coreHelix.getHeight() / 2));
				
				graphics.draw(coreHelix, x, y, getAngle());

				x = getX() - (leftHelix.getWidth() / 2);
				y = (getY() - (leftHelix.getHeight() / 2));
				angle = (int) (((float) time / 600) * 360);
				
				graphics.draw(leftHelix, x, y, angle);
				
				x = getX() - (rightHelix.getWidth() / 2);
				y = (getY() - (rightHelix.getHeight() / 2));
				angle = 360 - angle;

				graphics.draw(rightHelix, x, y, angle);
				
				edge = new Rectangle(getX() - offset / 2, getY() - offset / 2, offset, offset);
				graphics.draw(edge, Color.RED);
			}
			
			@Override
			protected Rectangle getEdge() {
				return edge;
			}
			
			@Override
			public void update(Graphics graphics, long delta) {

				draw(graphics);
				
				time += delta;
				System.out.println(time);
				
				Foundation.activity.post(new Runnable() {
					
					@Override
					public void run() {
						moveBy(0, -1);
					}

				});
			}
			
		};
	}
	
	
	public static Shot createMissileShot(EntityContainer ec) {
		return new AbstractShot(MISSILE_SHOT_TEXTURE, ec) {

			@Override
			public void receive(int message) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void update(Graphics graphics, long delta) {
				
				draw(graphics);
				
				Foundation.activity.post(new Runnable() {
					
					@Override
					public void run() {
						moveBy(0, 3);
						rotateBy(1);
					}

				});
			}
			
		};
	}
	
	public static Shot createShiboleetShot(EntityContainer ec) {
		
		return new AbstractShot(SHIBOLEET_SHOT_TEXTURE, ec) {

			@Override
			public void receive(int message) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void update(Graphics graphics, long delta) {
				
				draw(graphics);
				
				Foundation.activity.post(new Runnable() {
					
					@Override
					public void run() {
						moveBy(0, 3);
						rotateBy(1);
					}

				});
			}
			
		};
	}
}
