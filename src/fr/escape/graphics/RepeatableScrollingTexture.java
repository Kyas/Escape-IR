/*****************************************************************************
 * 
 * Copyright 2012 See AUTHORS file.
 * 
 * This file is part of Escape-IR.
 * 
 * Escape-IR is free software: you can redistribute it and/or modify
 * it under the terms of the zlib license. See the COPYING file.
 * 
 *****************************************************************************/

package fr.escape.graphics;

import java.awt.Graphics2D;

/**
 * <p>
 * A {@link TextureOperator} which handle repeatable scroll features for a Texture.
 * 
 * <p>
 * For scrolling, use {@link ScrollingTexture#setXPercent(float)} or {@link ScrollingTexture#setYPercent(float)}
 * for X or Y axis. Percent must be between 0 and 1. If you want to progress by 1%, call these with lastPercent+0.01.
 * 
 * @see TextureOperator
 * @see ScrollingTexture
 */
public final class RepeatableScrollingTexture extends ScrollingTexture {

	/**
	 * A Repeatable ScrollingTexture with a given Texture.
	 * 
	 * @see ScrollingTexture#ScrollingTexture(Texture)
	 * @param texture Texture to use.
	 */
	public RepeatableScrollingTexture(Texture texture) {
		super(texture);
	}
	
	/**
	 * Draw the RepeatableScrollingTexture in 4 part :
	 * 
	 * +-------+--------+
	 * |   1   |    2   |
	 * +-------+--------+
	 * |   3   |    4   |
	 * +-------+--------+
	 * 
	 * Part 1 Normal Texture
	 * Part 2 Beginning of the Texture in X-axis
	 * Part 3 Beginning of the Texture in Y-axis 
	 * Part 4 The leftover.
	 * 
	 * @see TextureOperator#draw(Graphics2D, int, int, int, int);
	 */
	@Override
	public void draw(Graphics2D graphics, int x, int y, int width, int height) {
		
		/**
		 * Check if we can draw safely without scaling.
		 */
		// TODO Implements Scaling
		if(width > getTexture().getWidth()) {
			throw new IllegalArgumentException("Scale is not implemented for RepeatableScrollingTexture, drawing width must be lesser than Texture width");
		}
		if(height > getTexture().getHeight()) {
			throw new IllegalArgumentException("Scale is not implemented for RepeatableScrollingTexture, drawing height must be lesser than Texture height");
		}
		
		/**
		 * Compute Texture Width Area
		 */
		int srcX = (int) (getXPercent() * getTexture().getWidth());
		int srcWidth = srcX + width;
		
		/**
		 * Check Width boundary.
		 */
		if(srcWidth > getTexture().getWidth()) {
			srcWidth = getTexture().getWidth();
		}
		
		/**
		 * Compute Texture Height Area
		 */
		int srcY = (int) (getYPercent() * getTexture().getHeight());
		int srcHeight = srcY + height;
		
		/**
		 * Check Height boundary.
		 */
		if(srcHeight > getTexture().getHeight()) {
			srcHeight = getTexture().getHeight();
		}
		
		/**
		 * Compute what is drawn
		 */
		int deltaHeight = srcHeight - srcY;
		int deltaWidth = srcWidth - srcX; 
		
		/**
		 * Draw the Part 1 Texture.
		 */
		getTexture().draw(graphics, x, y, deltaWidth, deltaHeight, srcX, srcY, srcWidth, srcHeight);
		
		/**
		 * Compute Texture Width Area for Part 2
		 */
		int srcX2 = 0;
		int srcWidth2 = width - deltaWidth;
		
		/**
		 * Draw the Part 2 Texture.
		 */
		getTexture().draw(graphics, deltaWidth, y, width, deltaHeight, srcX2, srcY, srcWidth2, srcHeight);
		
		/**
		 * Compute Texture Height Area for Part 3
		 */
		int srcY3 = 0;
		int srcHeight3 = height - deltaHeight;
		
		/**
		 * Draw the Part 3 Texture.
		 */
		getTexture().draw(graphics, x, deltaHeight, deltaWidth, height, srcX, srcY3, srcWidth, srcHeight3);
		
		/**
		 * Draw the Final Part, the Part 4 Texture. 
		 */
		getTexture().draw(graphics, deltaWidth, deltaHeight, width, height, srcX2, srcY3, srcWidth2, srcHeight3);
	}
	
	/**
	 * Tiny Wrapper for RepeatableScrollingTexture :
	 * 100% == 0% ( We reach the end )
	 * 
	 * @see ScrollingTexture#setXPercent(float)
	 */
	public void setXPercent(float percent) {
		super.setXPercent(percent % 1.0f);
	}
	
	/**
	 * Tiny Wrapper for RepeatableScrollingTexture :
	 * 100% == 0% ( We reach the end )
	 * 
	 * @see ScrollingTexture#setXPercent(float)
	 */
	public void setYPercent(float percent) {
		super.setYPercent(percent % 1.0f);
	}

}
