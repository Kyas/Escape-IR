package fr.escape.game.entity.weapons.shot;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public final class ShotFactory {
	
	private static final Texture MISSILE_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_MISSILE_SHOT);
	private static final Texture SHIBOLEET_SHOT_TEXTURE = Foundation.resources.getTexture(TextureLoader.WEAPON_SHIBOLEET_SHOT);

	public static Shot createMissileShot() {
		return new AbstractShot(MISSILE_SHOT_TEXTURE, new EntityContainer()) {

			@Override
			public void receive(int message) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	public static Shot createShiboleetShot() {
		
		return new AbstractShot(SHIBOLEET_SHOT_TEXTURE, new EntityContainer()) {

			@Override
			public void receive(int message) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
}
