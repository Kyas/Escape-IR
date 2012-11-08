package fr.escape.game.entity;

import fr.escape.app.Foundation;

public final class CoordinateConverter {
	
	private static final float COEFF = Math.max(Foundation.GRAPHICS.getHeight(),Foundation.GRAPHICS.getWidth());
	
	public static float toMeter(int x) {
		return x / COEFF * 10;
	}
	
	public static int toPixel(float x) {
		return (int)(x /10 * COEFF);
	}
	
}
