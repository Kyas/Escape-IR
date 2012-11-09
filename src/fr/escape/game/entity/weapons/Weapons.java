package fr.escape.game.entity.weapons;

import java.util.ArrayList;
import java.util.List;

import fr.escape.graphics.Texture;

public class Weapons {

	public static final int MISSILE_ID = 0;
	public static final int FIREBALL_ID = MISSILE_ID + 1;
	public static final int SHIBOLEET_ID = FIREBALL_ID + 1;
	public static final int BLACKHOLE_ID = SHIBOLEET_ID + 1;
	
	private static final int WIDTH = 40;
	private static final int HEIGHT = 40;
	
	public static List<Weapon> createListOfWeapons() {
		
		List<Weapon> list = new ArrayList<>(4);
		
		Weapon wB = new BlackHole(4);
		Weapons.validate(wB);
		
		Weapon wF = new FireBall(50);
		Weapons.validate(wF);
		
		Weapon wS = new Shiboleet(10);
		Weapons.validate(wS);
		
		Weapon wM = new Missile();
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
