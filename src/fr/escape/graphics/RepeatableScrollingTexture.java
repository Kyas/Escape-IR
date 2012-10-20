package fr.escape.graphics;

import java.awt.Graphics2D;

public class RepeatableScrollingTexture extends ScrollingTexture {

	public RepeatableScrollingTexture(Texture texture) {
		super(texture);
	}
	
	/**
	 * @see TextureOperator#draw(Graphics2D, int, int, int, int);
	 */
	@Override
	public void draw(Graphics2D graphics, int x, int y, int width, int height) {
		
		/**
		 * Compute and Check Drawing Area
		 */
		int maxHeight = getTexture().getHeight() - height; 
		int maxWidth = texture.getWidth() - width;
		
		if(maxHeight < 0) {
			maxHeight = 0;
		}
		
		if(maxWidth < 0) {
			maxWidth = 0;
		}
		
		/**
		 * Compute Texture Width Area
		 */
		int srcX = (int) (percentX * maxWidth);
		int srcWidth = srcX + width;
		
		/**
		 * Check Width boundary.
		 */
		if(srcWidth > texture.getWidth()) {
			srcWidth = texture.getWidth();
		}
		
		/**
		 * Compute Texture Height Area
		 */
		int srcY = (int) (percentY * maxHeight);
		int srcHeight = srcY + height;
		
		/**
		 * Check Height boundary.
		 */
		if(srcHeight > texture.getHeight()) {
			srcHeight = texture.getHeight();
		}
		
		/**
		 * Draw the Texture.
		 */
		graphics.drawImage(texture.getImage(), x, y, width, height, srcX, srcY, srcWidth, srcHeight, null);
	}
	
	// TODO May need to remove
	public void setXPercent(float percent) {
		super.setXPercent(percent % 1);
	}
	
	// TODO May need to remove
	public void setYPercent(float percent) {
		super.setXPercent(percent % 1);	
	}

}
