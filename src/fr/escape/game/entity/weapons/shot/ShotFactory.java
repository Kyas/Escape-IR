package fr.escape.game.entity.weapons.shot;

import java.awt.Color;
import java.awt.Rectangle;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class ShotFactory {
	
	//private static final Texture MISSILE_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_MISSILE_SHOT);
	//private static final Texture SHIBOLEET_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);

	public static Shot createBlackholeShot(EntityContainer ec) {
		return new BlackHoleShot(ec, ec);
	}
	
	
//	public static Shot createMissileShot(EntityContainer ec) {
//		return new AbstractShot(MISSILE_SHOT_TEXTURE, ec) {
//
//			@Override
//			public void receive(int message) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void update(Graphics graphics, long delta) {
//				
//				draw(graphics);
//				
//				Foundation.activity.post(new Runnable() {
//					
//					@Override
//					public void run() {
//						moveBy(0, 3);
//						rotateBy(1);
//					}
//
//				});
//			}
//			
//		};
//	}
//	
//	public static Shot createShiboleetShot(EntityContainer ec) {
//		
//		return new AbstractShot(SHIBOLEET_SHOT_TEXTURE, ec) {
//
//			@Override
//			public void receive(int message) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void update(Graphics graphics, long delta) {
//				
//				draw(graphics);
//				
//				Foundation.activity.post(new Runnable() {
//					
//					@Override
//					public void run() {
//						moveBy(0, 3);
//						rotateBy(1);
//					}
//
//				});
//			}
//			
//		};
//	}
}
