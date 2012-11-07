package fr.escape.app;

public class CoordinateConverter {
	
	private static final float COEFF = Math.max(Foundation.graphics.getHeight(),Foundation.graphics.getWidth());
	
	public static float toMeter(int x) {
		return x / COEFF * 10;
	}
	
	public static int toPixel(float x) {
		return (int)(x /10 * COEFF);
	}
	
}
