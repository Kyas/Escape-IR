package fr.escape.weapons;

import fr.escape.graphics.Texture;

public class Weapons {

	private static final int WIDTH = 40;
	private static final int HEIGHT = 40;
	
	public static void validate(Weapon w) {
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
