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

/**
 * <p>
 * An interface for Moveable Entity.
 * 
 */
public interface Moveable {

	/**
	 * Move the Entity with the given value for X-Axis and Y-Axis. 
	 * 
	 * @param x Move the Entity on X-Axis.
	 * @param y Move the Entity on Y-Axis.
	 */
	//public void moveBy(int x, int y);
	
	/**
	 * Rotate the Entity with the given angle. 
	 * 
	 * @param angle Rotate the Entity.
	 */
	public void rotateBy(int angle);
	
	/**
	 * Set the Position of the Entity in X-Axis and Y-Axis.
	 * 
	 * @param x X Position on axis.
	 * @param y Y Position on axis.
	 */
	public void moveTo(float x, float y);
	
	/**
	 * 
	 * @param velocity
	 */
	public void moveBy(float[] velocity);
	
	/**
	 * Set the Rotation of the Entity in degree.
	 * 
	 * @param angle Angle in Degree.
	 */
	public void setRotation(int angle);
	
}
