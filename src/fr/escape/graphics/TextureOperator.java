package fr.escape.graphics;

import java.awt.Graphics2D;

/**
 * <p>
 * An interface for drawing complicated Texture
 * 
 */
public interface TextureOperator {
	
	/**
	 * <p>
	 * Draw a rectangle with the top left corner at x,y having the given width and height in pixels. 
	 * 
	 * <p>
	 * The portion of the Texture is defined by the implementation of this interface.
	 * 
	 * @param graphics Use this Graphics2D for drawing.
	 * @param x Starting Position X in Display Screen.
	 * @param y Starting Position Y in Display Screen.
	 * @param width Ending Position X in Display Screen.
	 * @param height Ending Position Y in Display Screen.
	 */
	public void draw(final Graphics2D graphics, final int x, final int y, final int width, final int height);
}
