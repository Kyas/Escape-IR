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

package fr.escape.game.screen;

import java.awt.Color;
import java.awt.Font;

import fr.escape.app.Graphics;

/**
 * <p>
 * A static class for helping every implementation of {@link Screen}
 * to rendering easily.
 * 
 */
public final class Screens {

	private Screens() {}
	
	/**
	 * <p>
	 * Draw a String in the given position with a center effect.
	 * 
	 * @param graphics {@link Graphics} to use
	 * @param message String to display
	 * @param x Position X
	 * @param y Position Y
	 * @param font Font to use
	 * @param color Color to use
	 */
	public static void drawStringInCenterPosition(Graphics graphics, String message, int x, int y, Font font, Color color) {
		
		x -= ((message.length() / 2) * (font.getSize() / 2));
		y += (font.getSize() / 4);
		
		graphics.draw(message, x, y, font, color);
	}
	
}
