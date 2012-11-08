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
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * 
 * <p>
 * This class is Immutable.
 */
// TODO Comment
public final class Texture {

	private final BufferedImage image;
	
	public Texture(File file) throws IOException {
		Objects.requireNonNull(file);
		image = ImageIO.read(file);
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	private Image getImage() {
		return image;
	}

	public void draw(Graphics2D graphics, int x, int y, int width, int height,
			int srcX, int srcY, int srcWidth, int srcHeight) {
		
		draw(graphics, x, y, width, height, srcX, srcY, srcWidth, srcHeight, 0.);
	}
	
	public void draw(Graphics2D graphics, int x, int y, int width, int height,
			int srcX, int srcY, int srcWidth, int srcHeight, double angle) {

		AffineTransform transformMatrix = null;
		boolean updateMatrix = false;
		
		// Create a Transform Matrix if we need to apply a rotation on Texture
		if(angle != 0) {
			
			transformMatrix = graphics.getTransform();
			updateMatrix = true;
			
			AffineTransform rotationMatrix = new AffineTransform();
			
			// Create Transform Matrix
			rotationMatrix.rotate(Math.toRadians(angle), (x + width) / 2, (y + height) / 2);
			
			// Apply Transform Matrix
			graphics.setTransform(rotationMatrix);

		}
        
		graphics.drawImage(getImage(), x, y, width, height, srcX, srcY, srcWidth, srcHeight, null);
		
		// Restore Previous Matrix
		if(updateMatrix) {
			assert transformMatrix != null;
			graphics.setTransform(transformMatrix);
		}
		
	}
	
}
