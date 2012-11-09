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

package fr.escape.game.entity;

import org.jbox2d.dynamics.World;

import fr.escape.app.Foundation;
import fr.escape.app.Graphics;

/**
 * <p>
 * A static class which convert some {@link Graphics} Coordinate to {@link World} Coordinate for {@link Entity}, and vice versa.
 * 
 */
public final class CoordinateConverter {
	
	private static final float COEFF = Math.max(Foundation.GRAPHICS.getHeight(), Foundation.GRAPHICS.getWidth());
	
	/**
	 * You cannot instantiate this Class
	 */
	private CoordinateConverter() {}
	
	/**
	 * Convert a {@link Graphics} Coordinate to {@link World} Coordinate. 
	 * 
	 * @param x Graphics Coordinate
	 * @return World Coordinate
	 */
	public static float toMeter(int x) {
		return x / COEFF * 10;
	}
	
	/**
	 * Convert a {@link World} Coordinate to {@link Graphics} Coordinate.
	 * 
	 * @param x World Coordinate
	 * @return Graphics Coordinate
	 */
	public static int toPixel(float x) {
		return (int)(x /10 * COEFF);
	}
	
}
