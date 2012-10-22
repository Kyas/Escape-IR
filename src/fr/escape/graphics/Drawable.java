package fr.escape.graphics;

import java.awt.Graphics2D;

public interface Drawable {
	
	public void draw(final Graphics2D graphics, 
			final int x, final int y, final int width, final int height, 
			final int srcX, final int srcY, final int srcWidth, final int srcHeight);
	
}
