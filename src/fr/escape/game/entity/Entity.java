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

import org.jbox2d.dynamics.Body;
import java.awt.Graphics2D;

import org.jbox2d.dynamics.World;

import fr.escape.app.Graphics;

/**
 * <p>
 * An Entity is a Components in the {@link World}.
 * 
 * <p>
 * <b>Note:</b> This is an abstract concept.
 * 
 */
public interface Entity {

	/**
	 * <p>
	 * Update the state of the {@link Entity}.
	 * 
	 * <p>
	 * This {@link Entity} can use the given {@link Graphics2D} if it's a {@link Drawable}.
	 * 
	 * @param graphics {@link Graphics2D} which can be used for drawing this {@link Entity}.
	 * @param delta Time elapsed since the last update
	 */
	public void update(Graphics graphics, long delta);
	
	public Body getBody();
	
}
