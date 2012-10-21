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
 * A {@link TextureOperator} which handle scroll features for a Texture.
 * 
 * <p>
 * For scrolling, use {@link ScrollingTexture#setXPercent(float)} or {@link ScrollingTexture#setYPercent(float)}
 * for X or Y axis. Percent must be between 0 and 1. If you want to progress by 1%, call these with lastPercent+0.01.
 * 
 * @see TextureOperator
 */
public class ScrollingTexture implements TextureOperator {

	/**
	 * Texture used for rendering
	 */
	private final Texture texture;
	
	/**
	 * Percent of scrolling in X axis.
	 */
	private float percentX;
	
	/**
	 * Percent of scrolling in Y axis.
	 */
	private float percentY;
	
	/**
	 * A Scrolling Texture with a given Texture.
	 * 
	 * @param texture Texture to use.
	 */
	public ScrollingTexture(Texture texture) {
		this.texture = texture;
		this.percentX = 0;
		this.percentY = 0;
	}

	/**
	 * <p>
	 * Draw a Scrollable Texture defined by {@link ScrollingTexture#setXPercent(float)} 
	 * and {@link ScrollingTexture#setYPercent(float)}
	 * 
	 * @see TextureOperator#draw(Graphics2D, int, int, int, int);
	 */
	@Override
	public void draw(Graphics2D graphics, int x, int y, int width, int height) {
		
		/**
		 * Compute and Check Drawing Area
		 */
		int maxHeight = texture.getHeight() - height; 
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
	
	/**
	 * Define the percent of Scrolling in the Texture for X-Axis 
	 * 
	 * @param percent Percent of Scroll in X-Axis
	 */
	public void setXPercent(float percent) {
		
		if(percent < 0f || percent > 1.0f) {
			throw new IllegalArgumentException("Percent must be between 0 and 1 included");
		}
		
		this.percentX = percent;
	}
	
	/**
	 * Define the percent of Scrolling in the Texture for Y-Axis
	 * 
	 * @param percent Percent of Scroll in Y-Axis
	 */
	public void setYPercent(float percent) {
		
		if(percent < 0f || percent > 1.0f) {
			throw new IllegalArgumentException("Percent must be between 0 and 1 included");
		}

		this.percentY = percent;
	}
	
	/**
	 * Get the percent of scrolling defined for X-Axis.
	 * 
	 * @return Percent of scrolling defined for X-Axis
	 */
	public float getYPercent() {
		return this.percentY;
	}
	
	/**
	 * Get the percent of scrolling defined for Y-Axis.
	 * 
	 * @return Percent of scrolling defined for Y-Axis
	 */
	public float getXPercent() {
		return this.percentX;
	}

	/**
	 * Return the Texture.
	 * 
	 * @return Texture used for Rendering
	 */
	protected Texture getTexture() {
		return texture;
	}
	
}
