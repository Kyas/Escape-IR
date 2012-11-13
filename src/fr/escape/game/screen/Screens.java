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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.escape.app.Graphics;
import fr.escape.app.Input;
import fr.escape.graphics.Shapes;

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
	
	public static List<Input> drawEventsOnScreen(Graphics graphics, List<Input> events, Color color) {
		return drawEventsOnScreen(graphics, events, color, true);
	}
	
	public static List<Input> drawEventsOnScreen(Graphics graphics, List<Input> events, Color color, boolean filter) {
		
		Input lastInput = null;
		List<Input> render = null;
		
		if(filter) {
			render = new LinkedList<>();	
		}
		
		int booster = (int) (events.size() * 0.1f);
		int index = 0;
		
		Iterator<Input> it = events.iterator();
		
		// Find First Input
		if(it.hasNext()) {
			
			lastInput = it.next();
			
			if(filter) {
				render.add(lastInput);
			}
			
		}
		
		if(booster == 0) {
			booster = 1;
		}
		
		// Draw Line between Two Input 
		while(it.hasNext()) {
			
			Input input = it.next();
		
			if(filter) {
				
				// Create a small booster to skip input
				while(it.hasNext() && index != booster - 1) {
					index = (index + 1) % booster;
					input = it.next();
				}
				
				index = 0;
				render.add(input);
			}
			
			graphics.draw(Shapes.createLine(lastInput.getX(), lastInput.getY(), input.getX(), input.getY()), color);
			lastInput = input;
			
		}

		return render;
	}
	
}
