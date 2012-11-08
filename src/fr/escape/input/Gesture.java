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

package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

/**
 * <p>
 * An interface which represent an User Gesture ingame.
 * 
 */
// TODO Comment
public interface Gesture {
	
	/**
	 * 
	 * 
	 * @param start First Input for this Gesture
	 * @param events
	 * @param end
	 * @param velocity
	 * @return True if the Gesture is detected as successful.
	 */
	public boolean accept(Input start, List<Input> events, Input end, float[] velocity);
}
