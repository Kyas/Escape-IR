package fr.escape.game.entity.weapons;

import java.util.ArrayList;
import java.util.List;

import fr.escape.app.Foundation;
import fr.escape.game.entity.EntityContainer;
import fr.escape.game.entity.weapons.shot.Shot;
import fr.escape.game.entity.weapons.shot.ShotFactory;
import fr.escape.graphics.Texture;
import fr.escape.resources.texture.TextureLoader;

public class Weapons {

	public static final int MISSILE_ID = 0;
	public static final int FIREBALL_ID = MISSILE_ID + 1;
	public static final int SHIBOLEET_ID = FIREBALL_ID + 1;
	public static final int BLACKHOLE_ID = SHIBOLEET_ID + 1;
	
	private static final int WIDTH = 40;
	private static final int HEIGHT = 40;
	
	private static final int MISSILE_DEFAULT_AMMUNITION = 100;
	private static final int FIREBALL_DEFAULT_AMMUNITION = 50;
	private static final int SHIBOLEET_DEFAULT_AMMUNITION = 10;
	private static final int BLACKHOLE_DEFAULT_AMMUNITION = 4;
	
	private Weapons() {}
	
	public static List<Weapon> createListOfWeapons(EntityContainer entityContainer, ShotFactory shotFactory) {
		
		List<Weapon> list = new ArrayList<>(4);
		
		Weapon wB = new AbstractShot(
				Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_BLACKHOLE), 
				entityContainer, shotFactory, BLACKHOLE_DEFAULT_AMMUNITION) {
			
			@Override
			protected Shot createShot(float x, float y) {
				return getFactory().createBlackholeShot(x, y);
			}
			
		};
		Weapons.validate(wB);
		
		Weapon wF = new AbstractShot(
				Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_FIREBALL), 
				entityContainer, shotFactory, FIREBALL_DEFAULT_AMMUNITION) {
			
			@Override
			protected Shot createShot(float x, float y) {
				return getFactory().createFireBallShot(x, y);
			}
			
		};
		Weapons.validate(wF);
		
		Weapon wS = new AbstractShot(
				Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_SHIBOLEET), 
				entityContainer, shotFactory, SHIBOLEET_DEFAULT_AMMUNITION) {
			
			@Override
			protected Shot createShot(float x, float y) {
				return getFactory().createShiboleetShot(x, y);
			}
			
		};
		Weapons.validate(wS);
		
		Weapon wM = new AbstractShot(
				Foundation.RESOURCES.getTexture(TextureLoader.WEAPON_MISSILE), 
				entityContainer, shotFactory, MISSILE_DEFAULT_AMMUNITION) {
			
			@Override
			protected Shot createShot(float x, float y) {
				return getFactory().createMissileShot(x, y);
			}
			
		};
		Weapons.validate(wM);
		
		list.add(wM);
		list.add(wF);
		list.add(wS);
		list.add(wB);
		
		return list;
	}
	
	private static void validate(Weapon w) {
		checkDrawableFormat(w.getDrawable());
	}
	
	private static void checkDrawableFormat(Texture drawable) {
		
		if(drawable.getWidth() != WIDTH) {
			throw new IllegalArgumentException("Drawable width must be equals to "+WIDTH);
		}
		
		if(drawable.getHeight() != HEIGHT) {
			throw new IllegalArgumentException("Drawable height must be equals to "+HEIGHT);
		}
	}
	
	public static int getDrawableWidth() {
		return WIDTH;
	}
	
	public static int getDrawableHeight() {
		return HEIGHT;
	}
	
}
