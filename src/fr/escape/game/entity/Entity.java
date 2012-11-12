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
import org.jbox2d.dynamics.Body;

/**
 * <p>
 * An Entity is a Components in the {@link World}.
 * 
 * <p>
 * <b>Note:</b> This is an abstract concept.
 * 
 */
public interface Entity extends Updateable, Collisionable {
	
	public Body getBody();
	
	public void toDestroy();
	
}
